package com.proyecto.cct.feign.clients;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.cct.feign.clients.entity.Nivel;

@FeignClient( name = "feign-modalidad", url = "http://localhost:8090/api/nivel-educativo/nivel" )
public interface NivelClient { 
		
	@GetMapping( value = "/buscar-por-id/{id}" )
	public Optional<Nivel> buscarPorId( @PathVariable Long id );
	
	@GetMapping( value = "/listar"  )
	public List<Nivel> listarNiveles();
	
	
}
