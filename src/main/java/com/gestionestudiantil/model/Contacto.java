package com.gestionestudiantil.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "contacto")
public class Contacto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "identificacion", length = 64)
	@Size(max = 64, message = "Longitud máxima 64")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Sólo números o letras")
	private String identificacion;

	@Column(name = "nombres", length = 255)
	@Size(max = 255, message = "Longitud máxima 255")
	private String nombres;

	@Column(name = "apellidos", length = 255)
	@Size(max = 255, message = "Longitud máxima 255")
	private String apellidos;
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "domicilio_id", insertable = true, updatable = true, nullable = true, unique = false)
	private Direccion domicilio;
	@Column(name = "ocupacion", length = 64)
	@Size(max = 64, message = "Longitud máxima 64")
	private String ocupacion;
	@Column(name = "telefono_domicilio", length = 64)
	@Size(max = 64, message = "Longitud máxima 64")
	private String telefonoDomicilio;
	@Column(name = "telefono_trabajo", length = 64)
	@Size(max = 64, message = "Longitud máxima 64")
	private String telefonoTrabajo;
	@Column(name = "telefono_celular", length = 64)
	@Size(max = 64, message = "Longitud máxima 64")
	private String telefonoCelular;
	@Column(name = "correo", length = 64)
	@Size(max = 64, message = "Longitud máxima 64")
	private String correo;
	@Column(name = "nacionalidad", length = 64)
	@Size(max = 64, message = "Longitud máxima 64")
	private String nacionalidad;
	@Column(name = "refugiado", length = 2)
	@Size(max = 2, message = "Longitud máxima 2")
	private String refugiado;
	@Column(name = "parentesco", length = 64)
	@Size(max = 64, message = "Longitud máxima 64")
	private String parentesco;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Direccion getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Direccion domicilio) {
		this.domicilio = domicilio;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public String getTelefonoDomicilio() {
		return telefonoDomicilio;
	}

	public void setTelefonoDomicilio(String telefonoDomicilio) {
		this.telefonoDomicilio = telefonoDomicilio;
	}

	public String getTelefonoTrabajo() {
		return telefonoTrabajo;
	}

	public void setTelefonoTrabajo(String telefonoTrabajo) {
		this.telefonoTrabajo = telefonoTrabajo;
	}

	public String getTelefonoCelular() {
		return telefonoCelular;
	}

	public void setTelefonoCelular(String telefonoCelular) {
		this.telefonoCelular = telefonoCelular;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getRefugiado() {
		return refugiado;
	}

	public void setRefugiado(String refugiado) {
		this.refugiado = refugiado;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getParentesco() {
		return parentesco;
	}

	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Contacto)) {
			return false;
		}
		Contacto other = (Contacto) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gestionestudiantil.model.Contacto[ id=" + id + " ]";
	}

}
