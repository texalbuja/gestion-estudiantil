package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.GrupoDeEvaluacion;
import com.gestionestudiantil.model.PeriodoLectivo;

public class GrupoDeEvaluacionDao extends AbstractDao<GrupoDeEvaluacion, Long> {
	public GrupoDeEvaluacionDao() {
		super(GrupoDeEvaluacion.class);
	}

	public List<GrupoDeEvaluacion> obtenerGrupoDeEvaluacionPorPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		Query qry = this.em.createQuery("SELECT c FROM GrupoDeEvaluacion c WHERE c.periodoLectivo= :periodoLectivo",
				GrupoDeEvaluacion.class);
		qry.setParameter("periodoLectivo", periodoLectivo);
		return qry.getResultList();
	}

	public List<GrupoDeEvaluacion> obtenerGrupoDeEvaluacionActivosPorPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		Query qry = this.em.createQuery(
				"SELECT c FROM GrupoDeEvaluacion c WHERE c.periodoLectivo= :periodoLectivo AND c.habilitado = true",
				GrupoDeEvaluacion.class);
		qry.setParameter("periodoLectivo", periodoLectivo);
		return qry.getResultList();
	}

	public List<GrupoDeEvaluacion> obtenerGrupoDeEvaluacionActivosEstudiantePorPeriodoLectivo(
			PeriodoLectivo periodoLectivo) {
		Query qry = this.em.createQuery(
				"SELECT c FROM GrupoDeEvaluacion c WHERE c.periodoLectivo= :periodoLectivo AND c.habilitadoEstudiante = true",
				GrupoDeEvaluacion.class);
		qry.setParameter("periodoLectivo", periodoLectivo);
		return qry.getResultList();
	}

	public List<GrupoDeEvaluacion> obtenerGrupoDeEvaluacionDeComportamientoActivosPorPeriodoLectivo(
			PeriodoLectivo periodoLectivo) {
		Query qry = this.em.createQuery(
				"SELECT c FROM GrupoDeEvaluacion c WHERE c.periodoLectivo= :periodoLectivo AND c.habilitado = true AND c.codigo NOT IN ('q1', 'q2', 'em', 'es', 'er', 'eg', 'a')",
				GrupoDeEvaluacion.class);
		qry.setParameter("periodoLectivo", periodoLectivo);
		return qry.getResultList();
	}

	public List<GrupoDeEvaluacion> obtenerGruposDeEvaluacionDeDestrezasActivos(PeriodoLectivo periodoLectivo) {
		Query qry = this.em.createQuery(
				"SELECT c FROM GrupoDeEvaluacion c WHERE c.periodoLectivo= :periodoLectivo AND c.habilitado = true AND c.codigo NOT IN ('q1_p3', 'q2_p3', 'em', 'es', 'er', 'eg', 'a')",
				GrupoDeEvaluacion.class);
		qry.setParameter("periodoLectivo", periodoLectivo);
		return qry.getResultList();
	}

	public List<GrupoDeEvaluacion> obtenerGruposDeEvaluacionDeProyectosEscolaresActivos(PeriodoLectivo periodoLectivo) {
		Query qry = this.em.createQuery(
				"SELECT c FROM GrupoDeEvaluacion c WHERE c.periodoLectivo= :periodoLectivo AND c.habilitado = true AND c.codigo NOT IN ('em', 'es', 'er', 'eg', 'a')",
				GrupoDeEvaluacion.class);
		qry.setParameter("periodoLectivo", periodoLectivo);
		return qry.getResultList();
	}
}