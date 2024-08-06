/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionestudiantil.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "institucion")
public class Institucion implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Size(max = 255)
	@Column(name = "nombre")
	private String nombre;
	@Size(max = 255)
	@Column(name = "regimen")
	private String regimen;
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "imagen_id", insertable = true, updatable = true, nullable = true)
	private Imagen imagen;
	@OneToMany(mappedBy = "institucion", fetch= FetchType.LAZY)
	private List<Usuario> usuarios;
	@OneToMany(mappedBy = "institucion")
	private List<PeriodoLectivo> periodosLectivos;

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "rector_id", insertable = true, updatable = true, nullable = true)
	private Usuario rector;

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "vicerrector_id", insertable = true, updatable = true, nullable = true)
	private Usuario vicerrector;

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "secretaria_secundaria_id", insertable = true, updatable = true, nullable = true)
	private Usuario secretariaSecundaria;

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "inspector_id", insertable = true, updatable = true, nullable = true)
	private Usuario inspector;
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "director_id", insertable = true, updatable = true, nullable = true)
	private Usuario director;
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "secretaria_primaria_id", insertable = true, updatable = true, nullable = true)
	private Usuario secretariaPrimaria;
	
	@Column(name = "direccion")
	private String direccion;
	
	@Column
	private Integer contador;

	public Institucion() {
	}

	public Institucion(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<PeriodoLectivo> getPeriodosLectivos() {
		return periodosLectivos;
	}

	public void setPeriodosLectivos(List<PeriodoLectivo> periodosLectivos) {
		this.periodosLectivos = periodosLectivos;
	}

	public String getRegimen() {
		return regimen;
	}

	public void setRegimen(String regimen) {
		this.regimen = regimen;
	}

	public Usuario getRector() {
		return rector;
	}

	public void setRector(Usuario rector) {
		this.rector = rector;
	}

	public Usuario getVicerrector() {
		return vicerrector;
	}

	public void setVicerrector(Usuario vicerrector) {
		this.vicerrector = vicerrector;
	}

	public Usuario getInspector() {
		return inspector;
	}

	public void setInspector(Usuario inspector) {
		this.inspector = inspector;
	}

	public Usuario getSecretariaSecundaria() {
		return secretariaSecundaria;
	}

	public void setSecretariaSecundaria(Usuario secretariaSecundaria) {
		this.secretariaSecundaria = secretariaSecundaria;
	}

	public Usuario getDirector() {
		return director;
	}

	public void setDirector(Usuario director) {
		this.director = director;
	}

	public Usuario getSecretariaPrimaria() {
		return secretariaPrimaria;
	}

	public void setSecretariaPrimaria(Usuario secretariaPrimaria) {
		this.secretariaPrimaria = secretariaPrimaria;
	}

	public Integer getContador() {
		return contador;
	}

	public void setContador(Integer contador) {
		this.contador = contador;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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
		if (!(object instanceof Institucion)) {
			return false;
		}
		Institucion other = (Institucion) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gestionestudiantil.model.Institucion[ id=" + id + " ]";
	}

}
