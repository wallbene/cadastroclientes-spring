package br.com.bvrio.cadastrocliente.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.bvrio.cadastrocliente.models.Usuario;

public class UsuarioValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		System.out.println("metodo supports do Usuáriovalidation");
		 return Usuario.class.isAssignableFrom(clazz);
		 
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "nome", "field.required.usuarios.nome");
		ValidationUtils.rejectIfEmpty(errors, "email", "field.required.usuarios.email");
		ValidationUtils.rejectIfEmpty(errors, "senha", "field.required.usuarios.senha");

	}

}
