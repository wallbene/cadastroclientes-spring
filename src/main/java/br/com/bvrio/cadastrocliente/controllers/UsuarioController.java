package br.com.bvrio.cadastrocliente.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.bvrio.cadastrocliente.daos.UsuarioDAO;
import br.com.bvrio.cadastrocliente.exceptions.PersistenciaException;
import br.com.bvrio.cadastrocliente.models.Usuario;
import br.com.bvrio.cadastrocliente.validation.UsuarioValidation;

@Controller
@RequestMapping("/usuarios")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class UsuarioController {
	
	@Autowired
	private UsuarioDAO dao;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder){
		binder.addValidators(new UsuarioValidation());
	}

	@RequestMapping(value="/adicionar", method=GET)
	public String formAdicionar(Model model){
		System.out.println("usuario form adicionar");
		Usuario usuario = new Usuario();
		model.addAttribute(usuario);
		return "usuarios/form";	
	}
	
	@RequestMapping(method=POST)
	public String salvar(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult result, 
			Model model, final RedirectAttributes flash){
		System.out.println("metodo salvar");
		
		//Verifica se o resultado das validações do formulário contém erros.
		if(result.hasErrors()) 
			return "usuarios/form";
		
		//criptografar a senha
		String criptogradado = criptografa(usuario.getSenha());
		usuario.setSenha(criptogradado);
		
		try {
			dao.adiciona(usuario);
		}catch (PersistenciaException e) {	
			result.rejectValue("email", "field.required.usuarios.email.unico");
			return "usuarios/form";
		}
		
		
		//Adiciona objetos no corpo de Flash
		flash.addAttribute("msg", "Usuario criado com sucesso!")
			 .addAttribute("css", "success");
		
		return "redirect:/";
	}

	private String criptografa(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String criptogradado = encoder.encode(senha);
		return criptogradado;
	}
}