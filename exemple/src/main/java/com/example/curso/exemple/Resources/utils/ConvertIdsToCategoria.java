package com.example.curso.exemple.Resources.utils;

import com.example.curso.exemple.domain.Categoria;
import com.example.curso.exemple.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class ConvertIdsToCategoria {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> convert(List<Integer> ids){
        List<Categoria> categorias = new ArrayList<>();

        for(int id : ids){
            Optional<Categoria> cat = categoriaRepository.findById(id);
            categorias.add(cat.get());
        }
        return categorias;
    }
}
