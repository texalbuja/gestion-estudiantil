package com.gestionestudiantil.model;

import com.gestionestudiantil.enums.Roles;

public class Autoridad {

	public Autoridad(Usuario usuario, Roles rol) {
		this.usuario = usuario;
		this.rol = rol;
	}

	private Usuario usuario;
	private Roles rol;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Roles getRol() {
		return rol;
	}

	public void setRol(Roles rol) {
		this.rol = rol;
	}

}
