package com.example.curso.exemple.service;

//ONDE IRÁ TER AS OPERAÇÕES DE CONSULTA NO DB


import com.example.curso.exemple.domain.Categoria;
import com.example.curso.exemple.domain.Cliente;
import com.example.curso.exemple.dto.CategoriaDTO;
import com.example.curso.exemple.repositories.CategoriaRepository;
import com.example.curso.exemple.service.exception.DataIntegrityException;
import com.example.curso.exemple.service.exception.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired //Automaticamente instanciada
    private CategoriaRepository repo;


    public Categoria buscar (Integer id){
        Optional<Categoria> obj = repo.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado| Id: "+ id+", Tipo: "+Categoria.class.getName()));
    }

    public Categoria insert (Categoria obj){
        obj.setId(null);
        return repo.save(obj);
    }

    public Categoria update(Categoria obj){
        Categoria newObj = buscar(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    private void updateData(Categoria newObj, Categoria obj){ // obj são os valores que eu inseri para mudar e newObj serão atualizados no banco
        newObj.setNome(obj.getNome());
    }

    public void delete(Integer id){
        buscar(id);
        try{
            repo.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
        }
    }

    public List<Categoria> findAll(){
        return repo.findAll();
    }

    public Page<Categoria> findPage(Integer pager, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(pager, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Categoria fromDTO(CategoriaDTO obj){
        return new Categoria(obj.getId(), obj.getNome());
    }

}
