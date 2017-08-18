package br.com.improving.carrinho;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.improving.carrinho.infraestrutura.CarrinhoComprasDAO;
import br.com.improving.carrinho.infraestrutura.Sessao;

public class CarrinhoComprasTest {
	private static CarrinhoComprasFactory FACTORY;
	@BeforeClass
	public static void beforeClass(){
		FACTORY = new CarrinhoComprasFactory(new CarrinhoComprasDAO(),new Sessao());
	}
	
	@Test
	public void totalTest(){
		CarrinhoCompras cc2 = FACTORY.criar("1");
		cc2.adicionarItem(new Produto(1L, ""), new BigDecimal(10), 5);
		Assert.assertEquals(new BigDecimal(50), cc2.getValorTotal());
		
		cc2.removerItem(0);
		Assert.assertEquals(BigDecimal.ZERO, cc2.getValorTotal());
	}
	
	public void addItemTest(){
		CarrinhoCompras cc1 = FACTORY.criar("1");
		cc1.adicionarItem(new Produto(1L, ""), new BigDecimal(10), 5);
		Assert.assertEquals(1,FACTORY.criar("1").getItens().size());
	}
	
	public void removeItemTest(){
		CarrinhoCompras cc1 = FACTORY.criar("1");
		cc1.adicionarItem(new Produto(1L, ""), new BigDecimal(10), 5);
		
		cc1.removerItem(-1);
		Assert.assertEquals(1,FACTORY.criar("1").getItens().size());
		
		cc1.removerItem(0);
		Assert.assertEquals(0,FACTORY.criar("1").getItens().size());
		
		cc1.removerItem(0);
		Assert.assertEquals(0,FACTORY.criar("1").getItens().size());
		
		cc1.removerItem(-1);
		Assert.assertEquals(0,FACTORY.criar("1").getItens().size());
	}
}
