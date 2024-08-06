
package com.gestionestudiantil.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.model.Estudiante;
import com.gestionestudiantil.model.Imagen;
import com.gestionestudiantil.model.Paralelo;
import com.gestionestudiantil.service.CarnetizacionService;
import com.gestionestudiantil.service.MatriculacionService;

@ManagedBean
@ViewScoped
public class CarnetizacionController implements Serializable {

	private static final long serialVersionUID = 1782635962622860097L;

	@Inject
	private FacesContext facesContext;
	@Inject
	private CarnetizacionService carnetizacionService;
	@Inject
	private MatriculacionService matriculacionService;

	@ManagedProperty("#{paralelo}")
	private Paralelo paralelo;

	private Estudiante estudiante;

	private UploadedFile file;

	private List<Estudiante> estudiantes;

	@PostConstruct
	public void init() {
		estudiantes = matriculacionService.obtenerEstudiantesPorParalelo(paralelo);
	}

	public void guardarEstudiante() {
		try {
			if (!(file != null && file.getFileName().equals(""))) {
				Imagen imagen = new Imagen();
				imagen.setBytes(IOUtils.toByteArray(file.getInputstream()));
				estudiante.setImagen(imagen);
				carnetizacionService.guardarCodigoEstudiante(estudiante);
			}
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error imágen demasiado grande"));
		} catch (IOException e) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error imágen demasiado grande"));
		}

	}

	public void guardarCodigo(Estudiante estudiante) {
		try {
			carnetizacionService.guardarCodigoEstudiante(estudiante);
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar el estudiante", e.getMessage()));
		}
	}

	public void guardarCodigosCarnetizacion() {
		try {
			for (Estudiante estudiante : estudiantes) {
				carnetizacionService.guardarCodigoEstudiante(estudiante);
			}
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar el estudiante", e.getMessage()));
		}

	}

	public void cargarFotos() {
		try {
			carnetizacionService.cargarFotos(estudiantes);
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar el estudiante", e.getMessage()));
		}

	}

	public String imprimir() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/carnetizacion_paralelo.jasper");
		parametros.put("paralelo_id", paralelo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public Paralelo getParalelo() {
		return paralelo;
	}

	public void setParalelo(Paralelo paralelo) {
		this.paralelo = paralelo;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public List<Estudiante> getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(List<Estudiante> estudiantes) {
		this.estudiantes = estudiantes;
	}

}