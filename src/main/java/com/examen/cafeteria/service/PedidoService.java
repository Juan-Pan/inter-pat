package com.examen.cafeteria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.examen.cafeteria.repository.PedidoRepository;
import com.examen.cafeteria.model.*;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private TransactionalOperator transactionalOperator;

    //TODO 3

    public Pedido crearPedido(Pedido pedido) {

        if (pedidoRepository.existsByNombreAndEstado(pedido.getNombre(), pedido.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "ya tienes otro pedido en estado CREADO");
        } else {

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
        Pedido pedido = pedidoRepository.findById(id).get();
        if (!pedidoRepository.existsById(id))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró un pedido con el id dado");
        }
        if(pedido.getEstado() == Estado.CANCELADO || pedido.getEstado() == Estado.SERVIDO )
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No pueden agregar productos al pedido");
        }
        producto.setPedido(pedido); // te pertenezco (clase hija)
        pedido.getProductos().add(producto); // te añado a mi lista (clase padre)

        pedido.calcularTotal();

        return pedidoRepository.save(pedido);


    }

    public Pedido cambiarEstado(Long id, Estado nuevoEstado) {





            return null;
    }
}
