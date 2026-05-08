package com.examen.cafeteria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.examen.cafeteria.repository.PedidoRepository;
import com.examen.cafeteria.model.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    //TODO 3

    public Pedido crearPedido(Pedido pedido) {

        if (pedidoRepository.existsByNombreAndEstado(pedido.getNombre(), Estado.CREADO)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "ya tienes otro pedido en estado CREADO");
        }
        else {

            pedido.setEstado(Estado.CREADO);

            pedido.setTotal(0.0);

            return pedidoRepository.save(pedido);

        }

    }

    public Pedido getPedido(Long id) {
        if (!pedidoRepository.existsById(id))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El pedido no existe");
        }
        else
        {
            return pedidoRepository.findById(id).get(); //mejor para traer pedidos con repositorios
        }

    }

    public List<Pedido> getEstado(Estado estado)
    {
        List<Pedido> pedidos = pedidoRepository.findByEstado(estado);
        if (pedidos.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existes pedidos por el estado: "+ estado);
        }
        return pedidos;
    }

    public List<Pedido> getNombre(String nombreProducto)
    {
        List<Pedido> pedidos = pedidoRepository.findByProductosNombre(nombreProducto);
        if (pedidos.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no hay pedidos con el producto: " + nombreProducto);
        }
        return pedidos;
    }

    public List<Pedido> getAll()
    {
        List<Pedido> pedidos = pedidoRepository.findAll();
        if(pedidos.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay pedidos en la base de datos");
        }
        return  pedidos;
    }

    public void delete(Long id) {
        if (!pedidoRepository.existsById(id))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe un pedido con ese id");
        }
        pedidoRepository.deleteById(id);
    }

    public Pedido addProducto(Long id, Producto producto)
    {

        if (!pedidoRepository.existsById(id))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró un pedido con el id dado");
        }
        Pedido pedido = pedidoRepository.findById(id).get();
        if(pedido.getEstado() == Estado.CANCELADO || pedido.getEstado() == Estado.SERVIDO )
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No pueden agregar productos al pedido");
        }
        producto.setPedido(pedido); // te pertenezco (clase hija)
        pedido.getProductos().add(producto); // te añado a mi lista (clase padre)

        pedido.calcularTotal();

        return pedidoRepository.save(pedido);


    }

    public Pedido cambiarEstado(Long id, Estado nuevoEstado)
    {
        if (!pedidoRepository.existsById(id))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el pedido con el id proporcionado");
        }
        Pedido pedidoCambiado = pedidoRepository.findById(id).get();

        if(pedidoCambiado.getEstado() == nuevoEstado)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El pedido ya esta en ese estado");
        }
        if(nuevoEstado == Estado.EN_PREPARACION && pedidoCambiado.getEstado() == Estado.CREADO)
        {
            pedidoCambiado.setEstado(nuevoEstado);
        }
        else if(pedidoCambiado.getEstado() == Estado.EN_PREPARACION && nuevoEstado == Estado.SERVIDO)
        {
            pedidoCambiado.setEstado(nuevoEstado);
        }
        else if(pedidoCambiado.getEstado() == Estado.CREADO && nuevoEstado == Estado.CANCELADO)
        {
            pedidoCambiado.setEstado(nuevoEstado);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede realizar la trancision porque no es permitida");
        }

            return pedidoRepository.save(pedidoCambiado);
    }
}

/*NOTAS
* Borrar sin hacer la relacion bidireccional
*
* import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional // ¡Crucial! Si falla el paso 2, se deshace el paso 1 automáticamente
    public void delete(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El pedido no existe");
        }

        // 1. Matamos a los hijos primero (Evitamos huérfanos)
        productoRepository.deleteByPedidoId(id);

        // 2. Matamos al padre
        pedidoRepository.deleteById(id);
    }
}
*
* OTRA FORMA MEJOR
* package com.examen.cafeteria.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Producto {

    // ... otros atributos ...

    @ManyToOne
    @JoinColumn(nullable = false, name = "pedido_id")
    @OnDelete(action = OnDeleteAction.CASCADE) // <--- ¡AQUÍ ESTÁ LA MAGIA!
    private Pedido pedido;
}
*
*
* Añadir productos hijos correctamente
* import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired // ¡Inyectamos el repositorio de productos!
    private ProductoRepository productoRepository;

    @Transactional // Si el servidor explota a mitad del método, no se guarda nada
    public Producto addProducto(Long idPedido, Producto producto) {

        // 1. Buscamos el pedido (igual que antes)
        Pedido pedido = pedidoRepository.findById(idPedido)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no existe"));

        if (pedido.getEstado() == Estado.CANCELADO || pedido.getEstado() == Estado.SERVIDO) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede modificar");
        }

        // 2. Relacionamos (El folio conoce a la carpeta)
        producto.setPedido(pedido);

        // 3. ACTUALIZAMOS EL TOTAL (La forma optimizada)
        // En lugar de cargar todos los productos antiguos en la RAM para sumarlos,
        // simplemente cogemos el total que ya tenía el pedido y le sumamos el producto nuevo.
        double subtotalNuevo = producto.getPrecio() * producto.getCantidad();
        pedido.setTotal(pedido.getTotal() + subtotalNuevo);

        // 4. Guardamos cada cosa en su sitio
        pedidoRepository.save(pedido); // Actualiza el total en la tabla Pedido

        // ¡Devolvemos el producto guardado en SU propio repositorio!
        return productoRepository.save(producto);
    }
}
*
* NO OLVIDAR EL REPOSITORY
* public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Si algún día necesitas ver los productos de un pedido, usas esto:
    List<Producto> findByPedidoId(Long idPedido);
}
* */