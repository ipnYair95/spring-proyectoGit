package com.proyecto.usuarios.security.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.proyecto.usuarios.security.enums.RolNombre;
import com.sun.istack.NotNull;

@Entity
@Table( name = "rol" )
public class Rol implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@NotNull
	@Enumerated( EnumType.STRING )
	private RolNombre rolNombre;
	
	

	public Rol() {
		super();
	}

	public Rol(RolNombre rolNombre) {
		super();
		this.rolNombre = rolNombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RolNombre getRolNombre() {
		return rolNombre;
	}

	public void setRolNombre(RolNombre rolNombre) {
		this.rolNombre = rolNombre;
	}

	
	
	

}
