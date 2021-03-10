package com.proyecto.oauth.models.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.oauth.models.entity.Usuario;
 
public interface UsuarioRepository  extends JpaRepository<Usuario,Long> {
	
	
	public Optional<Usuario> findByNombre( String nombre );

}
