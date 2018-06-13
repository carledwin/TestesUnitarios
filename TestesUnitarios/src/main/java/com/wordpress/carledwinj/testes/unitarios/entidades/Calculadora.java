package com.wordpress.carledwinj.testes.unitarios.entidades;

import com.wordpress.carledwinj.testes.unitarios.exception.NaoPodeDividirPorZeroException;

public class Calculadora {

	public int soma(int a, int b) {
		
		System.out.println("Chamou o metodo soma. a: " + a + "e  b: " + b);
		return a + b;
	}
	
	public void imprime(String mensagem) {
		System.out.println("Calculadora imprimindo...  >>>> " + mensagem);
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
	
	public int divide(String a, String b) {
		return Integer.parseInt(a) / Integer.parseInt(b);
	}

}
