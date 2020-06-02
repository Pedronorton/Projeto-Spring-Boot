package com.example.curso.exemple.service;


import com.example.curso.exemple.domain.Cliente;
import com.example.curso.exemple.enums.Perfil;
import com.example.curso.exemple.repositories.ClienteRepository;
import com.example.curso.exemple.security.UserSS;
import com.example.curso.exemple.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class AuthService {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private ClienteRepository clienteRepository;

    private Random rand = new Random();

    @Autowired
    private EmailService emailService;

    public void sendNewPassword(String email){

        Cliente cli = clienteRepository.findByEmail(email);
        if(cli == null){
            throw new ObjectNotFoundException("Email não encontrado");
        }

        String newPass = newPassword();
        cli.setSenha(pe.encode(newPass));

        clienteRepository.save(cli);
        emailService.sendNewPasswordEmail(cli, newPass);


    }

    private String newPassword() {
        char[] vet = new char[10];
        for(int i=0; i<10;i++){
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);
        if(opt == 0){ //Gera um dígito
            return (char) (rand.nextInt(10) + 48);
        }
        if(opt == 1){ // Gera letra maiuscula
            return (char) (rand.nextInt(26) + 65);
        }else{
            return (char) (rand.nextInt(26) + 97);
        }
    }

    public Boolean isAdmin(){
        UserSS user = UserService.authenticated();
        if(user.hasRole(Perfil.ADMIN)){
            return true;
        }else{
            return false;
        }
    }


}
