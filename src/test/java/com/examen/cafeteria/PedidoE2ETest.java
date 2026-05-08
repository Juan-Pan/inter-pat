package com.examen.cafeteria;

import com.examen.cafeteria.model.Pedido;
import com.examen.cafeteria.repository.PedidoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // es buen uso para que cree la base datos limpia desde cero
public class PedidoE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PedidoRepository pedidoRepository;


    @Test
    public void crearYrecuperarPedido() {

        pedidoRepository.deleteAll(); // para tener la base de datos limpia para el test
        // 1. preparo: se crea un pedido para enviarlo por el post
        Pedido pedidoNuevo = new Pedido();
        pedidoNuevo.setNombre("Juan Test");
        pedidoNuevo.setEmail("juan@test.com");

        // 2. Actuar(Parte1): Hacemos el Post para guardar el pedido en la BD
        ResponseEntity<Pedido> respuestaPost = restTemplate.postForEntity(
                "/api/pedidos",
                pedidoNuevo,
                Pedido.class
        );

        // se extrae el id que nos generó la base de datos
        Long idGenerado = respuestaPost.getBody().getId();

        //2. Actuar (Parte 2): Hacemos el get al id generado.
        // Lo pedimos como string para cumplir la peticion exacta de tu profesor
        ResponseEntity<String> respuesta = restTemplate.getForEntity(
                "/api/pedidos/" + idGenerado,
                String.class
        );

        // 3. Comprobamos (Assert):
        Assertions.assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        Assertions.assertNotNull(respuesta.getBody());
        Assertions.assertFalse(respuesta.getBody().isBlank());

        // OJO se pidio el get como string pq el metodo de isBlank solo lo tiene el string asi que es mejor hacerlo en string

        /*
        // 1. Pedimos que la respuesta sea de tipo Pedido
ResponseEntity<Pedido> response = restTemplate.getForEntity(
        "/api/pedidos/" + idGenerado,
        Pedido.class
);

// 2. Comprobamos el código HTTP igual que antes
Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

// 3. Comprobamos que el cuerpo no sea nulo
Assertions.assertNotNull(response.getBody());

// 4. LA DIFERENCIA: Como ahora tenemos un objeto Pedido, evaluamos sus atributos
Pedido pedidoRecuperado = response.getBody();

// Ya no usamos isBlank(), usamos los métodos reales del objeto:
Assertions.assertEquals("Juan Test", pedidoRecuperado.getNombre());
Assertions.assertEquals("juan@test.com", pedidoRecuperado.getEmail());
Assertions.assertEquals(Estado.CREADO, pedidoRecuperado.getEstado());*/

       //#TODO 5
    }


}
