package com.proyecto.alumnos.controllers;

import java.beans.PropertyEditor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
 
import com.proyecto.alumnos.models.entity.Alumno;
import com.proyecto.alumnos.models.entity.Domicilio;
import com.proyecto.alumnos.models.entity.Tutor;
import com.proyecto.alumnos.services.AlumnoService;
import com.proyecto.alumnos.services.DomicilioService; 
import com.proyecto.alumnos.services.ReportService;
import com.proyecto.alumnos.services.TutorService;
import com.proyecto.alumnos.validadores.listas.Errores;
import com.proyecto.alumnos.validadores.listas.ValidList;

import net.sf.jasperreports.engine.JRException;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class AlumnoController {

	// Lista de respuestas
	Map<String, Object> respuesta = new HashMap<>();

	// Logger
	private static Logger log = LoggerFactory.getLogger(AlumnoController.class);

	@Autowired
	private AlumnoService as;
	
	@Autowired
	private TutorService ts;
	
	@Autowired
	private ReportService rs;

	@Autowired
	Validator validator;

	/////////////////// GETS

	/**
	 * Busca el alumno por id, empleado para editar
	 * 
	 * @param id ID del alumno
	 * @return Alumno con todos sus campos y relaciones Codigo de errores 404 - El
	 *         alumno no existe en la DB 500 - Error interno
	 */
	@GetMapping("/buscar-por-id/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {

		Alumno alumnoDb = null;

		try {

			alumnoDb = as.buscarPorId(id); // buscalos el alumno en cuestion
			// Si existe el registro, buscamos sus entidades asociadas ya que al estar en
			if (alumnoDb == null) {
				return enviarMensaje( "EL alumno no encontrado" ,  HttpStatus.NOT_FOUND );
			}

		} catch (DataAccessException e) {
			return enviarMensaje("Error interno",  HttpStatus.INTERNAL_SERVER_ERROR );
		}

		return ResponseEntity.ok().body(alumnoDb);
	}

	/**
	 * Busca el alumno por el CURP
	 * 
	 * @param CURP del alumno
	 * @return Alumno con todos sus campos y relaciones Codigo de errores 400 -
	 *         Formato invalido de CURP 404 - El alumno no existe en la DB 500 -
	 *         Error interno
	 */
	@GetMapping("/buscar-por-curp/{curp}")
	public ResponseEntity<?> buscarPorCurp(@PathVariable String curp) {

		Alumno alumnoDb = null;

		try {

			if (validarCURP(curp.trim())) {
				alumnoDb = as.buscarPorCurp(curp); // buscalos el alumno en cuestion

				if (alumnoDb == null) {
					return enviarMensaje( "EL alumno no encontrado" ,  HttpStatus.NOT_FOUND );
				}

				alumnoDb.setDomicilio(null);
				

			} else {
				return enviarMensaje( "Formato invalido de CURP" ,  HttpStatus.BAD_REQUEST );
			}

		} catch (DataAccessException e) {
			return  enviarMensaje( "Error interno" ,  HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(alumnoDb);

	}

	/**
	 * Busca el alumno por apellido paterno o materno
	 * 
	 * @param apellido Paterno o materno
	 * @return Alumno con todos sus campos y relaciones Codigo de errores 404 - El
	 *         alumno no existe en la DB 500 - Error interno
	 */
	@GetMapping("/buscar-por-apellido/{apellido}")
	public ResponseEntity<?> buscarPorApellido(@PathVariable String apellido) {


		List<Alumno> alumnosDb = null;

		try {
			
			alumnosDb = as.buscarPorApellido(apellido); // buscalos el alumno en cuestion

			if (alumnosDb.isEmpty()) {
				return enviarMensaje( "No existen registros" ,  HttpStatus.NOT_FOUND );
			} else {
				alumnosDb.forEach(al -> {
					al.setDomicilio(null);
				});
			}
			

		} catch (DataAccessException e) {
			return enviarMensaje( "Error interno" ,  HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(alumnosDb);

	}

	/**
	 * Listado de alumno sin entidades relacionadadas
	 * 
	 * @return Listado de alumnos
	 */
	@GetMapping("/listar")
	public ResponseEntity<?> listar() { 
		return ResponseEntity.ok().body( this.as.listar() );
	}

	/////////////////////// POST

	/**
	 * Crear alumno
	 * 
	 * @param alumno A crear con sus entidades correspondientes
	 * @return Alumno persistido
	 */
	@PostMapping("/crear")
	public ResponseEntity<?> crear(@Valid @RequestBody Alumno alumno, BindingResult validacion) {
		
		Alumno alumnoNuevo = null;

		if (validacion.hasErrors()) {
			log.info("error antes");
			return this.validar(validacion);
		}			

		// si el alumno viene con omision de domicilio, le creamos uno nulo para
		// persistir
		if (alumno.getDomicilio() == null) {
			alumno.setDomicilio(new Domicilio());
		}		
		

		try{		
			

			for( Tutor tutor : alumno.getTutores()  ) {
				
				int contador = 0;
				
				for( Tutor tutorCompare : alumno.getTutores() ) {
					
					if( tutor.getCurp().equals(tutorCompare.getCurp()) ) {
						contador++;
					}
					
					if( contador > 1 )
					{
						return enviarMensaje("Existen CURP duplicado como tutor para este alumno", HttpStatus.BAD_REQUEST);
					}
					
				}			
			}
			
			alumnoNuevo = this.as.guardar(alumno);		
			

		} catch (DataAccessException e) {
			return enviarMensaje( "Ya existe la curp" ,  HttpStatus.INTERNAL_SERVER_ERROR );
		} catch (ConstraintViolationException e) {
			return enviarMensaje( "Error en el campo tutor" ,  HttpStatus.BAD_REQUEST );
		}

		return ResponseEntity.ok().body(alumnoNuevo);

	}

	/**
	 * Crear alumno por medio de una lista
	 * 
	 * @param alumnos Lista de alumnos para persistir
	 * @return Listado persistido
	 */
	@PostMapping("/crear-con-lista")
	public ResponseEntity<?> crearConLista(@Valid @RequestBody ValidList<Alumno> alumnos, BindingResult validacion) {

		if (validacion.hasErrors()) {
			log.info("error");
			return validarConLista(alumnos.size(), validacion);
		}	
		

		List<Alumno> alumnosDb = this.as.guardarVarios(alumnos.getValues());
		return ResponseEntity.ok().body(alumnosDb);

	}

	//////////////////////// PUT

	/**
	 * Editar un alumno existente
	 * 
	 * @param alumno Alumno existente en la db
	 * @param id     Id del alumno existente
	 * @return Alumno editado
	 */
	@PutMapping("/editar/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Alumno alumno, 
			BindingResult validacion, 
			@PathVariable Long id) {

		Alumno alumnoDb = null;		
		
		if (validacion.hasErrors()) {
			log.info("error antes");
			return this.validar(validacion);
		}
		
		for( Tutor tutor : alumno.getTutores()  ) {
			
			int contador = 0;
			
			for( Tutor tutorCompare : alumno.getTutores() ) {
				
				if( tutor.getCurp().equals(tutorCompare.getCurp()) ) {
					contador++;
				}
				
				if( contador > 1 )
				{
					return enviarMensaje("Existen CURP duplicado como tutor para este alumno", HttpStatus.BAD_REQUEST);
				}
				
			}			
		}
		
		try {
			
			// realizamos los cambios en la relacion alumno -> domicilio
			alumnoDb = this.as.buscarPorId(id);

			if( alumnoDb.getDomicilio() == null ) {
				log.info("nulo");
				alumnoDb.setDomicilio( new Domicilio() );
			}
			
			alumnoDb.replaceAllAttributes(alumno);

			// Comparamos los tutores que se tienen que eliminar		

			try {
				for (Tutor tutorDb : alumnoDb.getTutores()) {
					// si no existe el curp, es para eliminar
					if (!(alumno.getTutores().contains(tutorDb))) {
						alumnoDb.removeTutor(tutorDb);
					}
				}
			} catch (Exception e) {
				log.info("arreglo vacio");
			}

			// Comparamos si se va añadir o editar un tutor

			for (Tutor tutor : alumno.getTutores()) {
				// Si existe el tutor, llamamos el servicio para actualizar ya que AService no
				// puede hacerlo por si solo
				if (alumnoDb.getTutores().contains(tutor)) {

					Tutor tutorEditar = this.ts.buscarPorId( tutor.getId() );
					tutorEditar.replaceAllAttributes(tutor);
					this.ts.guardar(tutorEditar);

				} else {
					alumnoDb.addATutor(tutor);
				}
			}
		
			this.as.guardar(alumnoDb);

		} catch (DataAccessException e) {
			log.info("ERROR --> ");
			log.info(e.getMostSpecificCause().toString());
			respuesta.put("mensaje", e.getMostSpecificCause().toString());
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}

		return ResponseEntity.ok().body(alumnoDb);

	}
	
	
	@DeleteMapping("/eliminar/{id}" )
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		try {
			this.as.eliminar(id);
		}catch (DataAccessException e) {
			respuesta.put("mensaje", e.getMostSpecificCause().toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
		return ResponseEntity.status( HttpStatus.NO_CONTENT ).build();
	}

	// Validar CURP
	// Fuente: https://vidadigital.com.mx/es/expresiones/regulares/curp/
	private boolean validarCURP(String curp) {

		String regex = "[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}" + "(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])" + "[HM]{1}"
				+ "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)"
				+ "[B-DF-HJ-NP-TV-Z]{3}" + "[0-9A-Z]{1}[0-9]{1}$";

		Pattern patron = Pattern.compile(regex);
		if (!patron.matcher(curp).matches()) {
			return false; // no validado
		} else {
			return true; // validado
		}
	}

	/**
	 * Envia los errores que se encuentran en la entidad
	 * 
	 * @param result Validador de spring
	 * @return Json con los campos con error
	 */
	private ResponseEntity<?> validar(BindingResult result) {

		Map<String, Object> errores = new HashMap<>();

		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "Campo " + err.getField() + " " + err.getDefaultMessage());
		});

		return ResponseEntity.badRequest().body(errores);

	}

	/**
	 * Envia los errores que se encuentran en una lista de registros
	 * 
	 * @param cantidadRegistros Cantidad de registros recibidos
	 * @param validacion        Validador spring
	 * @return Json con los campos con error de cada registro
	 */
	private ResponseEntity<?> validarConLista(int cantidadRegistros, BindingResult validacion) {

		// lista para enviar errores
		List<Errores> listaErrores = new ArrayList<>();

		// lista temporal para obtener los campos que tienen error
		ArrayList<String> camposTemporal = new ArrayList<>();

		// filtracion de campos value[n].campo
		validacion.getFieldErrors().forEach(err -> {
			camposTemporal.add(err.getField().substring(10));
		});

		// filtracion de campos repetidos
		Set<String> campos = new HashSet<>(camposTemporal);

		/**
		 * Evaluamos que campoError pertenece a cada registro
		 */
		for (int i = 0; i < cantidadRegistros; i++) {

			listaErrores.add(new Errores(i, new HashMap<String, Object>()));

			for (String campo : campos) {
				// extramos el campo a buscar
				String cadenaCampo = "values[" + i + "]." + campo;

				// si existe ese error en el registro, lo anexamos
				if (validacion.getFieldError(cadenaCampo) != null) {
					listaErrores.get(i).getCampos().put(campo,
							validacion.getFieldError(cadenaCampo).getDefaultMessage().toString());
				}

			}

		}

		return ResponseEntity.badRequest().body(listaErrores);

	}
	
	@GetMapping("/report/{format}")
	public void reporte( @PathVariable String format, HttpServletResponse res  ) throws JRException, IOException, URISyntaxException {
		
		// generamos el reporte
		this.rs.exportReport(format);
		
		// selecciona el archivo que vamos a enviar
		res.setHeader( "Content-Disposition", "attachment; filename = alumnos.pdf" );
		
		// escribimos en el stream su contenido
		res.getOutputStream().write( contentOf("alumnos.pdf") );		
	}
	
	@GetMapping("/reportInfo/{id}")
	public void reporteInfo( @PathVariable Long id, HttpServletResponse res  ) throws IOException, URISyntaxException, JRException {
		
		log.info( " entramos " );
		// generamos el reporte
		this.rs.exportarAlumnoInformacion(id);	
		
		res.setHeader( "Content-Disposition", "attachment; filename = alumnoInformacion.pdf" );
		
		res.getOutputStream().write( contentOf("alumnoInformacion.pdf") );
				
	}
	
	private byte[] contentOf( String fileName ) throws IOException, URISyntaxException {
		return Files.readAllBytes( Paths.get( getClass().getClassLoader().getResource(fileName).toURI() ) );
	}
	
	private ResponseEntity<?> enviarMensaje(String texto, HttpStatus estado) {

		respuesta.put("mensaje", texto);
		log.info( texto );
		return ResponseEntity.status(estado).body(respuesta);

	}
	


	

}
