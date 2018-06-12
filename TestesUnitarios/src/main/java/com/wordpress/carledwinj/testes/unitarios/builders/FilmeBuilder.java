package com.wordpress.carledwinj.testes.unitarios.builders;

import com.wordpress.carledwinj.testes.unitarios.entidades.Filme;

public class FilmeBuilder {

	private Filme filme;
	
	private FilmeBuilder() {}
	
	//Estrutura Change Method
	
	public static FilmeBuilder novoFilme(){
		 FilmeBuilder builder = new FilmeBuilder();
		 builder.filme = new Filme();
		 return builder;
	}
	
	public static FilmeBuilder novoFilmeDefault(){
		 FilmeBuilder builder = new FilmeBuilder();
		 builder.filme = new Filme();
		 builder.filme.setNome("Filme 1");
		 builder.filme.setEstoque(1);
		 builder.filme.setPrecoLocacao(5.0d);
		 return builder;
	}
	
	public Filme build() {
		return filme;
	}
	
	public FilmeBuilder setNome(String nome) {
		filme.setNome(nome);
		return this;
	}
	
	public FilmeBuilder setEstoque(Integer estoque) {
		filme.setEstoque(estoque);
		return this;
	}
	
	public FilmeBuilder setSemEstoque() {
		filme.setEstoque(0);
		return this;
	}
	
	public FilmeBuilder setPrecoLocacao(Double precoLocacao) {
		filme.setPrecoLocacao(precoLocacao);
		return this;
	}
	
}
