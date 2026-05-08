package com.examen.cafeteria.service;

import com.examen.cafeteria.model.Usuarios;
import com.examen.cafeteria.repository.UserRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServicio {
    @Autowired
    private UserRepositorio repositorio;

    @Transactional
    public Usuarios registrarUsuario(Usuarios usuarios)
    {
        if(usuarios == null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe el usuario");
        }
        if (repositorio.existsByEmail (usuarios.getEmail()))
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email ya registrado");
        }


        return repositorio.save(usuarios);
    }
    @Transactional
    public Usuarios loginUser(String email, String password)
    {
        if(email == null || password == null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email y contraseña son requeridos");
        }

        // Buscar el usuario con email y contraseña correctos
        Usuarios usuarioEncontrado = repositorio.findByEmailAndPassword(
            email,
            password
        );

        if(usuarioEncontrado == null)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email o contraseña incorrectos");
        }

        return usuarioEncontrado;
    }
}

