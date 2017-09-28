package br.com.bvrio.cadastrocliente.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.bvrio.cadastrocliente.exceptions.ClienteNotFoundException;
import br.com.bvrio.cadastrocliente.models.Cliente;
import br.com.bvrio.cadastrocliente.models.EstadosEnum;
import br.com.bvrio.cadastrocliente.services.ClienteService;
import br.com.bvrio.cadastrocliente.validations.ClienteValidation;

@Controller
@RequestMapping("/clientes")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class ClienteController {
	
	private static final String CSS__NAME = "css";
	private static final String CSS_VALUE_SUCCESS = "success";
	private static final String MESSAGE_NAME = "msg";
	//messages.properties
	private static final String CLIENTE_ADD_SUCCESS = "cliente.adicionado.sucesso";
	private static final String CLIENTE_ALTER_SUCCESS = "cliente.alterado.sucesso";
	private static final String CLIENTE_REMOVE_SUCCESS = "cliente.alterado.sucesso";
	
	/*@Autowired
	private DataSource dataSource;*/
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteValidation clienteValidation;
	
	EstadosEnum[] estados = EstadosEnum.values();
	
	@InitBinder
	public void InitBinder(WebDataBinder binder){
		binder.addValidators(clienteValidation);
	}
	
	@Cacheable(value="listaClientes")
	@RequestMapping(method=GET)
	public String listar(Model model){
		
		model.addAttribute("clientes", clienteService.listaTodos());
		return "clientes/lista";	
	}
	
	
	// grava ou atualiza cliente
	@CacheEvict(value="listaClientes", allEntries=true)
	@RequestMapping(method=POST)
	public String gravaOuAtualiza(@ModelAttribute("clienteForm") @Valid Cliente cliente, BindingResult result 
			,Model model, final RedirectAttributes flash){
		
		//caso haja erros de validação dos dados do formulário
		if(result.hasErrors()){
			model.addAttribute("estados", estados);
			return "clientes/form";
		}
		//Se usuário já existir, então atualiza
		
		if(cliente.isNew())
			flash.addFlashAttribute(MESSAGE_NAME, CLIENTE_ADD_SUCCESS);			
		else
			flash.addFlashAttribute(MESSAGE_NAME, CLIENTE_ALTER_SUCCESS);
		
		flash.addFlashAttribute(CSS__NAME, CSS_VALUE_SUCCESS);
		
		clienteService.salva(cliente);
					
		return "redirect:/clientes";//Redirect after POST 
	}
	
	//exibe detalhes de um cliente
	@RequestMapping("/{id}")
	public String detalhar(@PathVariable("id") Integer id, Model model){
		Cliente cliente = clienteService.buscaPorId(id);
		if(cliente ==null) throw new ClienteNotFoundException("Cliente não encontrado");
		
		model.addAttribute("cliente", cliente);
		
		System.out.println("entrou no detalhar");
		
		/*
		 * teste manual do pool de conexão
		 * 
		 * descomente esse bloco, descomente a variável membro que referencia um DataSource, 
		 * trate as exception e depois importe as classes com  Ctrl + shift + O; 
		 * 
		 * ComboPooledDataSource data =  (ComboPooledDataSource) dataSource;
		System.out.println("existentes :"+ data.getNumConnectionsDefaultUser());
		System.out.println("Ocupadas: "+data.getNumBusyConnectionsDefaultUser());
		System.out.println("Ociosas: "+data.getNumIdleConnectionsDefaultUser());		
		System.out.println();
		Thread.sleep(20000);*/
		
		return "clientes/detalhe";
		
	}
	
	// exibe o formulário de adicionar
	@RequestMapping(value="/adicionar", method=GET)
	public String formAdicionar(Model model){
		System.out.println("metodo form");
		Cliente cliente = new Cliente();
		
		model.addAttribute("estados", estados)
			.addAttribute("clienteForm", cliente);
		
		return "clientes/form";
	}
	
	// exibe o formulário de alterar
	@RequestMapping(value="/{id}/alterar", method=GET)
	public String formAlterar(@PathVariable("id") Integer id, Model model) throws SQLException{
		System.out.println("entrou no alterar");
		
		Cliente cliente = clienteService.buscaPorId(id);
		
		if(cliente ==null) throw new ClienteNotFoundException("Cliente não encontrado");
		
		model.addAttribute("estados", estados)
			.addAttribute("clienteForm", cliente);
		
		return "clientes/form";
	}
	
	
	//remove cliente
	@CacheEvict(value="listaClientes", allEntries=true)
	@RequestMapping(value="/{id}/remover", method = POST)
	public String remover(@PathVariable("id") Integer id, HttpServletResponse response, RedirectAttributes flash){
		
		Cliente cliente = clienteService.buscaPorId(id);
		if(cliente ==null) throw new ClienteNotFoundException("Cliente não encontrado");
		
		clienteService.remove(cliente);
		response.setStatus(200);
		
		//escopo de flash 
		flash.addFlashAttribute(MESSAGE_NAME, CLIENTE_REMOVE_SUCCESS)
			 .addFlashAttribute(CSS__NAME, CSS_VALUE_SUCCESS);
		  
		return "redirect:/clientes";//Redirect after POST
	}
}