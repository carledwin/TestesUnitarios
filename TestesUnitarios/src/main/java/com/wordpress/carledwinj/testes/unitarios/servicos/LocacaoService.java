package com.wordpress.carledwinj.testes.unitarios.servicos;

import static com.wordpress.carledwinj.testes.unitarios.utils.DataUtils.adicionarDias;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

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

	public Locacao alugarFilme(Usuario usuario, Filme filme) throws FilmeSemEstoqueException, LocacaoException {

		if (filme == null) {
			throw new LocacaoException("Filme vazio");
		}

		if (filme.getEstoque() == 0) {
			throw new FilmeSemEstoqueException("Filme sem estoque");
		}

		if (usuario == null) {
			throw new LocacaoException("Usuario vazio");
		}

		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);

		// Salvando a locacao...
		// TODO adicionar método para salvar

		return locacao;
	}

}