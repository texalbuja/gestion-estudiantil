package com.gestionestudiantil.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.gestionestudiantil.dao.AsignaturaDao;
import com.gestionestudiantil.dao.CriterioDeEvaluacionDao;
import com.gestionestudiantil.dao.DocenteDao;
import com.gestionestudiantil.dao.EstudianteDao;
import com.gestionestudiantil.dao.GradoDao;
import com.gestionestudiantil.dao.GrupoDeEvaluacionDao;
import com.gestionestudiantil.dao.InstitucionDao;
import com.gestionestudiantil.dao.ParaleloDao;
import com.gestionestudiantil.dao.PeriodoLectivoDao;
import com.gestionestudiantil.enums.Grados;
import com.gestionestudiantil.enums.Roles;
import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.exception.GestionEstudiantilPersistenceException;
import com.gestionestudiantil.model.CriterioDeEvaluacion;
import com.gestionestudiantil.model.Docente;
import com.gestionestudiantil.model.Estudiante;
import com.gestionestudiantil.model.Grado;
import com.gestionestudiantil.model.GrupoDeEvaluacion;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Paralelo;
import com.gestionestudiantil.model.PeriodoLectivo;
import com.gestionestudiantil.model.Usuario;

@Stateless
public class InstitucionService {
	@Inject
	private EstudianteDao estudianteDao;

	@Inject
	private ParaleloDao paraleloDao;

	@Inject
	private InstitucionDao institucionDao;

	@Inject
	private PeriodoLectivoDao periodoLectivoDao;

	@Inject
	private DocenteDao docenteDao;

	@Inject
	private SeguridadService seguridadService;

	@Inject
	private AsignaturaDao asignaturaDao;

	@Inject
	private GradoDao gradoDao;

	public List<Institucion> obtenerTodasLasInstituciones() {
		return institucionDao.obtenerTodos();
	}

	public Institucion guardarInstitucion(Institucion institucion) throws GestionEstudiantilException {
		try {
			institucion.setContador(0);
			return institucionDao.guardar(institucion);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se puede guardar la Institución", e);
		}
	}

	public void editarInstitucion(Institucion institucion) throws GestionEstudiantilException {
		try {
			institucionDao.editar(institucion);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se puede guardar la Institución", e);
		}
	}

	public void eliminarInstitucion(Institucion institucion) throws GestionEstudiantilException {
		try {
			if (institucion.getPeriodosLectivos().size() > 0) {
				throw new GestionEstudiantilException("Debe eliminar los periodos lectivos");
			}
			institucionDao.eliminar(institucion.getId());
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se puede eliminar la Institución", e);
		}
	}

	public List<Institucion> buscar(String criterio) {
		return institucionDao.buscar(criterio);
	}

	public void guardarPeriodoLectivo(PeriodoLectivo periodoLectivo, List<Grados> gradosHabilitados)
			throws GestionEstudiantilException {
		try {
			List<Grado> gradosList = new ArrayList<Grado>();
			periodoLectivo.setGrados(gradosList);
			for (Grados grados : gradosHabilitados) {
				Grado grado = new Grado();
				grado.setNivel(grados.getNivel());
				grado.setSubNivel(grados.getSubNivel());
				grado.setGrado(grados.getGrado());
				grado.setEtiqueta(grados.getDescripcionGrado());
				grado.setEtiquetaNivel(grados.getDescripcionNivel());
				grado.setEtiquetaSubNivel(grados.getDescripcionSubNivel());
				grado.setPeriodoLectivo(periodoLectivo);
				periodoLectivo.getGrados().add(grado);
			}
			List<GrupoDeEvaluacion> gruposDeEvaluacion = new ArrayList<GrupoDeEvaluacion>();
			gruposDeEvaluacion.add(new GrupoDeEvaluacion("q1_p1", "Quimestre 1 - Bloque 1", periodoLectivo,
					"quimestre1/parcial1.jsf", Boolean.TRUE, 1));
			gruposDeEvaluacion.add(new GrupoDeEvaluacion("q1_p2", "Quimestre 1 - Bloque 2", periodoLectivo,
					"quimestre1/parcial2.jsf", Boolean.TRUE, 2));
			gruposDeEvaluacion.add(new GrupoDeEvaluacion("q1_p3", "Quimestre 1 - Bloque 3", periodoLectivo,
					"quimestre1/parcial3.jsf", Boolean.FALSE, 3));
			gruposDeEvaluacion.add(
					new GrupoDeEvaluacion("q1", "Quimestre 1", periodoLectivo, "quimestre1.jsf", Boolean.FALSE, 0));
			gruposDeEvaluacion.add(new GrupoDeEvaluacion("q2_p1", "Quimestre 2 - Bloque 1", periodoLectivo,
					"quimestre2/parcial1.jsf", Boolean.FALSE, 4));
			gruposDeEvaluacion.add(new GrupoDeEvaluacion("q2_p2", "Quimestre 2 - Bloque 2", periodoLectivo,
					"quimestre2/parcial2.jsf", Boolean.FALSE, 5));
			gruposDeEvaluacion.add(new GrupoDeEvaluacion("q2_p3", "Quimestre 2 - Bloque 3", periodoLectivo,
					"quimestre2/parcial3.jsf", Boolean.FALSE, 6));
			gruposDeEvaluacion.add(
					new GrupoDeEvaluacion("q2", "Quimestre 2", periodoLectivo, "quimestre2.jsf", Boolean.FALSE, 0));
			gruposDeEvaluacion.add(new GrupoDeEvaluacion("em", "Examen de Mejoramiento", periodoLectivo,
					"mejoramiento.jsf", Boolean.FALSE, 0));
			gruposDeEvaluacion.add(new GrupoDeEvaluacion("es", "Examen Supletorio", periodoLectivo, "supletorio.jsf",
					Boolean.FALSE, 0));
			gruposDeEvaluacion.add(
					new GrupoDeEvaluacion("er", "Examen Remedial", periodoLectivo, "remedial.jsf", Boolean.FALSE, 0));
			gruposDeEvaluacion.add(new GrupoDeEvaluacion("eg", "Examen de Gracia", periodoLectivo, "gra" + "cia.jsf",
					Boolean.FALSE, 0));
			gruposDeEvaluacion.add(new GrupoDeEvaluacion("a", "Anual", periodoLectivo, "anual.jsf", Boolean.FALSE, 0));
			periodoLectivo.setGruposDeEvaluacion(gruposDeEvaluacion);
			periodoLectivo.setContador(0);
			periodoLectivo.setCodigoCertificado(obtenerCodigoCertificado(periodoLectivo));
			periodoLectivoDao.guardar(periodoLectivo);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error de Base de Datos", e);
		}
	}

	private Integer obtenerCodigoCertificado(PeriodoLectivo periodoLectivo) throws GestionEstudiantilPersistenceException {
		Institucion institucion = institucionDao.obtenerPorId(periodoLectivo.getInstitucion().getId());
		Integer contador = institucion.getContador() + 1;
		institucion.setContador(institucion.getContador() + 1);
		institucionDao.guardar(institucion);
		return contador;
	}

	public List<Estudiante> obtenerEstudiantesPorInstitucion(Institucion institucion) {
		return estudianteDao.obtenerEstudiantesPorInstitucion(institucion);
	}

	public void guardarParalelo(Paralelo paralelo) throws GestionEstudiantilException {
		try {
			paraleloDao.guardar(paralelo);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error de Base de Datos", e);
		}
	}

	public void editarParalelo(Paralelo paralelo) throws GestionEstudiantilException {
		try {
			paraleloDao.editar(paralelo);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error de base de datos", e);
		}
	}

	public void editarPeriodoLectivo(PeriodoLectivo periodoLectivo) throws GestionEstudiantilException {

		try {
			periodoLectivoDao.editar(periodoLectivo);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error de Base de Datos", e);
		}

	}

	public void eliminarPeriodoLectivo(PeriodoLectivo periodoLectivo) throws GestionEstudiantilException {

		for (Grado grado : periodoLectivo.getGrados()) {
			eliminarGrado(grado);
		}

		for (CriterioDeEvaluacion criterioDeEvaluacion : periodoLectivo.getCriterioDeEvaluacion()) {
			eliminarCriterioDeEvaluacion(criterioDeEvaluacion);
		}
		for (GrupoDeEvaluacion grupoDeEvaluacion : periodoLectivo.getGruposDeEvaluacion()) {
			eliminarGrupoDeEvaluacion(grupoDeEvaluacion);
		}
		try {
			periodoLectivoDao.eliminar(periodoLectivo.getId());
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al eliminar el estudiante", e);
		}

	}

	@Inject
	private GrupoDeEvaluacionDao grupoDeEvaluacionDao;

	private void eliminarGrupoDeEvaluacion(GrupoDeEvaluacion grupoDeEvaluacion) throws GestionEstudiantilException {
		try {
			grupoDeEvaluacionDao.eliminar(grupoDeEvaluacion.getId());
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al eliminar el grupo de evaluación");
		}
	}

	@Inject
	private CriterioDeEvaluacionDao criterioDeEvaluacionDao;

	private void eliminarCriterioDeEvaluacion(CriterioDeEvaluacion criterioDeEvaluacion)
			throws GestionEstudiantilException {
		try {
			criterioDeEvaluacionDao.eliminar(criterioDeEvaluacion.getId());
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se ha podido eliminar el criterio de Evaluación");
		}
	}

	private void eliminarGrado(Grado grado) throws GestionEstudiantilException {
		grado = gradoDao.obtenerPorId(grado.getId());
		if (grado.getParalelos().size() > 0) {
			throw new GestionEstudiantilException("Debe eliminar los paralelos");
		}
		try {
			gradoDao.eliminar(grado.getId());
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se ha podido eliminar el grado");
		}
	}

	public void registrarFuncionario(Institucion institucion, Usuario usuario, Roles rol)
			throws GestionEstudiantilException {
		try {
			if (usuario.getId() == null) {
				usuario = seguridadService.registrarUsuario(usuario, rol);
			} else {
				seguridadService.agregarRol(usuario, rol);
			}

			institucion = institucionDao.obtenerPorId(institucion.getId());
			switch (rol) {
			case RECTOR:
				institucion.setRector(usuario);
				break;
			case SECRETARIA_PRIMARIA:
				institucion.setSecretariaPrimaria(usuario);
				break;
			case SECRETARIA_SECUNDARIA:
				institucion.setSecretariaSecundaria(usuario);
				break;
			case INSPECTOR:
				institucion.setInspector(usuario);
				break;
			case DIRECTOR:
				institucion.setDirector(usuario);
				break;
			case VICERECTOR:
				institucion.setVicerrector(usuario);
				break;
			default:
				break;
			}

			institucionDao.editar(institucion);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al guardar la institución", e);
		}

	}

	public void agregarGrados(List<Grados> gradosAgregados, PeriodoLectivo periodoLectivo)
			throws GestionEstudiantilException {
		try {
			periodoLectivo = periodoLectivoDao.obtenerPorId(periodoLectivo.getId());
			for (Grados g : gradosAgregados) {
				Grado grado = new Grado();
				grado.setNivel(g.getNivel());
				grado.setSubNivel(g.getSubNivel());
				grado.setGrado(g.getGrado());
				grado.setEtiquetaNivel(g.getDescripcionNivel());
				grado.setEtiquetaSubNivel(g.getDescripcionSubNivel());
				grado.setEtiqueta(g.getDescripcionGrado());
				grado.setPeriodoLectivo(periodoLectivo);
				gradoDao.guardar(grado);
			}
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error de Base de Datos", e);
		}
	}

	public void eliminarDocente(Docente docente) throws GestionEstudiantilException {
		try {
			if (asignaturaDao.obtenerAsignaturasPorDocente(docente).size() > 0)
				throw new GestionEstudiantilException("Para eliminar se deben reasignar las materias del docente");
			docenteDao.eliminar(docente.getId());
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al eliminar el estudiante", e);
		}
	}

	public void actualizarDocente(Docente docente) throws GestionEstudiantilException {
		try {
			if (!docente.getUsuario().getIdentificacion().equals(docente.getIdentificacion())) {
				docente.getUsuario().setIdentificacion(docente.getIdentificacion());
				docente.getUsuario().setClave(seguridadService.cifrarCadenaEnSHA1(docente.getIdentificacion()));
			}
			docenteDao.editar(docente);

		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error de Base de Datos", e);
		}
	}

}