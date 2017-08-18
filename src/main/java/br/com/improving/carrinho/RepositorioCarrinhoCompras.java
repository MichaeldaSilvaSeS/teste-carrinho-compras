package br.com.improving.carrinho;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface RepositorioCarrinhoCompras {
	public Boolean existe(String identificacaoCliente);
	public CarrinhoCompras recuperar(String identificacaoCliente);
	public BigDecimal somaDoValorTotalDeTodosOsCarrinhos();
	public BigInteger quantidadeDeCarrinhos();
	public void remove(String identificacaoCliente);
	public void salvar(String identificador, CarrinhoCompras carrinhoCompras);
}
