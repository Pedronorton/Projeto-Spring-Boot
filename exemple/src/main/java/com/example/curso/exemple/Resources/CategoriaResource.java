package com.example.curso.exemple.Resources;


import com.example.curso.exemple.domain.Categoria;
import com.example.curso.exemple.dto.CategoriaDTO;
import com.example.curso.exemple.service.CategoriaService;
import com.example.curso.exemple.service.exception.DataIntegrityException;
import com.sun.mail.iap.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/categoria")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer id){ // O PATHVARIABLE SIGNIFICA QUE O ENDPOINT QUE É O /ID IRÁ PARA A VARIAVEL INTEGER ID
        //ResponseEntity quer dizer que irá retornar uma resposta completa http

        Categoria obj = service.buscar(id);


        return ResponseEntity.ok().body(obj); //Retorna uma resposta http que foi um sucesso e seu corpo é o objeto obj qu busquei no banco
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity listarTodos(){
        List<Categoria> obj = service.findAll();

        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> insert(@Valid @RequestBody CategoriaDTO objDto){ // Request body serve para transformar de Json para objeto
        Categoria obj = service.fromDTO(objDto);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.ok().body(obj.getId());
        //return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@Valid @RequestBody CategoriaDTO objDTO, @PathVariable Integer id){
        Categoria obj = service.fromDTO(objDTO);
        obj.setId(id);
        obj = service.update(obj);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                     @RequestParam(value = "linesPerPage", defaultValue = "2")Integer linesPerPage,
                                     @RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,
                                     @RequestParam(value = "direction", defaultValue = "ASC")String direction){

        Page<Categoria> list = service.findPage(page,linesPerPage,orderBy,direction);
        Page<CategoriaDTO> listDTO = list.map(obj -> new CategoriaDTO(obj));
        return ResponseEntity.ok().body(listDTO);
    }

}
