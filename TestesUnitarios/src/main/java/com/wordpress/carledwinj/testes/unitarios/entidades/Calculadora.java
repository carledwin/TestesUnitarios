package com.wordpress.carledwinj.testes.unitarios.entidades;

import com.wordpress.carledwinj.testes.unitarios.exception.NaoPodeDividirPorZeroException;

public class Calculadora {

	public int soma(int a, int b) {
		return a + b;
	}

	public int subtracao(int a, int b) {
		return a-b;
	}

	public int disao(int a, int b) throws NaoPodeDividirPorZeroException {
		
		if(b <= 0) {
			throw new NaoPodeDividirPorZeroException("O numero divisor nao pode ser menor ou igual a zero.");
		}
		
		return a/b;
	}

}
