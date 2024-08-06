package com.gestionestudiantil.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.gestionestudiantil.model.Rol;
import com.gestionestudiantil.service.SeguridadService;

@ManagedBean
@ViewScoped
public class RolesController implements Serializable {
	
	private static final long serialVersionUID = -1402933798849366953L;
	
	@Inject
	private SeguridadService seguridadService;
	
	private List<Rol> roles;

	@PostConstruct
	public void init() {
		roles = seguridadService.obtenerTodosRoles();
	}
	
	public List<Rol> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
}