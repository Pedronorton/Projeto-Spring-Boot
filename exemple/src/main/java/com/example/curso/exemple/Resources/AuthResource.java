package com.example.curso.exemple.Resources;


import com.example.curso.exemple.dto.EmailDTO;
import com.example.curso.exemple.dto.UserDTO;
import com.example.curso.exemple.enums.Perfil;
import com.example.curso.exemple.security.JWTUtil;
import com.example.curso.exemple.security.UserSS;
import com.example.curso.exemple.service.AuthService;
import com.example.curso.exemple.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;


    /*@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response){
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer "+token);
        return ResponseEntity.noContent().build();
        res.addHeader("access-control-expose-headers", "Authorization");

    }*/

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ResponseEntity<Void> forgot(@RequestBody EmailDTO objDTO){
        service.sendNewPassword(objDTO.getEmail());
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity getUser(){
        UserSS user = UserService.authenticated();
        if(user != null){
            return ResponseEntity.ok().body(user.getAuthorities());
        }else{
            return ResponseEntity.noContent().build();
        }
    }
    @RequestMapping(value = "/validateToken", method = RequestMethod.POST)
    public ResponseEntity validToken(@RequestParam(value = "token") String token){

        return ResponseEntity.ok().body(jwtUtil.tokenValido(token));
    }

}
