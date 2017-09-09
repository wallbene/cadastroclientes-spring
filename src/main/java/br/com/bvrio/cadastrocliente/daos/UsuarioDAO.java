package br.com.bvrio.cadastrocliente.daos;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.bvrio.cadastrocliente.errors.ErroDePersistencia;
import br.com.bvrio.cadastrocliente.models.Role;
import br.com.bvrio.cadastrocliente.models.Usuario;

@Transactional
@Repository
public class UsuarioDAO {
	
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
			return null;
		}
	}
	
	public void adiciona(Usuario usuario) throws PersistenciaException {
		if(buscaPorEmail(usuario.getEmail()) != null){
			throw new PersistenciaException(new ErroDePersistencia(Usuario.class, "Já existe cadastro de cliente com esse email"));
		}
		//busca a Role, associa ao usuario e persiste no banco
		Role role = manager.find(Role.class, "ROLE_ADMIN");
		if(role == null) throw new RuntimeException("Retornou a role vazia do banco");
		else{
			System.out.println("Retornou a role corretamente " + role.getNome());
		}
		usuario.addRole(role);
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
