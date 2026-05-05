package com.examen.cafeteria;



import com.examen.cafeteria.model.Estado;
import com.examen.cafeteria.model.Pedido;
import com.examen.cafeteria.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


public class EnvioCorreos {
  //TODO 4


    public void enviarRecordatorios() {
      return;

    }


}