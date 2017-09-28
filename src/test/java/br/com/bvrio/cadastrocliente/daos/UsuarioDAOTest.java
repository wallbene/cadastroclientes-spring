package br.com.bvrio.cadastrocliente.daos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.bvrio.cadastrocliente.conf.JPAConfiguration;
import br.com.bvrio.cadastrocliente.confs.DataSourceConfigurationTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class, UsuarioDAO.class, DataSourceConfigurationTest.class})
@ActiveProfiles("test")
public class UsuarioDAOTest {
	
	@SuppressWarnings("unused")
	@Autowired
	private UsuarioDAO dao;
	
	@Test
	@Transactional
	public void deveCriarUsuarioBuilder(){
		
	}

}
