package com.gestionestudiantil.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.gestionestudiantil.dao.EvaluacionAsignaturaDao;
import com.gestionestudiantil.dao.EvaluacionComportamientoDao;
import com.gestionestudiantil.dao.GrupoDeEvaluacionDao;
import com.gestionestudiantil.dao.ObservacionDao;
import com.gestionestudiantil.model.EvaluacionAsignatura;
import com.gestionestudiantil.model.EvaluacionComportamiento;
import com.gestionestudiantil.model.GrupoDeEvaluacion;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Observacion;
import com.gestionestudiantil.model.PeriodoLectivo;

@Stateless
public class InformesService {

	@Inject
	private ObservacionDao observacionDao;

	@Inject
	private EvaluacionAsignaturaDao evaluacionAsignaturaDao;

	@Inject
	private EvaluacionComportamientoDao evaluacionComportamientoDao;

	@Inject
	private GrupoDeEvaluacionDao grupoDeEvaluacionDao;

	public List<GrupoDeEvaluacion> obtenerGruposDeEvaluacionActivos(
			PeriodoLectivo periodoLectivo) {
		return grupoDeEvaluacionDao
				.obtenerGrupoDeEvaluacionActivosPorPeriodoLectivo(periodoLectivo);
	}
	
	public List<GrupoDeEvaluacion> obtenerGruposDeEvaluacionActivosEstudiante(
			PeriodoLectivo periodoLectivo) {
		return grupoDeEvaluacionDao
				.obtenerGrupoDeEvaluacionActivosEstudiantePorPeriodoLectivo(periodoLectivo);
	}

	public List<EvaluacionAsignatura> obtenerEvaluacionesPorMatricula(
			Matricula matricula) {
		return evaluacionAsignaturaDao.obtenerEvaluacionesAsignaturaPorMatricula(matricula);
	}

	public List<Observacion> obtenerObservacionesPorMatriculaYGrupoDeEvaluacion(
			Matricula matricula, GrupoDeEvaluacion grupoDeEvaluacion) {
		return observacionDao.obtenerObservacionesPorMatriculaYGrupoDeEvaluacion(matricula, grupoDeEvaluacion);
	}

	public List<EvaluacionComportamiento> obtenerEvaluacionesComportamientoPorMatricula(
			Matricula matricula) {
		return evaluacionComportamientoDao.obtenerEvaluacionesComportamientoPorMatricula(matricula);
	}

}