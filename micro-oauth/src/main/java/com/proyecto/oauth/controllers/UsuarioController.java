package com.proyecto.oauth.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.oauth.models.entity.Usuario;
import com.proyecto.oauth.models.entity.Token;
import com.proyecto.oauth.services.UsuarioService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Lista de respuestas
	Map<String, Object> respuesta = new HashMap<>();

	// Logger
	private static Logger log = LoggerFactory.getLogger(UsuarioController.class);

	@PostMapping("/crear")
	public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult validacion) {

		Usuario usuarioDb = null;
		ResponseEntityUsuario usuarioRespuesta = new ResponseEntityUsuario();

		if (validacion.hasErrors()) {
			return validar(validacion);
		}

		try {

			String encode = this.passwordEncoder.encode(usuario.getPassword());
			log.info(encode);
			usuario.setPassword(encode);

			String token = getJWTToken(usuario);
			log.info(token);

			usuarioDb = this.usuarioService.guardar(usuario);

			usuarioRespuesta.setOk(true);
			usuarioRespuesta.setUid(usuarioDb.getId());
			usuarioRespuesta.setNombre(usuarioDb.getNombre());
			usuarioRespuesta.setToken(token);

		} catch (DataIntegrityViolationException e) {
			return enviarMensaje("Ya existe el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ResponseEntity.ok().body(usuarioRespuesta);

	}

	@PostMapping("/login")
	public ResponseEntity<?> login( @Valid @RequestBody UsuarioLogin usuario, BindingResult validacion ){
		
		Usuario usuarioDb = null;
		ResponseEntityUsuario usuarioRespuesta = new ResponseEntityUsuario();
		
		if( validacion.hasErrors() ) {
			return validar(validacion);
		}
		
		try {
			
			usuarioDb = this.usuarioService.obtenerPorNombre( usuario.getNombre() );
			
			if( usuarioDb == null ) {
				return enviarMensaje("No existe el usuario",  HttpStatus.NOT_FOUND );
			}
			
			boolean valida = this.passwordEncoder.matches( usuario.getPassword() , usuarioDb.getPassword() );
			
			if( !valida ) {
				return enviarMensaje("Credenciales incorrectas",  HttpStatus.BAD_REQUEST );
			}
			
			usuarioRespuesta.setOk(true);
			usuarioRespuesta.setUid( usuarioDb.getId() );
			usuarioRespuesta.setNombre(  usuarioDb.getNombre() );
			usuarioRespuesta.setToken( getJWTToken( usuarioDb ) );		
			
		
			}catch (DataAccessException e) {
				return enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		
		return ResponseEntity.ok().body( usuarioRespuesta );
	}

	@PostMapping("/validarToken")
	public ResponseEntity<?> validarTokenRequest(@RequestBody Token token) {

		log.info(token.getToken());

		try {
			Claims claim = getClaimByToken(token.getToken());

			if (claim == null) {
				return enviarMensaje("Token no valido", HttpStatus.BAD_REQUEST);
			}

		} catch (MalformedJwtException e) {
			return enviarMensaje("Token con formato incorrecto", HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok().body(true);

	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	public Claims getClaimByToken(String token) {

		try {
			return Jwts.parser().setSigningKey("mySecretKey".getBytes("UTF-8")).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	private String getJWTToken(Usuario usuario) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		Map<String, Object> claims = new HashMap<>();

		claims.put("id", usuario.getId());
		claims.put("nombre", usuario.getNombre());
		claims.put("rol", usuario.getRol());

		String token = Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
		return token;
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
