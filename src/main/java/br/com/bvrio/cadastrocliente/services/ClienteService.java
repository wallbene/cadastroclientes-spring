package br.com.bvrio.cadastrocliente.services;

import java.util.List;

import br.com.bvrio.cadastrocliente.exceptions.ClienteNotFoundException;
import br.com.bvrio.cadastrocliente.models.Cliente;

public interface ClienteService {
	void salva(Cliente cliente);			
	void remove(Cliente t);
	void atualiza(Cliente t);
	List<Cliente> listaTodos();
	Cliente buscaPorId(Integer id) throws ClienteNotFoundException;
	int contaTodos();
	Cliente buscaPorEmail(String email);

}
