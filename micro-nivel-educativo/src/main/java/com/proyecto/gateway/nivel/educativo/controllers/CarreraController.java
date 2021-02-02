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
import com.proyecto.gateway.nivel.educativo.models.entity.Nivel;
import com.proyecto.gateway.nivel.educativo.services.CarreraService;
import com.proyecto.gateway.nivel.educativo.services.NivelService;

@RestController
@RequestMapping("/nivel/carrera")
public class CarreraController {

	@Autowired
	private CarreraService carreraService;

	@Autowired
	private NivelService nivelService;

	// Lista de respuestas
	Map<String, Object> respuesta = new HashMap<>();

	// Logger
	private static Logger log = LoggerFactory.getLogger(CarreraController.class);

	@GetMapping("/listar")
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok().body(this.carreraService.listar());
	}

	@GetMapping("/buscar-por-id/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {

		Carrera carreraDb = null;

		try {

			carreraDb = this.carreraService.buscarPorId(id);

			if (carreraDb == null) {
				return this.enviarMensaje("La carrera no existe", HttpStatus.NOT_FOUND);
			}

		} catch (DataAccessException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ResponseEntity.ok().body(carreraDb);

	}

	@PostMapping("/{idNivel}/crear")
	public ResponseEntity<?> guardar(@Valid @RequestBody Carrera carrera, BindingResult validacion,
			@PathVariable(name = "idNivel") Long idNivel) {

		Nivel nivelDb;
		Carrera carreraDb;

		if (validacion.hasErrors()) {
			log.info("error antes");
			return this.validar(validacion);
		}

		try {

			nivelDb = this.nivelService.buscarPorId(idNivel);

			if (nivelDb == null) {
				return this.enviarMensaje("No existe el nivel asociado para añadir", HttpStatus.BAD_REQUEST);
			}

			carrera.setNivel(nivelDb);
			carreraDb = this.carreraService.guardar(carrera);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

		return ResponseEntity.ok().body(carreraDb);
	}

	@PutMapping("/{idNivel}/editar/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Carrera carrera, BindingResult validacion,
			@PathVariable(name = "idNivel") Long idNivel, @PathVariable(name = "id") Long id) {

		Nivel nivelDb;
		Carrera carreraDb;

		if (validacion.hasErrors()) {
			return this.validar(validacion);
		}

		try {

			nivelDb = this.nivelService.buscarPorId(idNivel);
			carreraDb = this.carreraService.buscarPorId(id);

			if ( nivelDb == null || carreraDb == null ) {
				return this.enviarMensaje("No existe el nivel o carrera asociados", HttpStatus.BAD_REQUEST);
			}
			
			//log.info( "ids " + nivelDb.getId() + " - " + carreraDb.getNivel().getId() );
			
			if( nivelDb.getId() != carreraDb.getNivel().getId()  ) {
				return this.enviarMensaje("No existe el nivel o carrera asociados", HttpStatus.BAD_REQUEST);
			}			
			
						
			carreraDb.setNombreCarrera(carrera.getNombreCarrera());
			this.carreraService.guardar(carreraDb);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return enviarMensaje("Carrera actualizada", HttpStatus.OK);
	}

	@DeleteMapping("/{idNivel}/eliminar/{id}")
	public ResponseEntity<?> eliminar( @PathVariable( name = "idNivel" ) Long idNivel, @PathVariable( name = "id" ) Long id) {

		Nivel nivelDb;
		Carrera carreraDb;

		try {

			nivelDb = this.nivelService.buscarPorId(idNivel);
			carreraDb = this.carreraService.buscarPorId(id);

			if ( nivelDb == null || carreraDb == null ) {
				return this.enviarMensaje("No existe el nivel o carrera asociados", HttpStatus.BAD_REQUEST);
			}
			
			//log.info( "ids " + nivelDb.getId() + " - " + carreraDb.getNivel().getId() );
			
			if( nivelDb.getId() != carreraDb.getNivel().getId()  ) {
				return this.enviarMensaje("No existe el nivel o carrera asociados", HttpStatus.BAD_REQUEST);
			}	

			this.carreraService.eliminar(id);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return this.enviarMensaje("Carrera eliminado", HttpStatus.OK);

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
