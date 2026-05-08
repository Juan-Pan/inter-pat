package com.examen.cafeteria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


//TODO#4
@EnableScheduling // Ojo sin esto no funcionan las tareas programadas
@SpringBootApplication
public class CafeteriaApplication {
    public static void main(String[] args) {
        SpringApplication.run(CafeteriaApplication.class, args);
    }
}
