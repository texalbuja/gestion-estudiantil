package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.GrupoDeEvaluacion;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Observacion;
import com.gestionestudiantil.model.Paralelo;
import com.gestionestudiantil.model.PeriodoLectivo;

public class ObservacionDao extends AbstractDao<Observacion, Long> {
	public ObservacionDao() {
		super(Observacion.class);
	}

	public List<Observacion> obtenerObservacionesPorPeriodoLectivo(PeriodoLectivo periodoLectivo) {

		Query qry = this.em.createQuery(
				"SELECT o FROM Observacion o WHERE o.matricula.paralelo.grado.periodoLectivo = :periodoLectivo order by o.fecha desc",
				Observacion.class);
		qry.setParameter("periodoLectivo", periodoLectivo);
		return qry.getResultList();

	}

	public List<Observacion> obtenerObservacionesPorMatriculaYBloque(Matricula matricula, Integer bloque) {
		Query qry = this.em.createQuery(
				"SELECT o FROM Observacion o WHERE o.bloque = :bloque and o.matricula = :matricula order by o.fecha desc",
				Observacion.class);
		qry.setParameter("bloque", bloque);
		qry.setParameter("matricula", matricula);
		return qry.getResultList();
	}

	public List<Observacion> obtenerObservacionesPorMatriculaYGrupoDeEvaluacion(Matricula matricula,
			GrupoDeEvaluacion grupoDeEvaluacion) {
		Query qry = this.em.createQuery(
				"SELECT o FROM Observacion o WHERE o.bloque = :bloque and o.matricula = :matricula order by o.fecha desc",
				Observacion.class);
		qry.setParameter("bloque", grupoDeEvaluacion.getBloque());
		qry.setParameter("matricula", matricula);
		return qry.getResultList();
	}

	public List<Observacion> obtenerObservacionesPorInstitucionYPeriodo(Institucion institucion, String periodo) {

		Query qry = this.em.createQuery(
				"SELECT o FROM Observacion o WHERE o.matricula.paralelo.grado.periodoLectivo.institucion = :institucion AND o.matricula.paralelo.grado.periodoLectivo.etiqueta = :periodo order by o.fecha desc",
				Observacion.class);
		qry.setParameter("institucion", institucion);
		qry.setParameter("periodo", periodo);
		return qry.getResultList();
	}

	public List<Observacion> obtenerObservacionesPorMatricula(Matricula matricula) {
		Query qry = this.em.createQuery(
				"SELECT o FROM Observacion o WHERE o.matricula = :matricula order by o.fecha desc", Observacion.class);
		qry.setParameter("matricula", matricula);
		return qry.getResultList();
	}

	public List<Observacion> obtenerObservacionesPorParalelo(Paralelo paralelo, GrupoDeEvaluacion grupoDeEvaluacion) {
		Query qry = this.em.createQuery(
				"SELECT o FROM Observacion o WHERE o.matricula.paralelo = :paralelo AND o.bloque = :bloque order by o.fecha desc",
				Observacion.class);
		qry.setParameter("bloque", grupoDeEvaluacion.getBloque());
		qry.setParameter("paralelo", paralelo);
		return qry.getResultList();
	}

}