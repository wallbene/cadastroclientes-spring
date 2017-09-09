package br.com.bvrio.cadastrocliente.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

@Entity(name="role")
public class Role implements GrantedAuthority{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String nome;
	
	public Role(String nome) {
		this.nome = nome;
	}
	public Role() {
	}
	

	@Override
	public String getAuthority() {
		return this.getNome().toString();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	

}
