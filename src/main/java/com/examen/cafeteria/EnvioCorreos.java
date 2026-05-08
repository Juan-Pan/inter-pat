package com.examen.cafeteria;



import com.examen.cafeteria.model.Estado;
import com.examen.cafeteria.model.Pedido;
import com.examen.cafeteria.repository.PedidoRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class EnvioCorreos {

    private static final Log log = LogFactory.getLog(EnvioCorreos.class);
    //TODO 4
    @Autowired
    private PedidoRepository pedidoRepository;


    @Scheduled(fixedDelay = 10000)
    public void enviarRecordatorios() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        for(Pedido pedido : pedidos)
        {
            if (pedido.getEmail() != null && !pedido.getEmail().isBlank())
            {
                log.info("Enviamos correos a " + pedido.getEmail());
            }
            else {
                log.info("No se tiene el correo correcto con el id" + pedido.getId());
            }
        }

    }


}