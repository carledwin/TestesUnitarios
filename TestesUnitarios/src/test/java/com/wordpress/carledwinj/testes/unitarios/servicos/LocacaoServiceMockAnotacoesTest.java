package com.wordpress.carledwinj.testes.unitarios.servicos;

import static com.wordpress.carledwinj.testes.unitarios.utils.DataUtils.isMesmaData;
import static com.wordpress.carledwinj.testes.unitarios.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.wordpress.carledwinj.testes.unitarios.builders.FilmeBuilder;
import com.wordpress.carledwinj.testes.unitarios.builders.LocacaoBuilder;
import com.wordpress.carledwinj.testes.unitarios.builders.UsuarioBuilder;
import com.wordpress.carledwinj.testes.unitarios.daos.LocacaoDAO;
import com.wordpress.carledwinj.testes.unitarios.daos.LocacaoDAOFake;
import com.wordpress.carledwinj.testes.unitarios.entidades.Filme;
import com.wordpress.carledwinj.testes.unitarios.entidades.Locacao;
import com.wordpress.carledwinj.testes.unitarios.entidades.Usuario;
import com.wordpress.carledwinj.testes.unitarios.exception.FilmeSemEstoqueException;
import com.wordpress.carledwinj.testes.unitarios.exception.LocacaoException;
import com.wordpress.carledwinj.testes.unitarios.matchers.DiaSemanaMatcher;
import com.wordpress.carledwinj.testes.unitarios.matchers.MachersProprios;
//import com.wordpress.carledwinj.testes.unitarios.servicos.LocacaoService; //nao esta sendo importado pois esta no pacote com o mesmo nome
import com.wordpress.carledwinj.testes.unitarios.utils.DataUtils;

public class LocacaoServiceMockAnotacoesTest {

	
	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	//clase em teste que recebera os mocks abaixo
	@InjectMocks
	private LocacaoService locacaoService;
	
	//cria os mocks abaixo e injeta na classe em teste locacaoService
	@Mock
	private SPCService spcService;
	
	@Mock
	private LocacaoDAO locacaoDAO;
	
	@Mock
	private EmailService emailService;
	
	@Before 
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testeUsuarioNegativadoVerifyGenerico() throws FilmeSemEstoqueException {

		// cenario
		List<Filme> filmes = Arrays.asList(FilmeBuilder.novoFilmeDefault().build());
		
		//espectativa
		Mockito.when(spcService.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);

		// acao
		try {
			locacaoService.alugarFilme(UsuarioBuilder.novoUsuarioDefault().build(), filmes);
			//verificacao
			Assert.fail("Deveria lancar exception!");
		} catch (LocacaoException e) {
			
			//verificacao2
			Assert.assertEquals("Usuario Negativado", e.getMessage());
		}
		
		Mockito.verify(spcService).possuiNegativacao(Mockito.any(Usuario.class));
		
		System.out.println("FORMA NOVA - esta mensagem nunca sera impressa");
	}
	
	@Test
	public void deveEnviarEmailParaLocacoesAtrasadasSemProblema() {
		
		//cenario
		Usuario usuario = UsuarioBuilder.novoUsuarioDefault().build();
		Usuario usuarioEmDia = UsuarioBuilder.novoUsuarioDefault().setNome("Usuario Em Dia").build();
		Usuario usuarioAtrasado = UsuarioBuilder.novoUsuarioDefault().setNome("Usuario atrasado").build();
		Usuario usuarioAtrasado3 = UsuarioBuilder.novoUsuarioDefault().setNome("Usuario atrasado 3").build();
		
		List<Locacao> locacoesPendentes = Arrays.asList(
												LocacaoBuilder.umLocacao().comUsuario(usuario).atrasado().agora(),
												//teste verifica que o usuario recebeu o email mais de uma vez
												LocacaoBuilder.umLocacao().comUsuario(usuario).atrasado().agora(), 
												LocacaoBuilder.umLocacao().comUsuario(usuarioEmDia).agora(),
												LocacaoBuilder.umLocacao().comUsuario(usuarioAtrasado).atrasado().agora(),
												LocacaoBuilder.umLocacao().comUsuario(usuarioAtrasado3).atrasado().agora());
		
		//espectativa
		when(locacaoDAO.obterLocacoesPendentes()).thenReturn(locacoesPendentes);
		
		//acao
		locacaoService.notificarAtrasos();
		
		Mockito.verify(emailService, Mockito.times(2)).notificarAtraso(usuario);
		
		Mockito.verify(emailService).notificarAtraso(usuarioAtrasado);
		//vefifica que este metodo nunca foi chamado para o usuarioEmDia
		Mockito.verify(emailService, Mockito.never()).notificarAtraso(usuarioEmDia);
		
		//verifica que este metodo nunca foi invocado/chamado
		Mockito.verifyZeroInteractions(spcService);
		
	}
	
	@Test
	public void deveEnviarEmailParaLocacoesAtrasadasComProblema() {
		
		//cenario
		Usuario usuario = UsuarioBuilder.novoUsuarioDefault().build();
		
		List<Locacao> locacoesPendentes = Arrays.asList(
												LocacaoBuilder
												.umLocacao()
												.comUsuario(usuario)
												.comDataRetorno(obterDataComDiferencaDias(-2))
												.agora()
												);
		
		//espectativa
		when(locacaoDAO.obterLocacoesPendentes()).thenReturn(locacoesPendentes);
		
		//acao
		locacaoService.notificarAtrasos();
		
		//verificacao
		Mockito.verify(emailService).notificarAtraso(usuario);
	}
	
	@Test
	public void testeUsuarioNegativadoVerifyException() throws FilmeSemEstoqueException {

		// cenario
		Usuario usuario = UsuarioBuilder.novoUsuarioDefault().build();
		//teste de falso positivo
		List<Filme> filmes = Arrays.asList(FilmeBuilder.novoFilmeDefault().build());
		
		//espectativa
		Mockito.when(spcService.possuiNegativacao(usuario)).thenReturn(true);

		// acao
		try {
			locacaoService.alugarFilme(usuario, filmes);
			
			//verificacao1
			//garante que nao sera gerado um falso positivo
			Assert.fail("Deveria lancar exception!");
		} catch (LocacaoException e) {
			
			//verificacao2
			Assert.assertEquals("Usuario Negativado", e.getMessage());
		}
		
		Mockito.verify(spcService).possuiNegativacao(usuario);
		
		//teste de falso positivo
		System.out.println("FORMA NOVA - esta mensagem nunca sera impressa");
	}
	
	
	@Test
	public void testeUsuarioNegativadoException() throws FilmeSemEstoqueException, LocacaoException {

		// cenario
		Usuario usuario = UsuarioBuilder.novoUsuarioDefault().build();
		//teste de falso positivo
		List<Filme> filmes = Arrays.asList(FilmeBuilder.novoFilmeDefault().build());
		
		//espectativa
		Mockito.when(spcService.possuiNegativacao(usuario)).thenReturn(true);
		
		// verificacao
		expectedException.expect(LocacaoException.class);
		expectedException.expectMessage("Usuario Negativado");

		// acao
		locacaoService.alugarFilme(usuario, filmes);

		Mockito.verify(spcService).possuiNegativacao(usuario);
		
		System.out.println("FORMA NOVA - esta mensagem nunca sera impressa");
	}
	
	@Test
	public void deveAlugarFilmeMatcherDesafio() {

		// cenario
		List<Filme> filmes = Arrays.asList(FilmeBuilder.novoFilmeDefault().build());

		// acao
		Locacao locacao;
		try {
			locacao = locacaoService.alugarFilme(UsuarioBuilder.novoUsuarioDefault().build(), filmes);
			
			 errorCollector.checkThat(locacao.getValor(), is(5.0));
			 errorCollector.checkThat(locacao.getDataLocacao(), MachersProprios.eHoje());
			 errorCollector.checkThat(locacao.getDataRetorno(), MachersProprios.eHojeComDiferencaDias(1));
			 
		} catch (Exception e) {
			Assert.fail("Ocorreu uma falha, nao deveria lancar exception. Cause: " + e);
		}

	}
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabadoNovoMatcher() {
		
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// cenario
		Usuario usuario = new Usuario("Usuario 1");

		List<Filme> filmes = Arrays.asList(FilmeBuilder.novoFilmeDefault().build());
		
		// acao
		try {
			Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
			
			//verificacao
			
			assertThat(locacao.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
			assertThat(locacao.getDataRetorno(), MachersProprios.caiEm(Calendar.MONDAY));
			assertThat(locacao.getDataRetorno(), MachersProprios.caiNumaSegunda(Calendar.MONDAY));
			
		} catch (Exception e) {
			Assert.fail("Ocorreu uma falha, nao deveria lancar exception. Cause: " + e);
		}
	}
	
	@Test
	public void deveAlugarFilmeSucesso() {
		
		//o proprio teste indica quando pode ou nao ser executado
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// cenario
		List<Filme> filmes = Arrays.asList(FilmeBuilder.novoFilmeDefault().build());
		
		// acao
		try {
			Locacao locacao = locacaoService.alugarFilme(UsuarioBuilder.novoUsuarioDefault().build(), filmes);

			// verificacao
			assertTrue(locacao.getValor() == 5.0);
			assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
			assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));

			Assert.assertThat(locacao.getValor(), CoreMatchers.is(5.0));
			Assert.assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
			Assert.assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.not(6.0)));

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

		List<Filme> filmes = Arrays.asList(FilmeBuilder.novoFilmeDefault().build());
		
		// acao
		try {
			Locacao locacao = locacaoService.alugarFilme(UsuarioBuilder.novoUsuario().setNome("Usuario 1").build(), filmes);
			
			//verificacao
			boolean ehDomingo = DataUtils.verificarDiaSemana(locacao.getDataRetorno(),Calendar.SUNDAY);
			Assert.assertFalse("Data de retorno do filme nao deveria ser um Domingo.", ehDomingo);
			
		} catch (Exception e) {
			Assert.fail("Ocorreu uma falha, nao deveria lancar exception. Cause: " + e);
		}

	}
	
	@Test
	public void testeRobustoLocacaoUsuarioNuloException() throws FilmeSemEstoqueException {

		// cenario
		List<Filme> filmes = Arrays.asList(FilmeBuilder.novoFilmeDefault().build());

		// acao
		try {
			locacaoService.alugarFilme(null, filmes);
			fail("Deveria lancar execption");
		} catch (LocacaoException e) {
			Assert.assertThat("Usuario vazio", is(e.getMessage()));
		}
		
		System.out.println("FORMA ROBUSTA - esta mensagem sera impressa");
	}

	@Test
	public void testeFormaNovaLocacaoFilmeNuloException() throws FilmeSemEstoqueException, LocacaoException {

		// cenario
		
		// verificacao
		expectedException.expect(LocacaoException.class);
		expectedException.expectMessage("Filmes vazio");

		// acao
		locacaoService.alugarFilme(UsuarioBuilder.novoUsuarioDefault().build(), null);
		
		System.out.println("FORMA NOVA - esta mensagem nunca sera impressa");
	}

	// verificacao
	@Test
	public void testeRobustoLocacaoFilmeSemEstoqueException() {

		// cenario
		List<Filme> filmes = Arrays.asList(FilmeBuilder.novoFilmeDefault().build());

		// acao
		try {
			locacaoService.alugarFilme(UsuarioBuilder.novoUsuarioDefault().build(), filmes);
		} catch (Exception e) {
			Assert.assertThat(e.getMessage(), is("Filme sem estoque"));
		}
	}

	// verificacao
	@Test(expected = Exception.class)
	public void testeEleganteLocacaoFilmeSemEstoqueException() throws Exception {

		// cenario
		List<Filme> filmes = Arrays.asList(FilmeBuilder.novoFilmeDefault().setSemEstoque().build());

		// acao
		locacaoService.alugarFilme(UsuarioBuilder.novoUsuario().build(), filmes);
	}

	
}
