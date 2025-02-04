package com.proyecto.cct.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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

import com.proyecto.cct.feign.clients.ContadorClient;
import com.proyecto.cct.feign.clients.GradoClient;
import com.proyecto.cct.models.entity.CentroDeTrabajo;
import com.proyecto.cct.models.entity.Grupo;
import com.proyecto.cct.models.entity.Salon;
import com.proyecto.cct.services.CctService;
import com.proyecto.cct.services.GrupoService;
import com.proyecto.cct.services.SalonService;

import feign.FeignException;

@RestController
@RequestMapping( "/grupos" )
public class GrupoController {
	
	@Autowired
	private GradoClient gradoClient;

	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private SalonService salonService;

	// Lista de respuestas
	Map<String, Object> respuesta = new HashMap<>();

	// Logger
	private static Logger log = LoggerFactory.getLogger(GrupoController.class);

	
	@GetMapping("/buscar-por-id/{id}")
	public ResponseEntity<?> buscarPorCct(@PathVariable Long id) {

		Grupo grupoDb = null;

		try {						

			grupoDb = this.grupoService.buscarPorId(id);

			if (grupoDb == null) {
				return this.enviarMensaje("El grupo no existe", HttpStatus.NOT_FOUND);
			}

		} catch (DataAccessException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ResponseEntity.ok().body(grupoDb);

	}
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<?> editar( @PathVariable Long id, @Valid @RequestBody Grupo grupo, BindingResult validacion ){

		Grupo grupoDb = null;
		
		if( validacion.hasErrors() ) {
			return this.validar(validacion);
		}
		
		try {		
			
			grupoDb = this.grupoService.buscarPorId(id);
			
			if( grupoDb == null ) {
				return enviarMensaje("El grupo no existe", HttpStatus.NOT_FOUND );
			}
			

			
			grupoDb.replaceAll( grupo );
			grupoDb = this.grupoService.guardar(grupoDb);
						
		}catch( DataException e ) {
			enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResponseEntity.ok().body(grupoDb);
		
	}

	@PostMapping("/{idSalon}/crear")
	public ResponseEntity<?> guardar(@Valid @RequestBody Grupo grupo, BindingResult validacion,
			@PathVariable String idSalon
			) {

		Grupo grupoDb;

		if (validacion.hasErrors()) {
			log.info("error antes");
			return this.validar(validacion);
		}			

		try {
			
			/*Grupo grupoExiste = this.grupoService.estaAsignado(idSalon, grupo.getIdCicloEscolar());
			
			if( grupoExiste != null ) {
				log.info( grupoExiste.toString() );
				return enviarMensaje("El salon ya esta asignado para este ciclo",  HttpStatus.BAD_REQUEST );
			}
			
			log.info( "SALON : "+ idSalon );*/
			Salon salonDb = this.salonService.buscarPorId( idSalon );			
			
			this.gradoClient.buscarPorId( grupo.getIdGrado() ).orElse(null); 
			
			
			if( salonDb == null ) {
				return enviarMensaje("No existe salon",  HttpStatus.NOT_FOUND );
			}
			
			grupo.setSalon( salonDb );
			
			grupoDb = this.grupoService.guardar(grupo);


		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch ( FeignException.NotFound f) {
			return this.enviarMensaje("No existe el grado",  HttpStatus.NOT_FOUND );
		}

		return ResponseEntity.ok().body(grupoDb);
	}
	
	@DeleteMapping("/eliminar/{idGrupo}")
	public ResponseEntity<?> eliminarGrupo( @PathVariable Long idGrupo) {
		
		try {
			
			Grupo grupoExiste = this.grupoService.buscarPorId(idGrupo);
			
			if( grupoExiste == null ) {
				return this.enviarMensaje("No existe",  HttpStatus.NOT_FOUND );
			}
			
			this.grupoService.eliminar(idGrupo);
			
			
		}catch( DataException e ) {
			return this.enviarMensaje("Error interno",  HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
		return this.enviarMensaje("Eliminado",  HttpStatus.NO_CONTENT );
		
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
