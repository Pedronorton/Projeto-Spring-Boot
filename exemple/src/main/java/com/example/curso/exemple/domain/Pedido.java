package com.example.curso.exemple.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.*;

@Entity
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date instante;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private  Cliente cliente;

    @ManyToOne
    @JoinColumn(name="endereco_de_entrega_id")
    private Endereco enderecoEntrega;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Pagamento pagamento;

    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemPedido> itens = new HashSet<>();

    public Pedido(Date instante, Cliente cliente, Endereco enderecoEntrega){
        this.instante = instante;
        this.cliente = cliente;
        this.enderecoEntrega = enderecoEntrega;
    }
    public Pedido(){
    }

    public double getValorTotal(){
        double soma = 0.0;
        for(ItemPedido ip : itens){
            soma = soma+ ip.getSubtotal();
        }
        return soma;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInstante() {
        return instante;
    }

    public void setInstante(Date instante) {
        this.instante = instante;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Set<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(Set<ItemPedido> itens) {
        this.itens = itens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido)) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
        builder.append("Id Pedido: ");
        builder.append(getId()+"\n");
        builder.append("Cliente: ");
        builder.append(getCliente().getNome()+"\n");
        builder.append("Instante do pedido: ");
        builder.append(getInstante()+"\n");
        builder.append("Situação pagamento: ");
        builder.append(getPagamento().getEstado()+"\n");
        builder.append("Valor total: ");
        builder.append(nf.format(getValorTotal())+"\n");
        for(ItemPedido ip : itens){
            builder.append(ip.getProduto().getNome());
            builder.append(" | Qtd: ");
            builder.append(ip.getQuantidade());
            builder.append(" | Valor: ");
            builder.append(ip.getProduto().getPreco());
            builder.append(" | Subtotal: ");
            builder.append(nf.format(ip.getSubtotal())+"\n");
        }
        return builder.toString();
    }

}
