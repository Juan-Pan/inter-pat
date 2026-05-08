package com.examen.cafeteria.repository;

import com.examen.cafeteria.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import com.examen.cafeteria.model.Pedido;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    Boolean existsByNombreAndEstado(String nombre, Estado estado);

    List<Pedido> findByEstado(Estado estado);

    List<Pedido> findByNombre(String nombre);

    List<Pedido> findByProductosNombre(String nombreProducto);
    
}
