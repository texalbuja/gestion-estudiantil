package com.gestionestudiantil.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "paralelo")
public class Paralelo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String etiqueta;

	@Column(name = "especialidad", length = 64)
	@Size(max = 64, message = "Longitud máxima 64")
	private String especialidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grado_id")
	private Grado grado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "docente_id")
	private Docente tutor;

	@OneToMany(mappedBy = "paralelo", fetch = FetchType.LAZY)
	private List<Matricula> estudiantesMatriculados;

	@OneToMany(mappedBy = "paralelo", fetch = FetchType.LAZY)
	@OrderBy("etiqueta")
	private List<Asignatura> asignaturas;

	@OneToMany(mappedBy = "paralelo", fetch = FetchType.LAZY)
	@OrderBy("etiqueta")
	private List<ProyectoEscolar> proyectosEscolares;
	
	@OneToMany(mappedBy = "paralelo", fetch = FetchType.LAZY)
	@OrderBy("orden")
	private List<EjeDeDesarrollo> ejesDeDesarrollo;

	public List<ProyectoEscolar> getProyectosEscolares() {
		return proyectosEscolares;
	}

	public void setProyectosEscolares(List<ProyectoEscolar> proyectosEscolares) {
		this.proyectosEscolares = proyectosEscolares;
	}

	public List<Asignatura> getAsignaturas() {
		return this.asignaturas;
	}

	public void setAsignaturas(List<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}

	public Docente getTutor() {
		return this.tutor;
	}

	public void setTutor(Docente tutor) {
		this.tutor = tutor;
	}

	public List<Matricula> getEstudiantesMatriculados() {
		return this.estudiantesMatriculados;
	}

	public void setEstudiantesMatriculados(List<Matricula> estudiantesMatriculados) {
		this.estudiantesMatriculados = estudiantesMatriculados;
	}

	public String getEtiqueta() {
		return this.etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setGrado(Grado grado) {
		this.grado = grado;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public Grado getGrado() {
		return grado;
	}

	public List<EjeDeDesarrollo> getEjesDeDesarrollo() {
		return ejesDeDesarrollo;
	}

	public void setEjesDeDesarrollo(List<EjeDeDesarrollo> ejesDeDesarrollo) {
		this.ejesDeDesarrollo = ejesDeDesarrollo;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Paralelo)) {
			return false;
		}
		Paralelo other = (Paralelo) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gestionestudiantil.model.Paralelo[ id=" + id + " ]";
	}

}