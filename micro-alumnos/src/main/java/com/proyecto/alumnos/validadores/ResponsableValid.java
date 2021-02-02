package com.proyecto.alumnos.validadores;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target( ElementType.FIELD )  // target clase, metodo o atributo
@Retention( RetentionPolicy.RUNTIME ) // procesamiento a corrida
@Constraint( validatedBy =  ResponsableValidador.class )
public @interface ResponsableValid {

	String message() default "Debe existir 1 responsable";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
