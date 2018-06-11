package com.wordpress.carledwinj.testes.unitarios.matchers;

import java.util.Calendar;

public class MachersProprios {

	public static DiaSemanaMatcher caiEm(Integer diaSemana) {
		return new DiaSemanaMatcher(diaSemana);
	}

	public static DiaSemanaMatcher caiNumaSegunda(int monday) {
		return new DiaSemanaMatcher(Calendar.MONDAY);
	}
	
	public static DataDiferencaDiasMatcher eHoje() {
		return new DataDiferencaDiasMatcher(0);
	}
	
	public static DataDiferencaDiasMatcher eHojeComDiferencaDias(int quantidadeDias) {
		return new DataDiferencaDiasMatcher(quantidadeDias);
	}
	
	
}
