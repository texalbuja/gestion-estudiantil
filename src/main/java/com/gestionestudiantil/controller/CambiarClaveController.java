package com.gestionestudiantil.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.model.Usuario;
import com.gestionestudiantil.service.SeguridadService;

@ManagedBean
@ViewScoped
public class CambiarClaveController implements Serializable {
	
	private static final long serialVersionUID = -8330332584036487165L;
	
	@ManagedProperty("#{usuario}")
	private Usuario usuario;
	
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private SeguridadService seguridadService;
	
	private String clave;
	
	private String claveAnterior;

	
	public String cambiar() {
		HttpServletRequest request = (HttpServletRequest) facesContext
				.getExternalContext().getRequest();
		try {
			seguridadService
					.cambiarClave(usuario.getId(), clave, claveAnterior);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Éxito",
					"Contraseña actualizada correctamente"));
			if (request.isUserInRole("secretaria")) {
				return "/resumen/secretaria.jsf?faces-redirect=true";
			}
			if (request.isUserInRole("administrador")) {
				return "/resumen/administrador.jsf?faces-redirect=true";
			}
			if (request.isUserInRole("estudiante")) {
				return "/resumen/estudiante.jsf?faces-redirect=true";
			}
			if (request.isUserInRole("docente")) {
				return "/resumen/docente.jsf?faces-redirect=true";
			}
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error",
					"La contraseña ingresada no coincide"));
		}
		return "";
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getClaveAnterior() {
		return claveAnterior;
	}

	public void setClaveAnterior(String claveAnterior) {
		this.claveAnterior = claveAnterior;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}