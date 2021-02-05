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

import com.proyecto.gateway.nivel.educativo.models.entity.Carrera;
import com.proyecto.gateway.nivel.educativo.models.entity.CicloEscolar;
import com.proyecto.gateway.nivel.educativo.models.entity.Grado;
import com.proyecto.gateway.nivel.educativo.services.CarreraService;
import com.proyecto.gateway.nivel.educativo.services.CicloEscolarService;
import com.proyecto.gateway.nivel.educativo.services.GradoService;


@RestController
@RequestMapping("/nivel/carrera/ciclo-escolar/grado")
public class GradoController {
	
	@Autowired
	private GradoService gradoService;

	@Autowired
	private CicloEscolarService cicloService;

	// Lista de respuestas
	Map<String, Object> respuesta = new HashMap<>();

	// Logger
	private static Logger log = LoggerFactory.getLogger(GradoController.class);

	@GetMapping("/listar")
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok().body(this.gradoService.listar());
	}

	@GetMapping("/buscar-por-id/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {

		Grado gradoDb = null;

		try {

			gradoDb = this.gradoService.buscarPorId(id);

			if (gradoDb == null) {
				return this.enviarMensaje("El grado no existe", HttpStatus.NOT_FOUND);
			}

		} catch (DataAccessException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ResponseEntity.ok().body(gradoDb);

	}

	@PostMapping("/{idCicloEscolar}/crear")
	public ResponseEntity<?> guardar(@Valid @RequestBody Grado grado, BindingResult validacion,
			@PathVariable(name = "idCicloEscolar") Long idCicloEscolar) {

		Grado gradoDb;
		CicloEscolar cicloDb;

		if (validacion.hasErrors()) {
			log.info("error antes");
			return this.validar(validacion);
		}

		try {

			cicloDb = this.cicloService.buscarPorId(idCicloEscolar);

			if (cicloDb == null) {
				return this.enviarMensaje("No existe el ciclo asociado para añadir", HttpStatus.BAD_REQUEST);
			}
			
			grado.setCicloEscolar(cicloDb);
			gradoDb = this.gradoService.guardar(grado);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResponseEntity.ok().body(gradoDb);
	}

	@PutMapping("/{idCicloEscolar}/editar/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Grado grado, BindingResult validacion,
			@PathVariable(name = "idCicloEscolar") Long idCicloEscolar, @PathVariable(name = "id") Long id) {

		CicloEscolar cicloEscolarDb;
		Grado gradoDb;


		if (validacion.hasErrors()) {
			return this.validar(validacion);
		}

		try {

			cicloEscolarDb = this.cicloService.buscarPorId(idCicloEscolar);
			gradoDb = this.gradoService.buscarPorId(id);

			if ( gradoDb == null || cicloEscolarDb == null ) {
				return this.enviarMensaje("No existe el ciclo o grado asociados", HttpStatus.BAD_REQUEST);
			}
			
			if( gradoDb.getCicloEscolar().getId() != cicloEscolarDb.getId() ) {
				return this.enviarMensaje("No existe el ciclo o grado asociados", HttpStatus.BAD_REQUEST);
			}
			
						
			gradoDb.replaceAll(grado);
			this.gradoService.guardar(gradoDb);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return enviarMensaje("Grado actualizado", HttpStatus.OK);
	}

	@DeleteMapping("/{idGrado}/eliminar/{id}")
	public ResponseEntity<?> eliminar( @PathVariable( name = "idGrado" ) Long idNivel, @PathVariable( name = "id" ) Long id) {

		CicloEscolar cicloDb;
		Grado gradoDb;

		try {

			cicloDb = this.cicloService.buscarPorId(idNivel);
			gradoDb = this.gradoService.buscarPorId(id);

			if ( gradoDb == null || cicloDb == null ) {
				return this.enviarMensaje("No existe el ciclo o grado asociados", HttpStatus.BAD_REQUEST);
			}
			
			//log.info( "ids " + nivelDb.getId() + " - " + carreraDb.getNivel().getId() );
			
			if( gradoDb.getCicloEscolar().getId() != cicloDb.getId() ) {
				return this.enviarMensaje("No existe el ciclo o grado asociados", HttpStatus.BAD_REQUEST);
			}	

			this.gradoService.eliminar(id);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return this.enviarMensaje("Grado eliminado", HttpStatus.OK);

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
