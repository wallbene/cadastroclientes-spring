package br.com.bvrio.cadastrocliente.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.bvrio.cadastrocliente.models.Cliente;
import br.com.bvrio.cadastrocliente.services.ClienteService;

@Component
public class ClienteValidation implements Validator {
	
	private final String errorCode = "field.required.cliente.";
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailValidation emailValidation;

	@Override
	public boolean supports(Class<?> Clazz) {
		return Cliente.class.isAssignableFrom(Clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		System.out.println("validando");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", errorCode+"nome");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", errorCode+"email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dataNascimento", errorCode+"dataNascimento");
		//valores do Endereço do cliente
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endereco.cep", errorCode+"endereco.cep");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endereco.logradouro", errorCode+"endereco.logradouro");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endereco.bairro", errorCode+"endereco.bairro");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endereco.cidade", errorCode+"endereco.cidade");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endereco.estado", errorCode+"endereco.estado");
		
		Cliente cliente = (Cliente)target;
		
		//Não permitir clientes com idade inferior a 16
		if(cliente.getDataNascimento() != null 
				&& cliente.getIdade() < 16)
				errors.rejectValue("dataNascimento", "field.required.cliente.dataNascimento.idade");
		
		//Não permitir emails repetidos
		if(cliente.getEmail() != null){
			String email = cliente.getEmail();
			if(!emailValidation.patternIsValid(email)){
				errors.rejectValue("email", "field.pattern.cliente.email");
			}
			
			/*esse código verifica se o email inserido pelo usuario já existe no banco, e
			 *  caso não pertença a ale, então rejeita.*/ 
			Cliente clienteBuscado = clienteService.buscaPorEmail(email);
			if(clienteBuscado != null 
					&& cliente.getId() != clienteBuscado.getId()){
				errors.rejectValue("email", "field.required.cliente.email.unico");				 
			}
		}						
	}
}