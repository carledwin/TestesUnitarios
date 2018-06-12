package com.wordpress.carledwinj.testes.unitarios.builders;

import com.wordpress.carledwinj.testes.unitarios.entidades.Usuario;

public class UsuarioBuilder {

	private Usuario usuario;
	
	private UsuarioBuilder() {}
	
	public static UsuarioBuilder novoUsuario() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario();
		return builder;
	}
	
	public static UsuarioBuilder novoUsuarioDefault() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario();
		builder.usuario.setNome("Usuario 1");
		return builder;
	}
	
	public Usuario build() {
		return usuario;
	}
	
	public UsuarioBuilder setNome(String nome) {
		usuario.setNome(nome);
		return this;
	}
	
	
}
