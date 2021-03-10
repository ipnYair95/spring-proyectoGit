package com.proyecto.alumnos.feign.client;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
 

@FeignClient( name = "microservicio-centro-de-trabajo", url = "${url.feign}" + "/api/centro-de-trabajo" )
public interface EscuelaClient { 
		
	@GetMapping("/existe-escuela/{id}")
	public boolean existeEscuela( @PathVariable Long id );
	
	
}
