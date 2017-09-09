package br.com.bvrio.cadastrocliente.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.bvrio.cadastrocliente.models.EstadosEnum;

@Embeddable
public class Endereco {
	
	@Column(nullable=false, length=40)
	private String endereco;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private EstadosEnum estado;
	
	@Column(nullable=false, length=20)
	private String cidade;
	
	@Column(nullable=false, length=9)
	private String cep;

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public EstadosEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadosEnum estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public String toString() {
		return "Endereco [endereco=" + endereco + ", estado=" + estado + ", cidade=" + cidade + ", cep=" + cep + "]";
	}
}
