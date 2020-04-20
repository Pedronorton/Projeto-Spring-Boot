package com.example.curso.exemple.enums;

public enum EstadoPagamento {

    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private String descricao;
    private int cod;

    private EstadoPagamento(Integer cod, String descricao){
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

    public static EstadoPagamento toEnum(Integer cod){
        if(cod == null){
            return null;
        }
        for (EstadoPagamento x : EstadoPagamento.values()){
            if(x.getCod() == cod){
                return x;
            }
        }
        throw new IllegalArgumentException("Id inválido: "+cod);
    }
}
