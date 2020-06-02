package com.example.curso.exemple.service;

import com.example.curso.exemple.domain.Cliente;
import com.example.curso.exemple.domain.ItemPedido;
import com.example.curso.exemple.domain.PagamentoBoleto;
import com.example.curso.exemple.domain.Pedido;
import com.example.curso.exemple.enums.EstadoPagamento;
import com.example.curso.exemple.enums.Perfil;
import com.example.curso.exemple.repositories.*;
import com.example.curso.exemple.security.UserSS;
import com.example.curso.exemple.service.exception.AuthorizationException;
import com.example.curso.exemple.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ClienteService clienteService;

    public Pedido buscar(Integer id){
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado | id: "+ id + "tipo: "+Pedido.class.getName()));
    }

    public List<Pedido> getAll(){
        List<Pedido> obj = repo.findAll();
        return obj;
    }

    public List<Pedido> getByCliente(){
        UserSS user = UserService.authenticated();
        if(user == null){
            throw new AuthorizationException("Acesso negado");
        }
        if(user.hasRole(Perfil.ADMIN)){
            List<Pedido> list = repo.findAll();
            return list;

        }else if(user.hasRole(Perfil.CLIENTE)){
            Cliente cli = clienteService.buscar(user.getId());
            List<Pedido> list = repo.findByCliente(cli);
            return list;
        }else{
            return null;
        }

    }

    @Transactional
    public Pedido insert(Pedido obj){
        obj.setId(null);
        obj.setInstante(new Date());
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        obj.setCliente(clienteRepository.getOne(obj.getCliente().getId()));


        if(obj.getPagamento() instanceof PagamentoBoleto){
            PagamentoBoleto pgto = (PagamentoBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pgto, obj.getInstante());
        }

        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());

        for(ItemPedido ip : obj.getItens()){
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.buscar(ip.getProduto().getId()));
            ip.setPreco(produtoService.buscar(ip.getProduto().getId()).getPreco());
            ip.setPedido(obj);
        }

        itemPedidoRepository.saveAll(obj.getItens());
        emailService.sendOrderComfirmationHtmlEmail(obj);
        //emailService.sendOrderComfirmationEmail(obj);

        return obj;
    }

}
