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
@Table(name = "asignatura_dictada")
public class Asignatura implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "paralelo_id")
	private Paralelo paralelo;
	
	@ManyToOne
	@JoinColumn(name="area_id")
	private Area area;

	@ManyToOne
	@JoinColumn(name = "docente_id")
	private Docente docente;

	private String etiqueta;
	
	@Column(name="certificado_promocion")
	private String certificadoPromocion;
	
	private Double orden;

	@OneToMany(mappedBy = "asignatura", fetch = FetchType.LAZY)
	@OrderBy("informeAnual")
	private List<EvaluacionAsignatura> evaluaciones;

	@OneToMany(mappedBy = "asignatura", fetch = FetchType.LAZY)
	@OrderBy("etiqueta")
	private List<Asignatura> asignaturas;

	@ManyToOne
	@JoinColumn(name = "asignatura_id")
	private Asignatura asignatura;
	
	@Column(name = "logica_evaluacion")
	@Size(max = 3, message = "Longitud máxima 3")
	private String logicaEvaluacion;

	public String getEtiqueta() {
		return this.etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public Docente getDocente() {
		return this.docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Paralelo getParalelo() {
		return paralelo;
	}

	public void setParalelo(Paralelo paralelo) {
		this.paralelo = paralelo;
	}

	public List<EvaluacionAsignatura> getEvaluaciones() {
		return evaluaciones;
	}

	public void setEvaluaciones(
			List<EvaluacionAsignatura> evaluaciones) {
		this.evaluaciones = evaluaciones;
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

	public String getLogicaEvaluacion() {
		return logicaEvaluacion;
	}

	public void setLogicaEvaluacion(String logicaEvaluacion) {
		this.logicaEvaluacion = logicaEvaluacion;
	}

	public Double getOrden() {
		return orden;
	}

	public void setOrden(Double orden) {
		this.orden = orden;
	}


	public String getCertificadoPromocion() {
		return certificadoPromocion;
	}

	public void setCertificadoPromocion(String certificadoPromocion) {
		this.certificadoPromocion = certificadoPromocion;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
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
		if (!(object instanceof Asignatura)) {
			return false;
		}
		Asignatura other = (Asignatura) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gestionestudiantil.model.AsignaturaDictada[ id=" + id
				+ " ]";
	}
}
