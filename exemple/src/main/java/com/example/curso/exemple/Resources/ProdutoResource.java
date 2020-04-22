package com.example.curso.exemple.Resources;

import com.example.curso.exemple.Resources.utils.URL;
import com.example.curso.exemple.domain.Categoria;
import com.example.curso.exemple.domain.Produto;
import com.example.curso.exemple.dto.CategoriaDTO;
import com.example.curso.exemple.dto.ProdutoDTO;
import com.example.curso.exemple.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    ProdutoService service;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer id){
        Produto obj = service.buscar(id);

        return ResponseEntity.ok().body(obj); //retorna um json caso for encontrado
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ProdutoDTO>> findAll(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "0") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "2")Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction){

        List<Integer> ids = URL.decodeIntList(categorias);
        String nomeDecode = URL.decodeParam(nome);
        Page<Produto> list = service.search(nomeDecode, ids, page,linesPerPage,orderBy,direction);
        Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
        return ResponseEntity.ok().body(listDTO);
    }
}