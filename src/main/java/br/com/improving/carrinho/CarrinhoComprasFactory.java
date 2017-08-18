package br.com.improving.carrinho;

import java.math.BigDecimal;

/**
 * Classe responsável pela criação e recuperação dos carrinhos de compras.
 */
public class CarrinhoComprasFactory {
	private RepositorioCarrinhoCompras repositorioCarrinhoCompras;
	private ISessao sessao;
	public CarrinhoComprasFactory(RepositorioCarrinhoCompras repositorioCarrinhoCompras, ISessao sessao){
		this.repositorioCarrinhoCompras = repositorioCarrinhoCompras;
		this.sessao = sessao;
	}
	
    /**
     * Cria e retorna um novo carrinho de compras para o cliente passado como parâmetro.
     *
     * Caso já exista um carrinho de compras para o cliente passado como parâmetro, este carrinho deverá ser retornado.
     *
     * @param identificacaoCliente
     * @return CarrinhoCompras
     */
    public CarrinhoCompras criar(String identificacaoCliente) {
    	if(!repositorioCarrinhoCompras.existe(identificacaoCliente))
    		repositorioCarrinhoCompras.salvar(identificacaoCliente, new CarrinhoCompras());
    	return repositorioCarrinhoCompras.recuperar(identificacaoCliente); 
    }

    /**
     * Retorna o valor do ticket médio no momento da chamada ao método.
     * O valor do ticket médio é a soma do valor total de todos os carrinhos de compra dividido
     * pela quantidade de carrinhos de compra.
     * O valor retornado deverá ser arredondado com duas casas decimais, seguindo a regra:
     * 0-4 deve ser arredondado para baixo e 5-9 deve ser arredondado para cima.
     *
     * @return BigDecimal
     */
    public BigDecimal getValorTicketMedio() {
    	BigDecimal somaDoValorTotalDeTodosOsCarrinhos = repositorioCarrinhoCompras.somaDoValorTotalDeTodosOsCarrinhos();
    	BigDecimal quantidadeDeCarrinhos = new BigDecimal(repositorioCarrinhoCompras.quantidadeDeCarrinhos());
    	if(quantidadeDeCarrinhos.equals(BigDecimal.ZERO))
    		quantidadeDeCarrinhos = BigDecimal.ONE;
    	BigDecimal ticketMedioNoMomento = somaDoValorTotalDeTodosOsCarrinhos.divide(quantidadeDeCarrinhos, 2,BigDecimal.ROUND_HALF_UP);
    	return ticketMedioNoMomento;
    }

    /**
     * Invalida um carrinho de compras quando o cliente faz um checkout ou sua sessão expirar.
     * Deve ser efetuada a remoção do carrinho do cliente passado como parâmetro da listagem de carrinhos de compras.
     *
     * @param identificacaoCliente
     * @return Retorna um boolean, tendo o valor true caso o cliente passado como parämetro tenha um carrinho de compras e
     * e false caso o cliente não possua um carrinho.
     */
    public boolean invalidar(String identificacaoCliente) {
    	boolean temCarrinhoDeCompras = repositorioCarrinhoCompras.existe(identificacaoCliente);
    	if(sessao.expirada(identificacaoCliente) == false ||  sessao.checked(identificacaoCliente) == false)
    		repositorioCarrinhoCompras.remove(identificacaoCliente);
    	
    	return temCarrinhoDeCompras;
    }
}
