package com.examen.cafeteria;

import com.examen.cafeteria.model.Pedido;
import com.examen.cafeteria.model.Estado;
import com.examen.cafeteria.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class PedidoRepositoryIntegrationTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Test
    public void cuandoSalvasPedidoSinMail_thenThrowsDataConstraintViolation() {
        //#TODO 5
    }
}
