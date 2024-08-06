package com.gestionestudiantil.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.exception.GestionEstudiantilPersistenceException;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Rol;
import com.gestionestudiantil.model.Usuario;
import com.gestionestudiantil.service.InstitucionService;
import com.gestionestudiantil.service.SeguridadService;

@ManagedBean
@ViewScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = 1793943235598675537L;

	@Inject
	private SeguridadService seguridadService;
	@Inject
	private InstitucionService institucionService;
	@Inject
	private FacesContext facesContext;

	@ManagedProperty("#{institucion}")
	private Institucion institucion;

	private Usuario usuario;

	private List<Usuario> usuarios;

	private List<Rol> roles;

	private List<Rol> rolesSeleccionados;

	private List<Institucion> instituciones;

	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) facesContext
				.getExternalContext().getRequest();
		usuarios = seguridadService.obtenerUsuariosPorInstitucion(institucion);
		roles = seguridadService.obtenerTodosRoles();
		if (request.isUserInRole("2")) {
			Rol rol = seguridadService.obtenerRol(1L);
			roles.remove(rol);
		}
		instituciones = institucionService.obtenerTodasLasInstituciones();
	}

	public void preparaNuevo() {
		this.usuario = new Usuario();
	}

	public void editar() {
		try {
			seguridadService.editarUsuario(usuario);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Éxito", "Estudiante editado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void guardar() throws GestionEstudiantilPersistenceException {
		try {
			seguridadService.guardarUsuario(usuario, rolesSeleccionados);
			usuarios.add(usuario);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Éxito", "Usuario guardado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}

	}

	public List<Rol> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Rol> getRolesSeleccionados() {
		return rolesSeleccionados;
	}

	public void setRolesSeleccionados(List<Rol> rolesSeleccionados) {
		this.rolesSeleccionados = rolesSeleccionados;
	}

	public List<Institucion> getInstituciones() {
		return instituciones;
	}

	public void setInstituciones(List<Institucion> instituciones) {
		this.instituciones = instituciones;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

}