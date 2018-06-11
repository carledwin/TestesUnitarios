package com.wordpress.carledwinj.testes.unitarios.suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.wordpress.carledwinj.testes.unitarios.servicos.CalculadoraTest;
import com.wordpress.carledwinj.testes.unitarios.servicos.CalculoValorLocacaoComParametersTest;
import com.wordpress.carledwinj.testes.unitarios.servicos.CalculoValorLocacaoSemParametersTest;
import com.wordpress.carledwinj.testes.unitarios.servicos.LocacaoServiceTest;

@RunWith(Suite.class)
@SuiteClasses({
	LocacaoServiceTest.class, 
	CalculadoraTest.class, 
	CalculoValorLocacaoComParametersTest.class, 
	CalculoValorLocacaoSemParametersTest.class
})
public class SuiteExecucao {
	
	//Alguma declaracao aqui, pois e obrigatorio

	@BeforeClass
	public static void before() {
		System.out.println("BeforeClass Suite");
		System.out.println("Utilizado em baterias de testes que precisam de configuaracao inicial");
	}
	
	@AfterClass
	public static void after() {
		System.out.println("AfterClass Suite");
	}
}
