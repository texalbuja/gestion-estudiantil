package com.gestionestudiantil.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "catalogo")
public class Catalogo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "clave", length = 10)
	@Size(max = 10, message = "Longitud máxima 10")
	private String clave;
	@Column(name = "valor", length = 10)
	@Size(max = 10, message = "Longitud máxima 10")
	private String valor;
	@Column(name = "etiqueta", length = 64)
	@Size(max = 64, message = "Longitud máxima 64")
	private String etiqueta;
	
	@Column(name = "valor_padre", length = 10)
	@Size(max = 10, message = "Longitud máxima 10")
	private String valorPadre;
	

	public String getEtiqueta() {
		return this.etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getValorPadre() {
		return valorPadre;
	}

	public void setValorPadre(String valorPadre) {
		this.valorPadre = valorPadre;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Catalogo)) {
			return false;
		}
		Catalogo other = (Catalogo) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gestionestudiantil.model.Catalogo[ id=" + id + " ]";
	}

}