package br.com.bvrio.cadastrocliente.daos;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.bvrio.cadastrocliente.builders.UsuarioBuilder;
import br.com.bvrio.cadastrocliente.conf.JPAConfiguration;
import br.com.bvrio.cadastrocliente.confs.DataSourceConfiguration;
import br.com.bvrio.cadastrocliente.models.Role;
import br.com.bvrio.cadastrocliente.models.Usuario;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class, UsuarioDAO.class, DataSourceConfiguration.class})
@ActiveProfiles("test")
public class UsuarioDAOTest {
	
	@Autowired
	private UsuarioDAO dao;
	
	@PersistenceContext
	private EntityManager manager;
	
	private Role role;

	@Before
	@Transactional
	public void criaRole(){
		Role role = new Role("ROLE_ADMIN");
		manager.persist(role);
		this.role = manager.find(Role.class, "ROLE_ADMIN");
	}
	
	@Test
	@Transactional
	public void deveCriarClientesBuilder(){
		
		UsuarioBuilder builder = UsuarioBuilder.newUsuario("wallace", "wallacebenevides@hotmail.com", "12345", role);
		
		Usuario usuario =  builder.buildOne();
		
		try {
			dao.adiciona(usuario);
		} catch (PersistenciaException e) {
			System.out.println("já tem esse usuário");
		}
		
		Usuario usuario2 = dao.buscaPorEmail(usuario.getEmail());
		
		assertEquals(usuario.getRoles().get(0), usuario2.getRoles().get(0));
		
		assertEquals("wallace", usuario.getNome());
		assertEquals("wallacebenevides@hotmail.com", usuario.getEmail());
		assertEquals("12345", usuario.getSenha());
		assertEquals(role, usuario.getRoles().get(0));
		
		
		
		
	}
	

}
