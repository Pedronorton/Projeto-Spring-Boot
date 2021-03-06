package com.example.curso.exemple.service;

import com.example.curso.exemple.domain.Categoria;
import com.example.curso.exemple.domain.Cliente;
import com.example.curso.exemple.domain.Pedido;
import com.example.curso.exemple.domain.Produto;
import com.example.curso.exemple.dto.ProdutoNewDTO;
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
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repo;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ImageService imageService;
    @Autowired
    private S3Service s3Service;

    public Produto buscar(Integer id){
        Optional<Produto> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado | id: "+ id + "tipo: "+Produto.class.getName()));
    }

    public List<Categoria> buscarCategorias(Integer id){
        Optional<Produto> obj;
        try{
            obj = repo.findById(id);
        }
        catch (ObjectNotFoundException e){
            throw new ObjectNotFoundException("Objeto não encontrado | id: "+ id + "tipo: "+Produto.class.getName());
        }
        List<Categoria> list = obj.get().getCategorias();
        return list;
    }

    public Produto insert(Produto obj, MultipartFile file) throws IOException, URISyntaxException {
        BufferedImage jpgImage = imageService.getJpgImageFromFile(file);
        obj = repo.save(obj);
        String fileName = "Produto "+ obj.getId() + "jpg";
        URI uri = s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");

        Optional<Produto> produto = repo.findById(obj.getId());
        produto.get().setImageUrl(uri.toString());
        repo.save(produto.get());

        return obj;
    }

    public Produto fromDTO(ProdutoNewDTO objDTO) throws IOException, URISyntaxException {

        Produto obj = new Produto(null, objDTO.getNome(), objDTO.getPreco(),  null);
        obj.getCategorias().addAll(objDTO.getListaCategorias());
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

    public Produto updateCategoria(Integer id, List<Categoria> listCat){
        Produto newObj = buscar(id);
        for(Categoria cat : listCat){
            newObj.getCategorias().addAll(Arrays.asList(cat));
            cat.getProdutos().addAll(Arrays.asList(newObj));
        }
        return repo.save(newObj);

    }

    public void updateData(Produto newObj, Produto obj){ // possível apenas mudar o preço
        newObj.setImageUrl(obj.getImageUrl());
        newObj.setPreco(obj.getPreco());
    }
}
