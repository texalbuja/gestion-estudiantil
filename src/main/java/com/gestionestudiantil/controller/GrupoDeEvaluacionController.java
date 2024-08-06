package com.gestionestudiantil.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.gestionestudiantil.model.GrupoDeEvaluacion;
import com.gestionestudiantil.model.PeriodoLectivo;
import com.gestionestudiantil.service.EvaluacionService;

@ManagedBean
@ViewScoped
public class GrupoDeEvaluacionController implements Serializable {

	private static final long serialVersionUID = -8782161781698807587L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private EvaluacionService evaluacionService;

	private GrupoDeEvaluacion grupoDeEvaluacion;

	@ManagedProperty("#{periodoLectivo}")
	private PeriodoLectivo periodoLectivo;

	private List<GrupoDeEvaluacion> gruposDeEvaluacion;

	@PostConstruct
	public void init() {
		gruposDeEvaluacion = evaluacionService
				.obtenerGruposDeEvaluacionActivos(periodoLectivo);
	}

	public String seleccionarGrupoDeEvaluacion() {
		facesContext.getExternalContext().getSessionMap()
				.put("grupoDeEvaluacion", grupoDeEvaluacion);
		return "/evaluacion/" + grupoDeEvaluacion.getUrl()
				+ "?faces-redirect=true";
	}

	public GrupoDeEvaluacion getGrupoDeEvaluacion() {
		return grupoDeEvaluacion;
	}

	public void setGrupoDeEvaluacion(GrupoDeEvaluacion grupoDeEvaluacion) {
		this.grupoDeEvaluacion = grupoDeEvaluacion;
	}

	public PeriodoLectivo getPeriodoLectivo() {
		return periodoLectivo;
	}

	public void setPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		this.periodoLectivo = periodoLectivo;
	}

	public List<GrupoDeEvaluacion> getGruposDeEvaluacion() {
		return gruposDeEvaluacion;
	}

	public void setGruposDeEvaluacion(List<GrupoDeEvaluacion> gruposDeEvaluacion) {
		this.gruposDeEvaluacion = gruposDeEvaluacion;
	}

}