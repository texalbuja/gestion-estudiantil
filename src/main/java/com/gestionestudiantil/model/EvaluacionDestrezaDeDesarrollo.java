package com.gestionestudiantil.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "evaluacion_destreza_de_desarrollo")
public class EvaluacionDestrezaDeDesarrollo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "destreza_de_desarrollo_id")
	@ManyToOne
	private DestrezaDeDesarrollo destrezaDeDesarrollo;

	@JoinColumn(name = "matricula_id")
	@ManyToOne
	private Matricula matricula;

	private String q1_b1;
	private String q1_b2;
	private String q1;
	private String q2_b1;
	private String q2_b2;
	private String q2;

	public String getQ1() {
		return q1;
	}

	public void setQ1(String q1) {
		this.q1 = q1;
	}

	public String getQ2() {
		return q2;
	}

	public void setQ2(String q2) {
		this.q2 = q2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public DestrezaDeDesarrollo getDestrezaDeDesarrollo() {
		return destrezaDeDesarrollo;
	}

	public void setDestrezaDeDesarrollo(DestrezaDeDesarrollo destrezaDeDesarrollo) {
		this.destrezaDeDesarrollo = destrezaDeDesarrollo;
	}

	public String getQ1_b1() {
		return q1_b1;
	}

	public void setQ1_b1(String q1_b1) {
		this.q1_b1 = q1_b1;
	}

	public String getQ1_b2() {
		return q1_b2;
	}

	public void setQ1_b2(String q1_b2) {
		this.q1_b2 = q1_b2;
	}

	public String getQ2_b1() {
		return q2_b1;
	}

	public void setQ2_b1(String q2_b1) {
		this.q2_b1 = q2_b1;
	}

	public String getQ2_b2() {
		return q2_b2;
	}

	public void setQ2_b2(String q2_b2) {
		this.q2_b2 = q2_b2;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof EvaluacionDestrezaDeDesarrollo)) {
			return false;
		}
		EvaluacionDestrezaDeDesarrollo other = (EvaluacionDestrezaDeDesarrollo) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gestionestudiantil.model.EvaluacionProyectoEscolar[ id=" + id + " ]";
	}
}