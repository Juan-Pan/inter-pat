# Examen mayo 26

Aplicación web que usa Spring JPA para persistir los datos de un API
REST de gestión de pedidos de una cafetería.

Existirán dos tablas, una de pedidos y otra de productos (ver data.sql
para ver estructura).

La lógica de la aplicación es la siguiente:

1)  En la tabla de pedidos se guardan los pedidos realizados por los
    clientes con la siguiente información:  
    1.1) Nombre del cliente.\
    1.2) Estado del pedido.\
    1.3) Email que no puede ser ni blanco y se debe validar el formato del correo
    1.4) Importe total del pedido. (campo calculado)

2)  En la tabla productos se almacenan los productos asociados a cada
    pedido con la siguiente información:
    2.1) Nombre del producto (no nulo).\
    2.2) Precio del producto (no puede ser negativo ni ir vacío).\
    2.3) Cantidad del producto (no puede ser negativa ni cero ni vacío).\
    2.4) Cada producto pertenece a un único pedido.

3)  La lógica de negocio de los pedidos será la siguiente:\
    3.1) Cuando se crea un pedido su estado inicial será siempre CREADO y su importe
    total será 0. Estos dos campo no es necesario envíarlo en el JSON\
    3.2) El importe total del pedido será la suma de (precio \*
    cantidad) de todos los productos asociados.\
    3.3) Un pedido puede estar en uno de los siguientes estados: -
    CREADO\
    - EN_PREPARACION\
    - SERVIDO\
    - CANCELADO

    3.4) Las transiciones de estado permitidas son: - CREADO →
    EN_PREPARACION\
    - EN_PREPARACION → SERVIDO\
    - CREADO → CANCELADO

    3.5) No se puede modificar (añadir productos o cambiar estado) un
    pedido en estado SERVIDO o CANCELADO.

    3.6) No se puede cambiar directamente de CREADO a SERVIDO.

    3.7) Si se intenta realizar una transición no permitida se deberá
    devolver error.

    3.8) No se puede crear el pedido para el mismo cliente si ya tiene otro pedido en estado CREADO, en tal caso,
    debe devolver un conflicto.

------------------------------------------------------------------------

## Endpoints

  ----------------------------------------------------------------------------------------------------------------
Método   Ruta                          Descripción                                   Respuestas
  -------- ----------------------------- --------------------------------------------- ---------------------------
POST     /api/pedidos                  Crea un pedido: {"cliente": "Juan",           201 si OK,
"email": "juan@hotmail.com}              401 si un cliente ya
tiene un pedido en CREADO

GET      /api/pedidos/{id}             Devuelve un pedido                            200 si OK: Pedido completo,
404 si no existe

GET      /api/pedidos/productos/{nombre}Devuelve todos los pedidos                   200 si OK   
                                        que tienes un producto                       404 si no existe pedidos con ese artículo 

GET      /api/pedidos/estado/{estado}  Devuelve todos los pedido con un estado       200 si OK

DELETE   /api/pedidos/{id}             Borra un pedido                               204 si OK, 404 si no existe

POST     /api/pedidos/{id}/productos   Añade producto:                               200 si OK,
{"nombre":"Café","precio":150,"cantidad":2}   404 si no EXISTE
409 si no
modificable

PUT      /api/pedidos/{id}/estado      Actualiza estado: {"estado":"EN_PREPARACION"} 200 si OK, 400 transición
inválida, 404 si no existe
  ----------------------------------------------------------------------------------------------------------------
## PREGUNTAS

// TODO#1\ (10/85)
Rellena las entidades con las restricciones y el módelo entidad relación definido.
Dentro de la Entidad pedido existirá una lista de productos. Con añadir un producto a la lista
de la entidad Pedido se guarda en la tabla Producto, sin necesidad de llamar al ProductoRepository

//TODO#2\ (20/85)
Implementa los end-point de PedidoController que pemitan hacer la lógica de negocio

//TODO#3\ (25/85)
Implementa los servicios con la lógica de negocio y las restricciones descritas en el enunciado

//TODO#4\ (20/85)
Implementa una tarea programada para simular el envío de correos, en lugar de enviar correos,
se escribirá en el log a nivel de INFO con el siguiente mensaje: "Enviamos correos a prueba@gmail.com"
siempre que el correo no sea nullo y no sea en blanco en ese canso se un mensaje de log a nivel de INFO
con el id del cliente e indicando que no se tiene el correo correcto.

//TODO#5\ (10/85)
Test de integración E2E donde se comprueba la creación y que recupera un Pedido, comprobando
Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
Assertions.assertNotNull(response.getBody());
Assertions.assertFalse(response.getBody().isBlank());
