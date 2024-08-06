package com.gestionestudiantil.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "evaluacion_proyecto_escolar")
public class EvaluacionProyectoEscolar implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "proyecto_escolar_id", insertable = true, updatable = false, nullable = true)
	private ProyectoEscolar proyectoEscolar;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "matricula_id", insertable = true, updatable = false, nullable = true)
	private Matricula matricula;

	private String b1;
	private String b2;
	private String b3;
	private String b4;
	private String b5;
	private String b6;

	private String q1;
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

	public ProyectoEscolar getProyectoEscolar() {
		return proyectoEscolar;
	}

	public void setProyectoEscolar(ProyectoEscolar proyectoEscolar) {
		this.proyectoEscolar = proyectoEscolar;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public String getB1() {
		return b1;
	}

	public void setB1(String b1) {
		this.b1 = b1;
	}

	public String getB2() {
		return b2;
	}

	public void setB2(String b2) {
		this.b2 = b2;
	}

	public String getB3() {
		return b3;
	}

	public void setB3(String b3) {
		this.b3 = b3;
	}

	public String getB4() {
		return b4;
	}

	public void setB4(String b4) {
		this.b4 = b4;
	}

	public String getB5() {
		return b5;
	}

	public void setB5(String b5) {
		this.b5 = b5;
	}

	public String getB6() {
		return b6;
	}

	public void setB6(String b6) {
		this.b6 = b6;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof EvaluacionProyectoEscolar)) {
			return false;
		}
		EvaluacionProyectoEscolar other = (EvaluacionProyectoEscolar) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gestionestudiantil.model.EvaluacionProyectoEscolar[ id="
				+ id + " ]";
	}
}