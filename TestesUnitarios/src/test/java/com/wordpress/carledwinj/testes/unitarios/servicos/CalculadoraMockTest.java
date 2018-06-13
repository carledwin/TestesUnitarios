package com.wordpress.carledwinj.testes.unitarios.servicos;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.wordpress.carledwinj.testes.unitarios.entidades.Calculadora;

public class CalculadoraMockTest {

	
	@Test
	public void testeSucessoCaptor() {
		
		//cenario
		Calculadora calculadora = Mockito.mock(Calculadora.class);
		
		//espectativa de captura
		ArgumentCaptor<Integer> argumentCaptorInteger = ArgumentCaptor.forClass(Integer.class);
		
		//espectativa com captor
		/*Mockito.when(calculadora.soma(1, 2)).thenReturn(5); //ok*/
		//Mockito.when(calculadora.soma(argumentCaptorInteger.capture(), 2)).thenReturn(5);//erro pois os dois devem ser matchers
		//Mockito.when(calculadora.soma(argumentCaptorInteger.capture(), Mockito.anyInt())).thenReturn(5);// ok
		Mockito.when(calculadora.soma(argumentCaptorInteger.capture(), argumentCaptorInteger.capture())).thenReturn(5);
		
		//acao
		System.out.println("testeSucesso  >>>>  " + calculadora.soma(1, 2));
		
		//captura
		System.out.println("Captor last value: " + argumentCaptorInteger.getValue());
		System.out.println("Captor all values: " + argumentCaptorInteger.getAllValues());
	}
	
	@Test
	public void testeSucesso() {
		
		//cenario
		Calculadora calculadora = Mockito.mock(Calculadora.class);
		
		//espectativa
		Mockito.when(calculadora.soma(1, 2)).thenReturn(5);
		
		//acao
		System.out.println("testeSucesso  >>>>  " + calculadora.soma(1, 2));
	}
	
	@Test
	public void testeSucessoComRestricaoDeValorPodendoSerTudoMatcherComAlgunsEqs() {
		
		//cenario
		Calculadora calculadora = Mockito.mock(Calculadora.class);
		
		//espectativa
		Mockito.when(calculadora.soma(Mockito.eq(1), Mockito.anyInt())).thenReturn(5);
		
		//acao
		System.out.println("testeSucessoComRestricaoDeValorPodendoSerTudoMatcherComAlgunsEqs ** >>>>  " + calculadora.soma(1, 20000000));
	}
	
	@Test
	public void testeComErroDevidoParametroNaChamadarealSerDiferenteDoDeclaradoNoEqOMockitoRetornaValorPadraoDoMetodo() {
		
		//cenario
		Calculadora calculadora = Mockito.mock(Calculadora.class);
		
		//espectativa
		Mockito.when(calculadora.soma(Mockito.eq(1), Mockito.anyInt())).thenReturn(5);
		
		//acao
		System.out.println("testeComErroDevidoParametroNaChamadarealSerDiferenteDoDeclaradoNoEqOMockitoRetornaValorPadraoDoMetodo  >>>>  " + calculadora.soma(2, 2));
	}
	
	@Test
	public void testeRetornoValorPadraoMockitoQuandoNaoSabeOQueFazer() {
		
		//cenario
		Calculadora calculadora = Mockito.mock(Calculadora.class);
		
		//espectativa
		Mockito.when(calculadora.soma(1, 2)).thenReturn(5);
		
		//acao
		//entrada com valor diferente do declarado na espectativa
		System.out.println("testeRetornoValorPadraoMockitoQuandoNaoSabeOQueFazer >>>  " + calculadora.soma(1, 8));//ira retornar o valor padrao de retorno do metodo
	}
	
	@Ignore
	@Test
	public void testeMockParcialDeveriaDeclaraDoisMatchersLancaException() {
		
		//cenario
		Calculadora calculadora = Mockito.mock(Calculadora.class);
		
		//espectativa
		Mockito.when(calculadora.soma(1, Mockito.anyInt())).thenReturn(5);
		
		//acao
		//entrada com valor diferente do declarado na espectativa
		System.out.println(calculadora.soma(1, 8));//ira retornar o valor padrao de retorno do metodo
	}
	
	@Test
	public void testeMockDeclaraDoisMatchersSucessoExecucao() {
		
		//cenario
		Calculadora calculadora = Mockito.mock(Calculadora.class);
		
		//espectativa
		Mockito.when(calculadora.soma(Mockito.anyInt(), Mockito.anyInt())).thenReturn(5);
		
		//acao
		//entrada com valor diferente do declarado na espectativa
		System.out.println("testeMockDeclaraDoisMatchersSucessoExecucao >>> " + calculadora.soma(1, 8));//ira retornar o valor padrao de retorno do metodo
	}
}
