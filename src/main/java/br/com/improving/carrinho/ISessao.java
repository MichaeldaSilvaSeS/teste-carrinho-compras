package br.com.improving.carrinho;

public interface ISessao {
	public Boolean existe(String identificacaoCliente,String identificadoRecurso);
	public Object obter(String identificacaoCliente, String identificadoRecurso);
	public void armazenar(String identificacaoCliente, String identificadoRecurso, Object recurso);
	public Boolean expirada(String identificacaoCliente);
	public void expirar(String identificacaoCliente);
	public void checkin(String identificacaoCliente);
	public Boolean checked(String identificacaoCliente);
	public void checkout(String identificacaoCliente);
}
