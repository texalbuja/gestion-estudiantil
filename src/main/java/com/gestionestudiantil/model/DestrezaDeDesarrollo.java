package com.gestionestudiantil.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "destreza_de_desarrollo")
public class DestrezaDeDesarrollo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "ambito_de_desarrollo_id")
	@ManyToOne
	private AmbitoDeDesarrollo ambitoDeDesarrollo;

	@JoinColumn(name = "docente_id", insertable = true, updatable = true, nullable = false)
	@ManyToOne
	private Docente docente;

	@OneToMany(mappedBy = "destrezaDeDesarrollo", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<EvaluacionDestrezaDeDesarrollo> evaluaciones;

	private String etiqueta;

	private String orden;

	public Docente getDocente() {
		return this.docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public String getEtiqueta() {
		return this.etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public AmbitoDeDesarrollo getAmbitoDeDesarrollo() {
		return ambitoDeDesarrollo;
	}

	public void setAmbitoDeDesarrollo(AmbitoDeDesarrollo ambitoDeDesarrollo) {
		this.ambitoDeDesarrollo = ambitoDeDesarrollo;
	}

	public List<EvaluacionDestrezaDeDesarrollo> getEvaluaciones() {
		return evaluaciones;
	}

	public void setEvaluaciones(List<EvaluacionDestrezaDeDesarrollo> evaluaciones) {
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
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof DestrezaDeDesarrollo)) {
			return false;
		}
		DestrezaDeDesarrollo other = (DestrezaDeDesarrollo) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gestionestudiantil.model.ComponenteEjeDeAprendizaje[ id=" + id + " ]";
	}
}
