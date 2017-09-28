package br.com.bvrio.cadastrocliente.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bvrio.cadastrocliente.daos.ClienteDAO;
import br.com.bvrio.cadastrocliente.models.Cliente;

@Service("clienteService")
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	private ClienteDAO dao;
	
	@Override
	public Cliente buscaPorEmail(String email) {
		return dao.buscaPorEmail(email);
	}
	
	@Override
	public void salva(Cliente cliente){
		if(cliente.isNew()) 
			dao.adiciona(cliente); //cliente novo
		else 
			dao.atualiza(cliente); //cliente antigo
	}
	
	@Override
	public void remove(Cliente t) {
		dao.remove(t);
	}
	
	@Override
	public void atualiza(Cliente t) {
		dao.atualiza(t);
	}
	
	@Override
	public List<Cliente> listaTodos() {
		return dao.listaTodos();
	}
	
	@Override
	public Cliente buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	@Override
	public int contaTodos() {
		return dao.contaTodos();
	}

}
