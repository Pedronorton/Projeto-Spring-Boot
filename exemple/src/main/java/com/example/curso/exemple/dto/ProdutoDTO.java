package com.example.curso.exemple.dto;

import com.example.curso.exemple.domain.Produto;

import java.io.Serializable;

public class ProdutoDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    private Integer id;
    private String nome;
    private Double preco;
    private String imageUrl;
    public ProdutoDTO(Produto obj){
        id = obj.getId();
        nome = obj.getNome();
        preco = obj.getPreco();
        imageUrl = obj.getImageUrl();
    }

    public ProdutoDTO(){

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
