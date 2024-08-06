package com.gestionestudiantil.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "estudiante")
public class Estudiante implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, name = "identificacion", length = 64)
	@Size(max = 64, message = "Longitud máxima 64")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Sólo números o letras")
	private String identificacion;

	@Column(name = "nombres", length = 255)
	@Size(max = 255)
	private String nombres;

	@Column(name = "apellidos", length = 255)
	@Size(max = 255)
	private String apellidos;

	@Column(name = "sexo", length = 1)
	@Size(max = 1)
	private String sexo;

	@Column(name = "correo", length = 64)
	@Size(max = 64, message = "Longitud máxima 64")
	@Email(message = "Ingrese un correo electrónico")
	private String correo;

	@Column(name = "plantel_de_procedencia", length = 255)
	@Size(max = 255, message = "Longitud máxima 255")
	private String plantelDeProcedencia;

	@Column(name = "seccion", length = 1)
	@Size(max = 1)
	private String seccion;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_nacimiento")
	@Past(message = "Debe ser una fecha menor a la de hoy")
	private Date fechaNacimiento;

	@Column(name = "refugiado", length = 2)
	@Size(max = 2)
	private String refugiado;

	@Column(name = "nacionalidad", length = 32)
	@Size(max = 32)
	private String nacionalidad;

	@Column(name = "pais", length = 32)
	@Size(max = 32)
	private String pais;

	@Column(name = "provincia", length = 32)
	@Size(max = 32)
	private String provincia;

	@Column(name = "canton", length = 32)
	@Size(max = 32)
	private String canton;

	@Column(name = "parroquia", length = 32)
	@Size(max = 32)
	private String parroquia;

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", insertable = true, updatable = true, nullable = true)
	private Usuario usuario;

	@JoinColumn(name = "institucion_id", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Institucion institucion;
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "estudiante", fetch = FetchType.LAZY)
	private List<Matricula> matriculas;

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "imagen_id", insertable = true, updatable = true, nullable = true)
	private Imagen imagen;

	@Column(name = "vive_con", length = 32)
	@Size(max = 32)
	private String viveCon;

	@Column(name = "codigo_unico_electrico_nacional", length = 32)
	@Size(max = 32)
	private String codigoUnicoElectricoNacional;

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "domicilio_id", insertable = true, updatable = true, nullable = true)
	private Direccion domicilio;

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "padre_id", insertable = true, updatable = true, nullable = true)
	private Contacto padre;

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "madre_id", insertable = true, updatable = true, nullable = true)
	private Contacto madre;
	
	@Column(name = "codigo_foto", length = 32)
	@Size(max = 32)
	private String codigoFoto;


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

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public String getViveCon() {
		return viveCon;
	}

	public void setViveCon(String viveCon) {
		this.viveCon = viveCon;
	}

	public Direccion getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Direccion domicilio) {
		this.domicilio = domicilio;
	}

	public String getPlantelDeProcedencia() {
		return plantelDeProcedencia;
	}

	public void setPlantelDeProcedencia(String plantelDeProcedencia) {
		this.plantelDeProcedencia = plantelDeProcedencia;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getRefugiado() {
		return refugiado;
	}

	public void setRefugiado(String refugiado) {
		this.refugiado = refugiado;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public List<Matricula> getMatriculas() {
		return matriculas;
	}

	public String getCodigoFoto() {
		return codigoFoto;
	}

	public void setCodigoFoto(String codigoFoto) {
		this.codigoFoto = codigoFoto;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCanton() {
		return canton;
	}

	public void setCanton(String canton) {
		this.canton = canton;
	}

	public String getParroquia() {
		return parroquia;
	}

	public void setParroquia(String parroquia) {
		this.parroquia = parroquia;
	}

	public Contacto getPadre() {
		return padre;
	}

	public void setPadre(Contacto padre) {
		this.padre = padre;
	}

	public Contacto getMadre() {
		return madre;
	}

	public void setMadre(Contacto madre) {
		this.madre = madre;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getCodigoUnicoElectricoNacional() {
		return codigoUnicoElectricoNacional;
	}

	public void setCodigoUnicoElectricoNacional(String codigoUnicoElectricoNacional) {
		this.codigoUnicoElectricoNacional = codigoUnicoElectricoNacional;
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
		if (!(object instanceof Estudiante)) {
			return false;
		}
		Estudiante other = (Estudiante) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gestionestudiantil.model.Estudiante[ id=" + id + " ]";
	}

}