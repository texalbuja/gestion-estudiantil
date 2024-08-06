package com.gestionestudiantil.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name = "matricula")
public class Matricula implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estudiante_id", insertable = true, updatable = true, nullable = true)
	@OrderBy("apellidos")
	private Estudiante estudiante;

	@Column(name = "numero_matricula")
	private String numeroMatricula;

	@Column(name = "observacion", length = 255)
	@Size(max = 255, message = "Longitud máxima 255")
	private String observacion;

	@Column(name = "numero_matricula_grado")
	private String numeroMatriculaGrado;

	@Column(name = "fecha")
	@Temporal(TemporalType.DATE)
	private Date fecha;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grado_id")
	private Grado grado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paralelo_id")
	private Paralelo paralelo;

	@OneToOne(mappedBy = "matricula", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private InformeAnual informeAnual;

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "imagen_id", insertable = true, updatable = true, nullable = true)
	private Imagen imagen;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "matricula", fetch = FetchType.LAZY)
	private List<Observacion> observaciones;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "matricula", fetch = FetchType.LAZY)
	private List<EvaluacionComportamiento> evaluacionesComportamiento;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "matricula", fetch = FetchType.LAZY)
	private List<Asistencia> asistencias;
	
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "matricula", fetch = FetchType.LAZY)
	private List<EvaluacionProyectoEscolar> evaluacionesProyectoEscolar;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "matricula", fetch = FetchType.LAZY)
	private List<EvaluacionDestrezaDeDesarrollo> evaluacionesDestrezaDeDesarrollo;
	
	private String resultado;

	public Estudiante getEstudiante() {
		return this.estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public String getNumeroMatricula() {
		return this.numeroMatricula;
	}

	public void setNumeroMatricula(String numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public String getNumeroMatriculaGrado() {
		return numeroMatriculaGrado;
	}

	public void setNumeroMatriculaGrado(String numeroMatriculaGrado) {
		this.numeroMatriculaGrado = numeroMatriculaGrado;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public InformeAnual getInformeAnual() {
		return informeAnual;
	}

	public void setInformeAnual(InformeAnual informeAnual) {
		this.informeAnual = informeAnual;
	}

	public List<Observacion> getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(List<Observacion> observaciones) {
		this.observaciones = observaciones;
	}

	public List<EvaluacionComportamiento> getEvaluacionesComportamiento() {
		return evaluacionesComportamiento;
	}

	public void setEvaluacionesComportamiento(
			List<EvaluacionComportamiento> evaluacionesComportamiento) {
		this.evaluacionesComportamiento = evaluacionesComportamiento;
	}

	public List<Asistencia> getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(List<Asistencia> asistencias) {
		this.asistencias = asistencias;
	}

	public List<EvaluacionProyectoEscolar> getEvaluacionesProyectoEscolar() {
		return evaluacionesProyectoEscolar;
	}

	public void setEvaluacionesProyectoEscolar(List<EvaluacionProyectoEscolar> evaluacionesProyectoEscolar) {
		this.evaluacionesProyectoEscolar = evaluacionesProyectoEscolar;
	}

	public List<EvaluacionDestrezaDeDesarrollo> getEvaluacionesDestrezaDeDesarrollo() {
		return evaluacionesDestrezaDeDesarrollo;
	}

	public void setEvaluacionesDestrezaDeDesarrollo(List<EvaluacionDestrezaDeDesarrollo> evaluacionesDestrezaDeDesarrollo) {
		this.evaluacionesDestrezaDeDesarrollo = evaluacionesDestrezaDeDesarrollo;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Matricula)) {
			return false;
		}
		Matricula other = (Matricula) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return id.toString();
	}
}