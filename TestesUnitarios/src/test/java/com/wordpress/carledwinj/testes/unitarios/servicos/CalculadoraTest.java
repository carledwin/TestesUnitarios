package com.wordpress.carledwinj.testes.unitarios.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.wordpress.carledwinj.testes.unitarios.entidades.Calculadora;
import com.wordpress.carledwinj.testes.unitarios.exception.NaoPodeDividirPorZeroException;


public class CalculadoraTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none(); 
	
	private Calculadora calculadora;
	
	@Before
	public void setUp() {
		calculadora = new Calculadora();
	}
	
	@Test
	public void deveSomarDoisValores() {
		
		//cenario
		int a = 5;
		int b = 3;
		Calculadora calculadora = new Calculadora();
		
		//acao
		int resultado = calculadora.soma(a, b);
		
		//verificacao
		Assert.assertEquals(8, resultado);
	}
	
	@Test
	public void deveSubtrairDoisValores() {
		
		//cenario
		int a = 5;
		int b = 3;
		Calculadora calculadora = new Calculadora();
		
		//acao
		int resultado = calculadora.subtracao(a, b);
		
		//verificacao
		Assert.assertEquals(2, resultado);
	}
	
	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		
		//cenario
		int a = 12;
		int b = 3;
		
		//acao
		int resultado = calculadora.disao(a, b);
		
		//verificacao
		Assert.assertEquals(4, resultado);
	}
	
	@Test
	public void deveNaoPodeDividirPorZeros() throws NaoPodeDividirPorZeroException {
		
		//cenario
		int a = 12;
		int b = 0;
		//verificacao
		expectedException.expect(NaoPodeDividirPorZeroException.class);
		expectedException.expectMessage("O numero divisor nao pode ser menor ou igual a zero.");
		//acao
		calculadora.disao(a, b);
	}
	
	
}
