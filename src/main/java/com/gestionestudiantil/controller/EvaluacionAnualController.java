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
import com.gestionestudiantil.exception.GestionEstudiantilPersistenceException;
import com.gestionestudiantil.model.Asignatura;
import com.gestionestudiantil.model.EvaluacionAsignatura;
import com.gestionestudiantil.model.GrupoDeEvaluacion;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.PeriodoLectivo;
import com.gestionestudiantil.service.EvaluacionService;

@ManagedBean
@ViewScoped
public class EvaluacionAnualController implements Serializable {
	
	private static final long serialVersionUID = 2868495974797827340L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private EvaluacionService evaluacionService;

	@ManagedProperty("#{asignatura}")
	private Asignatura asignatura;

	@ManagedProperty("#{periodoLectivo}")
	private PeriodoLectivo periodoLectivo;

	private Matricula matricula;

	private List<EvaluacionAsignatura> evaluaciones;

	private EvaluacionAsignatura evaluacionAsignatura;

	private Boolean esPrincipal;

	private GrupoDeEvaluacion grupoDeEvaluacion;

	private String criterio;

	private List<GrupoDeEvaluacion> gruposDeEvaluacion;

	@PostConstruct
	public void init() {
		evaluaciones = evaluacionService
				.obtenerEvaluacionesPorAsignatura(asignatura);
		esPrincipal = asignatura.getAsignaturas().size() > 0;
		gruposDeEvaluacion = evaluacionService
				.obtenerGruposDeEvaluacionActivos(periodoLectivo);
	}

	public String seleccionarMatricula() {
		facesContext.getExternalContext().getSessionMap()
				.put("matricula", matricula);
		return "/matriculacion/matricula.jsf?faces-redirect=true";
	}

	public String seleccionarGrupoDeEvaluacion() {
		facesContext.getExternalContext().getSessionMap()
				.put("matricula", matricula);
		return "/evaluacion/" + grupoDeEvaluacion.getUrl()
				+ "?faces-redirect=true";
	}

	public String exportarReporte() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/asignaturas.jasper");
		parametros.put("asignatura_dictada_id", asignatura.getId());
		facesContext.getExternalContext().getSessionMap()
				.put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public void recomendacionesPlan() {
		try {
			evaluacionService.evaluarEvaluacionAsignatura(evaluacionAsignatura);
			evaluacionService.evaluarPromediosYResultado(evaluacionAsignatura.getInformeAnual());
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al evaluar", e.getMessage()));
		} catch (GestionEstudiantilPersistenceException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al promediar", e.getMessage()));
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

	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	public List<EvaluacionAsignatura> getEvaluaciones() {
		return evaluaciones;
	}

	public void setEvaluaciones(
			List<EvaluacionAsignatura> detallesInformesAnuales) {
		this.evaluaciones = detallesInformesAnuales;
	}

	public EvaluacionAsignatura getEvaluacionAsignatura() {
		return evaluacionAsignatura;
	}

	public void setEvaluacionAsignatura(
			EvaluacionAsignatura evaluacionAsignatura) {
		this.evaluacionAsignatura = evaluacionAsignatura;
	}

	public Boolean getEsPrincipal() {
		return esPrincipal;
	}

	public void setEsPrincipal(Boolean esPrincipal) {
		this.esPrincipal = esPrincipal;
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
}