package br.com.bvrio.cadastrocliente.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.bvrio.cadastrocliente.daos.ClienteDAO;
import br.com.bvrio.cadastrocliente.exceptions.ClienteNotFoundException;
import br.com.bvrio.cadastrocliente.exceptions.PersistenciaException;
import br.com.bvrio.cadastrocliente.models.Cliente;
import br.com.bvrio.cadastrocliente.models.EstadosEnum;
import br.com.bvrio.cadastrocliente.validation.ClienteValidation;

@Controller
@RequestMapping("/clientes")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class ClienteController {
	
	@Autowired
	private ClienteDAO dao;
	
	EstadosEnum[] estados = EstadosEnum.values();
	
	/*@Autowired
	private DataSource dataSource;*/
	
	@InitBinder
	public void InitBinder(WebDataBinder binder){
		binder.addValidators(new ClienteValidation());
	}
	
	@Cacheable(value="listaClientes")
	@RequestMapping(method=GET)
	public ModelAndView listar(){
		
		List<Cliente> clientes = dao.listaTodos();
		ModelAndView view = new ModelAndView("clientes/lista");
		view.addObject("clientes", clientes);
		
		return view;	
	}
	
	
	// grava ou atualiza cliente
	@CacheEvict(value="listaClientes", allEntries=true)
	@RequestMapping(method=POST)
	public ModelAndView gravaOuAtualiza(@ModelAttribute("clienteForm") @Valid Cliente cliente, BindingResult result, final RedirectAttributes flash){
		
		//caso haja erros de validação dos dados do formulário
		if(result.hasErrors()){
			ModelAndView view = new ModelAndView("clientes/form");
			view.addObject("estados", estados);
			return view;
		}
		//Se usuário já existir, então atualiza
		
		if(cliente.isNew()){
			try {
				dao.adiciona(cliente);
				flash.addFlashAttribute("msg", "Cliente adicionado com sucesso!")
					 .addFlashAttribute("css", "success");						
			}catch (PersistenciaException e) {
			
				result.rejectValue("email", "field.required.cliente.email.unico");
			
				ModelAndView view = new ModelAndView("clientes/form");
				view.addObject("estados", this.estados);
				return view;
		}				
		}else{
			//tratando no caso de email já existir no banco(somente na ação de adicionar )
				dao.atualiza(cliente);
				flash.addFlashAttribute("msg", "Cliente alterado com sucesso!")
				.addFlashAttribute("css", "success");
				
		}
		return new ModelAndView("redirect:/clientes");//Redirect after POST 
	}
	//exibe detalhes de um cliente
	@RequestMapping("/{id}")
	public ModelAndView detalhar(@PathVariable("id") Integer id) throws InterruptedException, SQLException{
		
		Cliente cliente = dao.buscaPorId(id);
		if(cliente ==null) throw new ClienteNotFoundException("Cliente não encontrado");
		
		ModelAndView view = new ModelAndView("clientes/detalhe");
		view.addObject("cliente", cliente);
		
		System.out.println("entrou no detalhar");
		
		
		/*
		 * teste manual do pool de conexão
		 * 
		 * ComboPooledDataSource data =  (ComboPooledDataSource) dataSource;
		System.out.println("existentes :"+ data.getNumConnectionsDefaultUser());
		System.out.println("Ocupadas: "+data.getNumBusyConnectionsDefaultUser());
		System.out.println("Ociosas: "+data.getNumIdleConnectionsDefaultUser());		
		System.out.println();
		Thread.sleep(20000);*/
		
		return view;
		
	}
	
	
	// mostra o formulário de adicionar
	@RequestMapping(value="/adicionar", method=GET)
	public ModelAndView formAdicionar(){
		System.out.println("metodo form");
		
		Cliente cliente = new Cliente();
		
		ModelAndView view = new ModelAndView("clientes/form");
		view.addObject("estados", estados)
			.addObject("clienteForm", cliente);
		
		return view;
	}
	
	// mostra o formulário de alterar
	@RequestMapping(value="/{id}/alterar", method=GET)
	public ModelAndView formAlterar(@PathVariable("id") Integer id) throws SQLException{
		System.out.println("entrou no alterar");
		
		Cliente cliente = dao.buscaPorId(id);
		
		if(cliente ==null) throw new ClienteNotFoundException("Cliente não encontrado");
		
		ModelAndView view = new ModelAndView("clientes/form");
		view.addObject("estados", estados)
			.addObject("clienteForm", cliente);
		
		return view;
	}
	
	
	//remove cliente
	@CacheEvict(value="listaClientes", allEntries=true)
	@RequestMapping(value="/{id}/remover", method = POST)
	public ModelAndView remover(@PathVariable("id") Integer id, HttpServletResponse response, RedirectAttributes flash){
		
		//refatorar para um serviceCliente
		Cliente cliente = dao.buscaPorId(id);
		if(cliente ==null) throw new ClienteNotFoundException("Cliente não encontrado");
		
		dao.remove(cliente);
		response.setStatus(200);
		
		//escopo de flash 
		flash.addFlashAttribute("msg", "Cliente removido com Sucesso!")
			 .addFlashAttribute("css", "success");
		  
		return new ModelAndView("redirect:/clientes");//Redirect after POST
	}
}