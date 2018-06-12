package com.wordpress.carledwinj.testes.unitarios.daos;

import java.util.List;

import com.wordpress.carledwinj.testes.unitarios.entidades.Locacao;

public interface LocacaoDAO {

	public void salvar(Locacao locacao);
	List<Locacao> obterLocacoesPendentes();
}
