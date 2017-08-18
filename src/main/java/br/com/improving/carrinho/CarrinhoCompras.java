package br.com.improving.carrinho;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe que representa o carrinho de compras de um cliente.
 */
public class CarrinhoCompras {
	private List<Item> itens = new ArrayList<Item>();
		
    /**
     * Permite a adição de um novo item no carrinho de compras.
     *
     * Caso o item já exista no carrinho para este mesmo produto, as seguintes regras deverão ser seguidas:
     * - A quantidade do item deverá ser a soma da quantidade atual com a quantidade passada como parâmetro.
     * - Se o valor unitário informado for diferente do valor unitário atual do item, o novo valor unitário do item deverá ser
     * o passado como parâmetro.
     *
     * Devem ser lançadas subclasses de RuntimeException caso não seja possível adicionar o item ao carrinho de compras.
     *
     * @param produto
     * @param valorUnitario
     * @param quantidade
     */
    public void adicionarItem(Produto produto, BigDecimal valorUnitario, int quantidade) {
    	Item item = new Item(produto, valorUnitario,quantidade);
    	Integer indiceDoItemExistente = buscarIndiceItem(item);
    	if(indiceDoItemExistente > -1){
    		Item itemExistente = recuperarItem(item);
    		if(itemExistente != null)
    			itemExistente.agregarUm(item);
    	}
    	else
    		this.itens.add(item);
    }

    /**
     * Permite a remoção do item que representa este produto do carrinho de compras.
     *
     * @param produto
     * @return Retorna um boolean, tendo o valor true caso o produto exista no carrinho de compras e false
     * caso o produto não exista no carrinho.
     */
    public boolean removerItem(Produto produto) {
		Integer posicaoItem = buscarIndiceProduto(produto);		
		Boolean itemEncontrado = posicaoItem >= 0;
		if(itemEncontrado)
			removerItem(posicaoItem);
    	return itemEncontrado;
    }

    /**
     * Permite a remoção do item de acordo com a posição.
     * Essa posição deve ser determinada pela ordem de inclusão do produto na 
     * coleção, em que zero representa o primeiro item.
     *
     * @param posicaoItem
     * @return Retorna um boolean, tendo o valor true caso o produto exista no carrinho de compras e false
     * caso o produto não exista no carrinho.
     */
    public boolean removerItem(int posicaoItem) {
    	Boolean posisaoItemValida = (!this.itens.isEmpty()) && posicaoItem < this.itens.size();
    	if(posisaoItemValida)
    		this.itens.remove(posicaoItem);
    	return posisaoItemValida;
    }

    /**
     * Retorna o valor total do carrinho de compras, que deve ser a soma dos valores totais
     * de todos os itens que compõem o carrinho.
     *
     * @return BigDecimal
     */
    public BigDecimal getValorTotal() {
    	return this.itens.stream().map(Item::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Retorna a lista de itens do carrinho de compras.
     *
     * @return itens
     */
    public Collection<Item> getItens() {
    	return this.itens;
    }
    
    private Integer buscarIndiceProduto(Produto produto){
		List<Produto> produtos = this.itens.stream().map(Item::getProduto).collect(Collectors.toList());
		Comparator<Produto> comparadorProduto = new Comparator<Produto>() {
			@Override
			public int compare(Produto produto1, Produto produto2) {
				return produto1.getCodigo().compareTo(produto2.getCodigo());				
			}
		};
		Collections.<Produto>sort(produtos,comparadorProduto);
		return Collections.binarySearch(produtos, produto, comparadorProduto ); 
	}
	
	private Integer buscarIndiceItem(Item item){
		List<Item> itens = this.itens;
		Comparator<Item> comparadorItem = new Comparator<Item>() {
			@Override
			public int compare(Item item1, Item item2) {
				return item1.getProduto().getCodigo().compareTo(item2.getProduto().getCodigo());				
			}
		};
		Collections.<Item>sort(itens,comparadorItem);
		return Collections.binarySearch(itens, item, comparadorItem);
	}
	
	private Item recuperarItem(Item item){
		Integer posicaoItem = buscarIndiceItem(item);
		if(posicaoItem >= 0)
			return this.itens.get(posicaoItem);
		return null;
	}
}