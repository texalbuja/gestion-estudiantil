package com.gestionestudiantil.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

@Entity
@Table(name = "evaluacion_asignatura_2016_julio")
public class EvaluacionAsignatura implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "asignatura_id", updatable = false, insertable = true, nullable = false)
	private Asignatura asignatura;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "informe_anual_id", updatable = false, insertable = true, nullable = false)
	private InformeAnual informeAnual;

	@Column(name = "nota_promedio_q1_q2")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal notaPromedioQuimestre1Quimestre2;
	@Column(name = "nota_final")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal notaFinal;
	@Column(name = "etiqueta_nota_final")
	private String etiquetaNotaFinal;
	@Column(name = "escala_cualitativa")
	private String escalaCualitativa;
	@Column(name = "resultado")
	private String resultado;

	@Column(name = "nota_examen_recuperacion")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal notaExamenRecuperacion;
	@Column(name = "nota_examen_supletorio")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal notaExamenSupletorio;
	@Column(name = "nota_examen_remedial")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal notaExamenRemedial;

	@Column(name = "nota_examen_gracia")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal notaExamenGracia;

	@Column(name = "q1_nota")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Nota; // 100%
	@Column(name = "q1_nota_promedio_parciales")
	@DecimalMax(value = "08.00", message = "Max 8")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1NotaPromedioParciales; // 80%
	@Column(name = "q1_nota_examen")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1NotaExamen;
	@Column(name = "q1_nota_promedio_examen")
	@DecimalMax(value = "02.00", message = "Max 2")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1NotaPromedioExamen; // 20%
	@Column(name = "q1_escala_cualitativa")
	private String quimestre1EscalaCualitativa;
	@Column(name = "q1_recomendaciones")
	private String quimestre1Recomendaciones;
	@Column(name = "q1_plan_mejora_academico")
	private String quimestre1PlanDeMejoraAcademico;

	@Column(name = "q2_nota")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Nota; // 100%
	@Column(name = "q2_nota_promedio_parciales")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2NotaPromedioParciales; // 80%
	@Column(name = "q2_nota_examen")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2NotaExamen; // 10%
	@Column(name = "q2_nota_promedio_examen")
	@DecimalMax(value = "02.00", message = "Max 2")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2NotaPromedioExamen; // 20%
	@Column(name = "q2_escala_cualitativa")
	private String quimestre2EscalaCualitativa;
	@Column(name = "q2_recomendaciones")
	private String quimestre2Recomendaciones;
	@Column(name = "q2_plan_mejora_academico")
	private String quimestre2PlanDeMejoraAcademico;

	@Column(name = "q1_p1_nota")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial1Nota;
	@Column(name = "q1_p1_escala_cualitativa")
	private String quimestre1Parcial1EscalaCualitativa;
	@Column(name = "q1_p1_recomendaciones")
	@Size(max = 255, message = "Longitud máxima 255")
	private String quimestre1Parcial1Recomendaciones;
	@Column(name = "q1_p1_plan_mejora_academico")
	@Size(max = 255, message = "Longitud máxima 255")
	private String quimestre1Parcial1PlanDeMejoraAcademico;

	@Column(name = "q1_p1_i1")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial1Insumo1;
	@Column(name = "q1_p1_i2")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial1Insumo2;
	@Column(name = "q1_p1_i3")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial1Insumo3;
	@Column(name = "q1_p1_i4")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial1Insumo4;

	@Column(name = "q1_p1_ra1")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial1RefuerzoAcademico1;
	@Column(name = "q1_p1_ra2")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial1RefuerzoAcademico2;
	@Column(name = "q1_p1_ra3")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial1RefuerzoAcademico3;
	@Column(name = "q1_p1_ra4")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial1RefuerzoAcademico4;

	@Column(name = "q1_p2_nota")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial2Nota;
	@Column(name = "q1_p2_escala_cualitativa")
	private String quimestre1Parcial2EscalaCualitativa;
	@Column(name = "q1_p2_recomendaciones")
	@Size(max = 255, message = "Longitud máxima 255")
	private String quimestre1Parcial2Recomendaciones;
	@Column(name = "q1_p2_plan_mejora_academico")
	@Size(max = 255, message = "Longitud máxima 255")
	private String quimestre1Parcial2PlanDeMejoraAcademico;

	@Column(name = "q1_p2_i1")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial2Insumo1;
	@Column(name = "q1_p2_i2")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial2Insumo2;
	@Column(name = "q1_p2_i3")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial2Insumo3;
	@Column(name = "q1_p2_i4")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial2Insumo4;

	@Column(name = "q1_p2_ra1")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial2RefuerzoAcademico1;
	@Column(name = "q1_p2_ra2")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial2RefuerzoAcademico2;
	@Column(name = "q1_p2_ra3")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial2RefuerzoAcademico3;
	@Column(name = "q1_p2_ra4")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial2RefuerzoAcademico4;

	@Column(name = "q1_p3_nota")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial3Nota;
	@Column(name = "q1_p3_escala_cualitativa")
	private String quimestre1Parcial3EscalaCualitativa;
	@Column(name = "q1_p3_recomendaciones")
	@Size(max = 255, message = "Longitud máxima 255")
	private String quimestre1Parcial3Recomendaciones;
	@Column(name = "q1_p3_plan_mejora_academico")
	@Size(max = 255, message = "Longitud máxima 255")
	private String quimestre1Parcial3PlanDeMejoraAcademico;

	@Column(name = "q1_p3_i1")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial3Insumo1;
	@Column(name = "q1_p3_i2")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial3Insumo2;
	@Column(name = "q1_p3_i3")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial3Insumo3;
	@Column(name = "q1_p3_i4")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial3Insumo4;

	@Column(name = "q1_p3_ra1")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial3RefuerzoAcademico1;
	@Column(name = "q1_p3_ra2")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial3RefuerzoAcademico2;
	@Column(name = "q1_p3_ra3")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial3RefuerzoAcademico3;
	@Column(name = "q1_p3_ra4")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre1Parcial3RefuerzoAcademico4;

	@Column(name = "q2_p1_nota")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial1Nota;
	@Column(name = "q2_p1_escala_cualitativa")
	private String quimestre2Parcial1EscalaCualitativa;
	@Column(name = "q2_p1_recomendaciones")
	@Size(max = 255, message = "Longitud máxima 255")
	private String quimestre2Parcial1Recomendaciones;
	@Column(name = "q2_p1_plan_mejora_academico")
	@Size(max = 255, message = "Longitud máxima 255")
	private String quimestre2Parcial1PlanDeMejoraAcademico;

	@Column(name = "q2_p1_i1")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial1Insumo1;
	@Column(name = "q2_p1_i2")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial1Insumo2;
	@Column(name = "q2_p1_i3")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial1Insumo3;
	@Column(name = "q2_p1_i4")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial1Insumo4;

	@Column(name = "q2_p1_ra1")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial1RefuerzoAcademico1;
	@Column(name = "q2_p1_ra2")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial1RefuerzoAcademico2;
	@Column(name = "q2_p1_ra3")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial1RefuerzoAcademico3;
	@Column(name = "q2_p1_ra4")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial1RefuerzoAcademico4;

	@Column(name = "q2_p2_nota")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial2Nota;
	@Column(name = "q2_p2_escala_cualitativa")
	private String quimestre2Parcial2EscalaCualitativa;
	@Column(name = "q2_p2_recomendaciones")
	@Size(max = 255, message = "Longitud máxima 255")
	private String quimestre2Parcial2Recomendaciones;
	@Column(name = "q2_p2_plan_mejora_academico")
	@Size(max = 255, message = "Longitud máxima 255")
	private String quimestre2Parcial2PlanDeMejoraAcademico;

	@Column(name = "q2_p2_i1")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial2Insumo1;
	@Column(name = "q2_p2_i2")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial2Insumo2;
	@Column(name = "q2_p2_i3")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial2Insumo3;
	@Column(name = "q2_p2_i4")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial2Insumo4;

	@Column(name = "q2_p2_ra1")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial2RefuerzoAcademico1;
	@Column(name = "q2_p2_ra2")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial2RefuerzoAcademico2;
	@Column(name = "q2_p2_ra3")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial2RefuerzoAcademico3;
	@Column(name = "q2_p2_ra4")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial2RefuerzoAcademico4;

	@Column(name = "q2_p3_nota")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial3Nota;
	@Column(name = "q2_p3_escala_cualitativa")
	private String quimestre2Parcial3EscalaCualitativa;
	@Column(name = "q2_p3_recomendaciones")
	@Size(max = 255, message = "Longitud máxima 255")
	private String quimestre2Parcial3Recomendaciones;
	@Column(name = "q2_p3_plan_mejora_academico")
	@Size(max = 255, message = "Longitud máxima 255")
	private String quimestre2Parcial3PlanDeMejoraAcademico;

	@Column(name = "q2_p3_i1")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial3Insumo1;
	@Column(name = "q2_p3_i2")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial3Insumo2;
	@Column(name = "q2_p3_i3")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial3Insumo3;
	@Column(name = "q2_p3_i4")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial3Insumo4;

	@Column(name = "q2_p3_ra1")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial3RefuerzoAcademico1;
	@Column(name = "q2_p3_ra2")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial3RefuerzoAcademico2;
	@Column(name = "q2_p3_ra3")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial3RefuerzoAcademico3;
	@Column(name = "q2_p3_ra4")
	@DecimalMax(value = "10.00", message = "Max 10")
	@DecimalMin(value = "00.00", message = "Min 0")
	private BigDecimal quimestre2Parcial3RefuerzoAcademico4;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	public InformeAnual getInformeAnual() {
		return informeAnual;
	}

	public void setInformeAnual(InformeAnual informeAnual) {
		this.informeAnual = informeAnual;
	}

	public BigDecimal getNotaPromedioQuimestre1Quimestre2() {
		return notaPromedioQuimestre1Quimestre2;
	}

	public void setNotaPromedioQuimestre1Quimestre2(BigDecimal notaPromedioQuimestre1Quimestre2) {
		this.notaPromedioQuimestre1Quimestre2 = notaPromedioQuimestre1Quimestre2;
	}

	public BigDecimal getNotaFinal() {
		return notaFinal;
	}

	public void setNotaFinal(BigDecimal notaFinal) {
		this.notaFinal = notaFinal;
	}

	public String getEtiquetaNotaFinal() {
		return etiquetaNotaFinal;
	}

	public void setEtiquetaNotaFinal(String etiquetaNotaFinal) {
		this.etiquetaNotaFinal = etiquetaNotaFinal;
	}

	public String getEscalaCualitativa() {
		return escalaCualitativa;
	}

	public void setEscalaCualitativa(String escalaCualitativa) {
		this.escalaCualitativa = escalaCualitativa;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public BigDecimal getNotaExamenRecuperacion() {
		return notaExamenRecuperacion;
	}

	public void setNotaExamenRecuperacion(BigDecimal notaExamenRecuperacion) {
		this.notaExamenRecuperacion = notaExamenRecuperacion;
	}

	public BigDecimal getNotaExamenSupletorio() {
		return notaExamenSupletorio;
	}

	public void setNotaExamenSupletorio(BigDecimal notaExamenSupletorio) {
		this.notaExamenSupletorio = notaExamenSupletorio;
	}

	public BigDecimal getNotaExamenRemedial() {
		return notaExamenRemedial;
	}

	public void setNotaExamenRemedial(BigDecimal notaExamenRemedial) {
		this.notaExamenRemedial = notaExamenRemedial;
	}

	public BigDecimal getNotaExamenGracia() {
		return notaExamenGracia;
	}

	public void setNotaExamenGracia(BigDecimal notaExamenGracia) {
		this.notaExamenGracia = notaExamenGracia;
	}

	public BigDecimal getQuimestre1Nota() {
		return quimestre1Nota;
	}

	public void setQuimestre1Nota(BigDecimal quimestre1Nota) {
		this.quimestre1Nota = quimestre1Nota;
	}

	public BigDecimal getQuimestre1NotaPromedioParciales() {
		return quimestre1NotaPromedioParciales;
	}

	public void setQuimestre1NotaPromedioParciales(BigDecimal quimestre1NotaPromedioParciales) {
		this.quimestre1NotaPromedioParciales = quimestre1NotaPromedioParciales;
	}

	public BigDecimal getQuimestre1NotaExamen() {
		return quimestre1NotaExamen;
	}

	public void setQuimestre1NotaExamen(BigDecimal quimestre1NotaExamen) {
		this.quimestre1NotaExamen = quimestre1NotaExamen;
	}

	public BigDecimal getQuimestre1NotaPromedioExamen() {
		return quimestre1NotaPromedioExamen;
	}

	public void setQuimestre1NotaPromedioExamen(BigDecimal quimestre1NotaPromedioExamen) {
		this.quimestre1NotaPromedioExamen = quimestre1NotaPromedioExamen;
	}

	public String getQuimestre1EscalaCualitativa() {
		return quimestre1EscalaCualitativa;
	}

	public void setQuimestre1EscalaCualitativa(String quimestre1EscalaCualitativa) {
		this.quimestre1EscalaCualitativa = quimestre1EscalaCualitativa;
	}

	public String getQuimestre1Recomendaciones() {
		return quimestre1Recomendaciones;
	}

	public void setQuimestre1Recomendaciones(String quimestre1Recomendaciones) {
		this.quimestre1Recomendaciones = quimestre1Recomendaciones;
	}

	public String getQuimestre1PlanDeMejoraAcademico() {
		return quimestre1PlanDeMejoraAcademico;
	}

	public void setQuimestre1PlanDeMejoraAcademico(String quimestre1PlanDeMejoraAcademico) {
		this.quimestre1PlanDeMejoraAcademico = quimestre1PlanDeMejoraAcademico;
	}

	public BigDecimal getQuimestre2Nota() {
		return quimestre2Nota;
	}

	public void setQuimestre2Nota(BigDecimal quimestre2Nota) {
		this.quimestre2Nota = quimestre2Nota;
	}

	public BigDecimal getQuimestre2NotaPromedioParciales() {
		return quimestre2NotaPromedioParciales;
	}

	public void setQuimestre2NotaPromedioParciales(BigDecimal quimestre2NotaPromedioParciales) {
		this.quimestre2NotaPromedioParciales = quimestre2NotaPromedioParciales;
	}

	public BigDecimal getQuimestre2NotaExamen() {
		return quimestre2NotaExamen;
	}

	public void setQuimestre2NotaExamen(BigDecimal quimestre2NotaExamen) {
		this.quimestre2NotaExamen = quimestre2NotaExamen;
	}

	public BigDecimal getQuimestre2NotaPromedioExamen() {
		return quimestre2NotaPromedioExamen;
	}

	public void setQuimestre2NotaPromedioExamen(BigDecimal quimestre2NotaPromedioExamen) {
		this.quimestre2NotaPromedioExamen = quimestre2NotaPromedioExamen;
	}

	public String getQuimestre2EscalaCualitativa() {
		return quimestre2EscalaCualitativa;
	}

	public void setQuimestre2EscalaCualitativa(String quimestre2EscalaCualitativa) {
		this.quimestre2EscalaCualitativa = quimestre2EscalaCualitativa;
	}

	public String getQuimestre2Recomendaciones() {
		return quimestre2Recomendaciones;
	}

	public void setQuimestre2Recomendaciones(String quimestre2Recomendaciones) {
		this.quimestre2Recomendaciones = quimestre2Recomendaciones;
	}

	public String getQuimestre2PlanDeMejoraAcademico() {
		return quimestre2PlanDeMejoraAcademico;
	}

	public void setQuimestre2PlanDeMejoraAcademico(String quimestre2PlanDeMejoraAcademico) {
		this.quimestre2PlanDeMejoraAcademico = quimestre2PlanDeMejoraAcademico;
	}

	public BigDecimal getQuimestre1Parcial1Nota() {
		return quimestre1Parcial1Nota;
	}

	public void setQuimestre1Parcial1Nota(BigDecimal quimestre1Parcial1Nota) {
		this.quimestre1Parcial1Nota = quimestre1Parcial1Nota;
	}

	public String getQuimestre1Parcial1EscalaCualitativa() {
		return quimestre1Parcial1EscalaCualitativa;
	}

	public void setQuimestre1Parcial1EscalaCualitativa(String quimestre1Parcial1EscalaCualitativa) {
		this.quimestre1Parcial1EscalaCualitativa = quimestre1Parcial1EscalaCualitativa;
	}

	public String getQuimestre1Parcial1Recomendaciones() {
		return quimestre1Parcial1Recomendaciones;
	}

	public void setQuimestre1Parcial1Recomendaciones(String quimestre1Parcial1Recomendaciones) {
		this.quimestre1Parcial1Recomendaciones = quimestre1Parcial1Recomendaciones;
	}

	public String getQuimestre1Parcial1PlanDeMejoraAcademico() {
		return quimestre1Parcial1PlanDeMejoraAcademico;
	}

	public void setQuimestre1Parcial1PlanDeMejoraAcademico(String quimestre1Parcial1PlanDeMejoraAcademico) {
		this.quimestre1Parcial1PlanDeMejoraAcademico = quimestre1Parcial1PlanDeMejoraAcademico;
	}

	public BigDecimal getQuimestre1Parcial1Insumo1() {
		return quimestre1Parcial1Insumo1;
	}

	public void setQuimestre1Parcial1Insumo1(BigDecimal quimestre1Parcial1Insumo1) {
		this.quimestre1Parcial1Insumo1 = quimestre1Parcial1Insumo1;
	}

	public BigDecimal getQuimestre1Parcial1Insumo2() {
		return quimestre1Parcial1Insumo2;
	}

	public void setQuimestre1Parcial1Insumo2(BigDecimal quimestre1Parcial1Insumo2) {
		this.quimestre1Parcial1Insumo2 = quimestre1Parcial1Insumo2;
	}

	public BigDecimal getQuimestre1Parcial1Insumo3() {
		return quimestre1Parcial1Insumo3;
	}

	public void setQuimestre1Parcial1Insumo3(BigDecimal quimestre1Parcial1Insumo3) {
		this.quimestre1Parcial1Insumo3 = quimestre1Parcial1Insumo3;
	}

	public BigDecimal getQuimestre1Parcial1Insumo4() {
		return quimestre1Parcial1Insumo4;
	}

	public void setQuimestre1Parcial1Insumo4(BigDecimal quimestre1Parcial1Insumo4) {
		this.quimestre1Parcial1Insumo4 = quimestre1Parcial1Insumo4;
	}

	public BigDecimal getQuimestre1Parcial1RefuerzoAcademico1() {
		return quimestre1Parcial1RefuerzoAcademico1;
	}

	public void setQuimestre1Parcial1RefuerzoAcademico1(BigDecimal quimestre1Parcial1RefuerzoAcademico1) {
		this.quimestre1Parcial1RefuerzoAcademico1 = quimestre1Parcial1RefuerzoAcademico1;
	}

	public BigDecimal getQuimestre1Parcial1RefuerzoAcademico2() {
		return quimestre1Parcial1RefuerzoAcademico2;
	}

	public void setQuimestre1Parcial1RefuerzoAcademico2(BigDecimal quimestre1Parcial1RefuerzoAcademico2) {
		this.quimestre1Parcial1RefuerzoAcademico2 = quimestre1Parcial1RefuerzoAcademico2;
	}

	public BigDecimal getQuimestre1Parcial1RefuerzoAcademico3() {
		return quimestre1Parcial1RefuerzoAcademico3;
	}

	public void setQuimestre1Parcial1RefuerzoAcademico3(BigDecimal quimestre1Parcial1RefuerzoAcademico3) {
		this.quimestre1Parcial1RefuerzoAcademico3 = quimestre1Parcial1RefuerzoAcademico3;
	}

	public BigDecimal getQuimestre1Parcial1RefuerzoAcademico4() {
		return quimestre1Parcial1RefuerzoAcademico4;
	}

	public void setQuimestre1Parcial1RefuerzoAcademico4(BigDecimal quimestre1Parcial1RefuerzoAcademico4) {
		this.quimestre1Parcial1RefuerzoAcademico4 = quimestre1Parcial1RefuerzoAcademico4;
	}

	public BigDecimal getQuimestre1Parcial2Nota() {
		return quimestre1Parcial2Nota;
	}

	public void setQuimestre1Parcial2Nota(BigDecimal quimestre1Parcial2Nota) {
		this.quimestre1Parcial2Nota = quimestre1Parcial2Nota;
	}

	public String getQuimestre1Parcial2EscalaCualitativa() {
		return quimestre1Parcial2EscalaCualitativa;
	}

	public void setQuimestre1Parcial2EscalaCualitativa(String quimestre1Parcial2EscalaCualitativa) {
		this.quimestre1Parcial2EscalaCualitativa = quimestre1Parcial2EscalaCualitativa;
	}

	public String getQuimestre1Parcial2Recomendaciones() {
		return quimestre1Parcial2Recomendaciones;
	}

	public void setQuimestre1Parcial2Recomendaciones(String quimestre1Parcial2Recomendaciones) {
		this.quimestre1Parcial2Recomendaciones = quimestre1Parcial2Recomendaciones;
	}

	public String getQuimestre1Parcial2PlanDeMejoraAcademico() {
		return quimestre1Parcial2PlanDeMejoraAcademico;
	}

	public void setQuimestre1Parcial2PlanDeMejoraAcademico(String quimestre1Parcial2PlanDeMejoraAcademico) {
		this.quimestre1Parcial2PlanDeMejoraAcademico = quimestre1Parcial2PlanDeMejoraAcademico;
	}

	public BigDecimal getQuimestre1Parcial2Insumo1() {
		return quimestre1Parcial2Insumo1;
	}

	public void setQuimestre1Parcial2Insumo1(BigDecimal quimestre1Parcial2Insumo1) {
		this.quimestre1Parcial2Insumo1 = quimestre1Parcial2Insumo1;
	}

	public BigDecimal getQuimestre1Parcial2Insumo2() {
		return quimestre1Parcial2Insumo2;
	}

	public void setQuimestre1Parcial2Insumo2(BigDecimal quimestre1Parcial2Insumo2) {
		this.quimestre1Parcial2Insumo2 = quimestre1Parcial2Insumo2;
	}

	public BigDecimal getQuimestre1Parcial2Insumo3() {
		return quimestre1Parcial2Insumo3;
	}

	public void setQuimestre1Parcial2Insumo3(BigDecimal quimestre1Parcial2Insumo3) {
		this.quimestre1Parcial2Insumo3 = quimestre1Parcial2Insumo3;
	}

	public BigDecimal getQuimestre1Parcial2Insumo4() {
		return quimestre1Parcial2Insumo4;
	}

	public void setQuimestre1Parcial2Insumo4(BigDecimal quimestre1Parcial2Insumo4) {
		this.quimestre1Parcial2Insumo4 = quimestre1Parcial2Insumo4;
	}

	public BigDecimal getQuimestre1Parcial2RefuerzoAcademico1() {
		return quimestre1Parcial2RefuerzoAcademico1;
	}

	public void setQuimestre1Parcial2RefuerzoAcademico1(BigDecimal quimestre1Parcial2RefuerzoAcademico1) {
		this.quimestre1Parcial2RefuerzoAcademico1 = quimestre1Parcial2RefuerzoAcademico1;
	}

	public BigDecimal getQuimestre1Parcial2RefuerzoAcademico2() {
		return quimestre1Parcial2RefuerzoAcademico2;
	}

	public void setQuimestre1Parcial2RefuerzoAcademico2(BigDecimal quimestre1Parcial2RefuerzoAcademico2) {
		this.quimestre1Parcial2RefuerzoAcademico2 = quimestre1Parcial2RefuerzoAcademico2;
	}

	public BigDecimal getQuimestre1Parcial2RefuerzoAcademico3() {
		return quimestre1Parcial2RefuerzoAcademico3;
	}

	public void setQuimestre1Parcial2RefuerzoAcademico3(BigDecimal quimestre1Parcial2RefuerzoAcademico3) {
		this.quimestre1Parcial2RefuerzoAcademico3 = quimestre1Parcial2RefuerzoAcademico3;
	}

	public BigDecimal getQuimestre1Parcial2RefuerzoAcademico4() {
		return quimestre1Parcial2RefuerzoAcademico4;
	}

	public void setQuimestre1Parcial2RefuerzoAcademico4(BigDecimal quimestre1Parcial2RefuerzoAcademico4) {
		this.quimestre1Parcial2RefuerzoAcademico4 = quimestre1Parcial2RefuerzoAcademico4;
	}

	public BigDecimal getQuimestre1Parcial3Nota() {
		return quimestre1Parcial3Nota;
	}

	public void setQuimestre1Parcial3Nota(BigDecimal quimestre1Parcial3Nota) {
		this.quimestre1Parcial3Nota = quimestre1Parcial3Nota;
	}

	public String getQuimestre1Parcial3EscalaCualitativa() {
		return quimestre1Parcial3EscalaCualitativa;
	}

	public void setQuimestre1Parcial3EscalaCualitativa(String quimestre1Parcial3EscalaCualitativa) {
		this.quimestre1Parcial3EscalaCualitativa = quimestre1Parcial3EscalaCualitativa;
	}

	public String getQuimestre1Parcial3Recomendaciones() {
		return quimestre1Parcial3Recomendaciones;
	}

	public void setQuimestre1Parcial3Recomendaciones(String quimestre1Parcial3Recomendaciones) {
		this.quimestre1Parcial3Recomendaciones = quimestre1Parcial3Recomendaciones;
	}

	public String getQuimestre1Parcial3PlanDeMejoraAcademico() {
		return quimestre1Parcial3PlanDeMejoraAcademico;
	}

	public void setQuimestre1Parcial3PlanDeMejoraAcademico(String quimestre1Parcial3PlanDeMejoraAcademico) {
		this.quimestre1Parcial3PlanDeMejoraAcademico = quimestre1Parcial3PlanDeMejoraAcademico;
	}

	public BigDecimal getQuimestre1Parcial3Insumo1() {
		return quimestre1Parcial3Insumo1;
	}

	public void setQuimestre1Parcial3Insumo1(BigDecimal quimestre1Parcial3Insumo1) {
		this.quimestre1Parcial3Insumo1 = quimestre1Parcial3Insumo1;
	}

	public BigDecimal getQuimestre1Parcial3Insumo2() {
		return quimestre1Parcial3Insumo2;
	}

	public void setQuimestre1Parcial3Insumo2(BigDecimal quimestre1Parcial3Insumo2) {
		this.quimestre1Parcial3Insumo2 = quimestre1Parcial3Insumo2;
	}

	public BigDecimal getQuimestre1Parcial3Insumo3() {
		return quimestre1Parcial3Insumo3;
	}

	public void setQuimestre1Parcial3Insumo3(BigDecimal quimestre1Parcial3Insumo3) {
		this.quimestre1Parcial3Insumo3 = quimestre1Parcial3Insumo3;
	}

	public BigDecimal getQuimestre1Parcial3Insumo4() {
		return quimestre1Parcial3Insumo4;
	}

	public void setQuimestre1Parcial3Insumo4(BigDecimal quimestre1Parcial3Insumo4) {
		this.quimestre1Parcial3Insumo4 = quimestre1Parcial3Insumo4;
	}

	public BigDecimal getQuimestre1Parcial3RefuerzoAcademico1() {
		return quimestre1Parcial3RefuerzoAcademico1;
	}

	public void setQuimestre1Parcial3RefuerzoAcademico1(BigDecimal quimestre1Parcial3RefuerzoAcademico1) {
		this.quimestre1Parcial3RefuerzoAcademico1 = quimestre1Parcial3RefuerzoAcademico1;
	}

	public BigDecimal getQuimestre1Parcial3RefuerzoAcademico2() {
		return quimestre1Parcial3RefuerzoAcademico2;
	}

	public void setQuimestre1Parcial3RefuerzoAcademico2(BigDecimal quimestre1Parcial3RefuerzoAcademico2) {
		this.quimestre1Parcial3RefuerzoAcademico2 = quimestre1Parcial3RefuerzoAcademico2;
	}

	public BigDecimal getQuimestre1Parcial3RefuerzoAcademico3() {
		return quimestre1Parcial3RefuerzoAcademico3;
	}

	public void setQuimestre1Parcial3RefuerzoAcademico3(BigDecimal quimestre1Parcial3RefuerzoAcademico3) {
		this.quimestre1Parcial3RefuerzoAcademico3 = quimestre1Parcial3RefuerzoAcademico3;
	}

	public BigDecimal getQuimestre1Parcial3RefuerzoAcademico4() {
		return quimestre1Parcial3RefuerzoAcademico4;
	}

	public void setQuimestre1Parcial3RefuerzoAcademico4(BigDecimal quimestre1Parcial3RefuerzoAcademico4) {
		this.quimestre1Parcial3RefuerzoAcademico4 = quimestre1Parcial3RefuerzoAcademico4;
	}

	public BigDecimal getQuimestre2Parcial1Nota() {
		return quimestre2Parcial1Nota;
	}

	public void setQuimestre2Parcial1Nota(BigDecimal quimestre2Parcial1Nota) {
		this.quimestre2Parcial1Nota = quimestre2Parcial1Nota;
	}

	public String getQuimestre2Parcial1EscalaCualitativa() {
		return quimestre2Parcial1EscalaCualitativa;
	}

	public void setQuimestre2Parcial1EscalaCualitativa(String quimestre2Parcial1EscalaCualitativa) {
		this.quimestre2Parcial1EscalaCualitativa = quimestre2Parcial1EscalaCualitativa;
	}

	public String getQuimestre2Parcial1Recomendaciones() {
		return quimestre2Parcial1Recomendaciones;
	}

	public void setQuimestre2Parcial1Recomendaciones(String quimestre2Parcial1Recomendaciones) {
		this.quimestre2Parcial1Recomendaciones = quimestre2Parcial1Recomendaciones;
	}

	public String getQuimestre2Parcial1PlanDeMejoraAcademico() {
		return quimestre2Parcial1PlanDeMejoraAcademico;
	}

	public void setQuimestre2Parcial1PlanDeMejoraAcademico(String quimestre2Parcial1PlanDeMejoraAcademico) {
		this.quimestre2Parcial1PlanDeMejoraAcademico = quimestre2Parcial1PlanDeMejoraAcademico;
	}

	public BigDecimal getQuimestre2Parcial1Insumo1() {
		return quimestre2Parcial1Insumo1;
	}

	public void setQuimestre2Parcial1Insumo1(BigDecimal quimestre2Parcial1Insumo1) {
		this.quimestre2Parcial1Insumo1 = quimestre2Parcial1Insumo1;
	}

	public BigDecimal getQuimestre2Parcial1Insumo2() {
		return quimestre2Parcial1Insumo2;
	}

	public void setQuimestre2Parcial1Insumo2(BigDecimal quimestre2Parcial1Insumo2) {
		this.quimestre2Parcial1Insumo2 = quimestre2Parcial1Insumo2;
	}

	public BigDecimal getQuimestre2Parcial1Insumo3() {
		return quimestre2Parcial1Insumo3;
	}

	public void setQuimestre2Parcial1Insumo3(BigDecimal quimestre2Parcial1Insumo3) {
		this.quimestre2Parcial1Insumo3 = quimestre2Parcial1Insumo3;
	}

	public BigDecimal getQuimestre2Parcial1Insumo4() {
		return quimestre2Parcial1Insumo4;
	}

	public void setQuimestre2Parcial1Insumo4(BigDecimal quimestre2Parcial1Insumo4) {
		this.quimestre2Parcial1Insumo4 = quimestre2Parcial1Insumo4;
	}

	public BigDecimal getQuimestre2Parcial1RefuerzoAcademico1() {
		return quimestre2Parcial1RefuerzoAcademico1;
	}

	public void setQuimestre2Parcial1RefuerzoAcademico1(BigDecimal quimestre2Parcial1RefuerzoAcademico1) {
		this.quimestre2Parcial1RefuerzoAcademico1 = quimestre2Parcial1RefuerzoAcademico1;
	}

	public BigDecimal getQuimestre2Parcial1RefuerzoAcademico2() {
		return quimestre2Parcial1RefuerzoAcademico2;
	}

	public void setQuimestre2Parcial1RefuerzoAcademico2(BigDecimal quimestre2Parcial1RefuerzoAcademico2) {
		this.quimestre2Parcial1RefuerzoAcademico2 = quimestre2Parcial1RefuerzoAcademico2;
	}

	public BigDecimal getQuimestre2Parcial1RefuerzoAcademico3() {
		return quimestre2Parcial1RefuerzoAcademico3;
	}

	public void setQuimestre2Parcial1RefuerzoAcademico3(BigDecimal quimestre2Parcial1RefuerzoAcademico3) {
		this.quimestre2Parcial1RefuerzoAcademico3 = quimestre2Parcial1RefuerzoAcademico3;
	}

	public BigDecimal getQuimestre2Parcial1RefuerzoAcademico4() {
		return quimestre2Parcial1RefuerzoAcademico4;
	}

	public void setQuimestre2Parcial1RefuerzoAcademico4(BigDecimal quimestre2Parcial1RefuerzoAcademico4) {
		this.quimestre2Parcial1RefuerzoAcademico4 = quimestre2Parcial1RefuerzoAcademico4;
	}

	public BigDecimal getQuimestre2Parcial2Nota() {
		return quimestre2Parcial2Nota;
	}

	public void setQuimestre2Parcial2Nota(BigDecimal quimestre2Parcial2Nota) {
		this.quimestre2Parcial2Nota = quimestre2Parcial2Nota;
	}

	public String getQuimestre2Parcial2EscalaCualitativa() {
		return quimestre2Parcial2EscalaCualitativa;
	}

	public void setQuimestre2Parcial2EscalaCualitativa(String quimestre2Parcial2EscalaCualitativa) {
		this.quimestre2Parcial2EscalaCualitativa = quimestre2Parcial2EscalaCualitativa;
	}

	public String getQuimestre2Parcial2Recomendaciones() {
		return quimestre2Parcial2Recomendaciones;
	}

	public void setQuimestre2Parcial2Recomendaciones(String quimestre2Parcial2Recomendaciones) {
		this.quimestre2Parcial2Recomendaciones = quimestre2Parcial2Recomendaciones;
	}

	public String getQuimestre2Parcial2PlanDeMejoraAcademico() {
		return quimestre2Parcial2PlanDeMejoraAcademico;
	}

	public void setQuimestre2Parcial2PlanDeMejoraAcademico(String quimestre2Parcial2PlanDeMejoraAcademico) {
		this.quimestre2Parcial2PlanDeMejoraAcademico = quimestre2Parcial2PlanDeMejoraAcademico;
	}

	public BigDecimal getQuimestre2Parcial2Insumo1() {
		return quimestre2Parcial2Insumo1;
	}

	public void setQuimestre2Parcial2Insumo1(BigDecimal quimestre2Parcial2Insumo1) {
		this.quimestre2Parcial2Insumo1 = quimestre2Parcial2Insumo1;
	}

	public BigDecimal getQuimestre2Parcial2Insumo2() {
		return quimestre2Parcial2Insumo2;
	}

	public void setQuimestre2Parcial2Insumo2(BigDecimal quimestre2Parcial2Insumo2) {
		this.quimestre2Parcial2Insumo2 = quimestre2Parcial2Insumo2;
	}

	public BigDecimal getQuimestre2Parcial2Insumo3() {
		return quimestre2Parcial2Insumo3;
	}

	public void setQuimestre2Parcial2Insumo3(BigDecimal quimestre2Parcial2Insumo3) {
		this.quimestre2Parcial2Insumo3 = quimestre2Parcial2Insumo3;
	}

	public BigDecimal getQuimestre2Parcial2Insumo4() {
		return quimestre2Parcial2Insumo4;
	}

	public void setQuimestre2Parcial2Insumo4(BigDecimal quimestre2Parcial2Insumo4) {
		this.quimestre2Parcial2Insumo4 = quimestre2Parcial2Insumo4;
	}

	public BigDecimal getQuimestre2Parcial2RefuerzoAcademico1() {
		return quimestre2Parcial2RefuerzoAcademico1;
	}

	public void setQuimestre2Parcial2RefuerzoAcademico1(BigDecimal quimestre2Parcial2RefuerzoAcademico1) {
		this.quimestre2Parcial2RefuerzoAcademico1 = quimestre2Parcial2RefuerzoAcademico1;
	}

	public BigDecimal getQuimestre2Parcial2RefuerzoAcademico2() {
		return quimestre2Parcial2RefuerzoAcademico2;
	}

	public void setQuimestre2Parcial2RefuerzoAcademico2(BigDecimal quimestre2Parcial2RefuerzoAcademico2) {
		this.quimestre2Parcial2RefuerzoAcademico2 = quimestre2Parcial2RefuerzoAcademico2;
	}

	public BigDecimal getQuimestre2Parcial2RefuerzoAcademico3() {
		return quimestre2Parcial2RefuerzoAcademico3;
	}

	public void setQuimestre2Parcial2RefuerzoAcademico3(BigDecimal quimestre2Parcial2RefuerzoAcademico3) {
		this.quimestre2Parcial2RefuerzoAcademico3 = quimestre2Parcial2RefuerzoAcademico3;
	}

	public BigDecimal getQuimestre2Parcial2RefuerzoAcademico4() {
		return quimestre2Parcial2RefuerzoAcademico4;
	}

	public void setQuimestre2Parcial2RefuerzoAcademico4(BigDecimal quimestre2Parcial2RefuerzoAcademico4) {
		this.quimestre2Parcial2RefuerzoAcademico4 = quimestre2Parcial2RefuerzoAcademico4;
	}

	public BigDecimal getQuimestre2Parcial3Nota() {
		return quimestre2Parcial3Nota;
	}

	public void setQuimestre2Parcial3Nota(BigDecimal quimestre2Parcial3Nota) {
		this.quimestre2Parcial3Nota = quimestre2Parcial3Nota;
	}

	public String getQuimestre2Parcial3EscalaCualitativa() {
		return quimestre2Parcial3EscalaCualitativa;
	}

	public void setQuimestre2Parcial3EscalaCualitativa(String quimestre2Parcial3EscalaCualitativa) {
		this.quimestre2Parcial3EscalaCualitativa = quimestre2Parcial3EscalaCualitativa;
	}

	public String getQuimestre2Parcial3Recomendaciones() {
		return quimestre2Parcial3Recomendaciones;
	}

	public void setQuimestre2Parcial3Recomendaciones(String quimestre2Parcial3Recomendaciones) {
		this.quimestre2Parcial3Recomendaciones = quimestre2Parcial3Recomendaciones;
	}

	public String getQuimestre2Parcial3PlanDeMejoraAcademico() {
		return quimestre2Parcial3PlanDeMejoraAcademico;
	}

	public void setQuimestre2Parcial3PlanDeMejoraAcademico(String quimestre2Parcial3PlanDeMejoraAcademico) {
		this.quimestre2Parcial3PlanDeMejoraAcademico = quimestre2Parcial3PlanDeMejoraAcademico;
	}

	public BigDecimal getQuimestre2Parcial3Insumo1() {
		return quimestre2Parcial3Insumo1;
	}

	public void setQuimestre2Parcial3Insumo1(BigDecimal quimestre2Parcial3Insumo1) {
		this.quimestre2Parcial3Insumo1 = quimestre2Parcial3Insumo1;
	}

	public BigDecimal getQuimestre2Parcial3Insumo2() {
		return quimestre2Parcial3Insumo2;
	}

	public void setQuimestre2Parcial3Insumo2(BigDecimal quimestre2Parcial3Insumo2) {
		this.quimestre2Parcial3Insumo2 = quimestre2Parcial3Insumo2;
	}

	public BigDecimal getQuimestre2Parcial3Insumo3() {
		return quimestre2Parcial3Insumo3;
	}

	public void setQuimestre2Parcial3Insumo3(BigDecimal quimestre2Parcial3Insumo3) {
		this.quimestre2Parcial3Insumo3 = quimestre2Parcial3Insumo3;
	}

	public BigDecimal getQuimestre2Parcial3Insumo4() {
		return quimestre2Parcial3Insumo4;
	}

	public void setQuimestre2Parcial3Insumo4(BigDecimal quimestre2Parcial3Insumo4) {
		this.quimestre2Parcial3Insumo4 = quimestre2Parcial3Insumo4;
	}

	public BigDecimal getQuimestre2Parcial3RefuerzoAcademico1() {
		return quimestre2Parcial3RefuerzoAcademico1;
	}

	public void setQuimestre2Parcial3RefuerzoAcademico1(BigDecimal quimestre2Parcial3RefuerzoAcademico1) {
		this.quimestre2Parcial3RefuerzoAcademico1 = quimestre2Parcial3RefuerzoAcademico1;
	}

	public BigDecimal getQuimestre2Parcial3RefuerzoAcademico2() {
		return quimestre2Parcial3RefuerzoAcademico2;
	}

	public void setQuimestre2Parcial3RefuerzoAcademico2(BigDecimal quimestre2Parcial3RefuerzoAcademico2) {
		this.quimestre2Parcial3RefuerzoAcademico2 = quimestre2Parcial3RefuerzoAcademico2;
	}

	public BigDecimal getQuimestre2Parcial3RefuerzoAcademico3() {
		return quimestre2Parcial3RefuerzoAcademico3;
	}

	public void setQuimestre2Parcial3RefuerzoAcademico3(BigDecimal quimestre2Parcial3RefuerzoAcademico3) {
		this.quimestre2Parcial3RefuerzoAcademico3 = quimestre2Parcial3RefuerzoAcademico3;
	}

	public BigDecimal getQuimestre2Parcial3RefuerzoAcademico4() {
		return quimestre2Parcial3RefuerzoAcademico4;
	}

	public void setQuimestre2Parcial3RefuerzoAcademico4(BigDecimal quimestre2Parcial3RefuerzoAcademico4) {
		this.quimestre2Parcial3RefuerzoAcademico4 = quimestre2Parcial3RefuerzoAcademico4;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof EvaluacionAsignatura)) {
			return false;
		}
		EvaluacionAsignatura other = (EvaluacionAsignatura) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gestionestudiantil.model.EvaluacionAsignatura[ id=" + id + " ]";
	}
}