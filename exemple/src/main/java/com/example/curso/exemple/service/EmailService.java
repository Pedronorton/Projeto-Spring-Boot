package com.example.curso.exemple.service;

import com.example.curso.exemple.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderComfirmationEmail(Pedido obj);

    void sendMail(SimpleMailMessage msg);
}
