package com.gestionestudiantil.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class NavegacionController implements Serializable {

	private static final long serialVersionUID = -567603743285986074L;

	public String iniciarSesion() {
		return "/iniciarSesion.jsf?faces-redirect=true";
	}

	public String inicio() {
		return "/resumen.jsf?faces-redirect=true";
	}

	public String nuevoEstudiante() {
		return "/matriculacion/inscribirNuevoEstudiante.jsf?faces-redirect=true";
	}

	public String seleccionarInstitucion() {
		return "/matriculacion/inicio.jsf?faces-redirect=true";
	}

	public String seleccionarMonitoreo() {
		return "/monitoring";
	}

	public String actualizarDatos() {
		return "/matriculacion/actualizarDatos.jsf?faces-redirect=true";
	}

	public String evaluaciones() {
		return "/matriculacion/matricula.jsf?faces-redirect=true";
	}

	public String asistencia() {
		return "/asistencia/asistencias.jsf?faces-redirect=true";
	}

	public String registroAsistencia() {
		return "/asistencia/registroAsistencia.jsf?faces-redirect=true";
	}

	public String registroObservaciones() {
		return "/comportamiento/registroObservacion.jsf?faces-redirect=true";
	}

	public String comportamiento() {
		return "/comportamiento/observaciones.jsf?faces-redirect=true";
	}

	public String periodosLectivos() {
		return "/matriculacion/institucion.jsf?faces-redirect=true";
	}

	public String docentes() {
		return "/matriculacion/docentes.jsf?faces-redirect=true";
	}

	public String docente() {
		return "/matriculacion/docente.jsf?faces-redirect=true";
	}

	public String estudiantes() {
		return "/matriculacion/estudiantes.jsf?faces-redirect=true";
	}

	public String bloqueos() {
		return "/seguridad/bloqueos.jsf?faces-redirect=true";
	}
}
