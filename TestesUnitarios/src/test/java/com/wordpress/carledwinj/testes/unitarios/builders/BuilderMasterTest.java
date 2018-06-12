package com.wordpress.carledwinj.testes.unitarios.builders;

import com.wordpress.carledwinj.testes.unitarios.entidades.Locacao;

import buildermaster.BuilderMaster;

public class BuilderMasterTest {

	public static void main(String[] args) {

		new BuilderMaster().gerarCodigoClasse(Locacao.class);

		// execute o metodo e gere automaticamente o builder especifico
	}

	/*
	 * import java.util.Arrays; import java.lang.Double; import
	 * com.wordpress.carledwinj.testes.unitarios.entidades.Usuario; import
	 * java.util.Date; import
	 * com.wordpress.carledwinj.testes.unitarios.entidades.Locacao;
	 * 
	 * 
	 * public class LocacaoBuilder { private Locacao elemento; private
	 * LocacaoBuilder(){}
	 * 
	 * public static LocacaoBuilder umLocacao() { LocacaoBuilder builder = new
	 * LocacaoBuilder(); inicializarDadosPadroes(builder); return builder; }
	 * 
	 * public static void inicializarDadosPadroes(LocacaoBuilder builder) {
	 * builder.elemento = new Locacao(); Locacao elemento = builder.elemento;
	 * 
	 * 
	 * elemento.setUsuario(null); elemento.setFilmes(null);
	 * elemento.setDataLocacao(null); elemento.setDataRetorno(null);
	 * elemento.setValor(0.0); }
	 * 
	 * public LocacaoBuilder comUsuario(Usuario param) { elemento.setUsuario(param);
	 * return this; }
	 * 
	 * public LocacaoBuilder comListaFilmes(Filme... params) {
	 * elemento.setFilmes(Arrays.asList(params)); return this; }
	 * 
	 * public LocacaoBuilder comDataLocacao(Date param) {
	 * elemento.setDataLocacao(param); return this; }
	 * 
	 * public LocacaoBuilder comDataRetorno(Date param) {
	 * elemento.setDataRetorno(param); return this; }
	 * 
	 * public LocacaoBuilder comValor(Double param) { elemento.setValor(param);
	 * return this; }
	 * 
	 * public Locacao agora() { return elemento; } }
	 * 
	 * 
	 * 
	 */
}
