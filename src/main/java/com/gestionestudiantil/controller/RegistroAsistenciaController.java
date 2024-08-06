package com.gestionestudiantil.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.model.Asistencia;
import com.gestionestudiantil.model.Estudiante;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.service.ComportamientoService;
import com.gestionestudiantil.service.EvaluacionService;

@ManagedBean
@ViewScoped
public class RegistroAsistenciaController implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private FacesContext facesContext;

	@Inject
	private EvaluacionService evaluacionService;

	@Inject
	private ComportamientoService comportamientoService;

	@ManagedProperty("#{institucion}")
	private Institucion institucion;

	private Estudiante estudiante;

	private Matricula matricula;

	private List<Matricula> matriculas;

	private List<Matricula> matriculasFiltradas;

	private String detalle;

	private Integer bloque;

	private Date fecha;

	private List<Asistencia> asistencias;

	private Asistencia asistencia;

	private String tipo;

	@ManagedProperty("#{periodo}")
	private String periodo;

	@PostConstruct
	public void init() {
		matriculas = evaluacionService.obtenerMatriculasPorInstitucion(institucion, periodo);
		matriculasFiltradas = new ArrayList<Matricula>();
		asistencias = new ArrayList<Asistencia>();
		bloque = 1;
		fecha = new Date();
		tipo = "A";
		matricula = new Matricula();
	}

	public void editar() {
		try {
			comportamientoService.editarAsistencia(asistencia);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Asistencia editada con éxito"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void consultar() {
		asistencias = comportamientoService.obtenerAsistenciasPorMatricula(matricula);
	}

	public void consultarTodos() {
		asistencias = comportamientoService.obtenerAsistenciasPorInstitucionYPeriodo(institucion, periodo);
	}

	public void justificar() {
		try {
			asistencia.setEstado("J");
			comportamientoService.editarAsistencia(asistencia);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Asistencia justificada con éxito"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void eliminar() {
		try {
			comportamientoService.eliminarAsistencia(asistencia);
			asistencias.remove(asistencia);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Asistencia eliminada con éxito"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void guardar() {
		try {
			asistencias.add(0, comportamientoService.guardarAsistencia(bloque, fecha, detalle, matricula, tipo));
			matricula = new Matricula();
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Asistencia registrada con éxito"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public List<Matricula> completarMatriculas(String query) {
		matriculasFiltradas = new ArrayList<Matricula>();
		for (Matricula matricula : matriculas) {
			if (matricula.getEstudiante().getApellidos().toUpperCase().startsWith(query.trim().toUpperCase()))
				matriculasFiltradas.add(matricula);
		}
		return matriculasFiltradas;
	}

	public EvaluacionService getEvaluacionService() {
		return evaluacionService;
	}

	public void setEvaluacionService(EvaluacionService evaluacionService) {
		this.evaluacionService = evaluacionService;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
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

	public List<Matricula> getMatriculasFiltradas() {
		return matriculasFiltradas;
	}

	public void setMatriculasFiltradas(List<Matricula> matriculasFiltradas) {
		this.matriculasFiltradas = matriculasFiltradas;
	}

	public Integer getBloque() {
		return bloque;
	}

	public void setBloque(Integer bloque) {
		this.bloque = bloque;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<Asistencia> getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(List<Asistencia> asistencias) {
		this.asistencias = asistencias;
	}

	public Asistencia getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(Asistencia asistencia) {
		this.asistencia = asistencia;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

}
