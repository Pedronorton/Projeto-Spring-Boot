package com.example.curso.exemple.service;

import com.example.curso.exemple.domain.Cliente;
import com.example.curso.exemple.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public interface EmailService {

    void sendOrderComfirmationEmail(Pedido obj);

    void sendMail(SimpleMailMessage msg);

    void sendOrderComfirmationHtmlEmail(Pedido obj);

    void sendHtmlMail(MimeMessage msg);

    void sendNewPasswordEmail(Cliente cliente, String newPass);

}
