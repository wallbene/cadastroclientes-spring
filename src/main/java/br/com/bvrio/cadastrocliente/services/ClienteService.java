package br.com.bvrio.cadastrocliente.services;

import java.util.List;

import br.com.bvrio.cadastrocliente.daos.ClienteDAO;
import br.com.bvrio.cadastrocliente.exceptions.PersistenciaException;
import br.com.bvrio.cadastrocliente.models.Cliente;

public class ClienteService {
	
	private ClienteDAO dao;

	public Cliente buscaPorEmail(String email) {
		return dao.buscaPorEmail(email);
	}

	public void salvar(Cliente cliente) throws PersistenciaException {
		//Se Cliente já existir atualiza as informações de cliente
		if(cliente.getId() != 0) 
			dao.atualiza(cliente);
		else 
		dao.adiciona(cliente);
	}

	public void remove(Cliente t) {
		dao.remove(t);
	}

	public void atualiza(Cliente t) {
		dao.atualiza(t);
	}

	public List<Cliente> listaTodos() {
		return dao.listaTodos();
	}

	public Cliente buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

}
