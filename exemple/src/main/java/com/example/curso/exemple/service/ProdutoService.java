package com.example.curso.exemple.service;

import com.example.curso.exemple.domain.Categoria;
import com.example.curso.exemple.domain.Pedido;
import com.example.curso.exemple.domain.Produto;
import com.example.curso.exemple.repositories.CategoriaRepository;
import com.example.curso.exemple.repositories.PedidoRepository;
import com.example.curso.exemple.repositories.ProdutoRepository;
import com.example.curso.exemple.security.UserSS;
import com.example.curso.exemple.service.exception.DataIntegrityException;
import com.example.curso.exemple.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<Produto> teste(List<Integer> ids){

        //List<Produto> list = repo.search();
        List<Produto> list = new ArrayList<>();
        return list;
    }


    /*public Page<Produto> search(    Integer pager, Integer linesPerPage, String orderBy, String direction){

        PageRequest pageRequest = PageRequest.of(pager, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        List<Categoria> categorias = categoriaRepository.findAll();
        return repo.findAll(pageRequest);
    }*/
    public Page<Produto> search( List<Integer> ids, Integer pager, Integer linesPerPage, String orderBy, String direction){

        PageRequest pageRequest = PageRequest.of(pager, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return repo.search(categorias,pageRequest);
    }

    public Page<Produto> searchAll(Integer pager, Integer linesPerPage, String orderBy, String direction){

        PageRequest pageRequest = PageRequest.of(pager, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        return repo.findAll(pageRequest);
    }


    public void delete(Integer id) {
        try{
            repo.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir produto");
        }
    }

    /**
     * Funcção recebe um objeto do tipo Produto faz a atualização dos dados e salva no banco
     * Obs: esse método apenas atualiza o produto e não valores do produto em pedidos ja feitos
     * @param obj
     * @return
     */
    public Produto update(Produto obj){
        Produto newObj = buscar(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);

    }

    public void updateData(Produto newObj, Produto obj){ // possível apenas mudar o preço
        newObj.setPreco(obj.getPreco());
    }
}
