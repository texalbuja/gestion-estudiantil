package com.gestionestudiantil.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.model.Docente;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.service.MatriculacionService;

@ManagedBean
@ViewScoped
public class InscribirNuevoDocenteController implements Serializable {

	private static final long serialVersionUID = 1487671307502637741L;

	@Inject
	private MatriculacionService matriculacionService;

	@Inject
	private FacesContext facesContext;

	@ManagedProperty("#{institucion}")
	private Institucion institucion;

	private Docente docente;

	@PostConstruct
	public void init() {
		docente = new Docente();
		docente.setInstitucion(institucion);
	}

	public void guardar() {
		try {
			matriculacionService.guardarDocente(docente);
			facesContext.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
							"Docente " + docente.getApellidos() + " "
									+ docente.getNombres() + " registrado"));
			docente = new Docente();
			docente.setInstitucion(institucion);
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

}