package com.example.curso.exemple.service;

import com.example.curso.exemple.domain.Pedido;
import com.example.curso.exemple.repositories.PedidoRepository;
import com.example.curso.exemple.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    public Pedido buscar(Integer id){
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado | id: "+ id + "tipo: "+Pedido.class.getName()));
    }

}
