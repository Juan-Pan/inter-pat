package com.examen.cafeteria.controller;

import com.examen.cafeteria.model.Usuarios;
import com.examen.cafeteria.service.UserServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class Controlador {

    @Autowired
    private UserServicio userServicio;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuarios crearUsuario(@RequestBody Usuarios usuariosNuevo)
    {
        return userServicio.registrarUsuario(usuariosNuevo);
    }@PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Usuarios iniciarSesion(@RequestBody Map<String, String> credentiales) {
        String email = credentiales.get("email");
        String password = credentiales.get("password");
        return userServicio.loginUser(email, password);
    }


}
