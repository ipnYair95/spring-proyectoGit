package com.proyecto.cct.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient( name = "contadorService", url = "https://api.backendless.com/4AFCACAA-922D-5B16-FF8A-D6A67ECB9A00/90493812-4256-448E-BA03-10E563675A77" )
public interface ContadorClient { 
		
	@GetMapping("/counters/mycounter")
	public String getContador();
	
	@PutMapping("/counters/mycounter/get/increment")
	public String getContadorIncrementar();

}
