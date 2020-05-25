package com.example.curso.exemple.service;

import com.example.curso.exemple.domain.Cliente;
import com.example.curso.exemple.domain.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractEmailService implements EmailService {


    @Value("${default.sender}")
    private String sender;

    @Value("spring.thymeleaf.prefix")
    private String path;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

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
        sm.setText("Email enviado ao desgraçado\n "+obj.toString());

        return sm;
    }

    protected String htmlFromTemplatePedido(Pedido obj){
        Context context = new Context(); // isso faz eu acessar o template html
        context.setVariable("pedido",obj); //dar apelido a uma varialvel relacionada com o template
        // o que foi feito é uma variavel receber o objeto Pedido
        return templateEngine.process("confirmacaoPedido",context);

    }

    @Override
    public void sendOrderComfirmationHtmlEmail(Pedido obj) {
        try{
            MimeMessage mm = prepareMimeMailMessage(obj);
            sendHtmlMail(mm);
        }
        catch (MessagingException e){
            sendOrderComfirmationEmail(obj);
        }

    }

    protected  MimeMessage prepareMimeMailMessage(Pedido obj) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage,true);
        mmh.setTo(obj.getCliente().getEmail());
        mmh.setFrom(sender);
        mmh.setSubject("Confirmação do pedido. Número do pedido: "+obj.getId());
        mmh.setText(htmlFromTemplatePedido(obj), true);

        return mimeMessage;

    }

    @Override
    public void sendNewPasswordEmail(Cliente cliente, String newPass){
        SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
        sendMail(sm);
    }

    protected  SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass){
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(cliente.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de nova senha");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Nova Senha: "+newPass);

        return sm;
    }


}
