package br.com.bvrio.cadastrocliente.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.bvrio.cadastrocliente.models.Cliente;

@Component
public class ClienteValidation implements Validator {
	
	private final String errorCode = "field.required.cliente.";
	
	/*@Autowired
	private ClienteDAO dao;*/

	@Override
	public boolean supports(Class<?> Clazz) {
		return Cliente.class.isAssignableFrom(Clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		System.out.println("validando");
		ValidationUtils.rejectIfEmpty(errors, "nome", errorCode+"nome");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", errorCode+"email");
		ValidationUtils.rejectIfEmpty(errors, "dataNascimento", errorCode+"dataNascimento");
		
		
		//valores do Endereço do cliente
		ValidationUtils.rejectIfEmpty(errors, "endereco.logradouro", errorCode+"endereco.logradouro");
		ValidationUtils.rejectIfEmpty(errors, "endereco.bairro", errorCode+"endereco.bairro");
		ValidationUtils.rejectIfEmpty(errors, "endereco.cidade", errorCode+"endereco.cidade");
		ValidationUtils.rejectIfEmpty(errors, "endereco.estado", errorCode+"endereco.estado");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endereco.cep", errorCode+"endereco.cep");
		
		
		Cliente cliente = (Cliente)target;
		
		//Não permitir clientes com idade inferior a 16
		
		if(cliente.getDataNascimento() != null && cliente.getIdade() < 16)
				errors.rejectValue("dataNascimento", "field.required.cliente.dataNascimento.idade");
		
		/*//Não permitir emails repetidos
		if(cliente.isNew()){
			if(dao.buscaPorEmail(cliente.getEmail()) != null){
				errors.rejectValue("email", "field.required.cliente.email.unico");
			}
			
		}*/
	}
}