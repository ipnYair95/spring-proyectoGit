package com.proyecto.gateway.nivel.educativo.validadores;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurpValidator implements ConstraintValidator<CurpValid, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		String regex = "[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}" + "(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])" + "[HM]{1}"
				+ "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)"
				+ "[B-DF-HJ-NP-TV-Z]{3}" + "[0-9A-Z]{1}[0-9]{1}$";
		
		if( value == null ) {
			return false;
		}

		Pattern patron = Pattern.compile(regex);
		if (!patron.matcher(value).matches()) {
			return false;  // no validado
		} else {
			return true;  // validado
		}
	}

}
