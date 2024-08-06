package com.gestionestudiantil.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.gestionestudiantil.enums.Grados;

@Entity
@Table(name = "grado")
public class Grado implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "nivel")
	private Integer nivel;
	
	@Column(name = "sub_nivel")
	private Integer subNivel;
	
	@Column(name = "grado")
	private Integer grado;
	
	@Column(name = "etiqueta")
	private String etiqueta;
	
	@Column(name = "etiqueta_sub_nivel")
	private String etiquetaSubNivel;
	
	@Column(name = "etiqueta_nivel")
	private String etiquetaNivel;
	
	@Column(name = "nombre_firmante_a")
	private String nombreFirmanteA;
	
	@Column(name = "nombre_firmante_b")
	private String nombreFirmanteB;
	
	@Column(name = "cargo_firmante_a")
	private String cargoFirmanteA;
	
	@Column(name = "cargo_firmante_b")
	private String cargoFirmanteB;
	
	@Column(name = "pronombre_a")
	private String pronombreA;
	
	@Column(name = "pronombre_b")
	private String pronombreB;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "periodo_lectivo_id")
	private PeriodoLectivo periodoLectivo;
	
	@OneToMany(mappedBy = "grado", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private List<Paralelo> paralelos;
	@Transient
	private Grados grados;

	public Grado(Grados grados) {
		this.nivel = grados.getNivel();
		this.subNivel = grados.getSubNivel();
		this.grado = grados.getGrado();
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public Grado() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public Integer getSubNivel() {
		return subNivel;
	}

	public void setSubNivel(Integer subNivel) {
		this.subNivel = subNivel;
	}

	public Integer getGrado() {
		return grado;
	}

	public void setGrado(Integer grado) {
		this.grado = grado;
	}

	public PeriodoLectivo getPeriodoLectivo() {
		return periodoLectivo;
	}

	public void setPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		this.periodoLectivo = periodoLectivo;
	}

	public List<Paralelo> getParalelos() {
		return paralelos;
	}

	public void setParalelos(List<Paralelo> paralelos) {
		this.paralelos = paralelos;
	}

	public Grados getGrados() {
		if (grados == null)
			grados = Grados.obtenerDesdeGrado(this);
		return grados;
	}

	public void setGrados(Grados grados) {
		this.grados = grados;
	}

	public String getEtiquetaSubNivel() {
		return etiquetaSubNivel;
	}

	public void setEtiquetaSubNivel(String etiquetaSubNivel) {
		this.etiquetaSubNivel = etiquetaSubNivel;
	}

	public String getEtiquetaNivel() {
		return etiquetaNivel;
	}

	public void setEtiquetaNivel(String etiquetaNivel) {
		this.etiquetaNivel = etiquetaNivel;
	}

	public String getNombreFirmanteA() {
		return nombreFirmanteA;
	}

	public void setNombreFirmanteA(String nombreFirmanteA) {
		this.nombreFirmanteA = nombreFirmanteA;
	}

	public String getNombreFirmanteB() {
		return nombreFirmanteB;
	}

	public void setNombreFirmanteB(String nombreFirmanteB) {
		this.nombreFirmanteB = nombreFirmanteB;
	}

	public String getCargoFirmanteA() {
		return cargoFirmanteA;
	}

	public void setCargoFirmanteA(String cargoFirmanteA) {
		this.cargoFirmanteA = cargoFirmanteA;
	}

	public String getCargoFirmanteB() {
		return cargoFirmanteB;
	}

	public void setCargoFirmanteB(String cargoFirmanteB) {
		this.cargoFirmanteB = cargoFirmanteB;
	}

	public String getPronombreA() {
		return pronombreA;
	}

	public void setPronombreA(String pronombreA) {
		this.pronombreA = pronombreA;
	}

	public String getPronombreB() {
		return pronombreB;
	}

	public void setPronombreB(String pronombreB) {
		this.pronombreB = pronombreB;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Grado)) {
			return false;
		}
		Grado other = (Grado) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gestionestudiantil.model.Grado[ id=" + id + " ]";
	}
}
