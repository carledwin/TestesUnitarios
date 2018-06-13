package com.wordpress.carledwinj.testes.unitarios.matchers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.wordpress.carledwinj.testes.unitarios.utils.DataUtils;

public class DataDiferencaDiasMatcher extends TypeSafeMatcher<Date>{

	private Integer quantidadeDias;
	
	public DataDiferencaDiasMatcher(Integer quantidadeDias) {
		this.quantidadeDias = quantidadeDias;
	}
	
	public void describeTo(Description description) {
		Date dataEsperada = new DataUtils().obterDataComDiferencaDias(quantidadeDias);
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		description.appendText(format.format(dataEsperada));
	}

	@Override
	protected boolean matchesSafely(Date date) {
		return DataUtils.isMesmaData(date, DataUtils.obterDataComDiferencaDias(quantidadeDias));
	}

	

}
