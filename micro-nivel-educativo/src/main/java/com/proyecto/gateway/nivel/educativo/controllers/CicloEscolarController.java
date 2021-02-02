package com.proyecto.gateway.nivel.educativo.controllers;

import java.time.Period;
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

import com.proyecto.gateway.nivel.educativo.models.entity.Carrera;
import com.proyecto.gateway.nivel.educativo.models.entity.CicloEscolar;
import com.proyecto.gateway.nivel.educativo.models.entity.Nivel;
import com.proyecto.gateway.nivel.educativo.services.CarreraService;
import com.proyecto.gateway.nivel.educativo.services.CicloEscolarService;
import com.proyecto.gateway.nivel.educativo.services.NivelService;

@RestController
@RequestMapping("/nivel/carrera/ciclo-escolar")
public class CicloEscolarController {

	@Autowired
	private CicloEscolarService cicloEscolarService;

	@Autowired
	private CarreraService carreraService;

	// Lista de respuestas
	Map<String, Object> respuesta = new HashMap<>();

	// Logger
	private static Logger log = LoggerFactory.getLogger(CicloEscolarController.class);

	@GetMapping("/listar")
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok().body(this.cicloEscolarService.listar());
	}

	@GetMapping("/buscar-por-id/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {

		CicloEscolar cicloDb = null;

		try {

			cicloDb = this.cicloEscolarService.buscarPorId(id);

			if (cicloDb == null) {
				return this.enviarMensaje("El ciclo escolar no existe", HttpStatus.NOT_FOUND);
			}

		} catch (DataAccessException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ResponseEntity.ok().body(cicloDb);

	}

	@PostMapping("/{idCarrera}/crear")
	public ResponseEntity<?> guardar(@Valid @RequestBody CicloEscolar cicloEscolar, BindingResult validacion,
			@PathVariable(name = "idCarrera") Long idCarrera) {

		Carrera carreraDb;
		CicloEscolar cicloDb;

		if (validacion.hasErrors()) {
			log.info("error antes");
			return this.validar(validacion);
		}

		try {

			carreraDb = this.carreraService.buscarPorId(idCarrera);

			if (carreraDb == null) {
				return this.enviarMensaje("No existe la carrera asociada para añadir", HttpStatus.BAD_REQUEST);
			}
			
			
			cicloEscolar.setCarrera(carreraDb);
			cicloDb = this.cicloEscolarService.guardar(cicloEscolar);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResponseEntity.ok().body(cicloDb);
	}

	@PutMapping("/{idCarrera}/editar/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody CicloEscolar cicloEscolar, BindingResult validacion,
			@PathVariable(name = "idCarrera") Long idCarrera, @PathVariable(name = "id") Long id) {

		Carrera carreraDb;
		CicloEscolar cicloDb;


		if (validacion.hasErrors()) {
			return this.validar(validacion);
		}

		try {

			carreraDb = this.carreraService.buscarPorId(idCarrera);
			cicloDb = this.cicloEscolarService.buscarPorId(id);

			if ( cicloDb == null || carreraDb == null ) {
				return this.enviarMensaje("No existe la carrera o ciclo escolar asociados", HttpStatus.BAD_REQUEST);
			}
			
			log.info( "ids " + carreraDb.getId() + " - " + cicloDb.getCarrera().getId() );
			
			if( cicloDb.getCarrera().getId() != carreraDb.getId() ) {
				return this.enviarMensaje("No existe la carrera o ciclo escolar asociados", HttpStatus.BAD_REQUEST);
			}
			
						
			cicloDb.replaceAll(cicloEscolar);
			this.cicloEscolarService.guardar(cicloDb);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return enviarMensaje("Ciclo escolar actualizado", HttpStatus.OK);
	}

	@DeleteMapping("/{idCarrera}/eliminar/{id}")
	public ResponseEntity<?> eliminar( @PathVariable( name = "idCarrera" ) Long idCarrera, @PathVariable( name = "id" ) Long id) {

		Carrera carreraDb;
		CicloEscolar cicloDb;

		try {

			carreraDb = this.carreraService.buscarPorId(idCarrera);
			cicloDb = this.cicloEscolarService.buscarPorId(id);

			if ( cicloDb == null || carreraDb == null ) {
				log.info(  "nulos ");
				return this.enviarMensaje("No existe la carrera o ciclo escolar asociados", HttpStatus.BAD_REQUEST);
			}
			
			//log.info( "ids " + nivelDb.getId() + " - " + carreraDb.getNivel().getId() );
			
			if( cicloDb.getCarrera().getId() != carreraDb.getId() ) {
				log.info(  "no coinciden ");
				return this.enviarMensaje("No existe la carrera o ciclo escolar asociados", HttpStatus.BAD_REQUEST);
			}	

			this.cicloEscolarService.eliminar(id);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return this.enviarMensaje("Ciclo escolar eliminado", HttpStatus.OK);

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
