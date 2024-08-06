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
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.PeriodoLectivo;
import com.gestionestudiantil.service.MatriculacionService;

@ManagedBean
@ViewScoped
public class GestionController implements Serializable {
	
	private static final long serialVersionUID = -2944936585606287822L;

	@Inject
	private FacesContext facesContext;
	
	@Inject
	private MatriculacionService matriculacionService;
	
	@ManagedProperty("#{periodoLectivo}")
	private PeriodoLectivo periodoLectivo;
	
	private List<Matricula> matriculas;

	private String criterio;

	private Matricula matricula;

	@PostConstruct
	public void init() {
		matriculas = matriculacionService
				.obtenerMatriculasPorPeriodoLectivo(periodoLectivo);
	}

	public void eliminar() {
		try {
			matriculacionService.eliminarMatricula(matricula);
			matriculas.remove(matricula);
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public String seleccionarMatricula() {
		facesContext.getExternalContext().getSessionMap()
				.put("matricula", matricula);
		return "/matriculacion/editarMatricula.jsf?faces-redirect=true";
	}

	public List<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

	public String getCriterio() {
		return criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public PeriodoLectivo getPeriodoLectivo() {
		return periodoLectivo;
	}

	public void setPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		this.periodoLectivo = periodoLectivo;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

}