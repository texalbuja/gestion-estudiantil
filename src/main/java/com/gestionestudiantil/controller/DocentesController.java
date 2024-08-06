package com.gestionestudiantil.controller;

import java.io.Serializable;
import java.util.List;

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
import com.gestionestudiantil.service.InstitucionService;
import com.gestionestudiantil.service.MatriculacionService;

@ManagedBean
@ViewScoped
public class DocentesController implements Serializable {

	private static final long serialVersionUID = 6474088025527199301L;

	@Inject
	private MatriculacionService matriculacionService;

	@Inject
	private InstitucionService institucionService;

	@Inject
	private FacesContext facesContext;

	@ManagedProperty("#{institucion}")
	private Institucion institucion;

	private List<Docente> docentes;

	private Docente docente;

	private String criterio;

	private String apellidos;

	private String identificacion;

	@PostConstruct
	public void init() {
		docentes = matriculacionService.obtenerDocentesPorInstitucion(institucion);
	}

	public void preparaNuevoDocente() {
		docente = new Docente();
		docente.setInstitucion(institucion);
	}

	public void consultar() {
		try {
			docentes = matriculacionService.buscarDocentes(apellidos, identificacion, institucion);
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}

	}

	public void guardar() {
		try {
			matriculacionService.guardarDocente(docente);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Docente " + docente.getApellidos() + " " + docente.getNombres() + " registrado"));
			docente = new Docente();
			docente.setInstitucion(institucion);
			docentes = matriculacionService.buscarDocentes(apellidos, identificacion, institucion);
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void eliminarDocente() {
		try {
			institucionService.eliminarDocente(docente);
			docentes.remove(docente);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Docente eliminado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void editarDocente() {
		try {
			institucionService.actualizarDocente(docente);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Docente editado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public String seleccionarDocente() {
		facesContext.getExternalContext().getSessionMap().put("docente", docente);
		return "/matriculacion/docente.jsf?faces-redirect=true";
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public List<Docente> getDocentes() {
		return docentes;
	}

	public void setDocentes(List<Docente> docentes) {
		this.docentes = docentes;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public String getCriterio() {
		return criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
}