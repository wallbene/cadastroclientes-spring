package br.com.bvrio.cadastrocliente.controllers;

import javax.persistence.OptimisticLockException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import br.com.bvrio.cadastrocliente.exceptions.ClienteNotFoundException;

@ControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(Exception.class)
	public ModelAndView tratamentoGenerico(Exception exception){
		System.out.println("Erro genérico acontecendo");
		exception.printStackTrace();
		ModelAndView view = new ModelAndView("error");

		view.addObject("exception", "Desculpa, ocorreu um problema inesperado.");
		
		return view;
	}
	
	@ExceptionHandler(ClienteNotFoundException.class)
	public ModelAndView trataClienteNaoEncontrado(Exception exception){
		System.out.println("Erro causado por uma tentativa de buscar um cliente com id não existente no banco");
		exception.printStackTrace();
		ModelAndView view = new ModelAndView("error");
		view.addObject("exception", exception.getMessage());
		System.out.println("returnando a página de erro");
		return view;
	}
	
	@ExceptionHandler(OptimisticLockException.class)
	public ModelAndView trataLockOtimista(Exception exception){
		System.out.println("Erro genérico acontecendo");
		exception.printStackTrace();
		ModelAndView view = new ModelAndView("error");
		view.addObject("exception", "Essa informação está desatualizada, "
				+ "Esse problema ocorre geralmente quando duas pessoas estão alterando o mesmo recurso.");
		
		return view;
	}
	
}
