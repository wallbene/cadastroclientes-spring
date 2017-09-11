package br.com.bvrio.cadastrocliente.daos;

import static org.junit.Assert.assertEquals;

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
import br.com.bvrio.cadastrocliente.confs.DataSourceConfiguration;
import br.com.bvrio.cadastrocliente.exceptions.PersistenciaException;
import br.com.bvrio.cadastrocliente.models.Cliente;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class, ClienteDAO.class, DataSourceConfiguration.class})
@ActiveProfiles("test")
public class ClienteDAOTest {
	
	@Autowired
	private ClienteDAO clienteDao;
	
	
	@Test
	@Transactional
	public void deveCriarClientes(){
		Calendar data = new Calendar.Builder().setDate(1990, Calendar.AUGUST, 19).build();
		List<Cliente> clientes = ClienteBuilder.newCliente("wallace@gmail.com", data)
					  .more(4)
					  .buildAll();
		
		clientes.stream().forEach(t -> {
			try {
				clienteDao.adiciona(t);
			} catch (PersistenciaException e) {
				e.printStackTrace();
			}
		});
		
		long idade = clienteDao.buscaPorEmail("wallace@gmail.com").getIdade();
		assertEquals(27, idade);
	}
	
	@Test
	@Transactional
	public void deveAceitarClientesComMaisDe16Anos() throws PersistenciaException{
		Calendar data = new Calendar.Builder().setDate(1990, Calendar.AUGUST, 19).build();
		Cliente cliente = ClienteBuilder.newCliente("wallace@gmail.com", data)
												.buildOne();
		clienteDao.adiciona(cliente);
		
		long idade = clienteDao.buscaPorEmail("wallace@gmail.com").getIdade();
		assertEquals(27, idade);
	}

}
