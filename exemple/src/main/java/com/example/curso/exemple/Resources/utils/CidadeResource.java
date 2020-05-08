package com.example.curso.exemple.Resources.utils;


import com.example.curso.exemple.domain.Cidade;
import com.example.curso.exemple.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeResource {

    @Autowired
    private CidadeService service;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity listar(){
        List<Cidade> cidades = service.findAll();

        return ResponseEntity.ok().body(cidades);
    }
    @RequestMapping(value = "/{nome}", method = RequestMethod.GET)
    public ResponseEntity<Cidade> getByname(@PathVariable String nome){
        Cidade cidade = service.findByNome(nome);


        return ResponseEntity.ok().body(cidade);
    }
}
