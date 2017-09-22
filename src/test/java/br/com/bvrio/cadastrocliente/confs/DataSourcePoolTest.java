package br.com.bvrio.cadastrocliente.confs;

import static org.junit.Assert.assertTrue;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import br.com.bvrio.cadastrocliente.conf.AppWebConfiguration;
import br.com.bvrio.cadastrocliente.conf.JPAConfiguration;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class, AppWebConfiguration.class, DataSourceConfigurationTest.class })
@ActiveProfiles("dev")
public class DataSourcePoolTest {
	
	@Autowired
	private DataSource dataSource;
	
	@Test
	public void testaPool() throws PropertyVetoException, SQLException, InterruptedException{
		
		ComboPooledDataSource cpdt = (ComboPooledDataSource) dataSource;
		for (int i = 1; i <= cpdt.getMaxPoolSize(); i++) {
			
			cpdt.getConnection();
			
			
			System.out.println("existentes :"+ cpdt.getNumConnectionsDefaultUser());
			System.out.println("Ocupadas: "+cpdt.getNumBusyConnectionsDefaultUser());
			System.out.println("Ociosas: "+cpdt.getNumIdleConnectionsDefaultUser());			
			System.out.println();
			
		}
		
		assertTrue(cpdt.getNumConnections() == 20);
		assertTrue(cpdt.getNumBusyConnections() == 20);
		assertTrue(cpdt.getNumIdleConnections() == 0);
			
		
		}
	
	public void deveManterUmaQtdMininaDeConexoesPeloPool(){
		
	}

}
