package com.gestionestudiantil.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "informe_anual")
public class InformeAnual implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "informeAnual", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OrderBy("asignatura")
	private List<EvaluacionAsignatura> informesAnuales;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "matricula_id", insertable = true)
	@OrderBy("estudiante")
	private Matricula matricula;

	@Column(name = "nota_final")
	private BigDecimal notaFinal;

	@Column(name = "escala_cualitativa_final")
	private String escalaCualitativaFinal;

	@Column(name = "resultado")
	private String resultado;

	@Column(name = "observaciones")
	private String observaciones;

	@Column(name = "nota_examen_gracia")
	private BigDecimal notaExamenDeGracia;

	/**
	 * Promedios Quimestre I
	 */

	@Column(name = "q1_nota")
	private BigDecimal quimestre1Nota;

	@Column(name = "q1_p1_nota")
	private BigDecimal quimestre1Parcial1Nota;

	@Column(name = "q1_p2_nota")
	private BigDecimal quimestre1Parcial2Nota;

	@Column(name = "q1_p3_nota")
	private BigDecimal quimestre1Parcial3Nota;

	/**
	 * Cabecera Quimestre II
	 */

	@Column(name = "q2_nota")
	private BigDecimal quimestre2Nota;

	@Column(name = "q2_p1_nota")
	private BigDecimal quimestre2Parcial1Nota;

	@Column(name = "q2_p2_nota")
	private BigDecimal quimestre2Parcial2Nota;

	@Column(name = "q2_p3_nota")
	private BigDecimal quimestre2Parcial3Nota;
	
	@Column(name = "etiqueta_nota_final")
	private String etiquetaNotaFinal;
	
	@Column(name = "evaluacion_comportamiento_b6")
	private String evaluacionComportamientoB6;
	

	public String getEtiquetaNotaFinal() {
		return etiquetaNotaFinal;
	}

	public void setEtiquetaNotaFinal(String etiquetaNotaFinal) {
		this.etiquetaNotaFinal = etiquetaNotaFinal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<EvaluacionAsignatura> getInformesAnuales() {
		return informesAnuales;
	}

	public void setInformesAnuales(List<EvaluacionAsignatura> informesAnuales) {
		this.informesAnuales = informesAnuales;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public String getEscalaCualitativaFinal() {
		return escalaCualitativaFinal;
	}

	public void setEscalaCualitativaFinal(String escalaCualitativaFinal) {
		this.escalaCualitativaFinal = escalaCualitativaFinal;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public BigDecimal getNotaExamenDeGracia() {
		return notaExamenDeGracia;
	}

	public void setNotaExamenDeGracia(BigDecimal notaExamenDeGracia) {
		this.notaExamenDeGracia = notaExamenDeGracia;
	}

	public BigDecimal getNotaFinal() {
		return notaFinal;
	}

	public void setNotaFinal(BigDecimal notaFinal) {
		this.notaFinal = notaFinal;
	}

	public BigDecimal getQuimestre1Nota() {
		return quimestre1Nota;
	}

	public void setQuimestre1Nota(BigDecimal quimestre1Nota) {
		this.quimestre1Nota = quimestre1Nota;
	}

	public BigDecimal getQuimestre1Parcial1Nota() {
		return quimestre1Parcial1Nota;
	}

	public void setQuimestre1Parcial1Nota(BigDecimal quimestre1Parcial1Nota) {
		this.quimestre1Parcial1Nota = quimestre1Parcial1Nota;
	}

	public BigDecimal getQuimestre1Parcial2Nota() {
		return quimestre1Parcial2Nota;
	}

	public void setQuimestre1Parcial2Nota(BigDecimal quimestre1Parcial2Nota) {
		this.quimestre1Parcial2Nota = quimestre1Parcial2Nota;
	}

	public BigDecimal getQuimestre1Parcial3Nota() {
		return quimestre1Parcial3Nota;
	}

	public void setQuimestre1Parcial3Nota(BigDecimal quimestre1Parcial3Nota) {
		this.quimestre1Parcial3Nota = quimestre1Parcial3Nota;
	}

	public BigDecimal getQuimestre2Nota() {
		return quimestre2Nota;
	}

	public void setQuimestre2Nota(BigDecimal quimestre2Nota) {
		this.quimestre2Nota = quimestre2Nota;
	}

	public BigDecimal getQuimestre2Parcial1Nota() {
		return quimestre2Parcial1Nota;
	}

	public void setQuimestre2Parcial1Nota(BigDecimal quimestre2Parcial1Nota) {
		this.quimestre2Parcial1Nota = quimestre2Parcial1Nota;
	}

	public BigDecimal getQuimestre2Parcial2Nota() {
		return quimestre2Parcial2Nota;
	}

	public void setQuimestre2Parcial2Nota(BigDecimal quimestre2Parcial2Nota) {
		this.quimestre2Parcial2Nota = quimestre2Parcial2Nota;
	}

	public BigDecimal getQuimestre2Parcial3Nota() {
		return quimestre2Parcial3Nota;
	}

	public void setQuimestre2Parcial3Nota(BigDecimal quimestre2Parcial3Nota) {
		this.quimestre2Parcial3Nota = quimestre2Parcial3Nota;
	}

	public String getEvaluacionComportamientoB6() {
		return evaluacionComportamientoB6;
	}

	public void setEvaluacionComportamientoB6(String evaluacionComportamientoB6) {
		this.evaluacionComportamientoB6 = evaluacionComportamientoB6;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof InformeAnual)) {
			return false;
		}
		InformeAnual other = (InformeAnual) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gestionestudiantil.model.InformeAnual[ id=" + id + " ]";
	}
}