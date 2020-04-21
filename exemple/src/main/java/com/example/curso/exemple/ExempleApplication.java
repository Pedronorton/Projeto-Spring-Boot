package com.example.curso.exemple;

import com.example.curso.exemple.domain.*;
import com.example.curso.exemple.enums.EstadoPagamento;
import com.example.curso.exemple.enums.TipoCliente;
import com.example.curso.exemple.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class ExempleApplication implements CommandLineRunner {


    public static void main(String[] args) {

        SpringApplication.run(ExempleApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {



    }
}
