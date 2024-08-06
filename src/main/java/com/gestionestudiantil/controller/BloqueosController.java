package com.gestionestudiantil.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.model.BloqueoUsuario;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Usuario;
import com.gestionestudiantil.service.SeguridadService;

@ManagedBean
@ViewScoped
public class BloqueosController implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private FacesContext facesContext;

	@Inject
	private SeguridadService seguridadService;

	private Usuario usuario;

	private List<Usuario> usuarios;

	private List<Usuario> usuariosFiltrados;

	private List<BloqueoUsuario> bloqueosUsuarios;

	private BloqueoUsuario bloqueoUsuario;

	@ManagedProperty("#{institucion}")
	private Institucion institucion;

	@PostConstruct
	public void init() {
		usuarios = seguridadService.obtenerUsuariosPorInstitucion(institucion);
		usuariosFiltrados = new ArrayList<Usuario>();
		bloqueosUsuarios = seguridadService
				.obtenerBloqueosUsuarioPorInstitucion(institucion);
		usuario = new Usuario();
	}

	public void bloquear() {
		try {
			BloqueoUsuario bloqueoUsuario = seguridadService
					.bloquearUsuario(usuario);
			bloqueosUsuarios.add(0, bloqueoUsuario);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Éxito",
					"Usuario bloqueado con éxito"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void reiniciarClave() {
		try {
			seguridadService.reiniciarClave(usuario);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Éxito",
					"Clave reiniciada con éxito"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void desbloquear() {
		try {
			seguridadService.desbloquearUsuario(bloqueoUsuario);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Éxito",
					"Asistencia editada con éxito"));
			bloqueosUsuarios.remove(bloqueoUsuario);
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public List<Usuario> completarUsuarios(String query) {
		usuariosFiltrados = new ArrayList<Usuario>();
		for (Usuario usuario : usuarios) {
			if (usuario.getNombre().toUpperCase()
					.contains(query.trim().toUpperCase()))
				usuariosFiltrados.add(usuario);
		}
		return usuariosFiltrados;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Usuario> getUsuariosFiltrados() {
		return usuariosFiltrados;
	}

	public void setUsuariosFiltrados(List<Usuario> usuariosFiltrados) {
		this.usuariosFiltrados = usuariosFiltrados;
	}

	public List<BloqueoUsuario> getBloqueosUsuarios() {
		return bloqueosUsuarios;
	}

	public void setBloqueosUsuarios(List<BloqueoUsuario> bloqueosUsuarios) {
		this.bloqueosUsuarios = bloqueosUsuarios;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public BloqueoUsuario getBloqueoUsuario() {
		return bloqueoUsuario;
	}

	public void setBloqueoUsuario(BloqueoUsuario bloqueoUsuario) {
		this.bloqueoUsuario = bloqueoUsuario;
	}

}