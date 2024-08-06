package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.Asignatura;
import com.gestionestudiantil.model.Docente;
import com.gestionestudiantil.model.Paralelo;

public class AsignaturaDao extends AbstractDao<Asignatura, Long> {
	public AsignaturaDao() {
		super(Asignatura.class);
	}

	public List<Asignatura> obtenerAsignaturasPorParalelo(Paralelo paralelo) {
		Query qry = this.em.createQuery("SELECT a FROM Asignatura a WHERE a.paralelo= :paralelo ORDER BY a.orden",
				Asignatura.class);
		qry.setParameter("paralelo", paralelo);
		return qry.getResultList();

	}

	public List<Asignatura> obtenerAsignaturasContenedorasPorParalelo(Paralelo paralelo) {
		Query qry = this.em.createQuery(
				"SELECT a FROM Asignatura a WHERE a.paralelo= :paralelo AND a.asignatura IS NULL ORDER BY a.etiqueta",
				Asignatura.class);
		qry.setParameter("paralelo", paralelo);
		return qry.getResultList();

	}

	public List<Asignatura> obtenerAsignaturasPorDocente(Docente docente) {
		Query qry = this.em.createQuery(
				"SELECT a FROM Asignatura a WHERE a.docente= :docente AND a.paralelo.grado.periodoLectivo.etiqueta = '2016-2017' ORDER BY a.paralelo.grado.nivel, a.etiqueta ASC",
				Asignatura.class);
		qry.setParameter("docente", docente);
		return qry.getResultList();
	}

	public List<Asignatura> obtenerAsignaturasPorDocenteYPeriodoLectivo(Docente docente, String periodo) {
		Query qry = this.em.createQuery(
				"SELECT a FROM Asignatura a WHERE a.docente= :docente AND a.paralelo.grado.periodoLectivo.etiqueta = :periodo ORDER BY a.paralelo.grado.nivel, a.etiqueta ASC",
				Asignatura.class);
		qry.setParameter("docente", docente);
		qry.setParameter("periodo", periodo);
		return qry.getResultList();
	}

}