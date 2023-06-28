package com.pjfs.projetocna;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pjfs.projetocna.domain.Categoria;
import com.pjfs.projetocna.domain.Cidade;
import com.pjfs.projetocna.domain.Cliente;
import com.pjfs.projetocna.domain.Endereco;
import com.pjfs.projetocna.domain.Estado;
import com.pjfs.projetocna.domain.ItemPedido;
import com.pjfs.projetocna.domain.Pagamento;
import com.pjfs.projetocna.domain.PagamentoComBoleto;
import com.pjfs.projetocna.domain.PagamentoComCartao;
import com.pjfs.projetocna.domain.Produto;
import com.pjfs.projetocna.domain.Pedido;
import com.pjfs.projetocna.domain.enums.EstadoPagamento;
import com.pjfs.projetocna.domain.enums.TipoCliente;
import com.pjfs.projetocna.repositories.CategoriaRepository;
import com.pjfs.projetocna.repositories.CidadeRepository;
import com.pjfs.projetocna.repositories.ClienteRepository;
import com.pjfs.projetocna.repositories.EnderecoRepository;
import com.pjfs.projetocna.repositories.EstadoRepository;
import com.pjfs.projetocna.repositories.ItemPedidoRepository;
import com.pjfs.projetocna.repositories.PagamentoRepository;
import com.pjfs.projetocna.repositories.PedidoRepository;
import com.pjfs.projetocna.repositories.ProdutoRepository;

@SpringBootApplication
public class ProjetocnaApplication implements CommandLineRunner{

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
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjetocnaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		Estado est3 = new Estado(null, "Santa Catarina");

		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "Suzano", est2);
		Cidade c3 = new Cidade(null, "Balneario Camboriu", est3);
		Cidade c4 = new Cidade(null, "Mogi das Cruzes", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c4));
		est3.getCidades().addAll(Arrays.asList(c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2, est3));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3, c4));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "4626253535252", TipoCliente.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("474976522", "997367826"));

		Endereco e1 = new Endereco(null, "Rua 1", "223", " ", "Nova America", "07658727", cli1, c2);
		Endereco e2 = new Endereco(null, "Rua 2", "234", " Casa 2 ", "Monte Castelo", "09358727", cli1, c3);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("15/09/2022 11:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2022 12:42"), cli1, e2);
		
		Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgto1);
		
		Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pgto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgto1, pgto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
	

}
