package com.wordpress.carledwinj.testes.unitarios.servicos;

import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import com.wordpress.carledwinj.testes.unitarios.entidades.Filme;
import com.wordpress.carledwinj.testes.unitarios.entidades.Locacao;
import com.wordpress.carledwinj.testes.unitarios.entidades.Usuario;

public class CalculoValorLocacaoSemParametersTest {

	private LocacaoService locacaoService;

	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before // of instance
	public void setUp() {
		locacaoService = new LocacaoService();
	}

	@Test
	public void devePagar25Porcentono3Filme() {

		// cenario
		Usuario usuario = new Usuario("Usuario 1");

		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0));

		// acao
		try {
			Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

			// verificacao
			Assert.assertThat("Deveria ganhar 25% de desconto no 3º filme.", locacao.getValor(), is(11.0));

		} catch (Exception e) {
			Assert.fail("Ocorreu uma falha, nao deveria lancar exception. Cause: " + e);
		}

	}

	@Test
	public void devePagar50Porcentono4Filme() {

		// cenario
		Usuario usuario = new Usuario("Usuario 1");

		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0));

		// acao
		try {
			Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

			// verificacao
			Assert.assertThat("Deveria ganhar 50% de desconto no 4º filme.", locacao.getValor(), is(13.0));

		} catch (Exception e) {
			Assert.fail("Ocorreu uma falha, nao deveria lancar exception. Cause: " + e);
		}

	}

	@Test
	public void devePagar75Porcentono5Filme() {

		// cenario
		Usuario usuario = new Usuario("Usuario 1");

		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0), new Filme("Filme 5", 2, 4.0));

		// acao
		try {
			Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

			// verificacao
			Assert.assertThat("Deveria ganhar 75% de desconto no 5º filme.", locacao.getValor(), is(14.0));

		} catch (Exception e) {
			Assert.fail("Ocorreu uma falha, nao deveria lancar exception. Cause: " + e);
		}
	}

	@Test
	public void devePagar100Porcentono6Filme() {

		// cenario
		Usuario usuario = new Usuario("Usuario 1");

		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0), new Filme("Filme 5", 2, 4.0));

		// acao
		try {
			Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

			// verificacao
			Assert.assertThat("Deveria ganhar 100% de desconto no 6º filme.", locacao.getValor(), is(14.0));

		} catch (Exception e) {
			Assert.fail("Ocorreu uma falha, nao deveria lancar exception. Cause: " + e);
		}
	}
}
