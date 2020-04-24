package com.example.curso.exemple.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

public class SmtpEmailService extends AbstractEmailService {

    @Autowired
    private MailSender mailSender; // instancia um objeto com todas as caracteristicas que foram colocadas em proprierts

    @Autowired
    private JavaMailSender javaMailSender;

    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);


    @Override
    public void sendMail(SimpleMailMessage msg) {
        LOG.info("Eviando email...");
        mailSender.send(msg);
        LOG.info("Email enviado com sucesso");
    }

    @Override
    public void sendHtmlMail(MimeMessage msg) {
        LOG.info("Eviando email...");
        javaMailSender.send(msg);
        LOG.info("Email enviado com sucesso");
    }
}
