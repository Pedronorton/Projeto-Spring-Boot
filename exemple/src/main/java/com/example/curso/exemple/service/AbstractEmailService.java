package com.example.curso.exemple.service;

import com.example.curso.exemple.domain.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService {


    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderComfirmationEmail(Pedido obj) {
        SimpleMailMessage sm = prepareSimpleMailMessage(obj);
        sendMail(sm);
    }

    private SimpleMailMessage prepareSimpleMailMessage(Pedido obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(obj.getCliente().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Confirmação do pedido. Número do pedido: "+obj.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(obj.toString());

        return sm;
    }

}
