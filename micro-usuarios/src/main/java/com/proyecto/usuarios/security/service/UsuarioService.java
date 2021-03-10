package com.proyecto.usuarios.security.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.usuarios.security.entity.Usuario;
import com.proyecto.usuarios.security.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository ur;
	
	public Usuario getByNombreUsuario( String nombreUsuario ){
		return this.ur.findByNombreUsuario( nombreUsuario ).orElse(null);
	}
	
	public boolean existeUsuario( String nombreUsuario ) {
		return this.ur.existsByNombreUsuario(nombreUsuario);
	}
	
	public boolean existeEmail( String email ) {
		return this.ur.existsByEmail( email );
	}
	
	public Usuario guardar( Usuario usuario ) {
		return this.ur.save(usuario);
	}

}
