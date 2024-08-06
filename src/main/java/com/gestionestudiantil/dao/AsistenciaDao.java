package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.Asistencia;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.PeriodoLectivo;

public class AsistenciaDao extends AbstractDao<Asistencia, Long> {
	public AsistenciaDao() {
		super(Asistencia.class);
	}

	public List<Asistencia> obtenerAsistenciaPorPeriodoLectivo(PeriodoLectivo periodoLectivo) {

		Query qry = this.em.createQuery(
				"SELECT a FROM Asistencia a WHERE a.matricula.paralelo.grado.periodoLectivo = :periodoLectivo order by a.fecha desc",
				Asistencia.class);
		qry.setParameter("periodoLectivo", periodoLectivo);
		return qry.getResultList();

	}

	public List<Asistencia> obtenerAsistenciasPorMatriculaBloque(Matricula matricula, Integer bloque) {
		Query qry = this.em.createQuery(
				"SELECT a FROM Asistencia a WHERE a.bloque = :bloque and a.matricula = :matricula order by a.fecha desc",
				Asistencia.class);
		qry.setParameter("bloque", bloque);
		qry.setParameter("matricula", matricula);
		return qry.getResultList();

	}

	public List<Asistencia> obtenerAsistenciaPorMatricula(Matricula matricula) {
		Query qry = this.em.createQuery(
				"SELECT a FROM Asistencia a WHERE a.matricula = :matricula order by a.fecha desc", Asistencia.class);
		qry.setParameter("matricula", matricula);
		return qry.getResultList();
	}

	public List<Asistencia> obtenerAsistenciasPorInstitucionYPeriodo(Institucion institucion, String periodo) {

		Query qry = this.em.createQuery(
				"SELECT a FROM Asistencia a WHERE a.matricula.paralelo.grado.periodoLectivo.institucion = :institucion AND a.matricula.paralelo.grado.periodoLectivo.etiqueta = :periodo order by a.fecha desc",
				Asistencia.class);
		qry.setParameter("institucion", institucion);
		qry.setParameter("periodo", periodo);
		return qry.getResultList();
	}
}