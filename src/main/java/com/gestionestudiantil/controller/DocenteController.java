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
import com.gestionestudiantil.model.Asignatura;
import com.gestionestudiantil.model.Docente;
import com.gestionestudiantil.model.Grado;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Paralelo;
import com.gestionestudiantil.model.ProyectoEscolar;
import com.gestionestudiantil.service.MatriculacionService;

@ManagedBean
@ViewScoped
public class DocenteController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private MatriculacionService matriculacionService;

	@Inject
	private FacesContext facesContext;

	@ManagedProperty("#{docente}")
	private Docente docente;

	@ManagedProperty("#{institucion}")
	private Institucion institucion;

	private List<Asignatura> asignaturas;

	private List<Paralelo> paralelos;

	private List<ProyectoEscolar> proyectosEscolares;

	private Paralelo paralelo;

	private Asignatura asignatura;

	private List<Docente> docentes;

	private ProyectoEscolar proyectoEscolar;

	private List<Paralelo> paralelosObjetivosDeDesarrollo;

	@ManagedProperty("#{periodo}")
	private String periodo;

	@PostConstruct
	public void init() {
		asignaturas = matriculacionService.obtenerAsignaturasPorDocenteYPeriodoLectivo(docente, periodo);
		docentes = matriculacionService.obtenerDocentesPorInstitucion(institucion);
		paralelos = matriculacionService.obtenerParalelosPorTutorYPeriodoLectivo(docente, periodo);
		paralelosObjetivosDeDesarrollo = matriculacionService
				.obtenerParalelosConDestrezasDeDesarrolloPorDocente(docente, periodo);
		proyectosEscolares = matriculacionService.obtenerProyectosEscolaresPorDocente(docente, periodo);
	}

	public String exportarReporteEvaluacionesPrimerQuimestrePrimerBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/docente_q1_b1.jasper");
		parametros.put("docente_id", docente.getId());
		parametros.put("etiqueta_periodo_lectivo", periodo);
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluacionesPrimerQuimestreSegundoBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/docente_q1_b2.jasper");
		parametros.put("docente_id", docente.getId());
		parametros.put("etiqueta_periodo_lectivo", periodo);
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluacionesPrimerQuimestreTercerBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/docente_q1_b3.jasper");
		parametros.put("docente_id", docente.getId());
		parametros.put("etiqueta_periodo_lectivo", periodo);
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluacionesPrimerQuimestre() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/docente_q1.jasper");
		parametros.put("docente_id", docente.getId());
		parametros.put("etiqueta_periodo_lectivo", periodo);
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluacionesSegundoQuimestrePrimerBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/docente_q2_b1.jasper");
		parametros.put("docente_id", docente.getId());
		parametros.put("etiqueta_periodo_lectivo", periodo);
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluacionesSegundoQuimestreSegundoBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/docente_q2_b2.jasper");
		parametros.put("docente_id", docente.getId());
		parametros.put("etiqueta_periodo_lectivo", periodo);
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluacionesSegundoQuimestreTercerBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/docente_q2_b3.jasper");
		parametros.put("docente_id", docente.getId());
		parametros.put("etiqueta_periodo_lectivo", periodo);
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluacionesSegundoQuimestre() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/docente_q2.jasper");
		parametros.put("docente_id", docente.getId());
		parametros.put("etiqueta_periodo_lectivo", periodo);
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEstudiantes() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/docente_estudiantes.jasper");
		parametros.put("docente_id", docente.getId());
		parametros.put("etiqueta_periodo_lectivo", periodo);
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/excel/excel.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluaciones1() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/docente_evaluaciones_1.jasper");
		parametros.put("docente_id", docente.getId());
		parametros.put("etiqueta_periodo_lectivo", periodo);
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/excel/excel.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluaciones2() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/docente_evaluaciones_2.jasper");
		parametros.put("docente_id", docente.getId());
		parametros.put("etiqueta_periodo_lectivo", periodo);
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/excel/excel.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluacionesAnual() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/docente_anual.jasper");
		parametros.put("docente_id", docente.getId());
		parametros.put("etiqueta_periodo_lectivo", periodo);
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String seleccionarAsignatura() {
		facesContext.getExternalContext().getSessionMap().put("asignatura", asignatura);
		facesContext.getExternalContext().getSessionMap().put("periodoLectivo",
				asignatura.getParalelo().getGrado().getPeriodoLectivo());
		facesContext.getExternalContext().getSessionMap().put("paralelo", asignatura.getParalelo());
		facesContext.getExternalContext().getSessionMap().put("grado", asignatura.getParalelo().getGrado());
		return "/matriculacion/grupoDeEvaluacion.jsf?faces-redirect=true";
	}

	public String seleccionarProyectoEscolar() {
		facesContext.getExternalContext().getSessionMap().put("proyectoEscolar", proyectoEscolar);
		facesContext.getExternalContext().getSessionMap().put("paralelo", proyectoEscolar.getParalelo());
		facesContext.getExternalContext().getSessionMap().put("grado", proyectoEscolar.getParalelo().getGrado());
		facesContext.getExternalContext().getSessionMap().put("periodoLectivo",
				proyectoEscolar.getParalelo().getGrado().getPeriodoLectivo());
		return "/matriculacion/proyectoEscolar.jsf?faces-redirect=true";
	}

	public String seleccionarParalelo() {
		facesContext.getExternalContext().getSessionMap().put("paralelo", paralelo);
		facesContext.getExternalContext().getSessionMap().put("grado", paralelo.getGrado());

		return "/matriculacion/" + obtenerRuta(paralelo.getGrado()) + "/paralelo.jsf?faces-redirect=true";
	}

	private String obtenerRuta(Grado grado) {
		String ruta = "preparatoria";

		if (grado.getGrado() >= 4) {
			ruta = "basica/elemental";
		}

		if (grado.getGrado() >= 7) {
			ruta = "basica/media";
		}
		if (grado.getGrado() >= 10) {
			ruta = "basica/superior";
		}

		if (grado.getGrado() >= 13) {
			ruta = "bachillerato";
		}

		return ruta;

	}

	public String evaluarParalelo() {
		facesContext.getExternalContext().getSessionMap().put("paralelo", paralelo);
		facesContext.getExternalContext().getSessionMap().put("grado", paralelo.getGrado());
		facesContext.getExternalContext().getSessionMap().put("periodoLectivo",
				paralelo.getGrado().getPeriodoLectivo());
		return "/comportamiento/grupoDeEvaluacion.jsf?faces-redirect=true";
	}

	public String evaluarDestrezasDeDesarrollo() {
		facesContext.getExternalContext().getSessionMap().put("paralelo", paralelo);
		facesContext.getExternalContext().getSessionMap().put("docente", docente);
		facesContext.getExternalContext().getSessionMap().put("periodoLectivo",
				paralelo.getGrado().getPeriodoLectivo());
		return "/evaluacion/ejesDeDesarrollo/grupoDeEvaluacion.jsf?faces-redirect=true";
	}

	public void editarAsignatura() {
		try {
			matriculacionService.editarAsignatura(asignatura);
			asignaturas = matriculacionService.obtenerAsignaturasPorDocente(docente);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Asignatura " + asignatura.getEtiqueta() + " reasignada exitósamente "));

		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void eliminarAsignatura() {
		try {
			matriculacionService.eliminarAsignatura(asignatura);
			asignaturas.remove(asignatura);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Asignatura " + asignatura.getEtiqueta() + " reasignada exitósamente "));

		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public List<Asignatura> getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(List<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}

	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	public List<Paralelo> getParalelos() {
		return paralelos;
	}

	public void setParalelos(List<Paralelo> paralelos) {
		this.paralelos = paralelos;
	}

	public Paralelo getParalelo() {
		return paralelo;
	}

	public void setParalelo(Paralelo paralelo) {
		this.paralelo = paralelo;
	}

	public List<Docente> getDocentes() {
		return docentes;
	}

	public void setDocentes(List<Docente> docentes) {
		this.docentes = docentes;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public List<ProyectoEscolar> getProyectosEscolares() {
		return proyectosEscolares;
	}

	public void setProyectosEscolares(List<ProyectoEscolar> proyectosEscolares) {
		this.proyectosEscolares = proyectosEscolares;
	}

	public ProyectoEscolar getProyectoEscolar() {
		return proyectoEscolar;
	}

	public void setProyectoEscolar(ProyectoEscolar proyectoEscolar) {
		this.proyectoEscolar = proyectoEscolar;
	}

	public List<Paralelo> getParalelosObjetivosDeDesarrollo() {
		return paralelosObjetivosDeDesarrollo;
	}

	public void setParalelosObjetivosDeDesarrollo(List<Paralelo> paralelosObjetivosDeDesarrollo) {
		this.paralelosObjetivosDeDesarrollo = paralelosObjetivosDeDesarrollo;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

}