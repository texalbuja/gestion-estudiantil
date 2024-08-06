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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 *
 * @author Tex
 */
@Entity
@Table(name = "usuario", uniqueConstraints = { @UniqueConstraint(columnNames = { "identificacion" }) })
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Long id;
	@Size(max = 255)
	@Column(name = "identificacion", length = 64, unique = true)
	private String identificacion;
	@Size(max = 255)
	@Column(name = "nombre", length = 255)
	private String nombre;
	@Size(max = 255)
	@Column(name = "clave", length = 255)
	private String clave;
	@Size(max = 255)
	@Column(name = "correo", length = 255)
	private String correo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "institucion_id", referencedColumnName = "id")
	private Institucion institucion;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY)
	private List<RolUsuario> rolUsuarioList;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY)
	private List<BloqueoUsuario> bloqueosUsuario;

	public Usuario() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public List<RolUsuario> getRolUsuarioList() {
		return rolUsuarioList;
	}

	public void setRolUsuarioList(List<RolUsuario> rolUsuarioList) {
		this.rolUsuarioList = rolUsuarioList;
	}

	public List<BloqueoUsuario> getBloqueosUsuario() {
		return bloqueosUsuario;
	}

	public void setBloqueosUsuario(List<BloqueoUsuario> bloqueosUsuario) {
		this.bloqueosUsuario = bloqueosUsuario;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Usuario)) {
			return false;
		}
		Usuario other = (Usuario) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gestionestudiantil.model.Usuario[ id=" + id + " ]";
	}

}
