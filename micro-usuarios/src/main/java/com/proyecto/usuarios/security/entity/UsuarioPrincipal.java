package com.proyecto.usuarios.security.entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioPrincipal implements UserDetails {

	private String nombreUsuario;

	private String password;

	private String email;

	// tipo granteed autority provio de secutiry de spring
	private Collection<? extends GrantedAuthority> authorities;

	public UsuarioPrincipal() {
		super();
	}

	public UsuarioPrincipal(String nombreUsuario, String password, String email,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.password = password;
		this.email = email;
		this.authorities = authorities;
	}
	
	// hacemos el casteo de usuario normal a usuario granted

	public static UsuarioPrincipal build(Usuario usuario) {
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name()))
				.collect(Collectors.toList());

		return new UsuarioPrincipal(usuario.getNombreUsuario(), usuario.getEmail(), usuario.getPassword(), authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.nombreUsuario;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	//////////////// 7

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

}
