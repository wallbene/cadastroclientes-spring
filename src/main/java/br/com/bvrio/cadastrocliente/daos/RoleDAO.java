package br.com.bvrio.cadastrocliente.daos;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.bvrio.cadastrocliente.models.Role;

@Transactional
@Repository
public class RoleDAO {
	
	@PersistenceContext
	private EntityManager manager;
	private DAO<Role, String> dao; 
	
	@PostConstruct
	public void init(){
		dao = new DAO<Role, String>(manager, Role.class);
	}

	public void adiciona(Role t) {
		dao.adiciona(t);
	}

	public Role buscaPorId(Serializable id) {
		return dao.buscaPorId(id);
	}
	
	

}
