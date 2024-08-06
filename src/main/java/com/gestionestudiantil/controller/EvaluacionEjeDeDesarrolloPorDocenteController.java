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
import com.gestionestudiantil.model.EvaluacionDestrezaDeDesarrollo;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Paralelo;
import com.gestionestudiantil.service.EvaluacionEjeDeDesarrolloService;

@ManagedBean
@ViewScoped
public class EvaluacionEjeDeDesarrolloPorDocenteController implements Serializable {

	private static final long serialVersionUID = 1782635962622860097L;

	@Inject
	private EvaluacionEjeDeDesarrolloService evaluacionEjeDeDesarrolloService;

	@ManagedProperty("#{paralelo}")
	private Paralelo paralelo;

	@ManagedProperty("#{docente}")
	private Docente docente;

	@ManagedProperty("#{matricula}")
	private Matricula matricula;

	@Inject
	private FacesContext facesContext;

	private List<EvaluacionDestrezaDeDesarrollo> evaluacionesDestrezaDeDesarrollo;

	@PostConstruct
	public void init() {
		evaluacionesDestrezaDeDesarrollo = evaluacionEjeDeDesarrolloService
				.obtenerEvaluacionesDestrezaDeDesarrolloPorDocenteYMatricula(docente, paralelo, matricula);
	}

	public void guardar() {
		try {
			evaluacionEjeDeDesarrolloService.guardarEvaluacionesDestrezaDeDesarrollo(evaluacionesDestrezaDeDesarrollo);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Éxito","Destrezas evaluadas exitosamente" ));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error al guardar las evaluaciones del estudiante", e.getMessage()));
		}
	}

	public List<EvaluacionDestrezaDeDesarrollo> getEvaluacionesDestrezaDeDesarrollo() {
		return evaluacionesDestrezaDeDesarrollo;
	}

	public void setEvaluacionesDestrezaDeDesarrollo(
			List<EvaluacionDestrezaDeDesarrollo> evaluacionesDestrezaDeDesarrollo) {
		this.evaluacionesDestrezaDeDesarrollo = evaluacionesDestrezaDeDesarrollo;
	}

	public Paralelo getParalelo() {
		return paralelo;
	}

	public void setParalelo(Paralelo paralelo) {
		this.paralelo = paralelo;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}
}