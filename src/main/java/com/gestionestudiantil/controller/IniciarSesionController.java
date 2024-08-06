package com.gestionestudiantil.controller;

import java.io.Serializable;
import java.security.Principal;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.model.Docente;
import com.gestionestudiantil.model.Estudiante;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Usuario;
import com.gestionestudiantil.service.SeguridadService;
import java.io.IOException;

@ManagedBean
@RequestScoped
public class IniciarSesionController implements Serializable {

	private static final long serialVersionUID = 3822466218951863801L;

	@Inject
	private SeguridadService seguridadService;

	@Inject
	private FacesContext facesContext;

	private String usuario;

	private String clave;

	public String iniciarSesion() throws GestionEstudiantilException {
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		try {
			Principal userPrincipal = request.getUserPrincipal();
			if (userPrincipal != null) {
				request.logout();
			}

			request.login(usuario, clave);
			String periodo;
			if (facesContext.getExternalContext().getSessionMap().get("periodo") != null)
				periodo = facesContext.getExternalContext().getSessionMap().get("periodo").toString();
			else {
				facesContext.getExternalContext().getSessionMap().put("periodo", "2021-2022");
				periodo = "2021-2022";
			}
			seguridadService.validarBloqueosUsuarioPorIdentificacion(usuario);

			String identificacion = request.getUserPrincipal().getName();
			Usuario usuario = seguridadService.obtenerUsuarioPorIdentificacion(identificacion);
			facesContext.getExternalContext().getSessionMap().put("usuario", usuario);
			facesContext.getExternalContext().getSessionMap().put("institucion", usuario.getInstitucion());
			if (request.isUserInRole("3")) {
				Estudiante estudiante = seguridadService.obtenerEstudiantePorIdentificacion(identificacion);
				facesContext.getExternalContext().getSessionMap().put("estudiante", estudiante);
				Matricula matricula = seguridadService.obtenerMatriculaPorEstudiante(estudiante, periodo);
				facesContext.getExternalContext().getSessionMap().put("matricula", matricula);
			}
			if (request.isUserInRole("4")) {
				Docente docente = seguridadService.obtenerDocentePorIdentificacion(identificacion);
				facesContext.getExternalContext().getSessionMap().put("docente", docente);
			}

			return "/resumen.jsf?faces-redirect=true";

		} catch (ServletException e) {

			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al iniciar sesión",
					"Usuario o contraseña incorrectos"));
		} catch (GestionEstudiantilException e) {

			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al iniciar sesión", e.getMessage()));
			try {
				request.logout();
			} catch (ServletException e1) {
			}
		}
		return "";
	}

	public String seleccionar20162017() throws IOException {
		facesContext.getExternalContext().getSessionMap().put("periodo", "2016-2017");
                return "/resumen.jsf?faces-redirect=true";
	}

	public String seleccionar20172018() throws IOException {
		facesContext.getExternalContext().getSessionMap().put("periodo", "2017-2018");
                return "/resumen.jsf?faces-redirect=true";
	}

	public String seleccionar20182019() throws IOException {
		facesContext.getExternalContext().getSessionMap().put("periodo", "2018-2019");
                return "/resumen.jsf?faces-redirect=true";
	}
        
        public String seleccionar20192020() throws IOException {
		facesContext.getExternalContext().getSessionMap().put("periodo", "2019-2020");
                return "/resumen.jsf?faces-redirect=true";
	}
        
        public String seleccionar20202021() throws IOException {
		facesContext.getExternalContext().getSessionMap().put("periodo", "2020-2021");
                return "/resumen.jsf?faces-redirect=true";
	}
        
        public String seleccionar20212022() throws IOException {
		facesContext.getExternalContext().getSessionMap().put("periodo", "2021-2022");
                return "/resumen.jsf?faces-redirect=true";
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
}