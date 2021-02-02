package com.proyecto.alumnos.validadores;

import java.util.List;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.proyecto.alumnos.models.entity.Alumno;
import com.proyecto.alumnos.models.entity.Tutor;

public class ResponsableValidador implements ConstraintValidator<ResponsableValid, List<Tutor> >{

	@Override
	public boolean isValid(List<Tutor> tutores, ConstraintValidatorContext context) {
		
		if( tutores.size() == 0 ) {
			return false;
		}
		
		for( Tutor tutor : tutores ) {
			if( tutor.isEsResponsable() ) {
				return true;
			}
		}
		
		return false;
		
	}

}
