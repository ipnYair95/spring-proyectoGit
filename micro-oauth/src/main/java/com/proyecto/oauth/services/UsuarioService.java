package com.proyecto.oauth.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.oauth.models.entity.Usuario;
import com.proyecto.oauth.models.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository ur;
	
	@Transactional
	public Usuario guardar( Usuario usuario ) {		
		return this.ur.save(usuario);
	}
	
	public Usuario obtenerPorNombre( String nombre) {
		return this.ur.findByNombre(nombre).orElse(null);
	}
	
	

}
