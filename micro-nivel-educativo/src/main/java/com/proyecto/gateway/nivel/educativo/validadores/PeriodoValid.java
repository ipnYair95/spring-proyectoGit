package com.proyecto.gateway.nivel.educativo.validadores;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target( ElementType.TYPE )  // target clase, metodo o atributo
@Retention( RetentionPolicy.RUNTIME ) // procesamiento a corrida
@Constraint( validatedBy =  PeriodoValidador.class )
public @interface PeriodoValid {

	String message() default "El periodo debe estar comprendido entre  5 dias a 1 año calendario";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
