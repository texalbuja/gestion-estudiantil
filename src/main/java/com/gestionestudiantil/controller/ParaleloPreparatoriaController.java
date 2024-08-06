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
import com.gestionestudiantil.model.AmbitoDeDesarrollo;
import com.gestionestudiantil.model.DestrezaDeDesarrollo;
import com.gestionestudiantil.model.Docente;
import com.gestionestudiantil.model.EjeDeDesarrollo;
import com.gestionestudiantil.model.Estudiante;
import com.gestionestudiantil.model.Grado;
import com.gestionestudiantil.model.GrupoDeEvaluacion;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Paralelo;
import com.gestionestudiantil.model.ProyectoEscolar;
import com.gestionestudiantil.model.Usuario;
import com.gestionestudiantil.service.MatriculacionService;

@ManagedBean
@ViewScoped
public class ParaleloPreparatoriaController implements Serializable {

	private static final long serialVersionUID = 1782635962622860097L;

	@Inject
	private FacesContext facesContext;
	@Inject
	private MatriculacionService matriculacionService;

	@ManagedProperty("#{usuario}")
	private Usuario usuario;

	@ManagedProperty("#{institucion}")
	private Institucion institucion;

	@ManagedProperty("#{paralelo}")
	private Paralelo paralelo;

	@ManagedProperty("#{grado}")
	private Grado grado;

	@ManagedProperty("#{docente}")
	private Docente docente;

	private List<Matricula> matriculas;

	private Matricula matricula;

	private List<Docente> docentes;

	private Estudiante estudiante;

	private List<ProyectoEscolar> proyectosEscolares;

	private ProyectoEscolar proyectoEscolar;

	private String tipoMatricula;

	private List<Estudiante> estudiantesAprobados;

	private List<EjeDeDesarrollo> ejesDeDesarrollo;

	private EjeDeDesarrollo ejeDeDesarrollo;

	private List<AmbitoDeDesarrollo> ambitosDeDesarrollo;

	private AmbitoDeDesarrollo ambitoDeDesarrollo;

	private DestrezaDeDesarrollo destrezaDeDesarrollo;

	@PostConstruct
	public void init() {
		matriculas = matriculacionService.obtenerMatriculasPorParalelo(paralelo);
		docentes = matriculacionService
				.obtenerDocentesPorInstitucion(paralelo.getGrado().getPeriodoLectivo().getInstitucion());
		ejesDeDesarrollo = matriculacionService.obtenerEjesDeDesarrolloPorParalelo(paralelo);
		estudiantesAprobados = matriculacionService.obtenerEstudiantesAprobados(paralelo.getGrado());
		setTipoMatricula("Antiguo");
		if (ejesDeDesarrollo.size() > 0) {
			ejeDeDesarrollo = ejesDeDesarrollo.get(0);
			ambitosDeDesarrollo = matriculacionService.obtenerAmbitosDeDesarrolloPorEjeDeDesarrollo(ejeDeDesarrollo);
		}
		destrezaDeDesarrollo = new DestrezaDeDesarrollo();
	}

	public void obtenerAmbitos() {
		ambitosDeDesarrollo = matriculacionService.obtenerAmbitosDeDesarrolloPorEjeDeDesarrollo(ejeDeDesarrollo);
	}

	public void guardarEjeDeDesarrollo() {
		try {
			ejeDeDesarrollo.setParalelo(paralelo);
			matriculacionService.guardarEjeDeDesarrollo(ejeDeDesarrollo);
			ejesDeDesarrollo = matriculacionService.obtenerEjesDeDesarrolloPorParalelo(paralelo);
			ejeDeDesarrollo = new EjeDeDesarrollo();
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error al guardar el destreza de desarrollo", e.getMessage()));
		}
	}

	public void guardarDestrezaDeDesarrollo() {
		try {
			destrezaDeDesarrollo.setDocente(docente);
			destrezaDeDesarrollo.setAmbitoDeDesarrollo(ambitoDeDesarrollo);
			matriculacionService.guardarDestrezaDeDesarrollo(destrezaDeDesarrollo);
			ejesDeDesarrollo = matriculacionService.obtenerEjesDeDesarrolloPorParalelo(paralelo);
			destrezaDeDesarrollo = new DestrezaDeDesarrollo();
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error al guardar el destreza de desarrollo", e.getMessage()));
		}
	}

	public void guardarAmbitoDeDesarrollo() {
		try {
			ambitoDeDesarrollo.setEjeDeDesarrollo(ejeDeDesarrollo);
			matriculacionService.guardarAmbitoDeDesarrollo(ambitoDeDesarrollo);
			ejesDeDesarrollo = matriculacionService.obtenerEjesDeDesarrolloPorParalelo(paralelo);
			ambitoDeDesarrollo = new AmbitoDeDesarrollo();
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error al guardar el destreza de desarrollo", e.getMessage()));
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

	public void crearEjesDeDesarrolloYAprendizaje() {
		try {
			matriculacionService.crearEjesDeDesarrollo(paralelo);
			ejesDeDesarrollo = matriculacionService.obtenerEjesDeDesarrolloPorParalelo(paralelo);
			if (ejesDeDesarrollo.size() > 0) {
				ejeDeDesarrollo = ejesDeDesarrollo.get(0);
				ambitosDeDesarrollo = matriculacionService
						.obtenerAmbitosDeDesarrolloPorEjeDeDesarrollo(ejeDeDesarrollo);
			}
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error al generar el código temporal", e.getMessage()));
		}
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

	public String exportarReporteCertificadosMatriculas() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/certificado_matricula.jasper");
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

	public String exportarReporteAsistenciaSegundoQuimestre() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/asistencia_q2.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public void guardarEstudiante() {
		try {
			matriculacionService.matricularEstudianteEnPreparatoria(estudiante, paralelo, matricula);
			matriculas = matriculacionService.obtenerMatriculasPorParalelo(paralelo);
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

	public void editarDestrezaDeDesarrollo() {
		try {
			matriculacionService.editarDestrezaDeDesarrollo(destrezaDeDesarrollo);
			ejesDeDesarrollo = matriculacionService.obtenerEjesDeDesarrolloPorParalelo(paralelo);
			destrezaDeDesarrollo = new DestrezaDeDesarrollo();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Destreza de Desarrollo " + destrezaDeDesarrollo.getEtiqueta() + " editado correctamente"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void editarEjeDeDesarrollo() {
		try {
			matriculacionService.editarEjeDeDesarrollo(ejeDeDesarrollo);
			ejesDeDesarrollo = matriculacionService.obtenerEjesDeDesarrolloPorParalelo(paralelo);
			ejeDeDesarrollo = new EjeDeDesarrollo();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Eje de Desarrollo " + destrezaDeDesarrollo.getEtiqueta() + " editado correctamente"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void editarAmbitoDeDesarrollo() {
		try {
			matriculacionService.editarAmbitoDeDesarrollo(ambitoDeDesarrollo);
			ejesDeDesarrollo = matriculacionService.obtenerEjesDeDesarrolloPorParalelo(paralelo);
			destrezaDeDesarrollo = new DestrezaDeDesarrollo();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Eje de Desarrollo " + destrezaDeDesarrollo.getEtiqueta() + " editado correctamente"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}
	
	public void eliminarAmbitoDeDesarrollo() {
		try {
			matriculacionService.eliminarAmbitoDeDesarrollo(ambitoDeDesarrollo);
			ejesDeDesarrollo = matriculacionService.obtenerEjesDeDesarrolloPorParalelo(paralelo);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Ambito de Desarrollo " + ambitoDeDesarrollo.getEtiqueta() + " eliminado correctamente"));
			destrezaDeDesarrollo = new DestrezaDeDesarrollo();
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}
	

	public void eliminarEjeDeDesarrollo() {
		try {
			matriculacionService.eliminarEjeDeDesarrollo(ejeDeDesarrollo);
			ejesDeDesarrollo = matriculacionService.obtenerEjesDeDesarrolloPorParalelo(paralelo);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Eje de Desarrollo " + ejeDeDesarrollo.getEtiqueta() + " eliminado correctamente"));
			ejeDeDesarrollo = new EjeDeDesarrollo();
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
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

	public void eliminarDestrezaDeDesarrollo() {
		try {
			matriculacionService.eliminarDestrezaDeDesarrollo(destrezaDeDesarrollo);
			ejesDeDesarrollo = matriculacionService.obtenerEjesDeDesarrolloPorParalelo(paralelo);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Destreza de Desarrollo " + destrezaDeDesarrollo.getEtiqueta() + " eliminado correctamente"));
			destrezaDeDesarrollo = new DestrezaDeDesarrollo();
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public String exportarReporteLibretasPrimerQuimestre() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_q1_inicial_preparatoria.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLibretasPrimerQuimestreBloqueUno() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_q1_b1_inicial_preparatoria.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLibretasPrimerQuimestreBloqueDos() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_q1_b2_inicial_preparatoria.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLibretasSegundoQuimestre() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_q2_inicial_preparatoria.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLibretasSegundoQuimestreBloqueUno() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_q2_b1_inicial_preparatoria.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLibretasSegundoQuimestreBloqueDos() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_q2_b2_inicial_preparatoria.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLibretasSegundoQuimestreBloqueTres() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_q2_b3_inicial_preparatoria.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLibretasPrimerQuimestreBloqueTres() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_q1_b3_inicial_preparatoria.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteLibretasAnual() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/libreta_anual_inicial_preparatoria.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
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

	public String actualizarDatosEstudiante() {
		facesContext.getExternalContext().getSessionMap().put("estudiante", estudiante);
		return "/matriculacion/actualizarDatos.jsf?faces-redirect=true";
	}

	public void prepararNuevoEstudiante() {
		estudiante = new Estudiante();
		estudiante.setInstitucion(this.institucion);
		estudiante.setSexo("M");
		estudiante.setSeccion("M");
		matricula = new Matricula();
		matricula.setNumeroMatriculaGrado("Primera");
	}

	public void prepararEjeDeDesarrollo() {
		ejeDeDesarrollo = new EjeDeDesarrollo();
	}

	public void prepararAmbitoDeDesarrollo() {
		ambitoDeDesarrollo = new AmbitoDeDesarrollo();
	}

	public List<Matricula> obtenerMatriculasParalelo(Paralelo paralelo) {
		return matriculacionService.obtenerMatriculasPorParalelo(paralelo);
	}

	public String seleccionarMatricula() {
		facesContext.getExternalContext().getSessionMap().put("matricula", matricula);
		return "/matriculacion/matricula.jsf?faces-redirect=true";
	}

	public String evaluarMatricula() {
		facesContext.getExternalContext().getSessionMap().put("matricula", matricula);
		return "/evaluacion/ejesDeDesarrollo/matricula.jsf?faces-redirect=true";
	}

	public String evaluarMatriculaPorDocente() {
		facesContext.getExternalContext().getSessionMap().put("matricula", matricula);
		facesContext.getExternalContext().getSessionMap().put("grado", paralelo.getGrado());
		GrupoDeEvaluacion grupoDeEvaluacion = (GrupoDeEvaluacion) facesContext.getExternalContext().getSessionMap()
				.get("grupoDeEvaluacion");
		return "/evaluacion/ejesDeDesarrollo/" + grupoDeEvaluacion.getUrl() + "?faces-redirect=true";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public AmbitoDeDesarrollo getAmbitoDeDesarrollo() {
		return ambitoDeDesarrollo;
	}

	public void setAmbitoDeDesarrollo(AmbitoDeDesarrollo ambitoDeDesarrollo) {
		this.ambitoDeDesarrollo = ambitoDeDesarrollo;
	}

	public Paralelo getParalelo() {
		return paralelo;
	}

	public void setParalelo(Paralelo paralelo) {
		this.paralelo = paralelo;
	}

	public Grado getGrado() {
		return grado;
	}

	public void setGrado(Grado grado) {
		this.grado = grado;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public List<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public List<Docente> getDocentes() {
		return docentes;
	}

	public void setDocentes(List<Docente> docentes) {
		this.docentes = docentes;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public List<ProyectoEscolar> getProyectosEscolares() {
		return proyectosEscolares;
	}

	public List<Estudiante> getEstudiantesAprobados() {
		return estudiantesAprobados;
	}

	public void setEstudiantesAprobados(List<Estudiante> estudiantesAprobados) {
		this.estudiantesAprobados = estudiantesAprobados;
	}

	public void setProyectosEscolares(List<ProyectoEscolar> proyectosEscolares) {
		this.proyectosEscolares = proyectosEscolares;
	}

	public List<AmbitoDeDesarrollo> getAmbitosDeDesarrollo() {
		return ambitosDeDesarrollo;
	}

	public void setAmbitosDeDesarrollo(List<AmbitoDeDesarrollo> ambitosDeDesarrollo) {
		this.ambitosDeDesarrollo = ambitosDeDesarrollo;
	}

	public ProyectoEscolar getProyectoEscolar() {
		return proyectoEscolar;
	}

	public void setProyectoEscolar(ProyectoEscolar proyectoEscolar) {
		this.proyectoEscolar = proyectoEscolar;
	}

	public String getTipoMatricula() {
		return tipoMatricula;
	}

	public void setTipoMatricula(String tipoMatricula) {
		this.tipoMatricula = tipoMatricula;
	}

	public List<EjeDeDesarrollo> getEjesDeDesarrollo() {
		return ejesDeDesarrollo;
	}

	public void setEjesDeDesarrollo(List<EjeDeDesarrollo> ejesDeDesarrollo) {
		this.ejesDeDesarrollo = ejesDeDesarrollo;
	}

	public EjeDeDesarrollo getEjeDeDesarrollo() {
		return ejeDeDesarrollo;
	}

	public void setEjeDeDesarrollo(EjeDeDesarrollo ejeDeDesarrollo) {
		this.ejeDeDesarrollo = ejeDeDesarrollo;
	}

	public DestrezaDeDesarrollo getDestrezaDeDesarrollo() {
		return destrezaDeDesarrollo;
	}

	public void setDestrezaDeDesarrollo(DestrezaDeDesarrollo destrezaDeDesarrollo) {
		this.destrezaDeDesarrollo = destrezaDeDesarrollo;
	}

}