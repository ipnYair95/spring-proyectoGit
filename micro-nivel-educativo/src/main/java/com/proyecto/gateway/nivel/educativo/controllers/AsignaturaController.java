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
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.gateway.nivel.educativo.models.entity.Asignatura;
import com.proyecto.gateway.nivel.educativo.models.entity.Carrera;
import com.proyecto.gateway.nivel.educativo.models.entity.CicloEscolar;
import com.proyecto.gateway.nivel.educativo.models.entity.Grado;
import com.proyecto.gateway.nivel.educativo.services.CarreraService;
import com.proyecto.gateway.nivel.educativo.services.GradoService;
import com.proyecto.gateway.nivel.educativo.services.AsignaturaService;


@RestController
@RequestMapping("/nivel/carrera/ciclo-escolar/grado/asignatura")
public class AsignaturaController {
	
	@Autowired
	private AsignaturaService asignaturaService;

	@Autowired 
	private GradoService gradoService;

	// Lista de respuestas
	Map<String, Object> respuesta = new HashMap<>();

	// Logger
	private static Logger log = LoggerFactory.getLogger(AsignaturaController.class);

	@GetMapping("/listar")
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok().body(this.asignaturaService.listar());
	}

	@GetMapping("/buscar-por-id/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {

		Asignatura asignaturaDb = null;

		try {

			asignaturaDb = this.asignaturaService.buscarPorId(id);

			if (asignaturaDb == null) {
				return this.enviarMensaje("La asignatura no existe", HttpStatus.NOT_FOUND);
			}

		} catch (DataAccessException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ResponseEntity.ok().body(asignaturaDb);

	}

	@PostMapping("/{idGrado}/crear")
	public ResponseEntity<?> guardar(@Valid @RequestBody Asignatura asignatura, BindingResult validacion,
			@PathVariable(name = "idGrado") Long idGrado) {

		Asignatura asignaturaDb;
		Grado gradoDb;

		if (validacion.hasErrors()) {
			log.info("error antes");
			return this.validar(validacion);
		}

		try {

			gradoDb = this.gradoService.buscarPorId(idGrado);

			if (gradoDb == null) {
				return this.enviarMensaje("No existe el grado asociado para añadir", HttpStatus.BAD_REQUEST);
			}
			
			asignatura.setGrado(gradoDb);
			asignaturaDb = this.asignaturaService.guardar(asignatura);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResponseEntity.ok().body(asignaturaDb);
	}

	@PutMapping("/{idGrado}/editar/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Asignatura asignatura, BindingResult validacion,
			@PathVariable(name = "idGrado") Long idGrado, @PathVariable(name = "id") Long id) {

		Grado gradoDb;
		Asignatura asignaturaDb;


		if (validacion.hasErrors()) {
			return this.validar(validacion);
		}

		try {

			gradoDb = this.gradoService.buscarPorId(idGrado);
			asignaturaDb = this.asignaturaService.buscarPorId(id);

			if ( asignaturaDb == null || gradoDb == null ) {
				return this.enviarMensaje("No existe grado o asignatura asiociados", HttpStatus.BAD_REQUEST);
			}
			
			if( asignaturaDb.getGrado().getId() != gradoDb.getId() ) {
				return this.enviarMensaje("No existe grado o asignatura asiociados", HttpStatus.BAD_REQUEST);
			}
			
						
			asignaturaDb.setNombreAsignatura( asignatura.getNombreAsignatura() );
			this.asignaturaService.guardar(asignaturaDb);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return enviarMensaje("Asignatura actualizado", HttpStatus.OK);
	}

	@DeleteMapping("/{idGrado}/eliminar/{id}")
	public ResponseEntity<?> eliminar( @PathVariable( name = "idGrado" ) Long idGrado, @PathVariable( name = "id" ) Long id) {

		Grado gradoDb;
		Asignatura asignaturaDb;

		try {

			gradoDb = this.gradoService.buscarPorId(idGrado);
			asignaturaDb = this.asignaturaService.buscarPorId(id);

			if ( asignaturaDb == null || gradoDb == null ) {
				return this.enviarMensaje("No existe grado o asignatura asiociados", HttpStatus.BAD_REQUEST);
			}
			
			//log.info( "ids " + nivelDb.getId() + " - " + carreraDb.getNivel().getId() );
			
			if( asignaturaDb.getGrado().getId() != gradoDb.getId() ) {
				return this.enviarMensaje("No existe grado o asignatura asiociados", HttpStatus.BAD_REQUEST);
			}	

			this.asignaturaService.eliminar(id);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return this.enviarMensaje("Asignatura eliminada", HttpStatus.OK);

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
		log.info(texto);
		return ResponseEntity.status(estado).body(respuesta);

	}

}
