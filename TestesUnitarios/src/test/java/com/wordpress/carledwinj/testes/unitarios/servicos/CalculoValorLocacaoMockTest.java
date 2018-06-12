package com.wordpress.carledwinj.testes.unitarios.servicos;

import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

import com.wordpress.carledwinj.testes.unitarios.daos.LocacaoDAO;
import com.wordpress.carledwinj.testes.unitarios.entidades.Filme;
import com.wordpress.carledwinj.testes.unitarios.entidades.Locacao;
import com.wordpress.carledwinj.testes.unitarios.entidades.Usuario;

//alterando o testRunner para que ela rode como se fosse um parameterized
@RunWith(Parameterized.class)
public class CalculoValorLocacaoMockTest {

	private LocacaoService locacaoService;

	private static Filme filme1 = new Filme("Filme 1", 2, 4.0);
	private static Filme filme2 = new Filme("Filme 2", 2, 4.0);
	private static Filme filme3 = new Filme("Filme 3", 2, 4.0);
	private static Filme filme4 = new Filme("Filme 4", 2, 4.0);
	private static Filme filme5 = new Filme("Filme 5", 2, 4.0);
	private static Filme filme6 = new Filme("Filme 6", 2, 4.0);
	private static Filme filme7 = new Filme("Filme 7", 2, 4.0);

	@Parameter
	public List<Filme> filmes;

	@Parameter(value = 1)
	public Double valorLocacao;

	@Parameter(value = 2)
	public String descricao;

	/**
	 * define a quantidade de execucoes de cada metodo de teste cada item do array
	 * executa todos os testes da class
	 * 
	 * @return
	 */
	@Parameters(name = "Teste: {index} >>> Param 1: {0}, Param {1}, Param{2}")
	public static Collection<Object[]> getParametros() {
		return Arrays.asList(new Object[][] { 
				{ Arrays.asList(filme1, filme2), 8.0, "2 Filmes: Sem desconto" },
				{ Arrays.asList(filme1, filme2, filme3), 11.0, "3 Filmes: 25%" },
				{ Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4 Filmes: 50%" },
				{ Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5 Filmes: 75%" },
				{ Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 Filmes: 100%" },
				{ Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18.0, "7 Filmes: Sem desconto" }, });

	}

	@Before
	public void setUp() {
		locacaoService = new LocacaoService();
		LocacaoDAO locacaoDAO = Mockito.mock(LocacaoDAO.class);
		locacaoService.setlocacaoDAO(locacaoDAO);
		locacaoService.setSPCService(Mockito.mock(SPCService.class));
	}

	// DDT - Data Driven Test - Teste orintado a dados
	@Test
	public void deveCalcularValorLocacaoConsiderandoDescontos() {

		// cenario
		Usuario usuario = new Usuario("Usuario 1");

		// acao
		try {
			Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

			// verificacao
			Assert.assertThat(locacao.getValor(), is(valorLocacao));

		} catch (Exception e) {
			Assert.fail("Ocorreu uma falha, nao deveria lancar exception. Cause: " + e);
		}
	}

	@Test
	public void print() {
		System.out.println("Valor de cada execucao: >>>>>   " + valorLocacao);
	}
}
