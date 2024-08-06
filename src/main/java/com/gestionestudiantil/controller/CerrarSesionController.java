package com.gestionestudiantil.controller;

import java.io.Serializable;
import java.security.Principal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class CerrarSesionController implements Serializable {

	private static final long serialVersionUID = -7620065634453319227L;

	@Inject
	private FacesContext facesContext;

	public String cerrarSesion() throws ServletException {
		HttpServletRequest request = (HttpServletRequest) facesContext
				.getExternalContext().getRequest();

		Principal userPrincipal = request.getUserPrincipal();
		if (userPrincipal != null) {
			request.logout();
		}

		return "/iniciarSesion.jsf?faces-redirect=true";

	}
}