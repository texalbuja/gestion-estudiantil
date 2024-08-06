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
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.service.InformesService;

@ManagedBean
@ViewScoped
public class MatriculaController implements Serializable {
	
	private static final long serialVersionUID = 9043735885534256003L;
	
	@Inject
	private InformesService informesService;

	@Inject
	private FacesContext facesContext;
	
	@ManagedProperty("#{matricula}")
	private Matricula matricula;
	
	private String criterio;

	private GrupoDeEvaluacion grupoDeEvaluacion;

	private List<GrupoDeEvaluacion> gruposDeEvaluacion;

	@PostConstruct
	public void init() {
		gruposDeEvaluacion = informesService
				.obtenerGruposDeEvaluacionActivosEstudiante(matricula.getParalelo()
						.getGrado().getPeriodoLectivo());
	}

	public String seleccionarGrupoDeEvaluacion() {
		facesContext.getExternalContext().getSessionMap()
				.put("grupoDeEvaluacion", grupoDeEvaluacion);
		facesContext.getExternalContext().getSessionMap().put("periodoLectivo",
				matricula.getParalelo().getGrado().getPeriodoLectivo());
		return "/informes/" + grupoDeEvaluacion.getUrl()
				+ "?faces-redirect=true";
	}

	public String getCriterio() {
		return criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public GrupoDeEvaluacion getGrupoDeEvaluacion() {
		return grupoDeEvaluacion;
	}

	public void setGrupoDeEvaluacion(GrupoDeEvaluacion grupoDeEvaluacion) {
		this.grupoDeEvaluacion = grupoDeEvaluacion;
	}

	public List<GrupoDeEvaluacion> getGruposDeEvaluacion() {
		return gruposDeEvaluacion;
	}

	public void setGruposDeEvaluacion(List<GrupoDeEvaluacion> gruposDeEvaluacion) {
		this.gruposDeEvaluacion = gruposDeEvaluacion;
	}

}