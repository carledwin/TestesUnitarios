package com.wordpress.carledwinj.testes.unitarios.servicos;

import static com.wordpress.carledwinj.testes.unitarios.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.wordpress.carledwinj.testes.unitarios.daos.LocacaoDAO;
import com.wordpress.carledwinj.testes.unitarios.daos.LocacaoDAOFake;
import com.wordpress.carledwinj.testes.unitarios.entidades.Filme;
import com.wordpress.carledwinj.testes.unitarios.entidades.Locacao;
import com.wordpress.carledwinj.testes.unitarios.entidades.Usuario;
import com.wordpress.carledwinj.testes.unitarios.exception.FilmeSemEstoqueException;
import com.wordpress.carledwinj.testes.unitarios.exception.LocacaoException;
import com.wordpress.carledwinj.testes.unitarios.utils.DataUtils;

public class LocacaoService {

	public String vPublic;
	private String vPrivate;
	protected String vProtected;
	String vDefault;
	private LocacaoDAO locacaoDAO;
	private SPCService spcService;
	private EmailService emailService;
	
	public void prorrogarLocacao(Locacao locacao, int dias) {
		Locacao novaLocacao =new Locacao();
		novaLocacao.setUsuario(locacao.getUsuario());
		novaLocacao.setFilmes(locacao.getFilmes());
		novaLocacao.setValor(locacao.getValor() * dias);
		novaLocacao.setDataLocacao(new Date());
		novaLocacao.setDataRetorno(DataUtils.obterDataComDiferencaDias(dias));
		locacaoDAO.salvar(novaLocacao);
	}

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws LocacaoException, FilmeSemEstoqueException {

		if (filmes == null || filmes.isEmpty()) {
			throw new LocacaoException("Filmes vazio");
		}

		for (Filme filme : filmes) {
			if (filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException("Filme sem estoque");
			}
		}

		if (usuario == null) {
			throw new LocacaoException("Usuario vazio");
		}

		boolean negativado;
		try {
			negativado = spcService.possuiNegativacao(usuario);
		} catch (Exception e) {
			throw new LocacaoException("Problemas com SPC, tente novamente");
		}
		
		if(negativado) {
			throw new LocacaoException("Usuario Negativado");
		}
		
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(0D);
		
		
		for (int item = 0; item < filmes.size(); item++) {
			
			Double valorFilme = filmes.get(item).getPrecoLocacao();
			
			switch(item){
				case 2: valorFilme = valorFilme * 0.75; break;
				case 3: valorFilme = valorFilme * 0.50; break;
				case 4: valorFilme = valorFilme * 0.25; break;
				case 5: valorFilme = 0d; break;
			}
			
			Double valorTotalLocacao = locacao.getValor() + valorFilme;
			locacao.setValor(valorTotalLocacao );
		}
		

		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);

		if(DataUtils.verificarDiaSemana(locacao.getDataRetorno(), Calendar.SUNDAY)) {
			locacao.setDataRetorno(DataUtils.adicionarDias(locacao.getDataRetorno(), 1));
		}
		
		locacaoDAO.salvar(locacao);
		
		return locacao;
	}
	
	public void notificarAtrasos() {
		List<Locacao> locacoes = locacaoDAO.obterLocacoesPendentes();
		
		for (Locacao locacao : locacoes) {
			if(locacao.getDataRetorno().before(new Date())){
				emailService.notificarAtraso(locacao.getUsuario());
			}
		}
	}
	
	public void setlocacaoDAO(LocacaoDAO locacaoDAO) {
		this.locacaoDAO = locacaoDAO;
	}
	
	public void setSPCService(SPCService spcService) {
		this.spcService = spcService;
	}
	
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

}