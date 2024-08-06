package com.gestionestudiantil.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.model.UploadedFile;

import com.gestionestudiantil.dao.AmbitoDeDesarrolloDao;
import com.gestionestudiantil.dao.AreaDao;
import com.gestionestudiantil.dao.AsignaturaDao;
import com.gestionestudiantil.dao.ContactoDao;
import com.gestionestudiantil.dao.CriterioDeEvaluacionDao;
import com.gestionestudiantil.dao.DestrezaDeDesarrolloDao;
import com.gestionestudiantil.dao.DocenteDao;
import com.gestionestudiantil.dao.EjeDeDesarrolloDao;
import com.gestionestudiantil.dao.EstudianteDao;
import com.gestionestudiantil.dao.EvaluacionAsignaturaDao;
import com.gestionestudiantil.dao.EvaluacionComportamientoDao;
import com.gestionestudiantil.dao.EvaluacionDestrezaDeDesarrolloDao;
import com.gestionestudiantil.dao.EvaluacionProyectoEscolarDao;
import com.gestionestudiantil.dao.GradoDao;
import com.gestionestudiantil.dao.GrupoDeEvaluacionDao;
import com.gestionestudiantil.dao.InformeAnualDao;
import com.gestionestudiantil.dao.InstitucionDao;
import com.gestionestudiantil.dao.MatriculaDao;
import com.gestionestudiantil.dao.ParaleloDao;
import com.gestionestudiantil.dao.ParametroDao;
import com.gestionestudiantil.dao.PeriodoLectivoDao;
import com.gestionestudiantil.dao.ProyectoEscolarDao;
import com.gestionestudiantil.dao.RolDao;
import com.gestionestudiantil.dao.UsuarioDao;
import com.gestionestudiantil.enums.Roles;
import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.exception.GestionEstudiantilPersistenceException;
import com.gestionestudiantil.model.AmbitoDeDesarrollo;
import com.gestionestudiantil.model.Area;
import com.gestionestudiantil.model.Asignatura;
import com.gestionestudiantil.model.Contacto;
import com.gestionestudiantil.model.CriterioDeEvaluacion;
import com.gestionestudiantil.model.DestrezaDeDesarrollo;
import com.gestionestudiantil.model.Direccion;
import com.gestionestudiantil.model.Docente;
import com.gestionestudiantil.model.EjeDeDesarrollo;
import com.gestionestudiantil.model.Estudiante;
import com.gestionestudiantil.model.EvaluacionAsignatura;
import com.gestionestudiantil.model.EvaluacionComportamiento;
import com.gestionestudiantil.model.EvaluacionDestrezaDeDesarrollo;
import com.gestionestudiantil.model.EvaluacionProyectoEscolar;
import com.gestionestudiantil.model.Grado;
import com.gestionestudiantil.model.GrupoDeEvaluacion;
import com.gestionestudiantil.model.InformeAnual;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Paralelo;
import com.gestionestudiantil.model.Parametro;
import com.gestionestudiantil.model.PeriodoLectivo;
import com.gestionestudiantil.model.ProyectoEscolar;
import com.gestionestudiantil.model.RolUsuario;
import com.gestionestudiantil.model.Usuario;

@Stateless
public class MatriculacionService {

	@Inject
	private EvaluacionService evaluacionService;

	@Inject
	private PeriodoLectivoDao periodoLectivoDao;

	@Inject
	private EstudianteDao estudianteDao;

	@Inject
	private DocenteDao docenteDao;

	@Inject
	private GradoDao gradoDao;
	
	@Inject
	private AreaDao areaDao;

	@Inject
	private MatriculaDao matriculaDao;

	@Inject
	private RolDao rolDao;

	@Inject
	private SeguridadService seguridadService;

	@Inject
	private ParaleloDao paraleloDao;

	@Inject
	private AsignaturaDao asignaturaDao;

	@Inject
	private EvaluacionAsignaturaDao evaluacionAsignaturaDao;

	@Inject
	private InformeAnualDao informeAnualDao;

	@Inject
	private ContactoDao contactoDao;

	@Inject
	private UsuarioDao usuarioDao;

	@Inject
	private CriterioDeEvaluacionDao criterioDeEvaluacionDao;

	@Inject
	private EvaluacionComportamientoDao evaluacionComportamientoDao;

	@Inject
	private InstitucionDao institucionDao;

	@Inject
	private GrupoDeEvaluacionDao grupoDeEvaluacionDao;

	@Inject
	private ParametroDao parametroDao;

	@Inject
	private AmbitoDeDesarrolloDao ambitoDeDesarrolloDao;

	public PeriodoLectivo obtenerPeriodoActivo() {
		return periodoLectivoDao.getPeriodoLectivoActivo();
	}

	public List<PeriodoLectivo> obtenerPeriodosLectivosPorInstitucion(Institucion institucion, String periodo) {
		return periodoLectivoDao.obtenerPeriodosLectivosPorInstitucion(institucion, periodo);
	}

	public void matricularEstudiante(Estudiante estudiante, Paralelo paralelo, Matricula matricula)
			throws GestionEstudiantilException {
		try {
			estudiante = guardarEstudiante(estudiante);
			estudiante = estudianteDao.obtenerPorId(estudiante.getId());
			paralelo = paraleloDao.obtenerPorId(paralelo.getId());
			Grado grado = paralelo.getGrado();
			matricula.setEstudiante(estudiante);
			matricula.setGrado(grado);
			matricula.setNumeroMatricula(obtenerNumeroDeMatricula(grado.getPeriodoLectivo()));
			matricula.setFecha(new Date());
			matricula = matriculaDao.guardar(matricula);
			asignarMatriculaAParalelo(matricula, paralelo);
			asignarCriteriosDeEvaluacion(matricula, paralelo);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("El estudiante ya existe", e);
		}

	}

	private String obtenerNumeroDeMatricula(PeriodoLectivo periodoLectivo)
			throws GestionEstudiantilPersistenceException {
		periodoLectivo = periodoLectivoDao.obtenerPorId(periodoLectivo.getId());
		String anio = periodoLectivo.getEtiqueta().substring(0, 4);
		String contador = String.format("%04d", (periodoLectivo.getContador() + 1));
		String seccion = periodoLectivo.getSeccion();
		String numeroDeMatricula = anio + seccion + periodoLectivo.getCodigoCertificado() + contador;
		periodoLectivo.setContador(periodoLectivo.getContador() + 1);
		periodoLectivoDao.guardar(periodoLectivo);
		return numeroDeMatricula;
	}

	public List<Grado> obtenerGradosPorPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		return gradoDao.obtenerGradosPorPeriodoLectivo(periodoLectivo);
	}

	public List<Area> obtenerAreasPorPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		return areaDao.obtenerAreasPorPeriodoLectivo(periodoLectivo);
	}

	public void guardarDocente(Docente docente) throws GestionEstudiantilException {
		try {
			Usuario usuario = new Usuario();
			usuario.setNombre(docente.getNombres() + " " + docente.getApellidos());
			usuario.setIdentificacion(docente.getIdentificacion());
			usuario.setCorreo(docente.getCorreo());
			usuario.setInstitucion(docente.getInstitucion());
			usuario.setClave(seguridadService.cifrarCadenaEnSHA1(docente.getIdentificacion()));
			RolUsuario rolEstudiante = new RolUsuario();
			rolEstudiante.setRol(rolDao.obtenerRol(Roles.DOCENTE));
			rolEstudiante.setUsuario(usuario);
			List<RolUsuario> roles = new ArrayList<RolUsuario>();
			roles.add(rolEstudiante);
			usuario.setRolUsuarioList(roles);
			docente.setUsuario(usuario);
			docenteDao.guardar(docente);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("El docente ya ha sido registrado", e);
		}

	}

	public List<Docente> obtenerDocentesPorInstitucion(Institucion institucion) {
		return docenteDao.obtenerDocentesPorInstitucion(institucion);
	}

	public List<Matricula> obtenerMatriculasPorParalelo(Paralelo paralelo) {
		return matriculaDao.obtenerEstudiantesPorParalelo(paralelo);
	}

	public void asignarMatriculaAParalelo(Matricula matricula, Paralelo paralelo) throws GestionEstudiantilException {
		try {
			matricula = matriculaDao.obtenerPorId(matricula.getId());
			paralelo = paraleloDao.obtenerPorId(paralelo.getId());
			paralelo.getEstudiantesMatriculados().add(matricula);
			matricula.setParalelo(paralelo);
			matricula.setGrado(paralelo.getGrado());
			matricula = matriculaDao.editar(matricula);
			InformeAnual informeAnual = new InformeAnual();
			informeAnual.setInformesAnuales(new ArrayList<EvaluacionAsignatura>());
			informeAnual.setMatricula(matricula);
			informeAnual = informeAnualDao.guardar(informeAnual);
			for (Asignatura asignatura : paralelo.getAsignaturas()) {

				EvaluacionAsignatura evaluacionAsignatura = new EvaluacionAsignatura();
				evaluacionAsignatura.setInformeAnual(informeAnual);
				evaluacionAsignatura.setAsignatura(asignatura);
				informeAnual.getInformesAnuales().add(evaluacionAsignatura);

				if (asignatura.getEvaluaciones() == null)
					asignatura.setEvaluaciones(new ArrayList<EvaluacionAsignatura>());
				asignatura.getEvaluaciones().add(evaluacionAsignatura);
				evaluacionAsignaturaDao.guardar(evaluacionAsignatura);
			}
			for (ProyectoEscolar proyectoEscolar : paralelo.getProyectosEscolares()) {
				EvaluacionProyectoEscolar evaluacionProyectoEscolar = new EvaluacionProyectoEscolar();
				evaluacionProyectoEscolar.setMatricula(matricula);
				evaluacionProyectoEscolar.setProyectoEscolar(proyectoEscolar);
				evaluacionProyectoEscolarDao.guardar(evaluacionProyectoEscolar);
			}
			if (paralelo.getGrado().getGrado() <= 3) {
				List<DestrezaDeDesarrollo> destrezasDeDesarrolloPorParalelo = destrezaDeDesarrolloDao
						.obtenerDestrezasDeDesarrolloPorParalelo(paralelo);
				for (DestrezaDeDesarrollo destrezaDeDesarrollo : destrezasDeDesarrolloPorParalelo) {
					EvaluacionDestrezaDeDesarrollo evaluacionDestrezaDeDesarrollo = new EvaluacionDestrezaDeDesarrollo();
					evaluacionDestrezaDeDesarrollo.setMatricula(matricula);
					evaluacionDestrezaDeDesarrollo.setDestrezaDeDesarrollo(destrezaDeDesarrollo);
					evaluacionDestrezaDeDesarrolloDao.guardar(evaluacionDestrezaDeDesarrollo);
				}

			}

		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error de Base de Datos", e);
		}

	}

	public void asignarCriteriosDeEvaluacion(Matricula matricula, Paralelo paralelo)
			throws GestionEstudiantilException {
		try {
			matricula = matriculaDao.obtenerPorId(matricula.getId());
			matricula.setEvaluacionesComportamiento(new ArrayList<EvaluacionComportamiento>());
			PeriodoLectivo periodoLectivo = periodoLectivoDao
					.obtenerPorId(paralelo.getGrado().getPeriodoLectivo().getId());
			for (CriterioDeEvaluacion criterio : periodoLectivo.getCriterioDeEvaluacion()) {
				EvaluacionComportamiento evaluacion = new EvaluacionComportamiento();
				evaluacion.setCriterioDeEvaluacion(criterio);
				evaluacion.setMatricula(matricula);
				matricula.getEvaluacionesComportamiento().add(evaluacion);
				evaluacionComportamientoDao.guardar(evaluacion);
			}

		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error de Base de Datos", e);
		}

	}

	public void guardarAsignatura(Asignatura asignatura, Docente docente, Paralelo paralelo)
			throws GestionEstudiantilException {
		try {
			docente = docenteDao.obtenerPorId(docente.getId());
			paralelo = paraleloDao.obtenerPorId(paralelo.getId());
			asignatura.setDocente(docente);
			asignatura.setParalelo(paralelo);
			asignatura = asignaturaDao.guardar(asignatura);
			for (Matricula matricula : paralelo.getEstudiantesMatriculados()) {
				EvaluacionAsignatura evaluacionAsignatura = new EvaluacionAsignatura();
				evaluacionAsignatura.setAsignatura(asignatura);
				evaluacionAsignatura.setInformeAnual(matricula.getInformeAnual());
				evaluacionAsignaturaDao.guardar(evaluacionAsignatura);
			}
			for (InformeAnual informeAnual : informeAnualDao
					.obtenerInformesAnualesPorParalelo(asignatura.getParalelo())) {
				evaluacionService.evaluarPromediosYResultado(informeAnual);
			}

		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al guardar la Asignatura", e);
		}

	}

	public List<Asignatura> obtenerAsignaturasPorParalelo(Paralelo paralelo) {
		return asignaturaDao.obtenerAsignaturasPorParalelo(paralelo);
	}

	public List<Paralelo> obtenerParalelosPorGrado(Grado grado) {
		return paraleloDao.obtenerParalelosPorGrado(grado);
	}

	public Estudiante guardarEstudiante(Estudiante estudiante) throws GestionEstudiantilException {
		try {
			if (estudiante.getId() != null) {
				return estudianteDao.obtenerPorId(estudiante.getId());
			}
			Usuario usuario = new Usuario();
			usuario.setNombre(estudiante.getNombres() + " " + estudiante.getApellidos());
			usuario.setIdentificacion(estudiante.getIdentificacion());
			usuario.setCorreo(estudiante.getCorreo());
			usuario.setInstitucion(estudiante.getInstitucion());
			usuario.setClave(seguridadService.cifrarCadenaEnSHA1(estudiante.getIdentificacion()));
			RolUsuario rolEstudiante = new RolUsuario();
			rolEstudiante.setRol(this.rolDao.obtenerRol(Roles.ESTUDIANTE));
			rolEstudiante.setUsuario(usuario);
			List<RolUsuario> roles = new ArrayList<RolUsuario>();
			roles.add(rolEstudiante);
			usuario.setRolUsuarioList(roles);
			estudiante.setUsuario(usuario);
			return estudianteDao.guardar(estudiante);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("El estudiante ya ha sido registrado", e);
		}

	}

	public void editarEstudiante(Estudiante estudiante) throws GestionEstudiantilException {
		try {
			estudianteDao.editar(estudiante);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error de base de datos", e);
		}

	}

	public List<Asignatura> obtenerAsignaturasPorDocente(Docente docente) {
		List<Asignatura> asignaturas = asignaturaDao.obtenerAsignaturasPorDocente(docente);
		List<Asignatura> asignaturasPorDocente = new ArrayList<>();
		for (Asignatura asignatura : asignaturas) {
			if (asignatura.getAsignaturas().isEmpty())
				asignaturasPorDocente.add(asignatura);
		}
		return asignaturasPorDocente;
	}

	public List<Paralelo> obtenerParalelosPorTutor(Docente docente) {
		return paraleloDao.obtenerParalelosPorTutor(docente);
	}

	public Grado obtenerGradoPorId(Long id) {
		return gradoDao.obtenerPorId(id);
	}

	public Contacto obtenerContactoPorIdentificacion(String identificacion) {
		Contacto contacto;

		try {
			contacto = contactoDao.obtenerContactoPorIdentificacion(identificacion);

		} catch (PersistenceException e) {
			contacto = new Contacto();
			contacto.setDomicilio(new Direccion());
			contacto.setRefugiado("Si");
			contacto.setParentesco("Padre");
		}
		return contacto;

	}

	public List<Matricula> obtenerMatriculasPorPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		return matriculaDao.obtenerMatriculasPorPeriodoLectivo(periodoLectivo);
	}

	public void editarEstudiante(Estudiante estudiante, String identificacionAnterior)
			throws GestionEstudiantilException {
		try {
			Usuario usuario = usuarioDao.obtenerUsuarioPorIdentificacion(identificacionAnterior);
			usuario.setNombre(estudiante.getNombres() + " " + estudiante.getApellidos());
			usuario.setIdentificacion(estudiante.getIdentificacion());
			usuario.setCorreo(estudiante.getCorreo());
			usuario.setInstitucion(estudiante.getInstitucion());
			usuario.setClave(seguridadService.cifrarCadenaEnSHA1(estudiante.getIdentificacion()));
			RolUsuario rolEstudiante = new RolUsuario();
			rolEstudiante.setRol(rolDao.obtenerRol(Roles.ESTUDIANTE));
			rolEstudiante.setUsuario(usuario);
			List<RolUsuario> roles = new ArrayList<RolUsuario>();
			roles.add(rolEstudiante);
			usuario.setRolUsuarioList(roles);
			estudiante.setUsuario(usuario);
			estudianteDao.editar(estudiante);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al editar el Estudiante", e);
		}

	}

	public void desvincularMatricula(Matricula matricula) throws GestionEstudiantilException {
		try {
			matricula = matriculaDao.obtenerPorId(matricula.getId());
			InformeAnual informeAnual = informeAnualDao.obtenerPorMatricula(matricula);
			matricula.setFecha(new Date());
			matricula.setGrado(null);
			matricula.setParalelo(null);
			matriculaDao.editar(matricula);
			informeAnual.setMatricula(null);
			informeAnualDao.eliminar(informeAnual.getId());

		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error de Base de Datos", e);
		}
	}

	public void eliminarMatricula(Matricula matricula) throws GestionEstudiantilException {
		try {
			if (matricula.getEstudiante().getMatriculas().size() > 1) {
				matriculaDao.eliminar(matricula.getId());
			} else {
				estudianteDao.eliminar(matricula.getEstudiante().getId());
			}
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al eliminar la matrícula", e);
		}
	}

	public Institucion obtenerInstitucionPorId(Long id) {
		return institucionDao.obtenerPorId(id);
	}

	public CriterioDeEvaluacion guardarCriterioDeEvaluacion(CriterioDeEvaluacion criterioDeEvaluacion,
			PeriodoLectivo periodoLectivo) throws GestionEstudiantilException {
		try {
			periodoLectivo = periodoLectivoDao.obtenerPorId(periodoLectivo.getId());
			periodoLectivo.getCriterioDeEvaluacion().add(criterioDeEvaluacion);
			criterioDeEvaluacion.setPeriodoLectivo(periodoLectivo);
			criterioDeEvaluacion.setEvaluaciones(new ArrayList<EvaluacionComportamiento>());
			for (Grado grado : periodoLectivo.getGrados()) {
				for (Paralelo paralelo : grado.getParalelos()) {
					for (Matricula matricula : paralelo.getEstudiantesMatriculados()) {
						EvaluacionComportamiento evaluacionComportamiento = new EvaluacionComportamiento();
						evaluacionComportamiento.setMatricula(matricula);
						evaluacionComportamiento.setCriterioDeEvaluacion(criterioDeEvaluacion);
						criterioDeEvaluacion.getEvaluaciones().add(evaluacionComportamiento);
					}
				}
			}
			return criterioDeEvaluacionDao.guardar(criterioDeEvaluacion);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al crear el criterio de evaluación", e);
		}
	}

	public List<CriterioDeEvaluacion> obtenerCriteriosPorPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		return criterioDeEvaluacionDao.obtenerPorPeriodoLectivo(periodoLectivo);
	}

	public Matricula obtenerMatriculaPorId(Long id) {
		return matriculaDao.obtenerPorId(id);
	}

	public void actualizarEstudiante(Estudiante estudiante) throws GestionEstudiantilException {
		try {
			if (!estudiante.getUsuario().getIdentificacion().equals(estudiante.getIdentificacion())) {
				estudiante.getUsuario().setIdentificacion(estudiante.getIdentificacion());
				estudiante.getUsuario().setClave(seguridadService.cifrarCadenaEnSHA1(estudiante.getIdentificacion()));
			}
			estudianteDao.editar(estudiante);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al actualizar el estudiante", e);
		}
	}

	public void eliminarEstudiante(Estudiante estudiante) throws GestionEstudiantilException {
		try {
			estudianteDao.eliminar(estudiante.getId());
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al eliminar el estudiante", e);
		}
	}

	public void editarAsignatura(Asignatura asignatura) throws GestionEstudiantilException {
		try {
			asignaturaDao.editar(asignatura);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al actualizar la asignatura", e);
		}
	}

	public void eliminarAsignatura(Asignatura asignatura) throws GestionEstudiantilException {
		try {
			for (EvaluacionAsignatura evaluacionAsignatura : asignatura.getEvaluaciones()) {
				evaluacionAsignaturaDao.eliminar(evaluacionAsignatura.getId());
			}
			asignaturaDao.eliminar(asignatura.getId());
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al eliminar la asignatura", e);
		}
	}

	public void eliminarGrado(Grado grado) throws GestionEstudiantilException {
		try {
			if (grado.getParalelos().size() > 0)
				throw new GestionEstudiantilException("Debe eliminar los paralelos");
			gradoDao.eliminar(grado.getId());
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al eliminar la asignatura", e);
		}
	}

	public void eliminarParalelo(Paralelo paralelo) throws GestionEstudiantilException {
		try {
			if (paralelo.getEstudiantesMatriculados().size() > 0)
				throw new GestionEstudiantilException("Debe eliminar los estudiantes matriculados");
			if (paralelo.getAsignaturas().size() > 0)
				throw new GestionEstudiantilException("Debe eliminar las asignaturas");
			paraleloDao.eliminar(paralelo.getId());
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al eliminar el paralelo", e);
		}
	}

	public List<Paralelo> obtenerParalelosPorPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		return paraleloDao.obtenerParalelosPorPeriodoLectivo(periodoLectivo);
	}

	public void editarCriterioDeEvaluacion(CriterioDeEvaluacion criterioDeEvaluacion, PeriodoLectivo periodoLectivo)
			throws GestionEstudiantilException {
		try {
			criterioDeEvaluacionDao.editar(criterioDeEvaluacion);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al editar el criterio de evaluación", e);
		}
	}

	public void eliminarCriterioDeEvaluacion(CriterioDeEvaluacion criterioDeEvaluacion)
			throws GestionEstudiantilException {
		try {
			criterioDeEvaluacionDao.eliminar(criterioDeEvaluacion.getId());
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al eliminar el criterio de evaluación", e);
		}

	}

	public List<Asignatura> obtenerAsignaturasPorUsuario(Usuario usuario) {
		try {
			Docente docente = docenteDao.obtenerDocentePorIdentificacion(usuario.getIdentificacion());
			return asignaturaDao.obtenerAsignaturasPorDocente(docente);
		} catch (GestionEstudiantilPersistenceException e) {
			return new ArrayList<Asignatura>();
		}
	}

	public InformeAnual obtenerInformeAnualPorMatricula(Matricula matricula) {
		return informeAnualDao.obtenerPorMatricula(matricula);
	}

	public List<GrupoDeEvaluacion> obtenerGruposDeEvaluacionPorPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		return grupoDeEvaluacionDao.obtenerGrupoDeEvaluacionPorPeriodoLectivo(periodoLectivo);
	}

	public void editarGrupoDeEvaluacion(GrupoDeEvaluacion grupoDeEvaluacion) throws GestionEstudiantilException {
		try {
			grupoDeEvaluacionDao.editar(grupoDeEvaluacion);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se puede eliminar el usuario");
		}
	}

	public List<PeriodoLectivo> buscarPeriodoLectivo(String criterio) {
		return periodoLectivoDao.buscarPeriodoLectivo(criterio);
	}

	public void cambiarMatriculaDeParalelo(Matricula matricula, List<String> asignaturasAAsignar,
			List<String> asignaturasAsignadas, Paralelo paraleloAAsignar) throws GestionEstudiantilException {
		matricula = matriculaDao.obtenerPorId(matricula.getId());
		paraleloAAsignar = paraleloDao.obtenerPorId(paraleloAAsignar.getId());
		matricula.setParalelo(paraleloAAsignar);
		for (int i = 0; i < asignaturasAAsignar.size() && i < asignaturasAsignadas.size(); i++) {
			Asignatura asignaturaAAsignar = asignaturaDao.obtenerPorId(Long.parseLong(asignaturasAAsignar.get(i)));
			Asignatura asignaturaAsignada = asignaturaDao.obtenerPorId(Long.parseLong(asignaturasAsignadas.get(i)));

			for (EvaluacionAsignatura evaluacionAsignatura : matricula.getInformeAnual().getInformesAnuales()) {
				if (evaluacionAsignatura.getAsignatura().equals(asignaturaAsignada)) {
					evaluacionAsignatura.setAsignatura(asignaturaAAsignar);
				}
			}
		}
		try {
			List<EvaluacionAsignatura> detallesInformeAnualABorrar = new ArrayList<EvaluacionAsignatura>();
			for (EvaluacionAsignatura evaluacionAsignatura : matricula.getInformeAnual().getInformesAnuales()) {
				Boolean seDebeBorrar = true;
				for (Asignatura asignatura : paraleloAAsignar.getAsignaturas()) {
					if (asignatura.equals(evaluacionAsignatura.getAsignatura())) {
						seDebeBorrar = false;
					}
				}
				if (seDebeBorrar) {
					detallesInformeAnualABorrar.add(evaluacionAsignatura);
				}
			}

			for (EvaluacionAsignatura evaluacionAsignatura : detallesInformeAnualABorrar) {
				evaluacionAsignaturaDao.eliminar(evaluacionAsignatura.getId());
			}
			matricula.getInformeAnual().getInformesAnuales().removeAll(detallesInformeAnualABorrar);

			for (Asignatura asignatura : paraleloAAsignar.getAsignaturas()) {
				Boolean seDebeAgregar = true;
				for (EvaluacionAsignatura evaluacionAsignatura : matricula.getInformeAnual().getInformesAnuales()) {
					if (asignatura.equals(evaluacionAsignatura.getAsignatura())) {
						seDebeAgregar = false;
					}
				}
				if (seDebeAgregar) {
					EvaluacionAsignatura evaluacionAsignatura = new EvaluacionAsignatura();
					evaluacionAsignatura.setInformeAnual(matricula.getInformeAnual());
					evaluacionAsignatura.setAsignatura(asignatura);
					matricula.getInformeAnual().getInformesAnuales().add(evaluacionAsignatura);

					if (asignatura.getEvaluaciones() == null)
						asignatura.setEvaluaciones(new ArrayList<EvaluacionAsignatura>());
					asignatura.getEvaluaciones().add(evaluacionAsignatura);
					evaluacionAsignaturaDao.guardar(evaluacionAsignatura);
				}
			}

			matriculaDao.editar(matricula);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se ha podido realizar el cambio de Paralelo", e);
		}

	}

	public Asignatura obtenerAsignaturaPorId(Long id) {
		return asignaturaDao.obtenerPorId(id);
	}

	public void matricularEstudiantesDesdeExcel(UploadedFile file, Paralelo paralelo)
			throws GestionEstudiantilException, IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(file.getInputstream());
		HSSFSheet worksheet = workbook.getSheetAt(0);

		for (int i = 1; i < worksheet.getLastRowNum(); i++) {
			HSSFRow row = worksheet.getRow(i);
			Estudiante estudiante = obtenerEstudianteDesdeFila(row);

			Matricula matricula = new Matricula();
			matricula.setNumeroMatricula("Primera");
			matricula.setObservacion("Carga Masiva");
			matricula.setParalelo(paralelo);

			matricularEstudiante(estudiante, paralelo, matricula);
		}

	}

	private Estudiante obtenerEstudianteDesdeFila(HSSFRow row) {
		HSSFCell cedulaCell = row.getCell(0);
		HSSFCell apellidosCell = row.getCell(1);
		HSSFCell nombresCell = row.getCell(2);
		HSSFCell correoCell = row.getCell(3);
		HSSFCell sexoCell = row.getCell(4);
		Estudiante estudiante = new Estudiante();
		estudiante.setIdentificacion(cedulaCell.getStringCellValue());
		estudiante.setApellidos(apellidosCell.getStringCellValue());
		estudiante.setNombres(nombresCell.getStringCellValue());
		estudiante.setCorreo(correoCell.getStringCellValue());
		estudiante.setSexo(sexoCell.getStringCellValue());
		return estudiante;
	}

	public String obtenerCodigoTemporal() throws GestionEstudiantilException {
		try {
			Parametro parametroSecuencia;
			parametroSecuencia = parametroDao.obtenerParametroPorCodigo("secuencia");
			parametroSecuencia.setValorLong(parametroSecuencia.getValorLong() + 1L);
			parametroDao.guardar(parametroSecuencia);
			return parametroSecuencia.getValorLong().toString();
		} catch (GestionEstudiantilPersistenceException pe) {
			throw new GestionEstudiantilException("Error al obtener la secuencia");
		}
	}

	@Inject
	private ProyectoEscolarDao proyectoEscolarDao;

	@Inject
	private EvaluacionProyectoEscolarDao evaluacionProyectoEscolarDao;

	public ProyectoEscolar guardarProyectoEscolar(ProyectoEscolar proyectoEscolar) throws GestionEstudiantilException {
		try {
			proyectoEscolar = proyectoEscolarDao.guardar(proyectoEscolar);
			for (Matricula matricula : matriculaDao.obtenerEstudiantesPorParalelo(proyectoEscolar.getParalelo())) {
				EvaluacionProyectoEscolar evaluacionProyectoEscolar = new EvaluacionProyectoEscolar();
				evaluacionProyectoEscolar.setMatricula(matricula);
				evaluacionProyectoEscolar.setProyectoEscolar(proyectoEscolar);
				evaluacionProyectoEscolarDao.guardar(evaluacionProyectoEscolar);

			}
			return proyectoEscolar;

		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se pudo guardar el Proyecto Escolar");
		}
	}

	public List<ProyectoEscolar> obtenerProyectosEscolaresPorParalelo(Paralelo paralelo) {
		return proyectoEscolarDao.obtenerProyectosEscolaresPorParalelo(paralelo);
	}

	public List<ProyectoEscolar> obtenerProyectosEscolaresPorUsuario(Usuario usuario)
			throws GestionEstudiantilException {
		Docente docente;
		try {
			docente = docenteDao.obtenerDocentePorIdentificacion(usuario.getIdentificacion());
			return proyectoEscolarDao.obtenerProyectosEscolaresPorDocente(docente);
		} catch (GestionEstudiantilPersistenceException e) {
			return new ArrayList<ProyectoEscolar>();
		}

	}

	public List<ProyectoEscolar> obtenerProyectosEscolaresPorDocente(Docente docente) {

		return proyectoEscolarDao.obtenerProyectosEscolaresPorDocente(docente);

	}

	public void editarProyectoEscolar(ProyectoEscolar proyectoEscolar) throws GestionEstudiantilException {
		try {
			proyectoEscolarDao.editar(proyectoEscolar);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se puede editar el proyecto Escolar");
		}
	}

	public void eliminarProyectoEscolar(ProyectoEscolar proyectoEscolar) throws GestionEstudiantilException {
		try {
			proyectoEscolarDao.eliminar(proyectoEscolar.getId());
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se puede eliminar el proyecto Escolar");
		}
	}

	public List<Docente> obtenerDocentesPorCriterio(String criterio, Institucion institucion) {
		return docenteDao.obtenerDocentesPorCriterio(criterio, institucion);
	}

	public List<Paralelo> obtenerParalelosPorUsuario(Usuario usuario) {
		try {
			Docente docente = docenteDao.obtenerDocentePorIdentificacion(usuario.getIdentificacion());
			return paraleloDao.obtenerParalelosPorTutor(docente);
		} catch (GestionEstudiantilPersistenceException e) {
			return new ArrayList<Paralelo>();
		}
	}

	public void matricularEstudianteEnPreparatoria(Estudiante estudiante, Paralelo paralelo, Matricula matricula)
			throws GestionEstudiantilException {
		try {
			estudiante = guardarEstudiante(estudiante);
			estudiante = estudianteDao.obtenerPorId(estudiante.getId());
			Grado grado = paralelo.getGrado();
			matricula.setEstudiante(estudiante);
			matricula.setGrado(grado);
			matricula.setParalelo(paralelo);
			matricula.setNumeroMatricula(obtenerNumeroDeMatricula(grado.getPeriodoLectivo()));
			matricula.setFecha(new Date());
			matricula = matriculaDao.guardar(matricula);
			paralelo = paraleloDao.obtenerPorId(paralelo.getId());
			asignarMatriculaAParalelo(matricula, paralelo);
			asignarCriteriosDeEvaluacion(matricula, paralelo);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se ha podido matricular al estudiante");
		}

	}

	public List<Estudiante> obtenerEstudiantesAprobados(Grado grado) {
		return estudianteDao.obtenerEstudiantesAprobadosPorGrado(grado);
	}

	public List<Estudiante> obtenerEstudiantesPorParalelo(Paralelo paralelo) {
		return estudianteDao.obtenerEstudiantesPorParalelo(paralelo);
	}

	public List<Estudiante> buscarEstudiantes(String apellidos, String identificacion, Institucion institucion)
			throws GestionEstudiantilException {
		try {
			return estudianteDao.buscarEstudiantes(apellidos, identificacion, institucion);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Consulta sin resultados");
		}
	}

	public List<Docente> buscarDocentes(String apellidos, String identificacion, Institucion institucion)
			throws GestionEstudiantilException {
		try {
			return docenteDao.buscarDocentes(apellidos, identificacion, institucion);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Consulta sin resultados");
		}
	}

	@Inject
	private EjeDeDesarrolloDao ejeDeDesarrolloDao;

	public void crearEjesDeDesarrollo(Paralelo paralelo) throws GestionEstudiantilException {
		try {
			EjeDeDesarrollo desarrolloPersonalYSocial = new EjeDeDesarrollo();
			desarrolloPersonalYSocial.setEtiqueta("DESARROLLO PERSONAL Y SOCIAL");
			desarrolloPersonalYSocial.setParalelo(paralelo);
			desarrolloPersonalYSocial.setOrden("1");

			List<AmbitoDeDesarrollo> ambitosDPYS = new ArrayList<>();

			if (paralelo.getGrado().getGrado() == 1) {
				AmbitoDeDesarrollo vinculacionEmocionalYSocial = new AmbitoDeDesarrollo();
				vinculacionEmocionalYSocial.setEtiqueta("VINCULACIÓN EMOCIONAL Y SOCIAL");
				vinculacionEmocionalYSocial.setEjeDeDesarrollo(desarrolloPersonalYSocial);
				vinculacionEmocionalYSocial.setOrden("1.1");
				ambitosDPYS.add(vinculacionEmocionalYSocial);
			}
			if (paralelo.getGrado().getGrado() == 2 || paralelo.getGrado().getGrado() == 3) {
				AmbitoDeDesarrollo identidadYAutonomia = new AmbitoDeDesarrollo();
				identidadYAutonomia.setEtiqueta("IDENTIDAD Y AUTONOMÍA");
				identidadYAutonomia.setEjeDeDesarrollo(desarrolloPersonalYSocial);
				identidadYAutonomia.setOrden("1.1");
				ambitosDPYS.add(identidadYAutonomia);

				AmbitoDeDesarrollo convivencia = new AmbitoDeDesarrollo();
				convivencia.setEtiqueta("CONVIVENCIA");
				convivencia.setEjeDeDesarrollo(desarrolloPersonalYSocial);
				convivencia.setOrden("1.2");
				ambitosDPYS.add(convivencia);
			}

			desarrolloPersonalYSocial.setAmbitosDeDesarrollo(ambitosDPYS);
			ejeDeDesarrolloDao.guardar(desarrolloPersonalYSocial);

			EjeDeDesarrollo descubrimientoDelMedioNaturalYSocial = new EjeDeDesarrollo();
			descubrimientoDelMedioNaturalYSocial.setEtiqueta("DESCUBRIMIENTO DEL MEDIO NATURAL Y SOCIAL");
			descubrimientoDelMedioNaturalYSocial.setParalelo(paralelo);
			descubrimientoDelMedioNaturalYSocial.setOrden("2");

			List<AmbitoDeDesarrollo> ambitosDDMNYS = new ArrayList<>();

			if (paralelo.getGrado().getGrado() == 1) {
				AmbitoDeDesarrollo componenteDescubrimientoDelMedioNaturalYSocial = new AmbitoDeDesarrollo();
				componenteDescubrimientoDelMedioNaturalYSocial.setEtiqueta("DESCUBRIMIENTO DEL MEDIO NATURAL Y SOCIAL");
				componenteDescubrimientoDelMedioNaturalYSocial.setEjeDeDesarrollo(descubrimientoDelMedioNaturalYSocial);
				componenteDescubrimientoDelMedioNaturalYSocial.setOrden("2.1");
				ambitosDDMNYS.add(componenteDescubrimientoDelMedioNaturalYSocial);
			}
			if (paralelo.getGrado().getGrado() == 2) {
				AmbitoDeDesarrollo relacionesConElMedioNaturalYCultural = new AmbitoDeDesarrollo();
				relacionesConElMedioNaturalYCultural.setEtiqueta("RELACIONES CON EL MEDIO NATURAL Y CULTURAL");
				relacionesConElMedioNaturalYCultural.setEjeDeDesarrollo(descubrimientoDelMedioNaturalYSocial);
				relacionesConElMedioNaturalYCultural.setOrden("2.1");
				ambitosDDMNYS.add(relacionesConElMedioNaturalYCultural);

				AmbitoDeDesarrollo relacionesLogicoMatematicas = new AmbitoDeDesarrollo();
				relacionesLogicoMatematicas.setEtiqueta("RELACIONES LÓGICO/MATEMÁTICAS");
				relacionesLogicoMatematicas.setEjeDeDesarrollo(descubrimientoDelMedioNaturalYSocial);
				relacionesLogicoMatematicas.setOrden("2.2");
				ambitosDDMNYS.add(relacionesLogicoMatematicas);
			}

			if (paralelo.getGrado().getGrado() == 3) {
				AmbitoDeDesarrollo descubrimientoYComprensionDelMedioNaturalYCultural = new AmbitoDeDesarrollo();
				descubrimientoYComprensionDelMedioNaturalYCultural
						.setEtiqueta("DESCUBRIMIENTO Y COMPRENSIÓN DEL MEDIO NATURAL Y CULTURAL");
				descubrimientoYComprensionDelMedioNaturalYCultural
						.setEjeDeDesarrollo(descubrimientoDelMedioNaturalYSocial);
				descubrimientoYComprensionDelMedioNaturalYCultural.setOrden("2.1");
				ambitosDDMNYS.add(descubrimientoYComprensionDelMedioNaturalYCultural);

				AmbitoDeDesarrollo relacionesLogicoMatematicas = new AmbitoDeDesarrollo();
				relacionesLogicoMatematicas.setEtiqueta("RELACIONES LÓGICO/MATEMÁTICAS");
				relacionesLogicoMatematicas.setEjeDeDesarrollo(descubrimientoDelMedioNaturalYSocial);
				relacionesLogicoMatematicas.setOrden("2.2");
				ambitosDDMNYS.add(relacionesLogicoMatematicas);
			}
			descubrimientoDelMedioNaturalYSocial.setAmbitosDeDesarrollo(ambitosDDMNYS);
			ejeDeDesarrolloDao.guardar(descubrimientoDelMedioNaturalYSocial);

			EjeDeDesarrollo expresionYComunicacion = new EjeDeDesarrollo();
			expresionYComunicacion.setEtiqueta("EXPRESIÓN Y COMUNICACIÓN");
			expresionYComunicacion.setParalelo(paralelo);
			expresionYComunicacion.setOrden("3");

			List<AmbitoDeDesarrollo> ambitosEYC = new ArrayList<>();

			if (paralelo.getGrado().getGrado() == 1) {
				AmbitoDeDesarrollo manifestacionDelLenguajeVerbal = new AmbitoDeDesarrollo();
				manifestacionDelLenguajeVerbal.setEtiqueta("MANIFESTACIÓN DEL LEGUAJE VERBAL Y NO VERBAL");
				manifestacionDelLenguajeVerbal.setEjeDeDesarrollo(expresionYComunicacion);
				manifestacionDelLenguajeVerbal.setOrden("3.1");
				ambitosEYC.add(manifestacionDelLenguajeVerbal);

				AmbitoDeDesarrollo exploracionDelCuerpoYMotricidad = new AmbitoDeDesarrollo();
				exploracionDelCuerpoYMotricidad.setEtiqueta("EXPLORACIÓN DEL CUERPO Y MOTRICIDAD");
				exploracionDelCuerpoYMotricidad.setEjeDeDesarrollo(expresionYComunicacion);
				exploracionDelCuerpoYMotricidad.setOrden("3.2");
				ambitosEYC.add(exploracionDelCuerpoYMotricidad);
			}
			if (paralelo.getGrado().getGrado() == 2) {
				AmbitoDeDesarrollo comprensionYExpresionDelLenguaje = new AmbitoDeDesarrollo();
				comprensionYExpresionDelLenguaje.setEtiqueta("COMPRENSIÓN Y EXPRESIÓN DEL LENGUAJE");
				comprensionYExpresionDelLenguaje.setEjeDeDesarrollo(expresionYComunicacion);
				comprensionYExpresionDelLenguaje.setOrden("3.1");
				ambitosEYC.add(comprensionYExpresionDelLenguaje);

				AmbitoDeDesarrollo expresionArtistica = new AmbitoDeDesarrollo();
				expresionArtistica.setEtiqueta("EXPRESIÓN ARTÍSTICA");
				expresionArtistica.setEjeDeDesarrollo(expresionYComunicacion);
				expresionArtistica.setOrden("3.2");
				ambitosEYC.add(expresionArtistica);

				AmbitoDeDesarrollo expresionCorporalYMotricidad = new AmbitoDeDesarrollo();
				expresionCorporalYMotricidad.setEtiqueta("EXPRESIÓN CORPORAL Y MOTRICIDAD");
				expresionCorporalYMotricidad.setEjeDeDesarrollo(expresionYComunicacion);
				expresionCorporalYMotricidad.setOrden("3.3");
				ambitosEYC.add(expresionCorporalYMotricidad);
			}

			if (paralelo.getGrado().getGrado() == 3) {
				AmbitoDeDesarrollo comprensionYExpresionOralYEscrita = new AmbitoDeDesarrollo();
				comprensionYExpresionOralYEscrita.setEtiqueta("COMPRENSIÓN Y EXPRESIÓN ORAL Y ESCRITA");
				comprensionYExpresionOralYEscrita.setEjeDeDesarrollo(expresionYComunicacion);
				comprensionYExpresionOralYEscrita.setOrden("3.1");
				ambitosEYC.add(comprensionYExpresionOralYEscrita);

				AmbitoDeDesarrollo comprensionYExpresionArtistica = new AmbitoDeDesarrollo();
				comprensionYExpresionArtistica.setEtiqueta("COMPRENSIÓN Y EXPRESIÓN ARTÍSTICA");
				comprensionYExpresionArtistica.setEjeDeDesarrollo(expresionYComunicacion);
				comprensionYExpresionArtistica.setOrden("3.2");
				ambitosEYC.add(comprensionYExpresionArtistica);

				AmbitoDeDesarrollo expresionCorporal = new AmbitoDeDesarrollo();
				expresionCorporal.setEtiqueta("EXPRESIÓN CORPORAL");
				expresionCorporal.setEjeDeDesarrollo(expresionYComunicacion);
				expresionCorporal.setOrden("3.3");
				ambitosEYC.add(expresionCorporal);
			}
			expresionYComunicacion.setAmbitosDeDesarrollo(ambitosEYC);
			ejeDeDesarrolloDao.guardar(expresionYComunicacion);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se pudo guardar los ejes de aprendizaje");
		}

	}

	public List<EjeDeDesarrollo> obtenerEjesDeDesarrolloPorParalelo(Paralelo paralelo) {
		return ejeDeDesarrolloDao.obtenerEjesDeDesarrolloPorParalelo(paralelo);
	}

	public List<AmbitoDeDesarrollo> obtenerAmbitosDeDesarrolloPorEjeDeDesarrollo(EjeDeDesarrollo ejeDeDesarrollo) {
		return ambitoDeDesarrolloDao.obtenerEjesDeDesarrolloPorEjeDeDesarrollo(ejeDeDesarrollo);
	}

	@Inject
	private DestrezaDeDesarrolloDao destrezaDeDesarrolloDao;

	@Inject
	private EvaluacionDestrezaDeDesarrolloDao evaluacionDestrezaDeDesarrolloDao;

	public void guardarDestrezaDeDesarrollo(DestrezaDeDesarrollo destrezaDeDesarrollo)
			throws GestionEstudiantilException {
		try {
			destrezaDeDesarrollo = destrezaDeDesarrolloDao.guardar(destrezaDeDesarrollo);
			Paralelo paralelo = paraleloDao.obtenerPorId(
					destrezaDeDesarrollo.getAmbitoDeDesarrollo().getEjeDeDesarrollo().getParalelo().getId());
			List<EvaluacionDestrezaDeDesarrollo> evaluaciones = new ArrayList<>();

			for (Matricula matricula : paralelo.getEstudiantesMatriculados()) {
				EvaluacionDestrezaDeDesarrollo evaluacionDestrezaDeDesarrollo = new EvaluacionDestrezaDeDesarrollo();
				evaluacionDestrezaDeDesarrollo.setMatricula(matricula);
				evaluacionDestrezaDeDesarrollo.setDestrezaDeDesarrollo(destrezaDeDesarrollo);
				evaluaciones.add(evaluacionDestrezaDeDesarrollo);
				evaluacionDestrezaDeDesarrolloDao.guardar(evaluacionDestrezaDeDesarrollo);
			}

		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se pudo guardar el destreza de desarrollo");
		}
	}

	public void editarDestrezaDeDesarrollo(DestrezaDeDesarrollo destrezaDeDesarrollo)
			throws GestionEstudiantilException {
		try {
			destrezaDeDesarrolloDao.editar(destrezaDeDesarrollo);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se pudo editar el destreza de desarrollo");
		}
	}

	public void eliminarDestrezaDeDesarrollo(DestrezaDeDesarrollo destrezaDeDesarrollo)
			throws GestionEstudiantilException {
		try {
			for (EvaluacionDestrezaDeDesarrollo evaluacion : destrezaDeDesarrollo.getEvaluaciones()) {
				evaluacion.setDestrezaDeDesarrollo(null);
				evaluacionDestrezaDeDesarrolloDao.eliminar(evaluacion.getId());
			}
			destrezaDeDesarrollo.setEvaluaciones(null);
			AmbitoDeDesarrollo ambitoDeDesarrollo = ambitoDeDesarrolloDao
					.obtenerPorId(destrezaDeDesarrollo.getAmbitoDeDesarrollo().getId());
			ambitoDeDesarrollo.getDestrezasDeDesarrollo().remove(destrezaDeDesarrollo);
			destrezaDeDesarrollo.setAmbitoDeDesarrollo(null);
			destrezaDeDesarrolloDao.eliminar(destrezaDeDesarrollo.getId());
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se pudo eliminar el destreza de desarrollo");
		}
	}

	public List<Paralelo> obtenerParalelosConDestrezasDeDesarrolloPorDocente(Docente docente) {
		return paraleloDao.obtenerParalelosConDestrezasDeDesarrolloPorDocente(docente);
	}

	public void guardarEjeDeDesarrollo(EjeDeDesarrollo ejeDeDesarrollo) throws GestionEstudiantilException {
		try {
			ejeDeDesarrolloDao.guardar(ejeDeDesarrollo);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se pudo guardar el eje de desarrollo");
		}
	}

	public void guardarAmbitoDeDesarrollo(AmbitoDeDesarrollo ambitoDeDesarrollo) throws GestionEstudiantilException {
		try {
			ambitoDeDesarrolloDao.guardar(ambitoDeDesarrollo);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se pudo guardar el ámbito de desarrollo");
		}
	}

	public List<Matricula> obtenerMatriculasPorEstudiante(Estudiante estudiante) {
		return matriculaDao.obtenerMatriculasPorEstudiante(estudiante);
	}

	public void editarPeriodoLectivo(PeriodoLectivo periodoLectivo) throws GestionEstudiantilException {
		try {
			periodoLectivoDao.editar(periodoLectivo);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se pudo editar el periodo Lectivo");
		}
	}

	public void editarGrado(Grado grado) throws GestionEstudiantilException {
		try {
			gradoDao.editar(grado);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se pudo editar el grado");
		}
	}
	
	public void guardarArea(Area area) throws GestionEstudiantilException {
		try {
			areaDao.guardar(area);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se pudo guardar el area");
		}
	}

	public void editarArea(Area area) throws GestionEstudiantilException {
		try {
			areaDao.editar(area);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se pudo editar el area");
		}
	}

	public void editarEjeDeDesarrollo(EjeDeDesarrollo ejeDeDesarrollo) throws GestionEstudiantilException {
		try {
			ejeDeDesarrolloDao.editar(ejeDeDesarrollo);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se pudo editar el Eje de Desarrollo");
		}
	}

	public void editarAmbitoDeDesarrollo(AmbitoDeDesarrollo ambitoDeDesarrollo) throws GestionEstudiantilException {
		try {
			ambitoDeDesarrolloDao.editar(ambitoDeDesarrollo);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se pudo editar el Eje de Desarrollo");
		}
	}

	public void eliminarAmbitoDeDesarrollo(AmbitoDeDesarrollo ambitoDeDesarrollo) throws GestionEstudiantilException {
		try {
			for (DestrezaDeDesarrollo destrezaDeDesarrollo : ambitoDeDesarrollo.getDestrezasDeDesarrollo()) {
				eliminarDestrezaDeDesarrollo(destrezaDeDesarrollo);
			}
			ambitoDeDesarrollo.setDestrezasDeDesarrollo(null);
			EjeDeDesarrollo ejeDeDesarrollo = ejeDeDesarrolloDao
					.obtenerPorId(ambitoDeDesarrollo.getEjeDeDesarrollo().getId());
			ejeDeDesarrollo.getAmbitosDeDesarrollo().remove(ambitoDeDesarrollo);
			ambitoDeDesarrollo.setEjeDeDesarrollo(null);
			ambitoDeDesarrolloDao.eliminar(ambitoDeDesarrollo.getId());

		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se pudo eliminar el ambito de desarrollo");
		}
	}

	public void eliminarEjeDeDesarrollo(EjeDeDesarrollo ejeDeDesarrollo) throws GestionEstudiantilException {
		try {
			for (AmbitoDeDesarrollo ambitoDeDesarrollo : ejeDeDesarrollo.getAmbitosDeDesarrollo()) {
				eliminarAmbitoDeDesarrollo(ambitoDeDesarrollo);
			}
			ejeDeDesarrollo.setAmbitosDeDesarrollo(null);
			ejeDeDesarrolloDao.eliminar(ejeDeDesarrollo.getId());
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se pudo eliminar el eje de desarrollo");
		}
	}

	public List<Asignatura> obtenerAsignaturasPorDocenteYPeriodoLectivo(Docente docente, String periodo) {
		List<Asignatura> asignaturas = asignaturaDao.obtenerAsignaturasPorDocenteYPeriodoLectivo(docente, periodo);
		List<Asignatura> asignaturasPorDocente = new ArrayList<>();
		for (Asignatura asignatura : asignaturas) {
			if (asignatura.getAsignaturas().isEmpty())
				asignaturasPorDocente.add(asignatura);
		}
		return asignaturasPorDocente;
	}

	public List<Paralelo> obtenerParalelosPorTutorYPeriodoLectivo(Docente docente, String periodo) {
		return paraleloDao.obtenerParalelosPorTutor(docente, periodo);
	}

	public List<Paralelo> obtenerParalelosConDestrezasDeDesarrolloPorDocente(Docente docente, String periodo) {
		return paraleloDao.obtenerParalelosConDestrezasDeDesarrolloPorDocente(docente, periodo);
	}

	public List<ProyectoEscolar> obtenerProyectosEscolaresPorDocente(Docente docente, String periodo) {

		return proyectoEscolarDao.obtenerProyectosEscolaresPorDocente(docente, periodo);

	}

}
