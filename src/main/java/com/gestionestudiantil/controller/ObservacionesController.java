package com.gestionestudiantil.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Observacion;
import com.gestionestudiantil.service.ComportamientoService;
import com.gestionestudiantil.service.EvaluacionService;

@ManagedBean
@ViewScoped
public class ObservacionesController implements Serializable {

	private static final long serialVersionUID = -2360556937900146971L;


	@Inject
	private EvaluacionService evaluacionService;

	@Inject
	private ComportamientoService comportamientoService;

	@ManagedProperty("#{matricula}")
	private Matricula matricula;

	private List<Observacion> observaciones;

	@PostConstruct
	public void init() {

		observaciones = comportamientoService
				.obtenerObservacionesPorMatricula(matricula);
	}

	public List<Observacion> getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(List<Observacion> observaciones) {
		this.observaciones = observaciones;
	}

	public EvaluacionService getEvaluacionService() {
		return evaluacionService;
	}

	public void setEvaluacionService(EvaluacionService evaluacionService) {
		this.evaluacionService = evaluacionService;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}
}