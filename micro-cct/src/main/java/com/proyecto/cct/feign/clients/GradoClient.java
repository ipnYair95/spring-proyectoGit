package com.proyecto.cct.feign.clients;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.cct.feign.clients.entity.Carrera;
import com.proyecto.cct.feign.clients.entity.Grado;
import com.proyecto.cct.feign.clients.entity.Nivel;

@FeignClient( name = "grado-nivel", url = "http://localhost:8090/api/nivel-educativo/nivel/carrera/ciclo-escolar/grado" )
public interface GradoClient { 
		
	@GetMapping( value = "/buscar-por-id/{id}" )
	public Optional<Grado> buscarPorId( @PathVariable Long id );
	
	
}
