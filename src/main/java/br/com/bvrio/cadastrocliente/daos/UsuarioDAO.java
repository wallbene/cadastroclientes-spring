package br.com.bvrio.cadastrocliente.daos;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.bvrio.cadastrocliente.models.Usuario;

@Transactional
@Repository
public class UsuarioDAO{
	
	@PersistenceContext
	private EntityManager manager;
	
	private DAO<Usuario, String> dao;
	
	@PostConstruct
	public void init(){
		this.dao = new DAO<Usuario, String>(manager, Usuario.class);
		
	}
	
	public Usuario buscaPorEmail(String email){
		TypedQuery<Usuario> query = manager.createQuery("select u from usuario u where u.email = :pEmail", Usuario.class)
									.setParameter("pEmail", email);
		
		try{
			return query.getSingleResult();
		}catch (NoResultException e) {
			System.out.println("nenhum email encontrado");
			return null;
		}
	}
	
	public void adiciona(Usuario usuario) {		
		dao.adiciona(usuario);
	}

	public void remove(Usuario t) {
		dao.remove(t);
	}

	public void atualiza(Usuario t) {
		dao.atualiza(t);
	}

	public List<Usuario> listaTodos() {
		return dao.listaTodos();
	}

	public Usuario buscaPorId(String id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
