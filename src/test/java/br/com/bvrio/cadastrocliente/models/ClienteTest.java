package br.com.bvrio.cadastrocliente.models;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ClienteTest {
	
	@Test
	public void umClienteDeveTerIdadeSuperiorA16(){
		
		Calendar idade = new Calendar.Builder().setDate(1990, Calendar.AUGUST, 19).build();
		
		Date aux = idade.getTime();
		
		Calendar data = Calendar.getInstance();
		
		data.setTime(aux);
		
		Cliente cliente = new Cliente();
		
		cliente.setDataNascimento(idade);
		
		assertEquals(27, cliente.getIdade());
		
		assertEquals(data, cliente.getDataNascimento());
	}
	
	@Test
	public void deveConferirOValorCriptografado(){
		String encode = new BCryptPasswordEncoder().encode("12345");
		
		System.out.println(encode.toString());
		
	}

}
