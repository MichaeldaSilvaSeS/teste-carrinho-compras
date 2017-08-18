package br.com.improving.carrinho.infraestrutura;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import br.com.improving.carrinho.CarrinhoCompras;
import br.com.improving.carrinho.RepositorioCarrinhoCompras;

public class CarrinhoComprasDAO implements RepositorioCarrinhoCompras{
	private Map<String,CarrinhoCompras> persistencia = new  HashMap<String,CarrinhoCompras>();
	public void salvar(String identificador, CarrinhoCompras carrinhoCompras){
		if(!persistencia.containsKey(identificador))
			persistencia.put(identificador, new CarrinhoCompras());
	}
	public Boolean existe(String identificador){
		return persistencia.containsKey(identificador);
	}
	@Override
	public CarrinhoCompras recuperar(String identificacaoCliente) {
		if(persistencia.containsKey(identificacaoCliente))
			return persistencia.get(identificacaoCliente);
		return null;
	}
	@Override
	public BigDecimal somaDoValorTotalDeTodosOsCarrinhos() {
		return persistencia.values().stream().map(CarrinhoCompras::getValorTotal).reduce(BigDecimal.ZERO,BigDecimal::add);
	}
	@Override
	public BigInteger quantidadeDeCarrinhos() {
		return BigInteger.valueOf(persistencia.values().size());
	}
	@Override
	public void remove(String identificacaoCliente) {
		if(persistencia.containsKey(identificacaoCliente))
			persistencia.remove(identificacaoCliente);
	}
}
