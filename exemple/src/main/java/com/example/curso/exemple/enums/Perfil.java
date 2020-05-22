package com.example.curso.exemple.enums;

public enum Perfil {
    //Role é padrão do srping
    ADMIN(1, "ROLE_ADMIN"),
    CLIENTE(2, "ROLE_CLIENTE");

    private String descricao;
    private int cod;

    private Perfil(Integer cod, String descricao){
        this.cod = cod;
        this.descricao = descricao;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer id) {
        this.cod = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static Perfil toEnum(Integer cod){
        if(cod == null){
            return null;
        }
        for (Perfil x : Perfil.values()){
            if(x.getCod() == cod){
                return x;
            }
        }
        throw new IllegalArgumentException("Id inválido: "+cod);
    }
}
