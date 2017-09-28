package br.com.bvrio.cadastrocliente.daos;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.bvrio.cadastrocliente.builders.ClienteBuilder;
import br.com.bvrio.cadastrocliente.conf.JPAConfiguration;
import br.com.bvrio.cadastrocliente.confs.DataSourceConfigurationTest;
import br.com.bvrio.cadastrocliente.exceptions.PersistenciaException;
import br.com.bvrio.cadastrocliente.models.Cliente;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class, ClienteDAO.class, DataSourceConfigurationTest.class})
@ActiveProfiles("test")
public class ClienteDAOTest {
	
	@Autowired
	private ClienteDAO clienteDao;
	
	@Test
	public void deveBuildarClientesCorretamente(){
		Calendar data = new Calendar.Builder().setDate(1990, Calendar.AUGUST, 19).build();
		List<Cliente> clientes = ClienteBuilder.newCliente("wallace@gmail.com", data)
					  .more(3)
					  .buildAll();
		
		assertEquals(4, clientes.size());
		assertEquals("wallace@gmail.com", clientes.get(0).getEmail());
		assertEquals("wallace@gmail.com1", clientes.get(1).getEmail());
		assertEquals("wallace@gmail.com2", clientes.get(2).getEmail());
		assertEquals("wallace@gmail.com3", clientes.get(3).getEmail());
	}
	
	@Test
	@Transactional
	public void deveBuscarEmail() throws PersistenciaException{
		Cliente cliente = ClienteBuilder.newCliente().buildOne();
		clienteDao.adiciona(cliente);
		
		Cliente clienteAtual = clienteDao.buscaPorEmail(cliente.getEmail());
		
		assertThat(clienteAtual, notNullValue());
		assertThat(cliente.getEmail(), equalTo(clienteAtual.getEmail()));
		
	}
	
	
	@Test
	@Transactional
	public void deveAdicionarClientes(){
		Calendar data = new Calendar.Builder().setDate(1990, Calendar.AUGUST, 19).build();
		List<Cliente> clientes = ClienteBuilder.newCliente("wallace@gmail.com", data)
					  .more(4)
					  .buildAll();
		
		clientes.stream().forEach(t -> {			
				clienteDao.adiciona(t);			
		});
		
		long idade = clienteDao.buscaPorEmail("wallace@gmail.com").getIdade();
		List<Cliente> clientesDoBanco = clienteDao.listaTodos();
		
		assertEquals(5, clientesDoBanco.size());
		assertEquals(27, idade);
	}

}
