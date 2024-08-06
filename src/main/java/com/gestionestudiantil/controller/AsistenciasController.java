package com.gestionestudiantil.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.gestionestudiantil.model.Asistencia;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.service.ComportamientoService;
import com.gestionestudiantil.service.EvaluacionService;

@ManagedBean
@ViewScoped
public class AsistenciasController implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private EvaluacionService evaluacionService;

	@Inject
	private ComportamientoService comportamientoService;

	@ManagedProperty("#{matricula}")
	private Matricula matricula;

	private List<Asistencia> asistencias;

	@PostConstruct
	public void init() {
		asistencias = comportamientoService
				.obtenerAsistenciasPorMatricula(matricula);
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

	public List<Asistencia> getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(List<Asistencia> asistencias) {
		this.asistencias = asistencias;
	}

}
