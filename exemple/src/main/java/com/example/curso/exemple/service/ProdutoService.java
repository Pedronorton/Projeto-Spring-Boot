package com.example.curso.exemple.service;

import com.example.curso.exemple.domain.Categoria;
import com.example.curso.exemple.domain.Pedido;
import com.example.curso.exemple.domain.Produto;
import com.example.curso.exemple.repositories.CategoriaRepository;
import com.example.curso.exemple.repositories.PedidoRepository;
import com.example.curso.exemple.repositories.ProdutoRepository;
import com.example.curso.exemple.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repo;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto buscar(Integer id){
        Optional<Produto> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado | id: "+ id + "tipo: "+Produto.class.getName()));
    }

    public Produto insert(Produto obj){
        obj = repo.save(obj);

        return obj;
    }
    public Page<Produto> search(Integer pager, Integer linesPerPage, String orderBy, String direction){

        PageRequest pageRequest = PageRequest.of(pager, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        List<Categoria> categorias = categoriaRepository.findAll();
        return repo.findAll(pageRequest);
    }

}
