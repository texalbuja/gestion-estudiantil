
package com.gestionestudiantil.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.model.Area;
import com.gestionestudiantil.model.Asignatura;
import com.gestionestudiantil.model.Docente;
import com.gestionestudiantil.model.Estudiante;
import com.gestionestudiantil.model.Grado;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Paralelo;
import com.gestionestudiantil.model.ProyectoEscolar;
import com.gestionestudiantil.model.Usuario;
import com.gestionestudiantil.service.EvaluacionService;
import com.gestionestudiantil.service.MatriculacionService;

@ManagedBean
@ViewScoped
public class ParaleloController implements Serializable {

	private static final long serialVersionUID = 1782635962622860097L;

	@Inject
	private FacesContext facesContext;
	@Inject
	private MatriculacionService matriculacionService;
	@Inject
	private EvaluacionService evaluacionService;

	@ManagedProperty("#{usuario}")
	private Usuario usuario;

	@ManagedProperty("#{paralelo}")
	private Paralelo paralelo;

	@ManagedProperty("#{grado}")
	private Grado grado;

	@ManagedProperty("#{docente}")
	private Docente docente;

	@ManagedProperty("#{institucion}")
	private Institucion institucion;

	private List<ProyectoEscolar> proyectosEscolares;

	private ProyectoEscolar proyectoEscolar;

	private List<Matricula> matriculas;

	private Matricula matricula;

	private List<Asignatura> asignaturas;

	private Asignatura asignatura;

	private List<Docente> docentes;

	private List<String> asignaturasAsignadas;

	private List<String> asignaturasAAsignar;

	private Paralelo paraleloAAsignar;

	private String criterio;

	private List<Asignatura> asignaturasUsuario;

	private Estudiante estudiante;

	private List<ProyectoEscolar> proyectosUsuario;

	private String tipoMatricula;

	private List<Estudiante> estudiantesAprobados;
	
	private List<Area> areas;

	@PostConstruct
	public void init() {
		try {
			matriculas = matriculacionService.obtenerMatriculasPorParalelo(paralelo);
			docentes = matriculacionService
					.obtenerDocentesPorInstitucion(paralelo.getGrado().getPeriodoLectivo().getInstitucion());
			areas = matriculacionService.obtenerAreasPorPeriodoLectivo(paralelo.getGrado().getPeriodoLectivo());
			asignaturas = matriculacionService.obtenerAsignaturasPorParalelo(paralelo);
			proyectosEscolares = matriculacionService.obtenerProyectosEscolaresPorParalelo(paralelo);
			asignaturasUsuario = matriculacionService.obtenerAsignaturasPorUsuario(usuario);
			proyectosUsuario = matriculacionService.obtenerProyectosEscolaresPorUsuario(usuario);
			asignaturasAsignadas = new ArrayList<String>();
			asignaturasAAsignar = new ArrayList<String>();
			grado.getParalelos().remove(paralelo);
			tipoMatricula = "Antiguo";

		} catch (GestionEstudiantilException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void promediar() {
		try {
			evaluacionService.promediar(paralelo);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Evaluaciones promediadas", ""));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al evaluar los promedios", e.getMessage()));
		}
	}

	public void obtenerCodigoTemporal() {
		try {
			estudiante.setIdentificacion(matriculacionService.obtenerCodigoTemporal());
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error al generar el código temporal", e.getMessage()));
		}
	}

	public void guardarEstudiante() {
		try {
			matriculacionService.matricularEstudiante(estudiante, paralelo, matricula);
			matriculas = matriculacionService.obtenerMatriculasPorParalelo(paralelo);
			estudiantesAprobados = matriculacionService.obtenerEstudiantesAprobados(paralelo.getGrado());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
							"Estudiante " + estudiante.getNombres() + " " + estudiante.getApellidos()
									+ " matriculado en " + grado.getGrados().getDescripcionGrado() + " - Paralelo "
									+ paralelo.getEtiqueta()));
			prepararNuevoEstudiante();
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al inscribir el estudiante", e.getMessage()));
		}

	}

	public void eliminarMatricula() {
		try {
			matriculacionService.eliminarMatricula(matricula);
			matriculas = matriculacionService.obtenerMatriculasPorParalelo(paralelo);
			estudiantesAprobados = matriculacionService.obtenerEstudiantesAprobados(paralelo.getGrado());
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Matrícula eliminada "));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al inscribir el estudiante", e.getMessage()));
		}

	}

	public void guardarProyectoEscolar() {
		try {
			proyectoEscolar.setDocente(docente);
			proyectoEscolar = matriculacionService.guardarProyectoEscolar(proyectoEscolar);
			proyectosEscolares = matriculacionService.obtenerProyectosEscolaresPorParalelo(paralelo);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Proyecto Escolar " + proyectoEscolar.getEtiqueta() + " Guardado con éxito"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al inscribir el estudiante", e.getMessage()));
		}

	}

	public void prepararNuevoEstudiante() {
		estudiantesAprobados = matriculacionService.obtenerEstudiantesAprobados(paralelo.getGrado());
		estudiante = new Estudiante();
		estudiante.setInstitucion(this.institucion);
		estudiante.setSexo("M");
		estudiante.setSeccion("M");
		if (estudiantesAprobados != null && estudiantesAprobados.size() > 0) {
			estudiante = estudiantesAprobados.get(0);
		}
		matricula = new Matricula();
		matricula.setNumeroMatriculaGrado("Primera");
		tipoMatricula = "Antiguo";
	}

	public void cambiarTipoMatricula() {
		if (tipoMatricula.equals("Nuevo")) {
			estudiante = new Estudiante();
			estudiante.setInstitucion(this.institucion);
			estudiante.setSexo("M");
			estudiante.setSeccion("M");
		}
		if (tipoMatricula.equals("Antiguo")) {
			if (estudiantesAprobados != null && estudiantesAprobados.size() > 0) {
				estudiante = estudiantesAprobados.get(0);
			}

		}
		matricula = new Matricula();
		matricula.setNumeroMatriculaGrado("Primera");
	}

	public void cambiarDeParalelo() {

		try {
			matriculacionService.cambiarMatriculaDeParalelo(matricula, asignaturasAAsignar, asignaturasAsignadas,
					paraleloAAsignar);
			matriculas = matriculacionService.obtenerMatriculasPorParalelo(paralelo);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Estudiante actualizado"));

		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public String obtenerEtiquetaPorAsignatura(String asignatura) {
		return matriculacionService.obtenerAsignaturaPorId(Long.valueOf(asignatura)).getEtiqueta();
	}

	public boolean asignaturaPerteneceDocente(Asignatura asignatura) {
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		if (request.isUserInRole("1") || request.isUserInRole("2") || request.isUserInRole("7")) {
			return true;
		}
		if (request.isUserInRole("4")) {
			return asignaturasUsuario.contains(asignatura);
		}
		return false;
	}

	public boolean proyectoEscolarPerteneceDocente(ProyectoEscolar proyectoEscolar) {
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		if (request.isUserInRole("1") || request.isUserInRole("2") || request.isUserInRole("7")) {
			return true;
		}
		if (request.isUserInRole("4")) {
			return proyectosUsuario.contains(proyectoEscolar);
		}
		return false;
	}

	public void preparaNuevaAsignatura() {
		asignatura = new Asignatura();
		asignaturas = matriculacionService.obtenerAsignaturasPorParalelo(paralelo);
		asignatura.setLogicaEvaluacion("SP");
		asignatura.setCertificadoPromocion("S");
	}

	public void preparaNuevoProyectoEscolar() {
		proyectoEscolar = new ProyectoEscolar();
		proyectoEscolar.setParalelo(paralelo);
	}

	public List<Matricula> obtenerMatriculasParalelo(Paralelo paralelo) {
		return matriculacionService.obtenerMatriculasPorParalelo(paralelo);
	}

	public String seleccionarMatricula() {
		facesContext.getExternalContext().getSessionMap().put("matricula", matricula);
		return "/matriculacion/matricula.jsf?faces-redirect=true";
	}

	public String carnetizacion() {
		facesContext.getExternalContext().getSessionMap().put("paralelo", paralelo);
		return "/carnetizacion/carnetizacion.jsf?faces-redirect=true";
	}

	public String seleccionarProyectoEscolar() {
		facesContext.getExternalContext().getSessionMap().put("proyectoEscolar", proyectoEscolar);
		return "/matriculacion/proyectoEscolar.jsf?faces-redirect=true";
	}

	public String seleccionarAsignaturaDictada() {
		facesContext.getExternalContext().getSessionMap().put("asignatura", asignatura);
		facesContext.getExternalContext().getSessionMap().put("docente", asignatura.getDocente());
		return "/matriculacion/grupoDeEvaluacion.jsf?faces-redirect=true";
	}

	public void guardar() {
		try {
			matriculacionService.guardarAsignatura(asignatura, docente, paralelo);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Asignatura " + asignatura.getEtiqueta() + " registrada correctamente"));
			asignaturas.add(asignatura);
			asignatura = new Asignatura();

		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void eliminar() {
		try {
			matriculacionService.eliminarAsignatura(asignatura);
			asignaturas = matriculacionService.obtenerAsignaturasPorParalelo(paralelo);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Asignatura " + asignatura.getEtiqueta() + " eliminada correctamente"));

		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void editar() {
		try {
			asignatura.setDocente(docente);
			matriculacionService.editarAsignatura(asignatura);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Asignatura " + asignatura.getEtiqueta() + " editada correctamente"));

		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void editarProyectoEscolar() {
		try {
			proyectoEscolar.setDocente(docente);
			matriculacionService.editarProyectoEscolar(proyectoEscolar);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Proyecto Escolar " + proyectoEscolar.getEtiqueta() + " editado correctamente"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void eliminarProyectoEscolar() {
		try {
			matriculacionService.eliminarProyectoEscolar(proyectoEscolar);
			proyectosEscolares = matriculacionService.obtenerProyectosEscolaresPorParalelo(paralelo);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Proyecto Escolar " + proyectoEscolar.getEtiqueta() + " eliminado correctamente"));

		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void preparaEditarAsignatura(Asignatura asignaturaExcluida) {

	}

	public String exportarReporteCertificadosMatriculas() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/certificado_matricula.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}
	public String exportarReporteFichaDeMatriculas() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/ficha_matricula.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteCertificadosPromocion() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/certificado_promocion.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteNominaEstudiantes() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/nomina_estudiantes_paralelo.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/excel/excel.jsf?faces-redirect=true";
	}

	public String exportarReporteNominaDocentes() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/nomina_docentes_paralelo.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/excel/excel.jsf?faces-redirect=true";
	}

	public void preparaEditarProyectoEscolar(ProyectoEscolar proyectoEscolar) {

	}

	public String exportarReporteEstadisticaPrimerQuimestrePrimerBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/estadistica_q1_b1.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEstadisticaPrimerQuimestreSegundoBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/estadistica_q1_b2.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEstadisticaPrimerQuimestreTercerBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/estadistica_q1_b3.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEstadisticaPrimerQuimestre() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/estadistica_q1.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluacionesPrimerQuimestrePrimerBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/evaluaciones_q1_b1.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteExamenes() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/evaluaciones_examenes.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluacionesAnual() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/evaluaciones_anual.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluacionesResumenPromocion() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/resumen_promocion.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluacionesPrimerQuimestreSegundoBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/evaluaciones_q1_b2.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluacionesPrimerQuimestreTercerBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/evaluaciones_q1_b3.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluacionesPrimerQuimestre() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/evaluaciones_q1.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLibretasPrimerQuimestrePrimerBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_q1_b1.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLibretasPrimerQuimestreSegundoBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_q1_b2.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLibretasPrimerQuimestreTercerBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_q1_b3.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLibretasPrimerQuimestre() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_q1.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteAsistenciaPrimerQuimestre() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/asistencia_q1.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEstadisticaSegundoQuimestrePrimerBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/estadistica_q2_b1.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEstadisticaSegundoQuimestreSegundoBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/estadistica_q2_b2.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEstadisticaSegundoQuimestreTercerBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/estadistica_q2_b3.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEstadisticaSegundoQuimestre() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/estadistica_q2.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEstadisticaAnualPrimeroSeptimo() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/estadistica_anual_primero_septimo.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEstadisticaAnualOctavoTerceroBachillerato() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/estadistica_anual_octavo_tercero.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluacionesSegundoQuimestrePrimerBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/evaluaciones_q2_b1.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLibretasSegundoQuimestrePrimerBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_q2_b1.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluacionesSegundoQuimestreSegundoBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/evaluaciones_q2_b2.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluacionesSegundoQuimestreTercerBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/evaluaciones_q2_b3.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteEvaluacionesSegundoQuimestre() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/evaluaciones_q2.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLibretasSegundoQuimestreSegundoBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_q2_b2.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLibretasSegundoQuimestreTercerBloque() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_q2_b3.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLibretasSegundoQuimestre() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_q2.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLibretasAnualPrimeroSeptimo() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_anual_primero_septimo.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLibretasAnualOctavoTerceroDeBachillerato() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_anual_octavo_tercero_bachillerato.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteAsistenciaSegundoQuimestre() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/asistencia_q2.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLegalizacionSegundoQuimestre() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/legalizacion/promedios_q2.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLegalizacionNominaMatriculados() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/legalizacion/nomina_matriculados.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/excel/excel.jsf?faces-redirect=true";
	}

	public String exportarReporteLegalizacionPrimerQuimestre() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/legalizacion/promedios_q1.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLegalizacionCertificadoPromocion() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/legalizacion/certificado_promocion.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/excel/excel.jsf?faces-redirect=true";
	}

	public String exportarReporteLegalizacionCuadroPromocion() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/legalizacion/cuadro_promocion.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/excel/excel.jsf?faces-redirect=true";
	}

	public String exportarReporteLegalizacionMejoraYSupletorios() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/legalizacion/mejora_supletorios.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/excel/excel.jsf?faces-redirect=true";
	}

	public String exportarReporteLegalizacionRemedial() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/legalizacion/remedial.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/excel/excel.jsf?faces-redirect=true";
	}

	public String exportarReporteLegalizacionGracia() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/legalizacion/gracia.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/excel/excel.jsf?faces-redirect=true";
	}

	public String actualizarDatosEstudiante() {
		facesContext.getExternalContext().getSessionMap().put("estudiante", estudiante);
		return "/matriculacion/actualizarDatos.jsf?faces-redirect=true";
	}

	public void seleccionarParalelo() {
		asignaturasAAsignar = new ArrayList<String>();

		for (Asignatura a : paraleloAAsignar.getAsignaturas()) {
			asignaturasAAsignar.add(a.getId().toString());
		}
	}

	public void seleccionarAsignaturasAsignadas() {
		asignaturasAsignadas = new ArrayList<String>();
		for (Asignatura a : paralelo.getAsignaturas()) {
			asignaturasAsignadas.add(a.getId().toString());
		}
	}

	public String getCriterio() {
		return criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public Paralelo getParalelo() {
		return paralelo;
	}

	public void setParalelo(Paralelo paralelo) {
		this.paralelo = paralelo;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public List<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
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

	public List<Docente> getDocentes() {
		return docentes;
	}

	public void setDocentes(List<Docente> docentes) {
		this.docentes = docentes;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<String> getAsignaturasAsignadas() {
		return asignaturasAsignadas;
	}

	public void setAsignaturasAsignadas(List<String> asignaturasAsignadas) {
		this.asignaturasAsignadas = asignaturasAsignadas;
	}

	public List<String> getAsignaturasAAsignar() {
		return asignaturasAAsignar;
	}

	public void setAsignaturasAAsignar(List<String> asignaturasAAsignar) {
		this.asignaturasAAsignar = asignaturasAAsignar;
	}

	public Grado getGrado() {
		return grado;
	}

	public void setGrado(Grado grado) {
		this.grado = grado;
	}

	public Paralelo getParaleloAAsignar() {
		return paraleloAAsignar;
	}

	public void setParaleloAAsignar(Paralelo paraleloAAsignar) {
		this.paraleloAAsignar = paraleloAAsignar;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public ProyectoEscolar getProyectoEscolar() {
		return proyectoEscolar;
	}

	public void setProyectoEscolar(ProyectoEscolar proyectoEscolar) {
		this.proyectoEscolar = proyectoEscolar;
	}

	public List<ProyectoEscolar> getProyectosEscolares() {
		return proyectosEscolares;
	}

	public void setProyectosEscolares(List<ProyectoEscolar> proyectosEscolares) {
		this.proyectosEscolares = proyectosEscolares;
	}

	public String getTipoMatricula() {
		return tipoMatricula;
	}

	public void setTipoMatricula(String tipoMatricula) {
		this.tipoMatricula = tipoMatricula;
	}

	public List<Estudiante> getEstudiantesAprobados() {
		return estudiantesAprobados;
	}

	public void setEstudiantesAprobados(List<Estudiante> estudiantesAprobados) {
		this.estudiantesAprobados = estudiantesAprobados;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

}