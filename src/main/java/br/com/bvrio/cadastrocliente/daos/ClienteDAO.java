package br.com.bvrio.cadastrocliente.daos;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.bvrio.cadastrocliente.models.Cliente;

@Repository
@Transactional
public class ClienteDAO {

	@PersistenceContext
	private EntityManager manager;
	
	private DAO<Cliente, Integer> dao;
	
	@PostConstruct
	public void init(){
		this.dao = new DAO<Cliente, Integer>(manager, Cliente.class);
	}

	public Cliente buscaPorEmail(String email){
		TypedQuery<Cliente> query = manager.createQuery("select c from cliente c where c.email = :pEmail", Cliente.class)
									.setParameter("pEmail", email);
		
		try{
			return query.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
	}
	public void adiciona(Cliente t) {
		
		dao.adiciona(t);
	}
	
	public Cliente buscaPorId(Serializable id) {
		return dao.buscaPorId(id);
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


	public int contaTodos() {
		return dao.contaTodos();
	}
	

}