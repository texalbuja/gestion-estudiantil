package com.gestionestudiantil.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.gestionestudiantil.enums.Secciones;
import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.model.Catalogo;
import com.gestionestudiantil.model.Contacto;
import com.gestionestudiantil.model.Direccion;
import com.gestionestudiantil.model.Estudiante;
import com.gestionestudiantil.service.MatriculacionService;
import com.gestionestudiantil.service.UtilService;

@ManagedBean
@ViewScoped
public class ActualizarDatosController implements Serializable {

	private static final long serialVersionUID = -4343261422354866668L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private MatriculacionService matriculacionService;

	@Inject
	private UtilService utilService;

	@ManagedProperty("#{estudiante}")
	private Estudiante estudiante;

	private String parentescoPrincipal;

	private ArrayList<String> parentescosPrincipal = new ArrayList<String>();
	private ArrayList<String> parentescosAlterno = new ArrayList<String>();

	private String parentescoAlterno;

	private List<Catalogo> provincias;
	private List<Catalogo> cantones;
	private List<Catalogo> parroquias;
	private List<Catalogo> paises;

	@PostConstruct
	public void init() {

		parentescosPrincipal.add("Madre");
		parentescosPrincipal.add("Padre");
		parentescosPrincipal.add("Otro");
		parentescoPrincipal = "Madre";
		parentescosAlterno.add("Padre");
		parentescosAlterno.add("Otro");
		parentescoAlterno = "Padre";

		preparaNuevo();

		obtenerPaises();

	}

	public void obtenerPaises() {
		paises = utilService.obtenerCatalogosPorClave("PAIS");
		obtenerProvincias();
	}

	public void obtenerProvincias() {
		if (estudiante.getPais() == null) {
			provincias = utilService.obtenerCatalogosPorClaveYValorPadre("PROVINCIA", paises.get(0).getValor());
		} else {
			provincias = utilService.obtenerCatalogosPorClaveYValorPadre("PROVINCIA", estudiante.getPais());
		}
		obtenerCantones();
	}

	public void obtenerCantones() {
		if (provincias.isEmpty()) {
			estudiante.setProvincia(null);
			estudiante.setCanton(null);
			estudiante.setParroquia(null);
			return;
		}
		if (estudiante.getProvincia() == null) {
			cantones = utilService.obtenerCatalogosPorClaveYValorPadre("CANTON", provincias.get(0).getValor());
		} else {
			cantones = utilService.obtenerCatalogosPorClaveYValorPadre("CANTON", estudiante.getProvincia());
		}

		obtenerParroquias();
	}

	public void obtenerParroquias() {
		if (cantones.isEmpty()) {
			estudiante.setCanton(null);
			estudiante.setParroquia(null);
			return;
		}
		if (estudiante.getCanton() == null && !cantones.isEmpty()) {
			parroquias = utilService.obtenerCatalogosPorClaveYValorPadre("PARROQUIA", cantones.get(0).getValor());
		} else {
			parroquias = utilService.obtenerCatalogosPorClaveYValorPadre("PARROQUIA", estudiante.getCanton());
		}

	}

	public void removerAlterno() {
		if (parentescoPrincipal.equals("Otro")) {
			parentescosAlterno.remove("Padre");
			parentescosAlterno.remove("Madre");
			parentescosAlterno.remove("Otro");
			parentescosAlterno.add("Madre");
			parentescosAlterno.add("Padre");
			parentescosAlterno.add("Otro");
			parentescoAlterno = "Madre";
		} else {
			if (parentescoPrincipal.equals("Madre")) {
				parentescosAlterno.remove("Madre");
				parentescosAlterno.remove("Padre");
				parentescosAlterno.remove("Otro");
				parentescosAlterno.add("Padre");
				parentescosAlterno.add("Otro");
				parentescoAlterno = "Padre";
			}
			if (parentescoPrincipal.equals("Padre")) {
				parentescosAlterno.remove("Padre");
				parentescosAlterno.remove("Madre");
				parentescosAlterno.remove("Otro");
				parentescosAlterno.add("Madre");
				parentescosAlterno.add("Otro");
				parentescoAlterno = "Madre";
			}
		}
	}

	public void consultaPadre() {

		estudiante.setPadre(
				matriculacionService.obtenerContactoPorIdentificacion(estudiante.getPadre().getIdentificacion()));

	}

	public void preparaNuevo() {
		if (estudiante.getMadre() == null) {
			estudiante.setMadre(new Contacto());
			estudiante.getMadre().setDomicilio(new Direccion());
			estudiante.getMadre().setRefugiado("Si");
		}
		if (estudiante.getPadre() == null) {
			estudiante.setPadre(new Contacto());
			estudiante.getPadre().setDomicilio(new Direccion());
			estudiante.getPadre().setRefugiado("Si");
		}
		if (estudiante.getDomicilio() == null) {
			estudiante.setDomicilio(new Direccion());
		}
		estudiante.setSexo("M");
		estudiante.setSeccion("M");
		estudiante.setRefugiado("No");

	}

	public Secciones[] getSecciones() {
		return Secciones.values();
	}

	public void actualizarDatos() {
		try {
			estudiante.getMadre().setParentesco(parentescoPrincipal);
			estudiante.getPadre().setParentesco(parentescoAlterno);
			matriculacionService.editarEstudiante(estudiante);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Datos actualizados correctamente"));

		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public String exportarReporteDatosEstudiante() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/ficha_datos_estudiante.jasper");
		parametros.put("estudiante_id", estudiante.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public String getParentescoPrincipal() {
		return parentescoPrincipal;
	}

	public void setParentescoPrincipal(String parentescoPrincipal) {
		this.parentescoPrincipal = parentescoPrincipal;
	}

	public String getParentescoAlterno() {
		return parentescoAlterno;
	}

	public void setParentescoAlterno(String parentescoAlterno) {
		this.parentescoAlterno = parentescoAlterno;
	}

	public ArrayList<String> getParentescosPrincipal() {
		return parentescosPrincipal;
	}

	public void setParentescosPrincipal(ArrayList<String> parentescosPrincipal) {
		this.parentescosPrincipal = parentescosPrincipal;
	}

	public ArrayList<String> getParentescosAlterno() {
		return parentescosAlterno;
	}

	public void setParentescosAlterno(ArrayList<String> parentescosAlterno) {
		this.parentescosAlterno = parentescosAlterno;
	}

	public List<Catalogo> getProvincias() {
		return provincias;
	}

	public void setProvincias(List<Catalogo> provincias) {
		this.provincias = provincias;
	}

	public List<Catalogo> getCantones() {
		return cantones;
	}

	public void setCantones(List<Catalogo> cantones) {
		this.cantones = cantones;
	}

	public List<Catalogo> getParroquias() {
		return parroquias;
	}

	public void setParroquias(List<Catalogo> parroquias) {
		this.parroquias = parroquias;
	}

	public List<Catalogo> getPaises() {
		return paises;
	}

	public void setPaises(List<Catalogo> paises) {
		this.paises = paises;
	}

}