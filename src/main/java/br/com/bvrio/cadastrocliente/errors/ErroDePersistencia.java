package br.com.bvrio.cadastrocliente.errors;

public class ErroDePersistencia {
	
	private Object classe;
	private String mensagem;
	
	public ErroDePersistencia(Object classe, String mensagem) {
		this.classe = classe;
		this.mensagem = mensagem;
	}
	
	public Object getClasse() {
		return classe;
	}

	public void setClasse(Object classe) {
		this.classe = classe;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	@Override
	public String toString() {
		return "ErroDePersistencia [classe=" + classe + ", mensagem=" + mensagem + "]";
	}
	
}