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
@Table(name = "proyecto_escolar")
public class ProyectoEscolar implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String etiqueta;

	@ManyToOne
	@JoinColumn(name = "paralelo_id", insertable = true, updatable = false, nullable = false)
	private Paralelo paralelo;

	@ManyToOne
	@JoinColumn(name = "docente_id", insertable = true, updatable = true, nullable = false)
	private Docente docente;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectoEscolar", fetch = FetchType.LAZY)
	private List<EvaluacionProyectoEscolar> evaluaciones;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public Paralelo getParalelo() {
		return paralelo;
	}

	public void setParalelo(Paralelo paralelo) {
		this.paralelo = paralelo;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public List<EvaluacionProyectoEscolar> getEvaluaciones() {
		return evaluaciones;
	}

	public void setEvaluaciones(List<EvaluacionProyectoEscolar> evaluaciones) {
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
		if (!(object instanceof ProyectoEscolar)) {
			return false;
		}
		ProyectoEscolar other = (ProyectoEscolar) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gestionestudiantil.model.ProyectoEscolar[ id=" + id + " ]";
	}
}
