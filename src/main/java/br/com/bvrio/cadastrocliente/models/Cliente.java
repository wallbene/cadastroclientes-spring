package br.com.bvrio.cadastrocliente.models;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity(name="cliente")
@DynamicUpdate(value=true)
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable=false, length=40)
	private String nome;
	
	//Lock otimista
	@Version
	private Integer versao;
	
	@Column(nullable=false, unique= true, length=40)
	private String email;
	
	//(idade >= 16) 
	@Temporal(TemporalType.DATE)
	@DateTimeFormat
	private Calendar dataNascimento;
	
	private Endereco endereco;
	
	public boolean isNew(){
		return this.id == null;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public long getIdade(){
		
		Date aux = dataNascimento.getTime();
		Calendar data = Calendar.getInstance();
		
		data.setTime(aux);
	
		// Cria um objeto calendar com a data atual
		Calendar hoje = Calendar.getInstance();
		
		// Obtém a idade baseado no ano
		int idade = hoje.get(Calendar.YEAR) - data.get(Calendar.YEAR);
		
		data.add(Calendar.YEAR, idade);
		
		//se a data de hoje é antes da data de Nascimento, então diminui 1(um)
		if (hoje.before(data)) {
		idade--;
		}
		return idade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", email=" + email + ", dataNascimento=" + dataNascimento
				+ ", endereco=" + endereco + "]";
	}

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}
	

}