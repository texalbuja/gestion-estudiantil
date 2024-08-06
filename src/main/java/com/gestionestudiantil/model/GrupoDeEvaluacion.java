package com.gestionestudiantil.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "grupo_de_evaluacion")
public class GrupoDeEvaluacion implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String etiqueta;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "periodo_lectivo_id")
	private PeriodoLectivo periodoLectivo;

	@Column(name="habilitado")
	private Boolean habilitado;
	
	@Column(name="habilitado_estudiante")
	private Boolean habilitadoEstudiante;

	@Column
	private String codigo;
	
	@Column
	private Integer bloque;

	@Column
	private Integer diasAsistidos;

	@Column
	private String url;

	public GrupoDeEvaluacion(String codigo, String etiqueta,
			PeriodoLectivo periodoLectivo, String url, Boolean habilitado, Integer bloque) {
		this.codigo = codigo;
		this.etiqueta = etiqueta;
		this.periodoLectivo = periodoLectivo;
		this.habilitado = habilitado;
		this.url = url;
		this.bloque = bloque;
	}

	public GrupoDeEvaluacion() {
	}

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

	public PeriodoLectivo getPeriodoLectivo() {
		return periodoLectivo;
	}

	public void setPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		this.periodoLectivo = periodoLectivo;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public Boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Integer getDiasAsistidos() {
		return diasAsistidos;
	}

	public void setDiasAsistidos(Integer diasAsistidos) {
		this.diasAsistidos = diasAsistidos;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getBloque() {
		return bloque;
	}

	public void setBloque(Integer bloque) {
		this.bloque = bloque;
	}

	public Boolean getHabilitadoEstudiante() {
		return habilitadoEstudiante;
	}

	public void setHabilitadoEstudiante(Boolean habilitadoEstudiante) {
		this.habilitadoEstudiante = habilitadoEstudiante;
	}

}
