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
import com.gestionestudiantil.model.Estudiante;
import com.gestionestudiantil.model.Grado;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Paralelo;
import com.gestionestudiantil.model.PeriodoLectivo;
import com.gestionestudiantil.service.MatriculacionService;

@ManagedBean
@ViewScoped
public class EditarMatriculaController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;
	@Inject
	private MatriculacionService matriculacionService;

	@ManagedProperty("#{institucion}")
	private Institucion institucion;

	@ManagedProperty("#{matricula}")
	private Matricula matricula;

	private List<PeriodoLectivo> periodosLectivos;

	private List<Grado> grados;

	private Grado grado;

	private Paralelo paralelo;

	private Estudiante estudiante;

	private String representante;

	private PeriodoLectivo periodoLectivo;

	private String identificacion;

	@ManagedProperty("#{periodo}")
	private String periodo;

	@PostConstruct
	public void init() {
		periodosLectivos = matriculacionService.obtenerPeriodosLectivosPorInstitucion(institucion, periodo);
		if (periodosLectivos.size() > 0) {
			periodoLectivo = periodosLectivos.get(0);
			obtenerGrados();
		}
		if (grados.size() > 0) {
			grado = grados.get(0);
		}
		identificacion = matricula.getEstudiante().getIdentificacion();
	}

	public void obtenerGrados() {
		grados = matriculacionService.obtenerGradosPorPeriodoLectivo(periodoLectivo);
		if (grados.size() > 0) {
			grado = grados.get(0);
		}
	}

	public void guardar() {
		try {

			matriculacionService.editarEstudiante(matricula.getEstudiante(), identificacion);
			matriculacionService.desvincularMatricula(matricula);
			matriculacionService.asignarMatriculaAParalelo(matricula, paralelo);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Estudiante " + matricula.getEstudiante().getNombres() + " "
							+ matricula.getEstudiante().getApellidos() + " matriculado en "
							+ grado.getGrados().getDescripcionGrado() + " - Paralelo " + paralelo.getEtiqueta()));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public List<PeriodoLectivo> getPeriodosLectivos() {
		return periodosLectivos;
	}

	public void setPeriodosLectivos(List<PeriodoLectivo> periodosLectivos) {
		this.periodosLectivos = periodosLectivos;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public PeriodoLectivo getPeriodoLectivo() {
		return periodoLectivo;
	}

	public void setPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		this.periodoLectivo = periodoLectivo;
	}

	public List<Grado> getGrados() {
		return grados;
	}

	public void setGrados(List<Grado> grados) {
		this.grados = grados;
	}

	public Grado getGrado() {
		return grado;
	}

	public void setGrado(Grado grado) {
		this.grado = grado;
	}

	public Paralelo getParalelo() {
		return paralelo;
	}

	public void setParalelo(Paralelo paralelo) {
		this.paralelo = paralelo;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

}