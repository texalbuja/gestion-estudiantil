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
import com.gestionestudiantil.model.Estudiante;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.service.InstitucionService;
import com.gestionestudiantil.service.MatriculacionService;

@ManagedBean
@ViewScoped
public class EstudiantesController implements Serializable {

	private static final long serialVersionUID = 7190772325375376308L;

	@Inject
	private MatriculacionService matriculacionService;

	@Inject
	private InstitucionService institucionService;

	@ManagedProperty("#{institucion}")
	private Institucion institucion;

	@Inject
	private FacesContext facesContext;

	private List<Estudiante> estudiantes;

	private Estudiante estudiante;

	private String apellidos;

	private String identificacion;

	@PostConstruct
	private void init() {
		estudiantes = institucionService.obtenerEstudiantesPorInstitucion(institucion);
	}

	public String actualizarDatosEstudiante() {
		facesContext.getExternalContext().getSessionMap().put("estudiante", estudiante);
		return "/matriculacion/actualizarDatos.jsf?faces-redirect=true";
	}
	
	public String seleccionarEstudiante() {
		facesContext.getExternalContext().getSessionMap().put("estudiante", estudiante);
		return "/matriculacion/estudiante.jsf?faces-redirect=true";
	}

	public void editarEstudiante() {
		try {
			matriculacionService.actualizarEstudiante(estudiante);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Estudiante editado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void eliminarEstudiante() {
		try {
			matriculacionService.eliminarEstudiante(estudiante);
			estudiantes.remove(estudiante);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Estudiante eliminado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}

	}
	
	public void consultar() {
		try {
			estudiantes = matriculacionService.buscarEstudiantes(apellidos, identificacion, institucion);
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}

	}

	public List<Estudiante> getEstudiantes() {
		return this.estudiantes;
	}

	public void setEstudiantes(List<Estudiante> estudiantes) {
		this.estudiantes = estudiantes;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCedula() {
		return identificacion;
	}

	public void setCedula(String cedula) {
		this.identificacion = cedula;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
}