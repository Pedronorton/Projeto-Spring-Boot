package com.example.curso.exemple.config;


import com.example.curso.exemple.service.DBService;
import com.example.curso.exemple.service.EmailService;
import com.example.curso.exemple.service.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBService dbService;

    //Aqui os métodos precisam ter algum tipo de retorno
    @Bean
    public boolean instantiateDatabase() throws ParseException {

        dbService.instantiateDatabase();

        return true;
    }

    @Bean // isso funciona como se fosse um polimorfismo, quando instancio em pedido service ele pode ser tanto do teste(Mock) quanto envio real (SMTP)
    public EmailService emailService(){
        return new SmtpEmailService();
    }

}
