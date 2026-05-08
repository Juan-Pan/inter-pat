package com.examen.cafeteria.repository;

import com.examen.cafeteria.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositorio extends JpaRepository<Usuarios, Long> {

    boolean existsByEmail(String email);
    Usuarios findByEmailAndPassword (String email, String password);

}
