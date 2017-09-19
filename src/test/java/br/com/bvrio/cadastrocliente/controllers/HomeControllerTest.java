package br.com.bvrio.cadastrocliente.controllers;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.bvrio.cadastrocliente.conf.AppWebConfiguration;
import br.com.bvrio.cadastrocliente.conf.JPAConfiguration;
import br.com.bvrio.cadastrocliente.conf.SecurityConfiguration;
import br.com.bvrio.cadastrocliente.confs.DataSourceConfigurationTest;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class, AppWebConfiguration.class, DataSourceConfigurationTest.class, SecurityConfiguration.class})
@ActiveProfiles("test")
public class HomeControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private Filter springSecurityFilterChain;
	
	private MockMvc mockMvc;

	@Before
	public void setup(){ 
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(springSecurityFilterChain).build();
	}
	
	@Test
	public void deveRetornarParaAHome() throws Exception{
		
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/views/index.jsp"));
	}
	
	@Test
	public void devePermitirAcessoLivreAHome() throws Exception{
		
		mockMvc.perform(MockMvcRequestBuilders.get("/")
				.with(SecurityMockMvcRequestPostProcessors.user("wallace@gmail.com").password("12345")
						.roles("user")))//user not exist
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/views/index.jsp"));
	}

}
