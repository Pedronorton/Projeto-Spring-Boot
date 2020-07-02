package com.example.curso.exemple.Resources;

import com.example.curso.exemple.Resources.utils.ConvertIdsToCategoria;
import com.example.curso.exemple.Resources.utils.URL;
import com.example.curso.exemple.domain.Categoria;
import com.example.curso.exemple.domain.Produto;
import com.example.curso.exemple.dto.CategoriaDTO;
import com.example.curso.exemple.dto.ProdutoDTO;
import com.example.curso.exemple.dto.ProdutoNewDTO;
import com.example.curso.exemple.service.ImageService;
import com.example.curso.exemple.service.ProdutoService;
import com.example.curso.exemple.service.S3Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    ProdutoService service;

    @Autowired
    private ImageService imageService;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private  ConvertIdsToCategoria convertIdsToCategoria;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer id){
        Produto obj = service.buscar(id);

        return ResponseEntity.ok().body(obj); //retorna um json caso for encontrado
    }

    @RequestMapping(value = "/categoria/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findCategoria(@PathVariable Integer id){
        List<Categoria> list = service.buscarCategorias(id);

        return ResponseEntity.ok().body(list); //retorna um json caso for encontrado
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> insert(@RequestParam(name="nome") String nome, @RequestParam(name="preco") Double preco, @RequestParam(name="file") MultipartFile file, @RequestParam(name="lista") List<Integer> listaIds ) throws IOException, URISyntaxException {
        Produto obj = new Produto(null, nome, preco, "");


        List<Categoria> cat = convertIdsToCategoria.convert(listaIds);

        for(Categoria x : cat){
            obj.getCategorias().addAll(Arrays.asList(x));
        }
        obj = service.insert(obj, file);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.ok().body(obj.getId());
        //return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestParam(name = "nome") String nome, @RequestParam(name = "preco") Double preco, @RequestParam(name = "file") MultipartFile file) throws IOException, URISyntaxException {

        BufferedImage jpgImage = imageService.getJpgImageFromFile(file);
        String fileName = "Produto "+id + "jpg";

        URI uri = s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");

        System.out.println(uri.toString());
        Produto obj = new Produto(id,nome,preco,uri.toString());
        obj = service.update(obj);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/categoria/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCategoria(@PathVariable Integer id, @RequestBody List<Categoria> listCat){

        Produto obj = service.updateCategoria(id, listCat);

        return ResponseEntity.noContent().build();
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ProdutoDTO>> findAll(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "all") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "100")Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction){

        String categoria = URL.decodeParam(categorias);
        if(categoria.equals("all")){
            Page<Produto> list = service.searchAll(page, linesPerPage, orderBy, direction);
            Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
            return ResponseEntity.ok().body(listDTO);
        }else {
            List<Integer> ids = URL.decodeIntList(categorias);
            String nomeDecode = URL.decodeParam(nome);
            Page<Produto> list = service.search(ids, page, linesPerPage, orderBy, direction);
            Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
            return ResponseEntity.ok().body(listDTO);
        }
    }
}
