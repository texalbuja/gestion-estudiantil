package com.gestionestudiantil.model;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "criterio_de_evaluacion")
public class CriterioDeEvaluacion implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "periodo_lectivo_id")
	private PeriodoLectivo periodoLectivo;

	private Integer orden;

	@Column(name = "etiqueta", length = 255)
	@Size(max = 255, message = "Longitud máxima 255")
	private String etiqueta;

	@OneToMany(mappedBy = "criterioDeEvaluacion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<EvaluacionComportamiento> evaluaciones;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PeriodoLectivo getPeriodoLectivo() {
		return periodoLectivo;
	}

	public void setPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		this.periodoLectivo = periodoLectivo;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public List<EvaluacionComportamiento> getEvaluaciones() {
		return evaluaciones;
	}

	public void setEvaluaciones(List<EvaluacionComportamiento> evaluaciones) {
		this.evaluaciones = evaluaciones;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof CriterioDeEvaluacion)) {
			return false;
		}
		CriterioDeEvaluacion other = (CriterioDeEvaluacion) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gestionestudiantil.model.CriterioDeEvaluacion[ id=" + id
				+ " ]";
	}
}
