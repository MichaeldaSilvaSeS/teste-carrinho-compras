package br.com.improving.carrinho.infraestrutura;

import java.util.HashMap;
import java.util.Map;

import br.com.improving.carrinho.ISessao;

public class Sessao implements ISessao{
	private static final String EXPIRACAO = "EXPIRACAO";
	private static final String CHECKED = "IDENTIFICADOR";
	private Map<String,Object> armazem = new  HashMap<String,Object>();
	
	private String comporIdentificador(String identificacaoCliente, String identificadoRecurso){
		return identificacaoCliente.concat(".").concat(identificadoRecurso);
	}
	
	public Boolean existe(String identificacaoCliente, String identificadoRecurso){
		String identificadorComposto = comporIdentificador(identificacaoCliente, identificadoRecurso);
		return armazem.containsKey(identificadorComposto);
	}
	
	public Object obter(String identificacaoCliente,String identificadoRecurso){
		String identificadorComposto = comporIdentificador(identificacaoCliente, identificadoRecurso);
		if(armazem.containsKey(identificadorComposto))
			return armazem.get(identificadorComposto);
		return null;
	}
	
	public void armazenar(String identificacaoCliente, String identificadoRecurso, Object recurso){
		String identificadorComposto = comporIdentificador(identificacaoCliente, identificadoRecurso);
		armazem.put(identificadorComposto, recurso);
	}
	
	public void retirar(String identificacaoCliente, String identificadoRecurso){
		String identificadorComposto = comporIdentificador(identificacaoCliente, identificadoRecurso);
		if(armazem.containsKey(identificadorComposto))
			armazem.remove(identificadorComposto);
	}

	@Override
	public Boolean expirada(String identificacaoCliente) {
		if(existe(identificacaoCliente, EXPIRACAO))
			return (Boolean)obter(identificacaoCliente, EXPIRACAO);
		return Boolean.TRUE;
	}
	
	public void expirar(String identificacaoCliente){
		if(existe(identificacaoCliente, EXPIRACAO))
			armazenar(identificacaoCliente, EXPIRACAO, Boolean.TRUE);
	}

	@Override
	public void checkin(String identificacaoCliente) {
		armazenar(identificacaoCliente, CHECKED, identificacaoCliente);
		armazenar(identificacaoCliente, EXPIRACAO, Boolean.FALSE);
	}
	
	

	@Override
	public void checkout(String identificacaoCliente) {
		retirar(identificacaoCliente, CHECKED);
	}

	@Override
	public Boolean checked(String identificacaoCliente) {
		return existe(identificacaoCliente, CHECKED);
	}	
}
