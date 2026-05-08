package com.examen.cafeteria.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.examen.cafeteria.service.PedidoService;
import com.examen.cafeteria.model.*;

import java.util.List;

//TODO#2
@RestController
public class PedidoController {
    @Autowired
    public PedidoService service;

    private Logger logger = LoggerFactory.getLogger(getClass());


    @PostMapping("/api/pedidos")
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido crear(@RequestBody Pedido pedido ) {

        return service.crearPedido(pedido);
    }

    @GetMapping("/api/pedidos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Pedido getPedidos(@PathVariable Long id)
    {
        return service.getPedido(id);
    }

    @GetMapping("/api/pedidos/estado/{estado}")
    public List<Pedido> getPedidosEstado(@PathVariable Estado estado) {

        return service.getEstado(estado);
    }

    @GetMapping("/api/pedidos/productos/{nombre}")
    public List<Pedido> pedidoPorProducto(@PathVariable String nombre) {


        return service.getNombre(nombre);
    }
    @GetMapping("/api/pedidos/productos")
    public List<Pedido> traerPedidos()
    {
        return service.getAll();
    }

    @DeleteMapping("/api/pedidos/{id}")
    public void delete(@PathVariable Long id)
    {
        service.delete(id);
    }

    @PostMapping("/api/pedidos/{id}/productos")
    public Pedido addProducto(@PathVariable Long id, @RequestBody Producto producto) {

        return service.addProducto(id, producto);
    }

    @PutMapping("/api/pedidos/{id}/estado")
    public Pedido cambiarEstado(@PathVariable Long id, @RequestBody Estado estado)
    {

        return service.cambiarEstado(id, estado);
    }
}
