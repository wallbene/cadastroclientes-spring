package br.com.bvrio.cadastrocliente.models;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ClienteTest {

	
	@Test
	public void devecalcularIdade(){
		
		Calendar idade = new Calendar.Builder().setDate(2001, Calendar.SEPTEMBER, 15).build();	
		Date aux = idade.getTime();
		Calendar data = Calendar.getInstance();
		data.setTime(aux);
		
		Cliente cliente = new Cliente();
		cliente.setDataNascimento(idade);
		
		assertEquals(16, cliente.getIdade());
		
		//Data deve permanecer imutável após o calculo da idade.
		assertEquals(data.getTime(), cliente.getDataNascimento().getTime());
	}	
	
	
	@Test
	public void deveConferirOValorCriptografado(){
		String encode = new BCryptPasswordEncoder().encode("12345");
		
		System.out.println(encode.toString());
		
	}

}
