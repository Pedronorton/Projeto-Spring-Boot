package com.example.curso.exemple.config;


import com.example.curso.exemple.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    //Aqui os métodos precisam ter algum tipo de retorno
    @Bean
    public boolean instantiateDatabase() throws ParseException {

        dbService.instantiateDatabase();

        return true;
    }

}
