package com.gestionestudiantil.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.gestionestudiantil.enums.Grados;
import com.gestionestudiantil.enums.Roles;
import com.gestionestudiantil.enums.Secciones;
import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.exception.GestionEstudiantilPersistenceException;
import com.gestionestudiantil.model.Autoridad;
import com.gestionestudiantil.model.Docente;
import com.gestionestudiantil.model.Grado;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.PeriodoLectivo;
import com.gestionestudiantil.model.Usuario;
import com.gestionestudiantil.service.InstitucionService;
import com.gestionestudiantil.service.MatriculacionService;
import com.gestionestudiantil.service.SeguridadService;

@ManagedBean
@ViewScoped
public class InstitucionController implements Serializable {

	private static final long serialVersionUID = 6474088025527199301L;

	@Inject
	private MatriculacionService matriculacionService;

	@Inject
	private SeguridadService seguridadService;

	@Inject
	private InstitucionService institucionService;

	@Inject
	private FacesContext facesContext;

	@ManagedProperty("#{institucion}")
	private Institucion institucion;

	@ManagedProperty("#{periodo}")
	private String periodo;

	private PeriodoLectivo periodoLectivo;

	private Roles rol;

	private List<PeriodoLectivo> periodosLectivos;

	private List<Roles> roles;

	private Usuario usuario;

	private Autoridad autoridad;

	private String criterio;

	private List<Grados> gradosHabilitados;

	private Docente docente;

	private List<Autoridad> autoridades;

	@PostConstruct
	public void init() {
		periodosLectivos = matriculacionService.obtenerPeriodosLectivosPorInstitucion(institucion, periodo);
		usuario = new Usuario();
		usuario.setInstitucion(institucion);
		roles = Arrays.asList(Roles.obtenerRolesFuncionarios());
		autoridades = new ArrayList<>();
		if (institucion != null) {
			if (institucion.getInspector() != null)
				autoridades.add(new Autoridad(institucion.getInspector(), Roles.INSPECTOR));
			if (institucion.getRector() != null)
				autoridades.add(new Autoridad(institucion.getRector(), Roles.RECTOR));
			if (institucion.getSecretariaPrimaria() != null)
				autoridades.add(new Autoridad(institucion.getSecretariaPrimaria(), Roles.SECRETARIA_PRIMARIA));
			if (institucion.getSecretariaSecundaria() != null)
				autoridades.add(new Autoridad(institucion.getSecretariaSecundaria(), Roles.SECRETARIA_SECUNDARIA));
			if (institucion.getVicerrector() != null)
				autoridades.add(new Autoridad(institucion.getVicerrector(), Roles.VICERECTOR));
			if (institucion.getDirector() != null)
				autoridades.add(new Autoridad(institucion.getDirector(), Roles.DIRECTOR));
		}
	}

	public void completarUsuario() {
		try {
			usuario = seguridadService.obtenerFuncionarioPorIdentificacionEInstitucion(usuario.getIdentificacion(),
					institucion);
		} catch (GestionEstudiantilException e) {
			usuario.setId(null);
		}
	}

	public String gestionarMatriculas() {
		facesContext.getExternalContext().getSessionMap().put("periodoLectivo", periodoLectivo);
		return "/matriculacion/gestion.jsf?faces-redirect=true";
	}

	public String ingresarObservacion() {
		facesContext.getExternalContext().getSessionMap().put("periodoLectivo", periodoLectivo);
		return "/comportamiento/registroObservacion.jsf?faces-redirect=true";
	}

	public String registrarAsistencia() {
		facesContext.getExternalContext().getSessionMap().put("periodoLectivo", periodoLectivo);
		return "/asistencia/registroAsistencia.jsf?faces-redirect=true";
	}

	@SuppressWarnings("deprecation")
	public void preparaNuevo() {
		periodoLectivo = new PeriodoLectivo();
		periodoLectivo.setInstitucion(institucion);
		periodoLectivo.setEtiqueta(periodo);
		periodoLectivo.setEstado("Inscripciones");
		periodoLectivo.setGrados(new ArrayList<Grado>());
		periodoLectivo.setSeccion("M");
		periodoLectivo.setInsumos(4);
	}

	public void eliminarUsuario() {
		try {
			seguridadService.desvincularAutoridad(autoridad);
			autoridades.remove(autoridad);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario eliminado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}

	}

	public void preparaNuevoFuncionario() {
		usuario = new Usuario();
		usuario.setInstitucion(institucion);
		rol = Roles.SECRETARIA_PRIMARIA;
	}

	public void guardarUsuario() throws IOException, GestionEstudiantilPersistenceException {

		try {
			institucionService.registrarFuncionario(institucion, usuario, rol);

			institucion = matriculacionService.obtenerInstitucionPorId(institucion.getId());
			autoridades = new ArrayList<>();
			if (institucion.getInspector() != null)
				autoridades.add(new Autoridad(institucion.getInspector(), Roles.INSPECTOR));
			if (institucion.getRector() != null)
				autoridades.add(new Autoridad(institucion.getRector(), Roles.RECTOR));
			if (institucion.getSecretariaPrimaria() != null)
				autoridades.add(new Autoridad(institucion.getSecretariaPrimaria(), Roles.SECRETARIA_PRIMARIA));
			if (institucion.getSecretariaSecundaria() != null)
				autoridades.add(new Autoridad(institucion.getSecretariaSecundaria(), Roles.SECRETARIA_SECUNDARIA));
			if (institucion.getVicerrector() != null)
				autoridades.add(new Autoridad(institucion.getVicerrector(), Roles.VICERECTOR));
			if (institucion.getDirector() != null)
				autoridades.add(new Autoridad(institucion.getDirector(), Roles.DIRECTOR));
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario guardado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}

	}

	public void editarDocente() {
		try {
			institucionService.actualizarDocente(docente);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Docente editado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void editarUsuario() {
		try {
			seguridadService.editarUsuarioNoCambiarClave(autoridad);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario editado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public String seleccionarPeriodoLectivo() {
		facesContext.getExternalContext().getSessionMap().put("periodoLectivo", periodoLectivo);
		return "/matriculacion/periodoLectivo.jsf?faces-redirect=true";
	}

	public String seleccionarDocente() {
		facesContext.getExternalContext().getSessionMap().put("docente", docente);
		return "/matriculacion/docente.jsf?faces-redirect=true";
	}

	public Grados[] getGrados() {
		return Grados.values();
	}

	public Secciones[] getSecciones() {
		return Secciones.values();
	}

	public String desdeSeccion(String seccion) {
		for (Secciones s : Secciones.values()) {
			if (s.getCodigo().equals(seccion))
				return s.getDescripcion();
		}
		return "";
	}

	public void buscar() throws IOException {
		periodosLectivos = matriculacionService.buscarPeriodoLectivo(criterio);
	}

	public void eliminarPeriodoLectivo() throws IOException {
		try {
			institucionService.eliminarPeriodoLectivo(periodoLectivo);
			periodosLectivos.remove(periodoLectivo);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Periodo Lectivo eliminado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void guardar() {
		try {
			institucionService.guardarPeriodoLectivo(periodoLectivo, gradosHabilitados);
			periodosLectivos.add(periodoLectivo);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Periodo Lectivo guardado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void editar() {
		try {
			institucionService.editarPeriodoLectivo(periodoLectivo);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Periodo Lectivo editado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public Roles getRol() {
		return rol;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRol(Roles rol) {
		this.rol = rol;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	public PeriodoLectivo getPeriodoLectivo() {
		return this.periodoLectivo;
	}

	public void setPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		this.periodoLectivo = periodoLectivo;
	}

	public String getCriterio() {
		return criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public List<PeriodoLectivo> getPeriodosLectivos() {
		return periodosLectivos;
	}

	public void setPeriodosLectivos(List<PeriodoLectivo> periodosLectivos) {
		this.periodosLectivos = periodosLectivos;
	}

	public List<Grados> getGradosHabilitados() {
		return gradosHabilitados;
	}

	public void setGradosHabilitados(List<Grados> gradosHabilitados) {
		this.gradosHabilitados = gradosHabilitados;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public List<Autoridad> getAutoridades() {
		return autoridades;
	}

	public void setAutoridades(List<Autoridad> autoridades) {
		this.autoridades = autoridades;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Autoridad getAutoridad() {
		return autoridad;
	}

	public void setAutoridad(Autoridad autoridad) {
		this.autoridad = autoridad;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
}