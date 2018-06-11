package com.wordpress.carledwinj.testes.unitarios.servicos;

import static com.wordpress.carledwinj.testes.unitarios.utils.DataUtils.isMesmaData;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
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

	//of instance
	private LocacaoService locacaoService;
	
	//of class
	private static int count = 0;
	
	//of instance
	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();

	//of instance
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before //of instance
	public void setUp() {
		//System.out.println("Before");
		locacaoService = new LocacaoService();
		count++;
		//System.out.println("Contador: " + count);
	}
	
	@After //of instance
	public void tearDown() {
		//System.out.println("After");
	}
	
	//of class
	@BeforeClass //deve ser marcado como static pois devera ser criado antes da criacao da instancia de classe
	public static void setUpClass() {
		//System.out.println("static BeforeClass");
	}
	//of class
	@AfterClass //deve ser marcado como static pois devera ser criado antes da criacao da instancia de classe
	public static void tearDownClass() {
		//System.out.println("static AfterClass");
	}
	
	/**FORMA ELEGANTE - nao consegue imprimir mais nada depois que a exception e lancada*/
	/**FORMA ROBUSTA - maior poder de tratamento e analise dos erros, consegue validar se a mensagem de erro é a esperada e validar o tipo de exception*/
	/**FORMA NOVA - consegue validar se a mensagem de erro é a esperada e validar o tipo de exception, nao consegue imprimir mais nada depois que a exception e lancada*/

	@Test
	public void deveAlugarFilmeSucesso() {
		
		//o proprio teste indica quando pode ou nao ser executado
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));
		
		// acao
		Locacao locacao;
		try {
			locacao = locacaoService.alugarFilme(usuario, filmes);

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
		} catch (Exception e) {
			Assert.fail("Ocorreu uma falha, nao deveria lancar exception. Cause: " + e);
		}
	}
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() {
		
		//o proprio teste indica quando pode ou nao ser executado
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// cenario
		Usuario usuario = new Usuario("Usuario 1");

		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0));
		
		// acao
		try {
			Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
			
			//verificacao
			boolean ehDomingo = DataUtils.verificarDiaSemana(locacao.getDataRetorno(),Calendar.SUNDAY);
			
			Assert.assertFalse("Data de retorno do filme nao deveria ser um Domingo.", ehDomingo);
			
		} catch (Exception e) {
			Assert.fail("Ocorreu uma falha, nao deveria lancar exception. Cause: " + e);
		}

	}
	
	// verificacao
	@Test(expected = FilmeSemEstoqueException.class)
	public void testeEleganteExceptionEspecificaLocacaoFilmeSemEstoqueException() throws Exception {

		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));

		// acao
		locacaoService.alugarFilme(usuario, filmes);
		
		System.out.println("FORMA ELEGANTE - esta mensagem nunca sera impressa");
	}

	@Test
	public void testeRobustoLocacaoUsuarioNuloException() throws FilmeSemEstoqueException {

		// cenario
		Usuario usuario = null;
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));

		// acao
		try {
			locacaoService.alugarFilme(usuario, filmes);
			fail("Deveria lancar execption");
		} catch (LocacaoException e) {
			Assert.assertThat("Usuario vazio", is(e.getMessage()));
		}
		
		System.out.println("FORMA ROBUSTA - esta mensagem sera impressa");
	}

	@Test
	public void testeFormaNovaLocacaoFilmeNuloException() throws FilmeSemEstoqueException, LocacaoException {

		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = null;

		// verificacao
		expectedException.expect(LocacaoException.class);
		expectedException.expectMessage("Filmes vazio");

		// acao
		locacaoService.alugarFilme(usuario, filmes);
		
		System.out.println("FORMA NOVA - esta mensagem nunca sera impressa");
	}

	@Test
	public void testeFormaNovaLocacaoFilmeSemEstoqueException() throws Exception {

		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));

		// verificacao
		expectedException.expect(Exception.class);
		expectedException.expectMessage("Filme sem estoque");

		// acao
		locacaoService.alugarFilme(usuario, filmes);
	}

	// verificacao
	@Test
	public void testeRobustoLocacaoFilmeSemEstoqueException() {

		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));

		// acao
		try {
			locacaoService.alugarFilme(usuario, filmes);
		} catch (Exception e) {
			Assert.assertThat(e.getMessage(), is("Filme sem estoque"));
		}
	}

	// verificacao
	@Ignore
	@Test
	public void testeRobustoComFailSeOTesteNaoFalharLocacaoFilmeSemEstoqueException() {

		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));

		// acao
		try {
			locacaoService.alugarFilme(usuario, filmes);
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
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));

		// acao
		locacaoService.alugarFilme(usuario, filmes);
	}

	@Test
	public void deveAlugarFilme() {

		// cenario
		Usuario usuario = new Usuario("Usuario 1");

		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));

		/*
		 * Acessando membros default e protected devido o nome do pacote ser o mesmo
		 * locacaoService.vDefault; locacaoService.vProtected; locacaoService.vPublic;
		 */

		// acao
		Locacao locacao;
		try {
			locacao = locacaoService.alugarFilme(usuario, filmes);

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
			Assert.fail("Ocorreu uma falha, nao deveria lancar exception. Cause: " + e);
		}

	}
}
