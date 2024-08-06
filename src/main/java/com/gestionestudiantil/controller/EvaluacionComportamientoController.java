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
import com.gestionestudiantil.model.EvaluacionComportamiento;
import com.gestionestudiantil.model.GrupoDeEvaluacion;
import com.gestionestudiantil.model.Observacion;
import com.gestionestudiantil.model.Paralelo;
import com.gestionestudiantil.service.ComportamientoService;
import com.gestionestudiantil.service.EvaluacionService;

@ManagedBean
@ViewScoped
public class EvaluacionComportamientoController implements Serializable {

	private static final long serialVersionUID = 7927391019952738097L;

	@Inject
	private EvaluacionService evaluacionService;

	@Inject
	private ComportamientoService comportamientoService;

	@Inject
	private FacesContext facesContext;

	@ManagedProperty("#{paralelo}")
	private Paralelo paralelo;

	@ManagedProperty("#{grupoDeEvaluacion}")
	private GrupoDeEvaluacion grupoDeEvaluacion;

	private List<EvaluacionComportamiento> evaluacionesComportamiento;

	private EvaluacionComportamiento evaluacionComportamiento;

	private List<Observacion> observaciones;

	@PostConstruct
	public void init() {
		evaluacionesComportamiento = evaluacionService.obtenerEvaluacionesComportamientoPorParalelo(paralelo);
		observaciones = comportamientoService.obtenerObservacionesPorParalelo(paralelo, grupoDeEvaluacion);
		evaluacionComportamiento = new EvaluacionComportamiento();
	}

	public void guardar() {
		try {
			for (EvaluacionComportamiento evaluacionComportamiento : evaluacionesComportamiento) {
				evaluacionService.actualizarEvaluacionComportamiento(evaluacionComportamiento);
			}
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Evaluación de comportamiento actualizada"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void copiar() {
		for (EvaluacionComportamiento evaluacion : evaluacionesComportamiento) {
			if (evaluacionComportamiento.getB1() != null)
				evaluacion.setB1(evaluacionComportamiento.getB1());
			if (evaluacionComportamiento.getB2() != null)
				evaluacion.setB2(evaluacionComportamiento.getB2());
			if (evaluacionComportamiento.getB3() != null)
				evaluacion.setB3(evaluacionComportamiento.getB3());
			if (evaluacionComportamiento.getB4() != null)
				evaluacion.setB4(evaluacionComportamiento.getB4());
			if (evaluacionComportamiento.getB5() != null)
				evaluacion.setB5(evaluacionComportamiento.getB5());
			if (evaluacionComportamiento.getB6() != null)
				evaluacion.setB6(evaluacionComportamiento.getB6());
		}
	}

	public Paralelo getParalelo() {
		return paralelo;
	}

	public void setParalelo(Paralelo paralelo) {
		this.paralelo = paralelo;
	}

	public List<EvaluacionComportamiento> getEvaluacionesComportamiento() {
		return evaluacionesComportamiento;
	}

	public void setEvaluacionesComportamiento(List<EvaluacionComportamiento> evaluacionesComportamiento) {
		this.evaluacionesComportamiento = evaluacionesComportamiento;
	}

	public List<Observacion> getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(List<Observacion> observaciones) {
		this.observaciones = observaciones;
	}

	public GrupoDeEvaluacion getGrupoDeEvaluacion() {
		return grupoDeEvaluacion;
	}

	public void setGrupoDeEvaluacion(GrupoDeEvaluacion grupoDeEvaluacion) {
		this.grupoDeEvaluacion = grupoDeEvaluacion;
	}

	public EvaluacionComportamiento getEvaluacionComportamiento() {
		return evaluacionComportamiento;
	}

	public void setEvaluacionComportamiento(EvaluacionComportamiento evaluacionComportamiento) {
		this.evaluacionComportamiento = evaluacionComportamiento;
	}
}