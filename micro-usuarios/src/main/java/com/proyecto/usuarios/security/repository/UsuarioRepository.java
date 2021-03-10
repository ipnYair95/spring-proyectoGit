package com.proyecto.usuarios.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.usuarios.security.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByNombreUsuario( String nombreUsuario );
	
	public boolean existsByNombreUsuario(String nombreUsuario );

	public boolean existsByEmail(String email );
	
}
