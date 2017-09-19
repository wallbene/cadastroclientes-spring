package br.com.bvrio.cadastrocliente.builders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.bvrio.cadastrocliente.models.Cliente;
import br.com.bvrio.cadastrocliente.models.Endereco;
import br.com.bvrio.cadastrocliente.models.EstadosEnum;

public class ClienteBuilder {

	private List<Cliente> clientes = new ArrayList<Cliente>();
	
	private static Endereco enderecoDefault = new Endereco();
	
	static{
		enderecoDefault.setLogradouro("Rua Teste");
		enderecoDefault.setBairro("Vila São João");
		enderecoDefault.setCidade("Rio de Janeiro");
		enderecoDefault.setEstado(EstadosEnum.RJ);
		enderecoDefault.setCep("25570210");
	}

	private ClienteBuilder(Cliente cliente) {
		this.clientes.add(cliente);
	}
	public static ClienteBuilder newCliente(String email, Calendar dataNascimento, Endereco endereco) {
		Cliente cliente = create("usuario1", email, dataNascimento, endereco);
		return new ClienteBuilder(cliente);
	}

	public static ClienteBuilder newCliente(String email, Calendar dataNascimento) {
		Cliente cliente = create("usuario1", email, dataNascimento, null);
		return new ClienteBuilder(cliente);
	}
	
	public static ClienteBuilder newCliente() {
		Cliente cliente = create("usuario1", "teste@gmail.com", Calendar.getInstance(), null);
		return new ClienteBuilder(cliente);
	}

	private static Cliente create(String nome, String email, Calendar dataNascimento, Endereco endereco) {
		Cliente cliente = new Cliente();
		cliente.setNome(nome);;
		cliente.setEmail(email);
		cliente.setDataNascimento(dataNascimento);
		if(endereco==null) 
			cliente.setEndereco(enderecoDefault);
		else 
			cliente.setEndereco(endereco);
		
		return cliente;
	}
	
	public ClienteBuilder more(int n){
		Cliente base = this.clientes.get(0);
		for(int i = 1; i <= n;i++ ){
			this.clientes.add(create("usuario"+i, base.getEmail()+i, base.getDataNascimento(), null));
		}
		return this;
	}
	
	public Cliente buildOne(){
		return this.clientes.get(0);
	}
	
	public List<Cliente> buildAll(){
		return this.clientes;
	}

}
