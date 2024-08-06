package com.gestionestudiantil.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.gestionestudiantil.model.Estudiante;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.service.EvaluacionService;

@ManagedBean
@ViewScoped
public class InformeAnualController implements Serializable {
	
	private static final long serialVersionUID = -4689332840817484597L;

	@Inject
	private EvaluacionService evaluacionService;

	@ManagedProperty("#{estudiante}")
	private Estudiante estudiante;
	
	@ManagedProperty("#{periodo}")
	private String periodo;

	private Matricula matricula;

	@PostConstruct
	public void init() {
		matricula = evaluacionService.obtenerMatriculaActiva(estudiante, periodo);
	}
	
	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

}