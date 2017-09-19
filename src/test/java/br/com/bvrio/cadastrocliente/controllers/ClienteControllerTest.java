package br.com.bvrio.cadastrocliente.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import br.com.bvrio.cadastrocliente.builders.ClienteBuilder;
import br.com.bvrio.cadastrocliente.conf.AppWebConfiguration;
import br.com.bvrio.cadastrocliente.conf.JPAConfiguration;
import br.com.bvrio.cadastrocliente.conf.SecurityConfiguration;
import br.com.bvrio.cadastrocliente.confs.DataSourceConfigurationTest;
import br.com.bvrio.cadastrocliente.daos.ClienteDAO;
import br.com.bvrio.cadastrocliente.exceptions.PersistenciaException;
import br.com.bvrio.cadastrocliente.models.Cliente;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class, AppWebConfiguration.class, ClienteDAO.class, DataSourceConfigurationTest.class, SecurityConfiguration.class})
@ActiveProfiles("test")
public class ClienteControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private Filter springSecurityFilterChain;
	
	@Autowired
	private ClienteDAO clienteDao;
	
	private MockMvc mockMvc;

	
	@Before
	public void setup(){ 
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(springSecurityFilterChain).build();
	}
	
	@Test
	public void deveRetornarParaAListaDeClientesComOsClientes() throws Exception{
		
		mockMvc.perform(get("/clientes")
				.with(SecurityMockMvcRequestPostProcessors
						.user("admin@bvrio.com").password("12345")
						.roles("ADMIN")))
				.andExpect(model().attributeExists("clientes"))
				.andExpect(forwardedUrl("/WEB-INF/views/clientes/lista.jsp"));
	}
	
	@Test
	public void deveExibirFormDeClientesComOsEstados() throws Exception{

		mockMvc.perform(get("/clientes/adicionar"))
				.andExpect(model().attributeExists("estados"))
				.andExpect(forwardedUrl("/WEB-INF/views/clientes/form.jsp"));
	}
	
	@Test
	@Transactional
	public void deveExibirFormDeAlteracaoDeCliente() throws Exception{
		Cliente cliente = alimentarObanco();
		
		mockMvc.perform(get("/clientes/"+cliente.getId()+"/alterar"))
			   .andExpect(model().attributeExists("estados", "clienteForm"))			   
			   .andExpect(forwardedUrl("/WEB-INF/views/clientes/form.jsp"));
	}
	
	@Test
	@Transactional
	public void deveRetornarParaODetalheDeClienteCasoExista() throws Exception{
		
		Cliente cliente = alimentarObanco();
		
		mockMvc.perform(get("/clientes/"+cliente.getId()))
				.andExpect(model().attributeExists("cliente"))
				.andExpect(forwardedUrl("/WEB-INF/views/clientes/detalhe.jsp"));
	}
	
	@Test
	public void deveRetornarParaAPaginaDeErroCasoNaoExistaClienteAoAcessarDetalhe() throws Exception{
		
			mockMvc.perform(get("/clientes/89"))
				   .andExpect(model().attributeExists("exception"))
				   .andExpect(forwardedUrl("/WEB-INF/views/error.jsp"));
	}
	
	//testando comportamento do sistema no envio do formulário
	@Test
	@Transactional
	public void deveGravarCliente() throws Exception{
		Cliente cliente = ClienteBuilder.newCliente().buildOne();		
		mockMvc.perform(post("/clientes")
					.param("nome", cliente.getNome())
					.param("email", cliente.getEmail())
					.param("dataNascimento", "19/08/1990")
					.param("endereco.logradouro", cliente.getEndereco().getLogradouro())
					.param("endereco.bairro", cliente.getEndereco().getBairro())
					.param("endereco.cidade", cliente.getEndereco().getCidade())
					.param("endereco.estado", cliente.getEndereco().getEstado().toString())
					.param("endereco.cep", cliente.getEndereco().getCep())
				.with(SecurityMockMvcRequestPostProcessors
					   .user("admin@bvrio.com.br")
					   .password("12345")
					   .roles("ADMIN")
					   )
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andDo(print())
				.andExpect(flash().attributeExists("css", "msg"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/clientes"));
	}
	
	@Test
	public void deveRetornarParaFormCasoHajaErrosEmGeral() throws Exception{
		mockMvc.perform(post("/clientes")
				.with(SecurityMockMvcRequestPostProcessors
						   .user("admin@bvrio.com.br")
						   .password("12345")
						   .roles("ADMIN")
						   )
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(model().hasErrors())
		.andExpect(forwardedUrl("/WEB-INF/views/clientes/form.jsp"));
	}
	
	@Test
	@Transactional
	public void deveRetornarParaFormCasoEmailJaExista() throws Exception{
		Cliente cliente = alimentarObanco();
		
		mockMvc.perform(post("/clientes")
				.param("nome", cliente.getNome())
				.param("email", cliente.getEmail())
				.param("dataNascimento", "19/08/1990")
				.param("endereco.logradouro", cliente.getEndereco().getLogradouro())
				.param("endereco.bairro", cliente.getEndereco().getBairro())
				.param("endereco.cidade", cliente.getEndereco().getCidade())
				.param("endereco.estado", cliente.getEndereco().getEstado().toString())
				.param("endereco.cep", cliente.getEndereco().getCep())
				.with(SecurityMockMvcRequestPostProcessors
						   .user("admin@bvrio.com.br")
						   .password("12345")
						   .roles("ADMIN")
						   )
				.with(SecurityMockMvcRequestPostProcessors.csrf()
		))
		.andDo(print())
		.andExpect(model().attributeHasFieldErrorCode("clienteForm", "email", "field.required.cliente.email.unico"))
		.andExpect(model().attributeExists("estados"))
		.andExpect(forwardedUrl("/WEB-INF/views/clientes/form.jsp"));
		
		List<Cliente> list = clienteDao.listaTodos();
		
		//verifica se não persistiu
		assertThat(list.size(), equalTo(1));
	}
	
	
	/**
	 * Particionamento em classes de equivalência
	 * permitir idade >=16
	 *
	 *	casos	classes / casos de teste
	 *	1		> 16	/ 20  ok
	 *	2		< 16    / 10  erro
	 *	3		= 16    / 16  ok
	 */
	
	@Test
	public void deveValidarIdadeFormCaso1MaiorQue16() throws Exception{
		mockMvc.perform(post("/clientes")
				.param("dataNascimento", "10/10/1997" )
				.with(SecurityMockMvcRequestPostProcessors
						   .user("admin@bvrio.com.br")
						   .password("12345")
						   .roles("ADMIN")
						   )
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				).andExpect(((model().attributeErrorCount("clienteForm", 7))));
	}
	
	@Test
	public void deveValidarIdadeFormCaso2Menor16() throws Exception{
		mockMvc.perform(post("/clientes")
				.param("dataNascimento", "10/10/2007" )
				.with(SecurityMockMvcRequestPostProcessors
						   .user("admin@bvrio.com.br")
						   .password("12345")
						   .roles("ADMIN")
						   )
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				)
		.andDo(print())
		.andExpect(((model().attributeHasFieldErrorCode("clienteForm", "dataNascimento", "field.required.cliente.dataNascimento.idade"))))
		.andExpect(((model().attributeErrorCount("clienteForm", 8))));
		
	}
	
	@Test
	public void deveValidarIdadeFormCaso3Igual16() throws Exception{
		mockMvc.perform(post("/clientes")
				.param("dataNascimento", "15/09/2001" )
				.with(SecurityMockMvcRequestPostProcessors
						   .user("admin@bvrio.com.br")
						   .password("12345")
						   .roles("ADMIN")
						   )
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				)
		.andDo(print())
		.andExpect(((model().attributeErrorCount("clienteForm", 7))));
		
	}
	
	
	/*
	 * 
	 * Análise de valor limite
	 * Permitir Idade >=16  
	 * 	casos	classes | casos de testes
	 *   1		>= 16   | 17 ok
	 * 	 2		<  16 	| 15 erro
	 */
	
	@Test
	public void deveValidarIdadeFormCaso1ValorLimite16() throws Exception{
		mockMvc.perform(post("/clientes")
				.param("dataNascimento", "15/09/2000" )
				.with(SecurityMockMvcRequestPostProcessors
						   .user("admin@bvrio.com.br")
						   .password("12345")
						   .roles("ADMIN")
						   )
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				)
		.andDo(print())
		.andExpect(((model().attributeErrorCount("clienteForm", 7))));
		
	}
	
	@Test
	public void deveValidarIdadeFormCaso2ValorLimite16() throws Exception{
		mockMvc.perform(post("/clientes")
				.param("dataNascimento", "15/09/2002")
				.with(SecurityMockMvcRequestPostProcessors
						   .user("admin@bvrio.com.br")
						   .password("12345")
						   .roles("ADMIN")
						   )
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				)
		.andDo(print())
		.andExpect(((model().attributeHasFieldErrorCode("clienteForm", "dataNascimento", "field.required.cliente.dataNascimento.idade"))))
		.andExpect(((model().attributeErrorCount("clienteForm", 8))));
		
	}
	
	
	/*fim dos testes do formulário do cliente*/
	
	@Test
	@Transactional
	public void deveApagarCliente() throws Exception{
		Cliente cliente = alimentarObanco();
		
		mockMvc.perform(post("/clientes/"+cliente.getId()+"/remover")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.with(SecurityMockMvcRequestPostProcessors
						.user("admin@bvrio.com")
						.password("12345")
						.roles("ADMIN")))
			   .andDo(print())
			   .andExpect(redirectedUrl("/clientes"));
	}
	
	@Test
	@Transactional
	public void deveExibirPaginaDeErroAoTentarApagarCliente() throws Exception{
		
		mockMvc.perform(post("/clientes/78/remover")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.with(SecurityMockMvcRequestPostProcessors
						.user("admin@bvrio.com")
						.password("12345")
						.roles("ADMIN")))
			   .andDo(print())
			   .andExpect(forwardedUrl("/WEB-INF/views/error.jsp"));
	}

	@Test
	public void permiteUsuarioAdminDeveAcessarClientesForm() throws Exception{
		mockMvc.perform(get("/clientes/adicionar")
				.with(SecurityMockMvcRequestPostProcessors
						.user("admin@bvrio.com.br").password("12345")
						.roles("ADMIN")))
				.andExpect(status().is(200));
	}
	
	@Test
	public void somenteAdminDeveAcessarClientesForm() throws Exception{
		mockMvc.perform(get("/clientes")
				.with(SecurityMockMvcRequestPostProcessors
						.user("admin@bvrio.com.br").password("12345")
						.roles("USUARIO")))
				.andExpect(status().is(403));
	}
	
	private Cliente alimentarObanco() throws PersistenciaException {
		Cliente cliente = ClienteBuilder.newCliente().buildOne();
		clienteDao.adiciona(cliente);
		return cliente;
	}
}
