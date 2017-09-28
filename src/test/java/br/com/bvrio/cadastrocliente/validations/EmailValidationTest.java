package br.com.bvrio.cadastrocliente.validations;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmailValidationTest {
	
	private EmailValidation validation;
	
	@Before
	public void init(){
		validation = new EmailValidation();
	}
	
	@Test
	public void deveRetornarTrueParaEmailValido(){
		
		Assert.assertTrue(validation.patternIsValid("wallacebenevides@gmail.com"));
		Assert.assertTrue(validation.patternIsValid("wallacebenevides@gmail.com.br"));
		Assert.assertTrue(validation.patternIsValid("wallace2benevides@gmail.com"));
		Assert.assertTrue(validation.patternIsValid("12wallacebenevides43@gmail.com"));
		Assert.assertTrue(validation.patternIsValid("WallaceBenevides@gmail.com"));	
		Assert.assertTrue(validation.patternIsValid("WallaceBenevides@gmail12.com"));
	}
	
	@Test
	public void deveRetornarFalseParaEmailInvalido(){
		
		Assert.assertFalse(validation.patternIsValid("wallacebenevides@gmail.com12"));
		Assert.assertFalse(validation.patternIsValid("wallacebenevidesgmail.com.br"));
		Assert.assertFalse(validation.patternIsValid("wallace2benevides@gmail.1com"));
		Assert.assertFalse(validation.patternIsValid("12wallacebenevides43.com"));
		Assert.assertFalse(validation.patternIsValid("WallaceBenevides@.com"));
		Assert.assertFalse(validation.patternIsValid("WallaceBenevides@com"));
		Assert.assertFalse(validation.patternIsValid("WallaceBenevidescom"));
	}

}
