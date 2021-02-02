package com.proyecto.alumnos.validadores.listas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class ValidList<E> {

	@JsonValue
	@Valid
	@NotNull
	@Size(min = 1, message = "array body must contain at least one item.")
	private List<E> values;

	@JsonCreator
	public ValidList(E... items) {
		this.values = Arrays.asList(items);
	}

	public List<E> getValues() {
		return values;
	}

	public void setValues(List<E> values) {
		this.values = values;
	}

	public int size() {
		return this.values.size();
	}

}
