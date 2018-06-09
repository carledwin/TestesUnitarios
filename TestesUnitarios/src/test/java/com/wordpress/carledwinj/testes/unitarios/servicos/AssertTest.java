package com.wordpress.carledwinj.testes.unitarios.servicos;

import org.junit.Assert;
import org.junit.Test;

import com.wordpress.carledwinj.testes.unitarios.entidades.Locacao;
import com.wordpress.carledwinj.testes.unitarios.entidades.Usuario;


public class AssertTest {

	@Test
	public void test() {
		Assert.assertTrue("Era esperado que fosse verdadeiro", true);
		Assert.assertFalse(false);
		Assert.assertEquals(1,1);
		Assert.assertNotEquals(1, 2);
		//Assert.assertEquals(0.51d, 0.51d); //depreciado pois precisa da declaracao do delta para margem de erro de comparacao.
		Assert.assertEquals("Era esperado o falor 0.51", 0.51d, 0.51d, 0.01);
		Assert.assertEquals(Math.PI, 3.14, 0.01);
		
		int intt = 5;
		Integer integer = 5;
		
		//deve ser declarado na ordem correta assert(expected, actual)
		
		Assert.assertEquals(Integer.valueOf(intt), integer);
		Assert.assertEquals(intt, integer.intValue());
		
		Assert.assertEquals("bola", "bola");
		
		Assert.assertTrue("bola".equalsIgnoreCase("BOLA"));
		Assert.assertFalse("bola".equals("BOLA"));
		
		Usuario usuario1 = new Usuario("Usuario 1");
		Usuario usuario2 = new Usuario("Usuario 1");
		Usuario usuario3 = null;
		Usuario usuario4 = new Usuario("Usuario 4");
		
		//Verifica se a sao iguais
		//
		
		//verifica se sao a mesma intancia
		Assert.assertSame(usuario1, usuario1);
		
		//verifica se nao sao a mesma intancia
		Assert.assertNotSame(usuario1, usuario2);
		
		Assert.assertNull(usuario3);
		
		Assert.assertNotNull(usuario2);
		
		Assert.assertNotEquals(usuario1, usuario4);
		
	}
}
