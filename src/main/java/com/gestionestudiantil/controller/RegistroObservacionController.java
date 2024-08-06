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
import com.gestionestudiantil.model.Estudiante;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Observacion;
import com.gestionestudiantil.service.ComportamientoService;
import com.gestionestudiantil.service.EvaluacionService;

@ManagedBean
@ViewScoped
public class RegistroObservacionController implements Serializable {

	private static final long serialVersionUID = -2360556937900146971L;

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

	private List<Observacion> observaciones;

	private Observacion observacion;

	@ManagedProperty("#{periodo}")
	private String periodo;

	@PostConstruct
	public void init() {
		matriculas = evaluacionService.obtenerMatriculasPorInstitucion(institucion, periodo);
		matriculasFiltradas = new ArrayList<Matricula>();
		observaciones = new ArrayList<Observacion>();
		bloque = 1;
		matricula = new Matricula();
		fecha = new Date();
	}

	public void consultar() {
		observaciones = comportamientoService.obtenerObservacionesPorMatricula(matricula);
	}

	public void consultarTodos() {
		observaciones = comportamientoService.obtenerObservacionesPorInstitucionYPeriodo(institucion, periodo);
	}

	public void editar() {
		try {
			comportamientoService.editarObservacion(observacion);
			observaciones = comportamientoService.obtenerObservacionesPorInstitucionYPeriodo(institucion, periodo);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Observación editada con éxito"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void eliminar() {
		try {
			comportamientoService.eliminarObservacion(observacion);
			observaciones.remove(observacion);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Observación eliminada con éxito"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void guardar() {
		try {
			observaciones.add(comportamientoService.guardarObservacion(bloque, fecha, detalle, matricula));
			matricula = new Matricula();
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Observación registrada con éxito"));
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

	public List<Observacion> getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(List<Observacion> observaciones) {
		this.observaciones = observaciones;
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

	public Observacion getObservacion() {
		return observacion;
	}

	public void setObservacion(Observacion observacion) {
		this.observacion = observacion;
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