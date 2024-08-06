package com.gestionestudiantil.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.model.EvaluacionProyectoEscolar;
import com.gestionestudiantil.model.GrupoDeEvaluacion;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.PeriodoLectivo;
import com.gestionestudiantil.model.ProyectoEscolar;
import com.gestionestudiantil.service.EvaluacionService;

@ManagedBean
@ViewScoped
public class ProyectoEscolarController implements Serializable {

	private static final long serialVersionUID = -8782161781698807587L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private EvaluacionService evaluacionService;

	@ManagedProperty("#{proyectoEscolar}")
	private ProyectoEscolar proyectoEscolar;

	@ManagedProperty("#{periodoLectivo}")
	private PeriodoLectivo periodoLectivo;

	private Matricula matricula;

	private List<EvaluacionProyectoEscolar> evaluaciones;

	private EvaluacionProyectoEscolar evaluacionProyectoEscolar;

	private GrupoDeEvaluacion grupoDeEvaluacion;

	private String criterio;

	private List<GrupoDeEvaluacion> gruposDeEvaluacion;

	@PostConstruct
	public void init() {
		evaluaciones = evaluacionService
				.obtenerEvaluacionesPorProyectoEscolar(proyectoEscolar);
		gruposDeEvaluacion = evaluacionService
				.obtenerGruposDeEvaluacionDeProyectosEscolaresActivos(periodoLectivo);
		evaluacionProyectoEscolar = new EvaluacionProyectoEscolar();
	}
	
	public void copiar() {
		for (EvaluacionProyectoEscolar evaluacion : evaluaciones) {
			if (evaluacionProyectoEscolar.getB1() != null)
				evaluacion.setB1(evaluacionProyectoEscolar.getB1());
			if (evaluacionProyectoEscolar.getB2() != null)
				evaluacion.setB2(evaluacionProyectoEscolar.getB2());
			if (evaluacionProyectoEscolar.getB3() != null)
				evaluacion.setB3(evaluacionProyectoEscolar.getB3());
			if (evaluacionProyectoEscolar.getB4() != null)
				evaluacion.setB4(evaluacionProyectoEscolar.getB4());
			if (evaluacionProyectoEscolar.getB5() != null)
				evaluacion.setB5(evaluacionProyectoEscolar.getB5());
			if (evaluacionProyectoEscolar.getB6() != null)
				evaluacion.setB6(evaluacionProyectoEscolar.getB6());
		}
	}

	public String seleccionarMatricula() {
		facesContext.getExternalContext().getSessionMap()
				.put("matricula", matricula);
		return "/matriculacion/matricula.jsf?faces-redirect=true";
	}

	public String seleccionarGrupoDeEvaluacion() {
		facesContext.getExternalContext().getSessionMap()
				.put("matricula", matricula);
		return "/evaluacion/proyectoEscolar/" + grupoDeEvaluacion.getUrl()
				+ "?faces-redirect=true";
	}

	public String exportarReporte() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/proyecto_escolar.jasper");
		parametros.put("proyecto_escolar_id", proyectoEscolar.getId());
		facesContext.getExternalContext().getSessionMap()
				.put("parametros", parametros);
		return "/reportes/excel/excel.jsf?faces-redirect=true";
	}

	public void evaluarEvaluacionProyectoEscolar() {
		try {
			for (EvaluacionProyectoEscolar evaluacionProyectoEscolar : evaluaciones) {
				evaluacionService
						.evaluarEvaluacionProyectoEscolar(evaluacionProyectoEscolar);
			}
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Éxito",
					"Evaluaciones de Proyectos guardada"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}

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

	public PeriodoLectivo getPeriodoLectivo() {
		return periodoLectivo;
	}

	public void setPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		this.periodoLectivo = periodoLectivo;
	}

	public ProyectoEscolar getProyectoEscolar() {
		return proyectoEscolar;
	}

	public void setProyectoEscolar(ProyectoEscolar proyectoEscolar) {
		this.proyectoEscolar = proyectoEscolar;
	}

	public List<EvaluacionProyectoEscolar> getEvaluaciones() {
		return evaluaciones;
	}

	public void setEvaluaciones(List<EvaluacionProyectoEscolar> evaluaciones) {
		this.evaluaciones = evaluaciones;
	}

	public EvaluacionProyectoEscolar getEvaluacionProyectoEscolar() {
		return evaluacionProyectoEscolar;
	}

	public void setEvaluacionProyectoEscolar(
			EvaluacionProyectoEscolar evaluacionProyectoEscolar) {
		this.evaluacionProyectoEscolar = evaluacionProyectoEscolar;
	}
}