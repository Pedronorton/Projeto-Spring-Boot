package com.example.curso.exemple.service;

import com.example.curso.exemple.domain.*;
import com.example.curso.exemple.enums.EstadoPagamento;
import com.example.curso.exemple.enums.Perfil;
import com.example.curso.exemple.enums.TipoCliente;
import com.example.curso.exemple.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;


@Service
public class DBService {

    @Autowired
    private BCryptPasswordEncoder pe;

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

    public void instantiateDatabase() throws ParseException {
        Categoria cat1 = new Categoria(null,"informatica");
        Categoria cat2 = new Categoria(null,"escritório");
        Categoria cat3 = new Categoria(null,"Cama mesa banho");
        Categoria cat4 = new Categoria(null,"cozinha");
        Categoria cat5 = new Categoria(null,"sala");
        Categoria cat6 = new Categoria(null,"banheiro");
        Categoria cat7 = new Categoria(null,"churrasco");
        Categoria cat8 = new Categoria(null,"bebedeira");

        Produto p1 = new Produto(null, "Notebook Compaq Presario CQ32 Intel Pentium 4GB 120GB SSD 14\" W10", 2000.00, "https://images-americanas.b2w.io/produtos/01/00/img/1499041/9/1499041951G1.jpg");
        Produto p2 = new Produto(null, "Smartphone Samsung Galaxy A10 32GB Dual Chip Android 9.0 Tela 6.2\" Octa-Core 4G Câmera 13MP - Preto", 150.00, "https://images-americanas.b2w.io/produtos/01/00/img7/01/00/item/134253/9/134253960G1.jpg");
        Produto p3 = new Produto(null, "Lavadora de Roupas Brastemp 9kg Branca BWJ09 - 220V", 50.00, "https://images-americanas.b2w.io/produtos/01/00/img7/01/00/item/132733/9/132733927G1.png");
        Produto p4 = new Produto(null, "Cadeira Gamer MX0 Giratoria Preto - Mymax", 300.00, "https://images-americanas.b2w.io/produtos/01/00/img/1335014/4/1335014498G1.jpg");
        Produto p5 = new Produto(null, "Smart TV LED 32'' Samsung 32T4300 HD - WIFI, HDR para Brilho e Contraste, Plataforma Tizen, 2 HDMI, 1 USB - Preta", 1199.00, "https://images-americanas.b2w.io/produtos/01/00/img/1618074/6/1618074634G1.jpg");
        Produto p6 = new Produto(null, "Batedeira Planetária Oster OBAT651 12 Velocidades Inox Preto - 750w 220v", 549.99, "https://images-americanas.b2w.io/produtos/01/00/image/134269/8/134269857G1.jpg");
        Produto p7 = new Produto(null, "Af-55 I - Fritadeira Grand Family Inox-5,5l 127v - Mondial", 1200.00, "https://images-americanas.b2w.io/produtos/01/00/img/133855/7/133855766_1GG.jpg");
        Produto p8 = new Produto(null, "Kit Gamer Teclado/mouse/ Mouse Pad/ Headset Trust", 252.99, "https://images-americanas.b2w.io/produtos/01/00/oferta/41352/2/41352219_1GG.jpg");
        Produto p9 = new Produto(null, "Ar Condicionado Split Hw Dual Inverter Voice Lg 12000 Btus Frio 220V Monofasico S4NQ12JA31C.EB2GAMZ", 100.00, "https://images-americanas.b2w.io/produtos/01/00/img/460503/2/460503231G1.jpg");
        Produto p10 = new Produto(null, "Panela De Pressão Digital 6l Inox Vermelho Pppv 01 220v", 539.89, "https://images-americanas.b2w.io/produtos/01/00/offers/01/00/item/132522/7/132522724G1.jpg");
        Produto p11 = new Produto(null, "Forno Elétrico Oster nano silver ultra 46l 127v", 399.99, "https://images-americanas.b2w.io/produtos/01/00/images/134494/0/134494018G1.jpg");

        p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
        p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
        p4.getCategorias().addAll(Arrays.asList(cat2));
        p5.getCategorias().addAll(Arrays.asList(cat3));
        p6.getCategorias().addAll(Arrays.asList(cat3));
        p7.getCategorias().addAll(Arrays.asList(cat4));
        p8.getCategorias().addAll(Arrays.asList(cat5));
        p9.getCategorias().addAll(Arrays.asList(cat6));
        p10.getCategorias().addAll(Arrays.asList(cat6));
        p11.getCategorias().addAll(Arrays.asList(cat7));



        cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
        cat2.getProdutos().addAll(Arrays.asList(p2, p4));
        cat3.getProdutos().addAll(Arrays.asList(p5, p6));
        cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
        cat5.getProdutos().addAll(Arrays.asList(p8));
        cat6.getProdutos().addAll(Arrays.asList(p9, p10));
        cat7.getProdutos().addAll(Arrays.asList(p11));


        categoriaRepository.saveAll(Arrays.asList(cat1, cat2,cat3, cat4,cat5, cat6,cat7));//Salva uma lista de objetos
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

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
        //lufelipini@gmail.com
        Cliente cli1 = new Cliente("Maria Silva","user@gmail.com","11169590624", TipoCliente.PESSOAFISICA, pe.encode("123"));
        cli1.getTelefones().addAll(Arrays.asList("3333","5555"));

        Cliente cli2 = new Cliente("Ademar","admin@gmail.com","11169590624", TipoCliente.PESSOAFISICA, pe.encode("123"));
        cli2.getTelefones().addAll(Arrays.asList("32412004","5555"));
        cli2.addPerfil(Perfil.ADMIN);

        Cliente cli3 = new Cliente("Cliente teste","ll@gmail.com","11169590624", TipoCliente.PESSOAFISICA, pe.encode("123"));
        cli3.getTelefones().addAll(Arrays.asList("3333","5555"));


        Endereco e1 = new Endereco("Rua flores","33","casa","centro", "37490000", cli1, c1);
        Endereco e2 = new Endereco("Av dos peixes", "99", "casa", "centro", "37490000", cli1, c2);
        Endereco e3 = new Endereco("Av dos tubarões", "99", "casa", "centro", "37490000", cli2, c2);
        Endereco e4 = new Endereco("Av teste", "99", "casa", "centro", "37490000", cli3, c2);


        cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
        cli2.getEnderecos().addAll(Arrays.asList(e3));
        cli3.getEnderecos().addAll(Arrays.asList(e4));



        clienteRepository.saveAll(Arrays.asList(cli1,cli2,cli3));
        enderecoRepository.saveAll(Arrays.asList(e1,e2,e3));

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
