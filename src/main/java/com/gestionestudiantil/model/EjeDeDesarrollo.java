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
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "eje_de_desarrollo")
public class EjeDeDesarrollo implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "paralelo_id")
	private Paralelo paralelo;

	private String etiqueta;

	private String orden;

	@OneToMany(mappedBy = "ejeDeDesarrollo", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@OrderBy(value = "orden")
	private List<AmbitoDeDesarrollo> ambitosDeDesarrollo;

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

	public Paralelo getParalelo() {
		return paralelo;
	}

	public void setParalelo(Paralelo paralelo) {
		this.paralelo = paralelo;
	}

	public List<AmbitoDeDesarrollo> getAmbitosDeDesarrollo() {
		return ambitosDeDesarrollo;
	}

	public void setAmbitosDeDesarrollo(List<AmbitoDeDesarrollo> ambitosDeDesarrollo) {
		this.ambitosDeDesarrollo = ambitosDeDesarrollo;
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
		if (!(object instanceof EjeDeDesarrollo)) {
			return false;
		}
		EjeDeDesarrollo other = (EjeDeDesarrollo) object;
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
