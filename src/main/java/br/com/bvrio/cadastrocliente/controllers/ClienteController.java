package br.com.bvrio.cadastrocliente.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.bvrio.cadastrocliente.daos.ClienteDAO;
import br.com.bvrio.cadastrocliente.daos.PersistenciaException;
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
	
	
	@InitBinder
	public void InitBinder(WebDataBinder binder){
		binder.addValidators(new ClienteValidation());
	}
	
	
	@ExceptionHandler(PersistenciaException.class)
	public void conflict(Exception e){
		System.out.println("Erro causado por uma tentativa de enviar Email repetido");
		e.printStackTrace();
		
	}
	
	@RequestMapping(method=GET)
	@Cacheable(value="listaClientes")
	public String listar(Model model){
		
		List<Cliente> clientes = dao.listaTodos();
		
		model.addAttribute("clientes", clientes);
		
		return "clientes/lista";	
	}
	
	//Redirect after POST
	// grava ou atualiza cliente
	@CacheEvict(value="listaClientes", allEntries=true)
	@RequestMapping(method=POST)
	public String gravaOuAtualiza(@ModelAttribute("clienteForm") @Valid Cliente cliente, BindingResult result, 
			Model model, final RedirectAttributes flash){
		
		//caso haja erros de validação dos dados do formulário
		if(result.hasErrors()){
			model.addAttribute("estados", EstadosEnum.values());
			return "clientes/form";
		}
		
		//Se usuário já existe atualiza
		
		if(cliente.getId() != null){
			
			flash.addFlashAttribute("css", "success");
			flash.addFlashAttribute("msg", "Usuário alterado com sucesso!");
			dao.atualiza(cliente);
			
		}else{
			//tratando em caso de email existente no banco 
			try {
				dao.adiciona(cliente);
			}catch (PersistenciaException e) {	
				result.rejectValue("email", "field.required.cliente.email.unico");
				return "clientes/form";
			}			
			
		}
		
			flash.addFlashAttribute("msg", "Usuário adicionado com sucesso!");		
			flash.addFlashAttribute("css", "success");
		
		return "redirect:/clientes"; 
		
	}
	//exibe detalhes de um cliente
	@RequestMapping("/{id}")
	public String detalhar(@PathVariable("id") Integer id, Model model){
		
		Cliente cliente = dao.buscaPorId(id);
		model.addAttribute("cliente", cliente);
		
		System.out.println("entrou no detalhar");
		
		return "clientes/detalhe";
		
	}
	
	
	// mostra o formulário de adicionar
	@RequestMapping(value="/adicionar", method=GET)
	public String formAdicionar(Model model){
		System.out.println("metodo form");
		
		Cliente cliente = new Cliente();
		
		model.addAttribute("estados", estados);
		model.addAttribute("clienteForm", cliente);
		
		
		return "clientes/form";
	}
	
	// mostra o formulário de alterar
	@RequestMapping(value="/{id}/alterar", method=GET)
	public String formAlterar(@PathVariable("id") Integer id, Model model){
		System.out.println("entrou no alterar");
		
		Cliente cliente = dao.buscaPorId(id);
		
		model.addAttribute("estados", estados);
		model.addAttribute("clienteForm", cliente);
		
		return "clientes/form";
	}
	
	//Redirect after POST
	//remove cliente
	@CacheEvict(value="listaClientes", allEntries=true)
	@RequestMapping(value="/{id}/remover", method = POST)
	public String remover(@PathVariable("id") Integer id, Model model, HttpServletResponse response, RedirectAttributes flash){
		
		//refatorar para um serviceCliente
		Cliente cliente = dao.buscaPorId(id);
		dao.remove(cliente);
		
		//escopo de flash 
		flash.addFlashAttribute("msg", "Cliente removido com Sucesso!");
		flash.addFlashAttribute("css", "success");
		
		return "redirect:/clientes";  
	}
}