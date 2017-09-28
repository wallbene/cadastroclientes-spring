package br.com.bvrio.cadastrocliente.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import javax.servlet.Filter;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import br.com.bvrio.cadastrocliente.builders.UsuarioBuilder;
import br.com.bvrio.cadastrocliente.conf.AppWebConfiguration;
import br.com.bvrio.cadastrocliente.conf.JPAConfiguration;
import br.com.bvrio.cadastrocliente.conf.SecurityConfiguration;
import br.com.bvrio.cadastrocliente.confs.DataSourceConfigurationTest;
import br.com.bvrio.cadastrocliente.exceptions.PersistenciaException;
import br.com.bvrio.cadastrocliente.models.Usuario;
import br.com.bvrio.cadastrocliente.services.UsuarioService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class, AppWebConfiguration.class, DataSourceConfigurationTest.class, SecurityConfiguration.class})
@ActiveProfiles("test")
public class UsuarioControllerTest {
	
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private Filter springSecurityFilterChain;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private MockMvc mockMvc;
	
	@Before
	public void init(){
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(springSecurityFilterChain).build();
	}
	
	@Test
	public void deveExibirFormAdicionar() throws Exception{
		mockMvc.perform(get("/usuarios/adicionar"))
			.andExpect(forwardedUrl("/WEB-INF/views/usuarios/form.jsp"));
	}
	
	@Test
	@Transactional
	public void deveGravarNovoUsuario() throws Exception{
		Usuario usuario = UsuarioBuilder.newUsuario("Wallace", "wallace@gmail.com", "12345", null).buildOne();
		
		mockMvc.perform(post("/usuarios")
				.param("nome", usuario.getNome())
				.param("email", usuario.getEmail())
				.param("senha", usuario.getPassword())
				.with(csrf()))
		.andDo(print());
		
		Usuario usuarioDoBanco = service.buscaPorEmail("wallace@gmail.com");		
		
		assertNotNull(usuarioDoBanco.getRoles().get(0));
		
		MatcherAssert.assertThat(usuarioDoBanco.getNome(), equalTo(usuario.getNome()));
		MatcherAssert.assertThat(usuarioDoBanco.getEmail(), equalTo(usuario.getEmail()));
		
		//testa se está a senha passada está sendo criptografada
		boolean matches = passwordEncoder.matches(usuario.getPassword(), usuarioDoBanco.getPassword());
		assertTrue(matches);
		
	}
	
	@Test	
	public void deveValidarOFormatoDoEmail() throws Exception{
		
		mockMvc.perform(post("/usuarios")				
				.param("email", "wallacebenevides")								
				.with(csrf()))		
		.andExpect(((model().attributeHasFieldErrorCode("usuario", "email", "field.pattern.usuarios.email"))));		
	}
	
	@Test
	@Transactional
	public void deveImpedirDeGravarUmUsuarioComEmailQueJaExistaNoBancoERetornarParaOFormulario() throws Exception{
		Usuario usuario = alimentarObanco();
		System.out.println("fazer post");
		
		mockMvc.perform(post("/usuarios")
				.param("nome", usuario.getNome())
				.param("email", usuario.getEmail())
				.param("senha", usuario.getSenha())			
				.with(csrf()))
		.andExpect(((model().attributeHasFieldErrorCode("usuario", "email", "field.required.usuarios.email.unico"))))				
		.andExpect(forwardedUrl("/WEB-INF/views/usuarios/form.jsp"));
	}
	
	private Usuario alimentarObanco() throws PersistenciaException {
		Usuario usuario = UsuarioBuilder.newUsuario().buildOne();
		service.adiciona(usuario);
		return usuario;
	}
}
