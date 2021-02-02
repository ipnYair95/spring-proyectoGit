package com.proyecto.gateway.nivel.educativo.controllers;


import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.alumnos.models.entity.Tutor;
import com.proyecto.gateway.nivel.educativo.models.entity.Carrera;
import com.proyecto.gateway.nivel.educativo.models.entity.Nivel;
import com.proyecto.gateway.nivel.educativo.services.CarreraService;
import com.proyecto.gateway.nivel.educativo.services.NivelService;

@RestController
@RequestMapping("/nivel")
public class NivelController {

	@Autowired
	private NivelService ns;
	
	@Autowired 
	private CarreraService cr;

	// Lista de respuestas
	Map<String, Object> respuesta = new HashMap<>();

	// Logger
	private static Logger log = LoggerFactory.getLogger(NivelController.class);

	@GetMapping("/listar")
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok().body(this.ns.listar());
	}
	 
	@GetMapping("/buscar-por-id/{id}")
	public ResponseEntity<?> buscarPorId( @PathVariable Long id ){
		
		Nivel nivelDb = null;
		
		try {
			
			nivelDb = this.ns.buscarPorId(id);
			
			if( nivelDb == null ) {
				return this.enviarMensaje("El nivel no existe", HttpStatus.NOT_FOUND );
			}
			
		}catch (DataAccessException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResponseEntity.ok().body( nivelDb );
		
		
	}

	@PostMapping("/crear")
	public ResponseEntity<?> crear(@Valid @RequestBody Nivel nivel, BindingResult validacion) {

		Nivel nivelDb;
		
		log.info( nivel.toString() );

		if (validacion.hasErrors()) {
			log.info("error antes");
			return this.validar(validacion);
		}
		

		try {
			
			nivelDb = this.ns.guardar(nivel);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ResponseEntity.ok().body(nivelDb);
	}
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<?> editar( @Valid @RequestBody Nivel nivel, BindingResult validacion , @PathVariable Long id ){
		
		Nivel nivelDb;
		
		if( validacion.hasErrors() ) {
			return this.validar(validacion);
		}
		
		try {
			
			nivelDb = this.ns.buscarPorId( id );
			
			if( nivelDb == null ) {
				return enviarMensaje("El nivel no existe",  HttpStatus.NOT_FOUND );
			}					
			nivelDb.replaceAll(nivel);
			this.ns.guardar(nivelDb);
			
		}catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return enviarMensaje("Nivel actualizado", HttpStatus.OK );
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminar( @PathVariable Long id ){
		
		Nivel nivelDb = null;
		
		try {
			
			nivelDb = this.ns.buscarPorId( id );

			if( nivelDb == null ) {
				return enviarMensaje("El nivel no existe",  HttpStatus.NOT_FOUND );
			}
			
			this.ns.eliminar(id);
			
			
		}catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return this.enviarMensaje("Nivel eliminado",  HttpStatus.OK );
		
	}

	private ResponseEntity<?> validar(BindingResult result) {

		Map<String, Object> errores = new HashMap<>();

		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "Campo " + err.getField() + " " + err.getDefaultMessage());
		});

		return ResponseEntity.badRequest().body(errores);

	}

	private ResponseEntity<?> enviarMensaje(String texto, HttpStatus estado) {

		respuesta.put("mensaje", texto);
		log.info( texto );
		return ResponseEntity.status(estado).body(respuesta);

	}

}
