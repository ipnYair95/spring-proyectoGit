package com.proyecto.gateway.nivel.educativo.validadores;

import java.time.Period;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.proyecto.gateway.nivel.educativo.controllers.NivelController;
import com.proyecto.gateway.nivel.educativo.models.entity.CicloEscolar;

public class PeriodoValidador implements ConstraintValidator<PeriodoValid, CicloEscolar>{
	
	@Autowired
	private static Logger log = LoggerFactory.getLogger(PeriodoValidador.class);

	@Override
	public boolean isValid(CicloEscolar cicloEscolar, ConstraintValidatorContext context) {
		
		if( cicloEscolar.getFechaInicio() == null || cicloEscolar.getFechaTermino() == null ) {
			return false;
		}

		Period faltante = cicloEscolar.getFechaInicio().until(cicloEscolar.getFechaTermino());
		log.info( "diferencia " + faltante.getYears() + " " + faltante.getMonths() + " " + faltante.getDays() );

		if (faltante.getYears() != 0) {
			return false;
		} else if (faltante.getMonths() == 0 && faltante.getDays() < 4) {
			return false;
		}
		
		return true;
		

	}

}
