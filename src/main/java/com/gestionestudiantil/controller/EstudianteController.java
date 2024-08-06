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
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.service.MatriculacionService;

@ManagedBean
@ViewScoped
public class EstudianteController implements Serializable {

	private static final long serialVersionUID = 7190772325375376308L;

	@Inject
	private MatriculacionService matriculacionService;
	
	@ManagedProperty("#{estudiante}")
	private Estudiante estudiante;

	@Inject
	private FacesContext facesContext;

	private List<Matricula> matriculas;

	private Matricula matricula;

	@PostConstruct
	private void init() {
		matriculas = matriculacionService.obtenerMatriculasPorEstudiante(estudiante);
	}

	public void eliminarMatricula() {
		try {
			matriculacionService.eliminarMatricula(matricula);
			matriculas = matriculacionService.obtenerMatriculasPorEstudiante(estudiante);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Estudiante eliminado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}

	}

	public List<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}
}