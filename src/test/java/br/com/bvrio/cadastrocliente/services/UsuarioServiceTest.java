package br.com.bvrio.cadastrocliente.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.bvrio.cadastrocliente.builders.UsuarioBuilder;
import br.com.bvrio.cadastrocliente.conf.JPAConfiguration;
import br.com.bvrio.cadastrocliente.confs.DataSourceConfigurationTest;
import br.com.bvrio.cadastrocliente.daos.RoleDAO;
import br.com.bvrio.cadastrocliente.daos.UsuarioDAO;
import br.com.bvrio.cadastrocliente.models.Role;
import br.com.bvrio.cadastrocliente.models.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class, UsuarioServiceImpl.class, UsuarioDAO.class, RoleDAO.class, DataSourceConfigurationTest.class})
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private RoleDAO roleDao;

	public Role buscaDobanco(){
		Role role = new Role("ROLE_ADMIN");
		roleDao.adiciona(role);
		return roleDao.buscaPorId(role.getNome());
	}
	
	@Test
	public void deveCriarUsuarioBuilder(){
		Role role = new Role("ROLE_ADMIN");
		UsuarioBuilder builder = UsuarioBuilder.newUsuario("wallace", "wallacebenevides@hotmail.com", "12345", role);
		
		Usuario usuario =  builder.buildOne();
		
		assertEquals("wallace", usuario.getNome());
		assertEquals("wallacebenevides@hotmail.com", usuario.getEmail());
		assertEquals("12345", usuario.getSenha());
		assertEquals(role, usuario.getRoles().get(0));
			
	}
	
	
	
	@Test
	@Transactional
	public void deveAdicionarUsuarioComRoleDObanco(){
		Role role = buscaDobanco();
		UsuarioBuilder builder = UsuarioBuilder.newUsuario("wallace", "wallacebenevides@hotmail.com", "12345", role);
		Usuario usuario =  builder.buildOne();
		service.adiciona(usuario);
		Usuario usuarioDoBanco = (Usuario) service.loadUserByUsername(usuario.getEmail());
	
		assertTrue(usuarioDoBanco.equals(usuario));		
	}
	
	@Test
	@Transactional
	public void deveAdicionarUsuarioSemRoleDoBanco(){	
		UsuarioBuilder builder = UsuarioBuilder.newUsuario("wallace", "wallacebenevides@hotmail.com", "12345", null);
		Usuario usuario =  builder.buildOne();
		service.adiciona(usuario);
		Usuario usuarioDoBanco = (Usuario) service.loadUserByUsername(usuario.getEmail());
	
		assertTrue(usuarioDoBanco.equals(usuario));
			
	}
	
}
