package com.examen.cafeteria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


import java.util.List;


//TODO#1
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, name = "cliente")
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    @Email
    @NotBlank
    @Column(nullable = false)
    private String email;

    @Column
    private Double total = 0.0;

    @OneToMany(orphanRemoval = true, mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<Producto> productos;

    //metodo para el calcilo total
    public void calcularTotal()
    {
        if(this.productos != null)
        {
            double sumaTotal = 0.0;
            for(Producto producto : this.productos)
            {
                double subTotal = producto.getPrecio() * producto.getCantidad();

                sumaTotal = subTotal + sumaTotal;
            }
            this.total = sumaTotal;
        }
    }

    public Pedido() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }




}
