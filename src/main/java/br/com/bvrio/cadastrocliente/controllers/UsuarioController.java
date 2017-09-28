package br.com.bvrio.cadastrocliente.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.bvrio.cadastrocliente.models.Usuario;
import br.com.bvrio.cadastrocliente.services.UsuarioService;
import br.com.bvrio.cadastrocliente.validations.UsuarioValidation;

@Controller
@RequestMapping("/usuarios")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private MailSender sender;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UsuarioValidation usuarioValidation;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder){
		binder.addValidators(usuarioValidation);
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
		
		if(result.hasErrors()) 
			return "usuarios/form";
		
		//criptografa a senha
		String criptogradada = criptografa(usuario.getSenha());
		usuario.setSenha(criptogradada);
		
			service.adiciona(usuario);
			
		/*enviaEmailCadastroUsuario(usuario);*/
			
		//Adiciona objetos no escopo de Flash
		flash.addAttribute("msg", "usuario.criado.sucesso")
			 .addAttribute("css", "success");
		
		return "redirect:/";
	}

	@SuppressWarnings("unused")
	private void enviaEmailCadastroUsuario(Usuario usuario) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject("Cadastro realizado com sucesso");
		email.setTo("wallbene@gmail.com");
		email.setText("Conta criada com sucesso");
		email.setFrom("wallbene@gmail.com");
		
		sender.send(email);
	}

	private String criptografa(String senha) {
		String criptogradado = encoder.encode(senha);
		return criptogradado;
	}
}