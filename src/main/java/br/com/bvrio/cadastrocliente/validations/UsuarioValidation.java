package br.com.bvrio.cadastrocliente.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.bvrio.cadastrocliente.models.Usuario;
import br.com.bvrio.cadastrocliente.services.UsuarioService;

@Component
public class UsuarioValidation implements Validator {
	
	@Autowired
	private EmailValidation emailValidation;
	
	@Autowired
	private UsuarioService usuarioService;

	@Override
	public boolean supports(Class<?> clazz) {
		System.out.println("metodo supports do Usuáriovalidation");
		 return Usuario.class.isAssignableFrom(clazz);
		 
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "field.required.usuarios.nome");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required.usuarios.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "senha", "field.required.usuarios.senha");
		
		Usuario usuario = (Usuario) target;
		
		
		if(usuario.getEmail() != null){
			//valida formato do email
			if(!emailValidation.patternIsValid(usuario.getEmail())){
				errors.rejectValue("email", "field.pattern.usuarios.email");
			}
			//valida se já existe usuário com esse email
			if(usuarioService.buscaPorEmail(usuario.getEmail()) != null){
				errors.rejectValue("email", "field.required.usuarios.email.unico");
			}
		}
	}
}
