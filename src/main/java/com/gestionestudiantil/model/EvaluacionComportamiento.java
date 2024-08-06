package com.gestionestudiantil.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "evaluacion_comportamiento")
public class EvaluacionComportamiento implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "matricula_id")
	private Matricula matricula;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "criterio_de_evaluacion_id")
	private CriterioDeEvaluacion criterioDeEvaluacion;
	private String b1;
	private String b2;
	private String b3;
	private String b4;
	private String b5;
	private String b6;

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CriterioDeEvaluacion getCriterioDeEvaluacion() {
		return criterioDeEvaluacion;
	}

	public void setCriterioDeEvaluacion(
			CriterioDeEvaluacion criterioDeEvaluacion) {
		this.criterioDeEvaluacion = criterioDeEvaluacion;
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

}
