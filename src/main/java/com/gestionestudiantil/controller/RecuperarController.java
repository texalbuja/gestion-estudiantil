package com.gestionestudiantil.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.service.SeguridadService;

@ManagedBean
@ViewScoped
public class RecuperarController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4064632692783726257L;

	@Inject
	private SeguridadService seguridadService;

	@Inject
	private FacesContext facesContext;

	private String cedula;

	public void recuperarContrasenia() {
		try {
			facesContext
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_INFO,
									"Procesando",
									"Estamos verificando la información ingresada, en breve te llegará un correo con instrucciones para recuperar la clave."));
			seguridadService.recuperarCredenciales(cedula);
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Error al recuperar contraseña", e.getMessage()));
		}
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
}