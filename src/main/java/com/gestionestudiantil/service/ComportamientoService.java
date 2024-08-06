package com.gestionestudiantil.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.gestionestudiantil.dao.AsistenciaDao;
import com.gestionestudiantil.dao.ObservacionDao;
import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.exception.GestionEstudiantilPersistenceException;
import com.gestionestudiantil.model.Asistencia;
import com.gestionestudiantil.model.GrupoDeEvaluacion;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Observacion;
import com.gestionestudiantil.model.Paralelo;
import com.gestionestudiantil.model.PeriodoLectivo;

@Stateless
public class ComportamientoService {

	@Inject
	private ObservacionDao observacionDao;

	@Inject
	private AsistenciaDao asistenciaDao;

	public List<Asistencia> obtenerAsistenciasPorPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		return asistenciaDao.obtenerAsistenciaPorPeriodoLectivo(periodoLectivo);
	}

	public void eliminarAsistencia(Asistencia asistencia) throws GestionEstudiantilException {
		try {
			asistenciaDao.eliminar(asistencia.getId());

		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al eliminar la asistencia", e);
		}
	}

	public void editarAsistencia(Asistencia asistencia) throws GestionEstudiantilException {
		try {
			asistenciaDao.editar(asistencia);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al actualizar la asistencia", e);
		}
	}

	public Asistencia guardarAsistencia(Integer bloque, Date fecha, String detalle, Matricula matricula, String tipo)
			throws GestionEstudiantilException {
		try {
			Asistencia asistencia = new Asistencia();
			asistencia.setBloque(bloque);
			asistencia.setFecha(fecha);
			asistencia.setMatricula(matricula);
			asistencia.setObservacion(detalle);
			asistencia.setTipo(tipo);
			asistencia.setEstado("SJ");
			return asistenciaDao.guardar(asistencia);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al guardar la asistencia", e);
		}
	}

	public Observacion guardarObservacion(Integer bloque, Date fecha, String detalle, Matricula matricula)
			throws GestionEstudiantilException {
		try {
			Observacion observacion = new Observacion();
			observacion.setBloque(bloque);
			observacion.setFecha(fecha);
			observacion.setMatricula(matricula);
			observacion.setObservacion(detalle);
			return observacionDao.guardar(observacion);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al guardar la observación", e);
		}
	}

	public List<Observacion> obtenerObservacionesPorMatricula(Matricula matricula) {
		return observacionDao.obtenerObservacionesPorMatricula(matricula);
	}

	public void editarObservacion(Observacion observacion) throws GestionEstudiantilException {
		try {
			observacionDao.editar(observacion);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al guardar la observación", e);
		}
	}

	public void eliminarObservacion(Observacion observacion) throws GestionEstudiantilException {
		try {
			observacionDao.eliminar(observacion.getId());
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error al eliminar la observación", e);
		}
	}

	public List<Observacion> obtenerObservacionesPorMatriculaBloque(Matricula matricula, Integer bloque) {
		return observacionDao.obtenerObservacionesPorMatriculaYBloque(matricula, bloque);
	}

	public List<Asistencia> obtenerAsistenciasPorMatricula(Matricula matricula) {
		return asistenciaDao.obtenerAsistenciaPorMatricula(matricula);
	}

	public List<Asistencia> obtenerAsistenciasPorMatriculaYBloque(Matricula matricula,
			GrupoDeEvaluacion grupoDeEvaluacion) {
		return asistenciaDao.obtenerAsistenciasPorMatriculaBloque(matricula, grupoDeEvaluacion.getBloque());
	}

	public List<Asistencia> obtenerAsistenciasPorInstitucionYPeriodo(Institucion institucion, String periodo) {
		return asistenciaDao.obtenerAsistenciasPorInstitucionYPeriodo(institucion, periodo);
	}

	public List<Observacion> obtenerObservacionesPorParalelo(Paralelo paralelo, GrupoDeEvaluacion grupoDeEvaluacion) {
		return observacionDao.obtenerObservacionesPorParalelo(paralelo, grupoDeEvaluacion);
	}

	public List<Observacion> obtenerObservacionesPorInstitucionYPeriodo(Institucion institucion, String periodo) {
		return observacionDao.obtenerObservacionesPorInstitucionYPeriodo(institucion, periodo);
	}

}
