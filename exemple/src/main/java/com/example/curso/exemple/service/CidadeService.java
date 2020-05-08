package com.example.curso.exemple.service;

import com.example.curso.exemple.domain.Cidade;
import com.example.curso.exemple.domain.Produto;
import com.example.curso.exemple.repositories.CidadeRepository;
import com.example.curso.exemple.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    CidadeRepository repo;


    public List<Cidade> findAll(){
        return repo.findAll();
    }

    public Cidade findByNome(String nome){

        Optional<Cidade> obj = Optional.ofNullable(repo.findByNome(nome));
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado | nome: "+ nome + "tipo: "+ Produto.class.getName()));
        //return repo.findByNome(nome);
    }
}
