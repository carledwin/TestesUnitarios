package com.wordpress.carledwinj.testes.unitarios.servicos;

import static com.wordpress.carledwinj.testes.unitarios.utils.DataUtils.isMesmaData;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import com.wordpress.carledwinj.testes.unitarios.entidades.Filme;
import com.wordpress.carledwinj.testes.unitarios.entidades.Locacao;
import com.wordpress.carledwinj.testes.unitarios.entidades.Usuario;
import com.wordpress.carledwinj.testes.unitarios.exception.FilmeSemEstoqueException;
import com.wordpress.carledwinj.testes.unitarios.exception.LocacaoException;
//import com.wordpress.carledwinj.testes.unitarios.servicos.LocacaoService; //nao esta sendo importado pois esta no pacote com o mesmo nome
import com.wordpress.carledwinj.testes.unitarios.utils.DataUtils;

public class LocacaoServiceTest {

	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	/**
	 * FORMA ELEGANTE - nao consegue imprimir mais nada depois que a exception e lancada
	 */
	
	/**
	 * FORMA ROBUSTA - maior poder de tratamento e analise dos erros, consegue validar se a mensagem de erro é a esperada e validar o tipo de exception
	 */
	
	/**
	 * FORMA NOVA - consegue validar se a mensagem de erro é a esperada e validar o tipo de exception, nao consegue imprimir mais nada depois que a exception e lancada
	 */
	
	// verificacao
	@Test(expected = FilmeSemEstoqueException.class)
	public void testeEleganteExceptionEspecificaLocacaoFilmeSemEstoqueException() throws Exception {

		// cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// acao
		locacaoService.alugarFilme(usuario, filme);
		
		System.out.println("FORMA ELEGANTE - esta mensagem nunca sera impressa");
	}

	@Test
	public void testeRobustoLocacaoUsuarioNuloException() throws FilmeSemEstoqueException {

		// cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = null;
		Filme filme = new Filme("Filme 1", 1, 5.0);

		// acao
		try {
			locacaoService.alugarFilme(usuario, filme);
			fail("Deveria lancar execption");
		} catch (LocacaoException e) {
			Assert.assertThat("Usuario vazio", is(e.getMessage()));
		}
		
		System.out.println("FORMA ROBUSTA - esta mensagem sera impressa");
	}

	@Test
	public void testeFormaNovaLocacaoFilmeNuloException() throws FilmeSemEstoqueException, LocacaoException {

		// cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = null;

		// verificacao
		expectedException.expect(LocacaoException.class);
		expectedException.expectMessage("Filme vazio");

		// acao
		locacaoService.alugarFilme(usuario, filme);
		
		System.out.println("FORMA NOVA - esta mensagem nunca sera impressa");
	}

	@Test
	public void testeFormaNovaLocacaoFilmeSemEstoqueException() throws Exception {

		// cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// verificacao
		expectedException.expect(Exception.class);
		expectedException.expectMessage("Filme sem estoque");

		// acao
		locacaoService.alugarFilme(usuario, filme);
	}

	// verificacao
	@Test
	public void testeRobustoLocacaoFilmeSemEstoqueException() {

		// cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// acao
		try {
			locacaoService.alugarFilme(usuario, filme);
		} catch (Exception e) {
			Assert.assertThat(e.getMessage(), is("Filme sem estoque"));
		}
	}

	// verificacao
	@Ignore
	@Test
	public void testeRobustoComFailSeOTesteNaoFalharLocacaoFilmeSemEstoqueException() {

		// cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 1, 5.0);

		// acao
		try {
			locacaoService.alugarFilme(usuario, filme);
			fail("Deveria ter lancado uma exception");// se resguardando que vai realmente falhar caso nao lance
														// exception
		} catch (Exception e) {
			Assert.assertThat(e.getMessage(), is("Filme sem estoque"));
		}
	}

	// verificacao
	@Test(expected = Exception.class)
	public void testeEleganteLocacaoFilmeSemEstoqueException() throws Exception {

		// cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// acao
		locacaoService.alugarFilme(usuario, filme);
	}

	@Test
	public void testeLocacao() {

		// cenario
		LocacaoService locacaoService = new LocacaoService();

		Usuario usuario = new Usuario("Usuario 1");

		Filme filme = new Filme("Filme 1", 2, 5.0);

		/*
		 * Acessando membros default e protected devido o nome do pacote ser o mesmo
		 * locacaoService.vDefault; locacaoService.vProtected; locacaoService.vPublic;
		 */

		// acao
		Locacao locacao;
		try {
			locacao = locacaoService.alugarFilme(usuario, filme);

			// verificacao
			assertTrue(locacao.getValor() == 5.0);
			assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
			assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));

			// Ordem de veirificacao muda assertThat(actual, matcher);
			Assert.assertThat(locacao.getValor(), CoreMatchers.is(5.0));
			Assert.assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
			Assert.assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.not(6.0)));

			// com import static
			assertThat(locacao.getValor(), is(5.0));
			assertThat(locacao.getValor(), is(equalTo(5.0)));
			assertThat(locacao.getValor(), is(not(6.0)));
			assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));

			// Rule para exibir pilha de erros
			// errorCollector.checkThat(locacao.getValor(), is(4.0));
			// errorCollector.checkThat(locacao.getValor(), is(7.0));
		} catch (Exception e) {
			Assert.fail("Ocorreu uma falha, nao deveria lancar exception");
		}

	}
}
