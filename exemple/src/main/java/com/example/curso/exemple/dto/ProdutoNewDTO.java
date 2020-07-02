package com.example.curso.exemple.dto;

import com.example.curso.exemple.domain.Categoria;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

public class ProdutoNewDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    private String nome;
    private Double preco;
    private List<Categoria> listaCategorias;

    public ProdutoNewDTO(){

    }
    public ProdutoNewDTO(String nome, Double preco, List<Categoria> listaCategorias){
        this.nome = nome;
        this.preco = preco;
        this.listaCategorias = listaCategorias;

    }

    public String getNome() {
        return nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

}
