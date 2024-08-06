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
import javax.servlet.http.HttpServletRequest;

import com.gestionestudiantil.enums.Grados;
import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.model.Area;
import com.gestionestudiantil.model.CriterioDeEvaluacion;
import com.gestionestudiantil.model.Docente;
import com.gestionestudiantil.model.Grado;
import com.gestionestudiantil.model.GrupoDeEvaluacion;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Paralelo;
import com.gestionestudiantil.model.PeriodoLectivo;
import com.gestionestudiantil.model.Usuario;
import com.gestionestudiantil.service.InstitucionService;
import com.gestionestudiantil.service.MatriculacionService;

@ManagedBean
@ViewScoped
public class PeriodoLectivoController implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private MatriculacionService matriculacionService;

	@Inject
	private InstitucionService institucionService;

	@Inject
	private FacesContext facesContext;

	@ManagedProperty("#{periodoLectivo}")
	private PeriodoLectivo periodoLectivo;

	@ManagedProperty("#{usuario}")
	private Usuario usuario;

	@ManagedProperty("#{institucion}")
	private Institucion institucion;

	private List<Paralelo> paralelosUsuario;

	private String criterio;

	private Paralelo paralelo;

	private List<Paralelo> paralelos;

	private CriterioDeEvaluacion criterioDeEvaluacion;

	private List<CriterioDeEvaluacion> criteriosDeEvaluacion;

	private List<Grados> gradosDisponibles;

	private List<Grados> gradosAgregados;

	private List<Docente> docentes;

	private Docente docente;

	private List<Grado> grados;

	private List<GrupoDeEvaluacion> gruposDeEvaluacion;

	private GrupoDeEvaluacion grupoDeEvaluacion;
	
	private List<Area> areas;
	
	private Area area;

	@PostConstruct
	public void init() {
		criteriosDeEvaluacion = matriculacionService.obtenerCriteriosPorPeriodoLectivo(periodoLectivo);

		paralelos = matriculacionService.obtenerParalelosPorPeriodoLectivo(periodoLectivo);
		docentes = matriculacionService.obtenerDocentesPorInstitucion(institucion);
		grados = matriculacionService.obtenerGradosPorPeriodoLectivo(periodoLectivo);
		areas = matriculacionService.obtenerAreasPorPeriodoLectivo(periodoLectivo);
		paralelosUsuario = matriculacionService.obtenerParalelosPorUsuario(usuario);
		setGruposDeEvaluacion(matriculacionService.obtenerGruposDeEvaluacionPorPeriodoLectivo(periodoLectivo));
	}

	public String evaluarParalelo() {
		facesContext.getExternalContext().getSessionMap().put("paralelo", paralelo);
		facesContext.getExternalContext().getSessionMap().put("grado", paralelo.getGrado());
		facesContext.getExternalContext().getSessionMap().put("periodoLectivo",
				paralelo.getGrado().getPeriodoLectivo());
		return "/comportamiento/grupoDeEvaluacion.jsf?faces-redirect=true";
	}

	public void preparaNuevo() {
		gradosDisponibles = new ArrayList<Grados>();
		List<Grados> gradosTomados = new ArrayList<Grados>();
		for (Grado g : matriculacionService.obtenerGradosPorPeriodoLectivo(periodoLectivo)) {
			gradosTomados.add(g.getGrados());
		}
		for (Grados g : Grados.values()) {
			if (!gradosTomados.contains(g)) {
				gradosDisponibles.add(g);
			}
		}
	}
	
	public void preparaNuevaArea() {
		area = new Area();
		area.setPeriodoLectivo(periodoLectivo);
	}

	public void preparaConfigurarCertificados() {

	}

	public void preparaNuevoCriterioDeEvaluacion() {
		criterioDeEvaluacion = new CriterioDeEvaluacion();
	}

	public String seleccionarParalelo() {
		facesContext.getExternalContext().getSessionMap().put("paralelo", paralelo);
		facesContext.getExternalContext().getSessionMap().put("grado", paralelo.getGrado());

		return "/matriculacion/" + obtenerRuta(paralelo.getGrado()) + "/paralelo.jsf?faces-redirect=true";
	}

	private String obtenerRuta(Grado grado) {
		String ruta = "preparatoria";

		if (grado.getGrado() >= 4) {
			ruta = "basica/elemental";
		}

		if (grado.getGrado() >= 7) {
			ruta = "basica/media";
		}
		if (grado.getGrado() >= 10) {
			ruta = "basica/superior";
		}

		if (grado.getGrado() >= 13) {
			ruta = "bachillerato";
		}

		return ruta;

	}

	public String exportarReporteListas() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/nomina_rapida.jasper");
		parametros.put("periodo_lectivo_id", periodoLectivo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteNominaVotacionesPrimaria() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/nomina_votaciones_primaria.jasper");
		parametros.put("periodo_lectivo_id", periodoLectivo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteNominaVotacionesSecundaria() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/nomina_votaciones_secundaria.jasper");
		parametros.put("periodo_lectivo_id", periodoLectivo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/pdf/pdf.jsf?faces-redirect=true";
	}

	public String exportarReporteDatosEstudiantes() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("reporte", "/reportes/datos_estudiante.jasper");
		parametros.put("periodo_lectivo_id", periodoLectivo.getId());
		facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
		return "/reportes/excel/excel.jsf?faces-redirect=true";
	}

	public void agregarGrados() {

		try {
			institucionService.agregarGrados(gradosAgregados, periodoLectivo);
			grados = matriculacionService.obtenerGradosPorPeriodoLectivo(periodoLectivo);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Grados agregados"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}

	}

	public void agregarArea() {
		try {
			matriculacionService.guardarArea(area);
			areas = matriculacionService.obtenerAreasPorPeriodoLectivo(periodoLectivo);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Area agregada"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}

	}

	public boolean paraleloPerteneceDocente(Paralelo paralelo) {
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		if (request.isUserInRole("1") || request.isUserInRole("2") || request.isUserInRole("7")
				|| request.isUserInRole("9") || request.isUserInRole("5") ||request.isUserInRole("6")) {
			return true;
		}
		if (request.isUserInRole("4")) {
			return paralelosUsuario.contains(paralelo);
		}
		return false;
	}

	public void preparaNuevoParalelo() {
		paralelo = new Paralelo();
	}

	public void guardarParalelo() {
		try {
			institucionService.guardarParalelo(paralelo);
			paralelos.add(paralelo);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Paralelo agregado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void guardarCriterioDeEvaluacion() {
		try {
			matriculacionService.guardarCriterioDeEvaluacion(criterioDeEvaluacion, periodoLectivo);
			criteriosDeEvaluacion = matriculacionService.obtenerCriteriosPorPeriodoLectivo(periodoLectivo);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Criterio de Evaluación guardado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void editarCriterioDeEvaluacion() {
		try {
			matriculacionService.editarCriterioDeEvaluacion(criterioDeEvaluacion, periodoLectivo);
			criteriosDeEvaluacion = matriculacionService.obtenerCriteriosPorPeriodoLectivo(periodoLectivo);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Criterio de Evaluación editado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	private Grado grado;

	public void editarGrado() {
		try {
			matriculacionService.editarGrado(grado);
			criteriosDeEvaluacion = matriculacionService.obtenerCriteriosPorPeriodoLectivo(periodoLectivo);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Criterio de Evaluación editado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}
	
	public void editarArea() {
		try {
			matriculacionService.editarArea(area);
			areas = matriculacionService.obtenerAreasPorPeriodoLectivo(periodoLectivo);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Area editado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}


	public void editarGrupoDeEvaluacion() {
		try {
			matriculacionService.editarGrupoDeEvaluacion(grupoDeEvaluacion);

			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Grupo de Evaluación editado"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void eliminarParalelo() {
		try {
			matriculacionService.eliminarParalelo(paralelo);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Paralelo eliminado correctamente"));
			paralelos.remove(paralelo);
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void eliminarCriterioDeEvaluacion() {
		try {
			matriculacionService.eliminarCriterioDeEvaluacion(criterioDeEvaluacion);
			criteriosDeEvaluacion = matriculacionService.obtenerCriteriosPorPeriodoLectivo(periodoLectivo);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Criterio de Evaluación eliminado correctamente"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}
	}

	public void editarParalelo() {
		try {
			institucionService.editarParalelo(paralelo);
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Parelo " + paralelo.getEtiqueta() + " registrado correctamente"));
		} catch (GestionEstudiantilException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		}

	}

	public void preparaMatriculas(Grado grado) {
		paralelo = new Paralelo();
	}

	public Paralelo getParalelo() {
		return this.paralelo;
	}

	public void setParalelo(Paralelo paralelo) {
		this.paralelo = paralelo;
	}

	public PeriodoLectivo getPeriodoLectivo() {
		return periodoLectivo;
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

	public List<Grados> getGradosDisponibles() {
		return gradosDisponibles;
	}

	public void setGradosDisponibles(List<Grados> gradosDisponibles) {
		this.gradosDisponibles = gradosDisponibles;
	}

	public List<Grados> getGradosAgregados() {
		return gradosAgregados;
	}

	public void setGradosAgregados(List<Grados> gradosAgregados) {
		this.gradosAgregados = gradosAgregados;
	}

	public CriterioDeEvaluacion getCriterioDeEvaluacion() {
		return criterioDeEvaluacion;
	}

	public void setCriterioDeEvaluacion(CriterioDeEvaluacion criterioDeEvaluacion) {
		this.criterioDeEvaluacion = criterioDeEvaluacion;
	}

	public List<CriterioDeEvaluacion> getCriteriosDeEvaluacion() {
		return criteriosDeEvaluacion;
	}

	public void setCriteriosDeEvaluacion(List<CriterioDeEvaluacion> criteriosDeEvaluacion) {
		this.criteriosDeEvaluacion = criteriosDeEvaluacion;
	}

	public List<Paralelo> getParalelos() {
		return paralelos;
	}

	public void setParalelos(List<Paralelo> paralelos) {
		this.paralelos = paralelos;
	}

	public List<Docente> getDocentes() {
		return docentes;
	}

	public void setDocentes(List<Docente> docentes) {
		this.docentes = docentes;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public List<Grado> getGrados() {
		return grados;
	}

	public void setGrados(List<Grado> grados) {
		this.grados = grados;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public List<GrupoDeEvaluacion> getGruposDeEvaluacion() {
		return gruposDeEvaluacion;
	}

	public void setGruposDeEvaluacion(List<GrupoDeEvaluacion> gruposDeEvaluacion) {
		this.gruposDeEvaluacion = gruposDeEvaluacion;
	}

	public GrupoDeEvaluacion getGrupoDeEvaluacion() {
		return grupoDeEvaluacion;
	}

	public void setGrupoDeEvaluacion(GrupoDeEvaluacion grupoDeEvaluacion) {
		this.grupoDeEvaluacion = grupoDeEvaluacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Grado getGrado() {
		return grado;
	}

	public void setGrado(Grado grado) {
		this.grado = grado;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
}