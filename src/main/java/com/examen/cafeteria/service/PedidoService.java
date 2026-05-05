package com.examen.cafeteria.service;

import org.springframework.stereotype.Service;
import com.examen.cafeteria.repository.PedidoRepository;
import com.examen.cafeteria.model.*;
import java.util.List;

@Service
public class PedidoService {

    //TODO 3
    public Pedido crearPedido(String cliente) {

        return  null;
    }

    public Pedido getPedido(Long id) {
        return null;
    }

    public List<Pedido> getAll() {
        return null;
    }

    public void delete(Long id) {
        return;
    }

    public Pedido addProducto(Long id, Producto producto) {

        return null;
    }

    public Pedido cambiarEstado(Long id, Estado nuevoEstado) {

            return null;
    }
}
