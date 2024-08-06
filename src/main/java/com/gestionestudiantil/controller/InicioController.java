package com.gestionestudiantil.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.model.Imagen;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.service.InstitucionService;

@ManagedBean
@ViewScoped
public class InicioController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private InstitucionService institucionService;

	@Inject
	private FacesContext facesContext;

	private Institucion institucion;

	private List<Institucion> instituciones;

	private UploadedFile file;

	private String criterio;

	@PostConstruct
	public void init() {
		instituciones = institucionService.obtenerTodasLasInstituciones();
	}

	public void preparaNuevo() {
		institucion = new Institucion();
		institucion.setRegimen("Sierra");
	}

	public void guardar() {
		try {
			Imagen imagen = new Imagen();
			imagen.setBytes(IOUtils.toByteArray(file.getInputstream()));
			institucion.setImagen(imagen);
			institucion = institucionService.guardarInstitucion(institucion);
			instituciones.add(institucion);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Institución Guardada"));
		} catch (IOException | GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}

	}

	public String seleccionar() {
		facesContext.getExternalContext().getSessionMap().put("institucion", institucion);
		return "/matriculacion/institucion.jsf?faces-redirect=true";
	}

	public void editar() throws IOException {
		try {
			if (!(file != null && file.getFileName().equals(""))) {
				Imagen imagen = new Imagen();
				imagen.setBytes(IOUtils.toByteArray(file.getInputstream()));
				institucion.setImagen(imagen);
			}
			institucionService.editarInstitucion(institucion);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Institución editada"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void eliminar() throws IOException {
		try {
			institucionService.eliminarInstitucion(institucion);
			instituciones.remove(institucion);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Institución eliminada"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public Institucion getInstitucion() {
		return this.institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public List<Institucion> getInstituciones() {
		return this.instituciones;
	}

	public void setInstituciones(List<Institucion> instituciones) {
		this.instituciones = instituciones;
	}

	public UploadedFile getFile() {
		return this.file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getCriterio() {
		return this.criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}
}