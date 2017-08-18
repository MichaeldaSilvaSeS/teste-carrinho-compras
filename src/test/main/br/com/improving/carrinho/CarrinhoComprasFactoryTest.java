package br.com.improving.carrinho;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import br.com.improving.carrinho.infraestrutura.CarrinhoComprasDAO;
import br.com.improving.carrinho.infraestrutura.Sessao;

public class CarrinhoComprasFactoryTest {

	@Test
	public void factoryTest() {
		new CarrinhoComprasFactory(new CarrinhoComprasDAO(),new Sessao());
	}
	
	@Test
	public void criarTest() {
		CarrinhoComprasFactory factory = new CarrinhoComprasFactory(new CarrinhoComprasDAO(),new Sessao());
		factory.criar("1").adicionarItem(new Produto(1L, ""), new BigDecimal(10), 1);
		Assert.assertEquals(10,factory.getValorTicketMedio().doubleValue(),0.1);
	}
	
	@Test
	public void invalidarTest() {
		CarrinhoComprasFactory factory = new CarrinhoComprasFactory(new CarrinhoComprasDAO(),new Sessao());
		Assert.assertFalse(factory.invalidar("1"));
		factory.criar("1");
		Assert.assertTrue(factory.invalidar("1"));
		Assert.assertFalse(factory.invalidar("1"));
		
		factory.criar("1").adicionarItem(new Produto(1L, ""), new BigDecimal(10), 1);
		Assert.assertEquals(10,factory.getValorTicketMedio().doubleValue(),0.1);
		
		Assert.assertTrue(factory.invalidar("1"));
		Assert.assertFalse(factory.invalidar("1"));
		Assert.assertEquals(0,factory.getValorTicketMedio().doubleValue(),0.1);
	}
	
	@Test
	public void ticketMedioTest() {
		new CarrinhoComprasFactory(new CarrinhoComprasDAO(),new Sessao()).getValorTicketMedio();
		CarrinhoComprasFactory factory = new CarrinhoComprasFactory(new CarrinhoComprasDAO(),new Sessao());
		
		CarrinhoCompras cc1 = factory.criar("1");
		cc1.adicionarItem(new Produto(1L, ""), new BigDecimal(10), 5);		
		
		CarrinhoCompras cc2 = factory.criar("2");
		cc2.adicionarItem(new Produto(1L, ""), new BigDecimal(10), 5);
		
		CarrinhoCompras cc3 = factory.criar("3");
		cc3.adicionarItem(new Produto(2L, ""), new BigDecimal(20), 5);
		
		CarrinhoCompras cc4 = factory.criar("4");
		cc4.adicionarItem(new Produto(2L, ""), new BigDecimal(0), 5);
		Assert.assertEquals(50,factory.getValorTicketMedio().doubleValue(),0.1);
	}

}
