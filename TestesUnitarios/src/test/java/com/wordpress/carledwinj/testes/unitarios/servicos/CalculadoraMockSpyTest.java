package com.wordpress.carledwinj.testes.unitarios.servicos;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.wordpress.carledwinj.testes.unitarios.entidades.Calculadora;

public class CalculadoraMockSpyTest {

	@Spy
	private Calculadora calculadoraSpy;
	
	@Mock
	private Calculadora calculadoraMock;
	
	/*
	 * Nao pode utilizar SPY para interfaces, somente para classes concretas
	 * 
	 ***** Somente o Mock pode ser utilizado em interfaces, nao havendo a necessidade de implementar 
	 * ****os metodos da interface, tao pouco criar uma classe concreta da mesma
	 * 
	 * @Spy
	private EmailService emailService;
	*/
	
	//Esta interface nao possui implementacao, mesmo assim pode ser Mockada
	@Mock
	private EmailService emailService;
	
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void deveEvitarExecucaoRealDoMetodoSoma() {
			
			//expectativa
			/*Mockito.when(calculadoraMock.soma(60, 2)).thenReturn(878776);//Mock nunca executa/chama o metodo
			Mockito.when(calculadoraSpy.soma(55, 9)).thenReturn(878776);//SPY sempre executa/chama o metodo*/
		
			/**
			 * O Java tenta chamar 'calculadoraSpy.soma(55, 9)' 
			 * quando o metodo e declarado na expectativa
			 * o que ocasiona a execucao do mesmo
			 * 
			 * para evitar isto alteramos a forma de declarar a expectativa para
			 * >>> 'doReturn(878776).when(calculadoraMock).soma(60, 2)'
			 * 
			 */
		
			Mockito.doReturn(878776).when(calculadoraMock).soma(60, 2);
			Mockito.doReturn(878776).when(calculadoraSpy).soma(55, 9);
		
			//acao
			//
			System.out.println("Resultado Mock:  " + calculadoraMock.soma(60, 2));//Mock nao executa o metodo retorna o valor da expectativa
			System.out.println("Resultado SPY:  " + calculadoraSpy.soma(55, 9));//SPY nao executa o metodo e retorna o valor da expectativa
			
			
			/**
			 *  
			 */
		}
	
	@Test
	public void deveForcarSpyNaoExecutarMetodoComRetornoVoid() {
			
			//expectativa
			//Forca o SPY nao executar o metodo
			Mockito.doNothing().when(calculadoraSpy).imprime(Mockito.anyString());

			//acao
			
			/*O Mock mocka a chamada e NAO executa/chama efetivamente o metodo void
			 * Padrao NAO EXECUTAR
			 */
			System.out.println("Mock imprime");
			calculadoraMock.imprime("MOCK");
			
			/*O SPY executa efetivamente o metodo void
			 * Padrao EXECUTAR
			 */
			System.out.println("SPY invoke imprime");
			calculadoraSpy.imprime("SPY");
			
			/**
			    Mock imprime
				SPY invoke imprime
			 */
		}
	
	@Test
	public void deveChamarMetodoComRetornoVoid() {
			
			//expectativa

			//acao
			
			/*O Mock mocka a chamada e NAO executa/chama efetivamente o metodo void
			 * Padrao NAO EXECUTAR
			 */
			System.out.println("Mock imprime");
			calculadoraMock.imprime("MOCK");
			
			/*O SPY executa efetivamente o metodo void
			 * Padrao EXECUTAR
			 */
			System.out.println("SPY invoke imprime");
			calculadoraSpy.imprime("SPY");
			
			/**
			    Mock imprime
				SPY invoke imprime
				Calculadora imprimindo...  >>>> SPY
			 */
		}
	
	@Test
	public void deveMostrarMockChamandoMetodoReal() {
			
			//expectativa
			Mockito.when(calculadoraMock.soma(60, 2)).thenCallRealMethod();//Mock sendo forcado a executa/chama o metodo
			Mockito.when(calculadoraSpy.soma(55, 9)).thenReturn(878776);//SPY sempre executa/chama o metodo

			//acao
			//
			System.out.println("Resultado Mock:  " + calculadoraMock.soma(60, 2));//Mock NAO EXECUTA O METODO retorna o valor da execucao do metodo
			System.out.println("Resultado SPY:  " + calculadoraSpy.soma(55, 9));//SPY NAO EXECUTA O METODO
			
			
			/**
			    Chamou o metodo soma. a: 55e  b: 9
				Chamou o metodo soma. a: 60e  b: 2
				Resultado Mock:  62
				Resultado SPY:  878776
			 */
		}
	
	@Test
	public void deveMostrarDiferencaEntreSpyEMockRetornandoValorDaExpectativa() {
			
			//expectativa
			Mockito.when(calculadoraMock.soma(60, 2)).thenReturn(878776);//Mock nunca executa/chama o metodo
			Mockito.when(calculadoraSpy.soma(55, 9)).thenReturn(878776);//SPY sempre executa/chama o metodo

			//acao
			//
			System.out.println("Resultado Mock:  " + calculadoraMock.soma(60, 2));//Mock nao executa o metodo retorna o valor da expectativa
			System.out.println("Resultado SPY:  " + calculadoraSpy.soma(55, 9));//SPY nao executa o metodo e retorna o valor da expectativa
			
			
			/**
			 *  Chamou o metodo soma. a: 55e  b: 9
				Resultado Mock:  878776
				Resultado SPY:  878776
			 */
		}

	@Test
	public void deveMostrarDiferencaEntreSpyEMockParametrosDaExpectativaDiferentesDaChamada() {
		
		//expectativa
		Mockito.when(calculadoraMock.soma(60, 2)).thenReturn(34232);//Mock nunca executa/chama o metodo
		Mockito.when(calculadoraSpy.soma(55, 9)).thenReturn(34232);//SPY sempre executa/chama o metodo 1ª vez

		//acao
		//
		System.out.println("Resultado Mock:  " + calculadoraMock.soma(1, 7));//Mock retorna o valor padrao
		System.out.println("Resultado SPY:  " + calculadoraSpy.soma(4, 8));//SPY executa o metodo efetivamente 2ª vez, pois a chamada e a expectativa nao coincidem
		
		/**
		 * Mock quando nao sabe o que fazer, RETORNA o valor padrao do tipo de retorno
		 * SPY quando nao sabe o que fazer, EXECUTA o metodo e RETORNA o valor da execucao
		 *  Chamou o metodo soma. a: 55e  b: 9
			Resultado Mock:  0
			Chamou o metodo soma. a: 4e  b: 8
			Resultado SPY:  12

		 */
	}
	
	
	@Test
	public void deveMostrarMockRetornandoValorDaExpectativa() {
		
		//expectativa
		Mockito.when(calculadoraMock.soma(1, 7)).thenReturn(6657);

		//acao
		//quando o mock recebe uma expectativa, retorna o que foi declarado nela, neste caso 6657
		System.out.println(calculadoraMock.soma(1, 7));
	}
	
	@Test
	public void deveMostrarMockRetornandoValorValorPadraoDaExpectativaDiferenteDaChamada() {
		
		//expectativa
		Mockito.when(calculadoraMock.soma(1, 7)).thenReturn(6657);

		//acao
		//quando o mock recebe uma expectativa, mas a chamada e com valor diferente do declarado na expectativa, tambem retorna o valor padrao
		System.out.println(calculadoraMock.soma(1, 5));
	}
	
	@Test
	public void deveMostrarMockRetornandoValorPadrao() {
		
		//expectativa
		//???

		//acao
		//quando o mock nao sabe o que fazer retorna o valor padrao em questao, no caso 0 pois nao declarou espectativa
		System.out.println(calculadoraMock.soma(1, 2));
	}
	
	
	@Test
	public void testeSucessoCaptor() {
		
		//cenario
		Calculadora calculadora = Mockito.mock(Calculadora.class);
		
		//espectativa de captura
		ArgumentCaptor<Integer> argumentCaptorInteger = ArgumentCaptor.forClass(Integer.class);
		
		//espectativa com captor
		Mockito.when(calculadora.soma(argumentCaptorInteger.capture(), argumentCaptorInteger.capture())).thenReturn(5);
		
		//acao
		System.out.println("testeSucesso  >>>>  " + calculadora.soma(1, 2));
		
		//captura
		System.out.println("Captor last value: " + argumentCaptorInteger.getValue());
		System.out.println("Captor all values: " + argumentCaptorInteger.getAllValues());
	}
	
	
}
