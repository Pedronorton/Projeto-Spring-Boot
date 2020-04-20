package com.example.curso.exemple;

import com.example.curso.exemple.domain.*;
import com.example.curso.exemple.enums.EstadoPagamento;
import com.example.curso.exemple.enums.TipoCliente;
import com.example.curso.exemple.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class ExempleApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    public static void main(String[] args) {

        SpringApplication.run(ExempleApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        Categoria cat1 = new Categoria(null,"informatica");
        Categoria cat2 = new Categoria(null,"escritório");
        Categoria cat3 = new Categoria(null,"Cama mesa banho");
        Categoria cat4 = new Categoria(null,"cozinha");
        Categoria cat5 = new Categoria(null,"sala");
        Categoria cat6 = new Categoria(null,"banheiro");
        Categoria cat7 = new Categoria(null,"churrasco");
        Categoria cat8 = new Categoria(null,"bebedeira");

        Produto p1 = new Produto(null, "computador", 2000.00);
        Produto p2 = new Produto(null, "impressora", 150.00);
        Produto p3 = new Produto(null, "telefone", 50.00);


        p1.getCategorias().addAll(Arrays.asList(cat2));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
        p3.getCategorias().addAll(Arrays.asList(cat2));

        cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
        cat2.getProdutos().addAll(Arrays.asList(p2));


        categoriaRepository.saveAll(Arrays.asList(cat1, cat2,cat3, cat4,cat5, cat6,cat7, cat8));//Salva uma lista de objetos
        produtoRepository.saveAll(Arrays.asList(p1,p2,p3));

        //ISSO JA INSERE NO BANCO PRA MIM

        Estado est1 = new Estado("Minas Gerais");
        Estado est2 = new Estado("São Paulo");

        Cidade c1 = new Cidade(null,"Uberlandia",est1);
        Cidade c2 = new Cidade(null,"São Paulo",est2);
        Cidade c3 = new Cidade(null,"Campinas",est2);

        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2,c3));


        estadoRepository.saveAll(Arrays.asList(est1,est2));
        cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));

        Cliente cli1 = new Cliente("Maria Silva","teste@gmail.com","1111", TipoCliente.PESSOAFISICA);
        cli1.getTelefones().addAll(Arrays.asList("3333","5555"));

        Endereco e1 = new Endereco("Rua flores","33","casa","centro", "37490000", cli1, c1);
        Endereco e2 = new Endereco("Av primavera", "99", "casa", "centro", "37490000", cli1, c2);

        cli1.getEnderecos().addAll(Arrays.asList(e1,e2));

        clienteRepository.saveAll(Arrays.asList(cli1));
        enderecoRepository.saveAll(Arrays.asList(e1,e2));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Pedido ped1 = new Pedido(sdf.parse("30/09/2017 10:32"), cli1, e1 );
        Pedido ped2 = new Pedido(sdf.parse("10/10/2017 10:32"), cli1, e2 );

        Pagamento pagto1 = new PagamentoCartao(null, EstadoPagamento.QUITADO, ped1, 6);
        ped1.setPagamento(pagto1);

        Pagamento pagto2 = new PagamentoBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("10/10/2017 00:00"), null);
        ped2.setPagamento(pagto2);

        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

        pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

        ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1 , 2000.00);
        ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.0);
        ItemPedido ip3 = new ItemPedido(ped2, p2, 100.0, 1, 800.0);

        ped1.getItens().addAll(Arrays.asList(ip1, ip2 ));
        ped2.getItens().addAll(Arrays.asList(ip3));

        p1.getItens().addAll(Arrays.asList(ip1));
        p2.getItens().addAll(Arrays.asList(ip3));
        p3.getItens().addAll(Arrays.asList(ip2));

        itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));



    }
}
