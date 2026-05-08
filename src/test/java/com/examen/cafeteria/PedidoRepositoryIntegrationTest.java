package com.examen.cafeteria;

import com.examen.cafeteria.model.Pedido;
import com.examen.cafeteria.model.Estado;
import com.examen.cafeteria.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

// 1. IMPORTANTE: Importa la excepción de validación de Jakarta
import jakarta.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class PedidoRepositoryIntegrationTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Test
    public void cuandoSalvasPedidoSinMail_thenThrowsDataConstraintViolation() {

        Pedido pedidoSinMail = new Pedido();
        pedidoSinMail.setNombre("Cliente malo");
        pedidoSinMail.setEstado(Estado.CREADO);
        pedidoSinMail.setTotal(0.0);

        // 2. Cambiamos la excepción que estamos esperando
        assertThrows(ConstraintViolationException.class, () -> {
            pedidoRepository.saveAndFlush(pedidoSinMail);
        });
    }
}