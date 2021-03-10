package com.proyecto.usuarios.security.jwt;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.proyecto.usuarios.security.entity.UsuarioPrincipal;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * genera el token y valida si esta formado 
 * @author T450s
 *
 */

@Component
public class JwtProvider {
	
	private static Logger log = LoggerFactory.getLogger( JwtProvider.class ); 
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private int expiration;
	
	public String generateToken( Authentication auth ) {
		
		UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) auth.getPrincipal();
		
		return Jwts.builder().setSubject( usuarioPrincipal.getNombreUsuario() )
				.setIssuedAt( new Date() )
				.setExpiration( new Date( new Date().getTime() + expiration * 1000 )  )
				.signWith( SignatureAlgorithm.HS512 , secret )
				.compact();
	}
	
	public String getNombreUsuarioFromToken( String token ) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateToken( String token ) {
		try {
			
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
			
		}catch ( MalformedJwtException  e) {
			log.error("Token mal formado");
		}catch ( UnsupportedJwtException  e) {
			log.error("Token no soportado");
		}catch ( ExpiredJwtException  e) {
			log.error("Token expirado");
		}catch ( IllegalArgumentException  e) {
			log.error("Token vacion");
		}catch ( SignatureException  e) {
			log.error("Fail en la firma");
		}
		
		return false;
	}

}
