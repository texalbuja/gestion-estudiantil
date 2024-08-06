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
@Table(name = "ambito_de_desarrollo")
public class AmbitoDeDesarrollo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "eje_de_desarrollo_id")
	private EjeDeDesarrollo ejeDeDesarrollo;

	@OneToMany(mappedBy = "ambitoDeDesarrollo", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@OrderBy(value = "orden")
	private List<DestrezaDeDesarrollo> destrezasDeDesarrollo;

	private String etiqueta;

	private String orden;

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

	public EjeDeDesarrollo getEjeDeDesarrollo() {
		return ejeDeDesarrollo;
	}

	public void setEjeDeDesarrollo(EjeDeDesarrollo ejeDeDesarrollo) {
		this.ejeDeDesarrollo = ejeDeDesarrollo;
	}

	public List<DestrezaDeDesarrollo> getDestrezasDeDesarrollo() {
		return destrezasDeDesarrollo;
	}

	public void setDestrezasDeDesarrollo(List<DestrezaDeDesarrollo> destrezasDeDesarrollo) {
		this.destrezasDeDesarrollo = destrezasDeDesarrollo;
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
		if (!(object instanceof AmbitoDeDesarrollo)) {
			return false;
		}
		AmbitoDeDesarrollo other = (AmbitoDeDesarrollo) object;
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
