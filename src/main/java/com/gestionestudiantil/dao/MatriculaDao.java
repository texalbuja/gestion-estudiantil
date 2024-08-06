package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.Estudiante;
import com.gestionestudiantil.model.InformeAnual;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Paralelo;
import com.gestionestudiantil.model.PeriodoLectivo;

public class MatriculaDao extends AbstractDao<Matricula, Long> {
	public MatriculaDao() {
		super(Matricula.class);
	}

	public List<Matricula> obtenerEstudiantesPorParalelo(Paralelo paralelo) {
		Query qry = this.em.createQuery(
				"SELECT m FROM Matricula m WHERE m.paralelo= :paralelo ORDER BY m.estudiante.apellidos, m.estudiante.nombres",
				Matricula.class);
		qry.setParameter("paralelo", paralelo);
		return qry.getResultList();
	}

	public Matricula obtenerMatriculaActiva(Estudiante estudiante, String periodo) {
		Query qry = this.em.createQuery(
				"SELECT m FROM Matricula m WHERE m.paralelo.grado.periodoLectivo.etiqueta = :periodo and m.estudiante = :estudiante",
				Matricula.class);
		qry.setParameter("estudiante", estudiante);
		qry.setParameter("periodo", periodo);
		return (Matricula) qry.getSingleResult();
	}

	public List<Matricula> obtenerMatriculasPorPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		Query qry = this.em.createQuery(
				"SELECT m FROM Matricula m WHERE m.paralelo.grado.periodoLectivo =:periodoLectivo", Matricula.class);
		qry.setParameter("periodoLectivo", periodoLectivo);
		return qry.getResultList();
	}

	public List<Matricula> obtenerMatriculasPorInstitucion(Institucion institucion, String periodo) {
		Query qry = this.em.createQuery(
				"SELECT m FROM Matricula m WHERE m.paralelo.grado.periodoLectivo.institucion =:institucion and m.paralelo.grado.periodoLectivo.etiqueta = :periodo",
				Matricula.class);
		qry.setParameter("institucion", institucion);
		qry.setParameter("periodo", periodo);
		return qry.getResultList();
	}

	public List<Matricula> obtenerMatriculasPorEstudiante(Estudiante estudiante) {
		Query qry = this.em.createQuery("SELECT m FROM Matricula m WHERE m.estudiante = :estudiante", Matricula.class);
		qry.setParameter("estudiante", estudiante);
		return qry.getResultList();
	}

	public Matricula obtenerMatriculaPorInformeAnual(InformeAnual informeAnual) {
		Query qry = this.em.createQuery("SELECT m FROM Matricula m WHERE m.informeAnual = :informeAnual",
				Matricula.class);
		qry.setParameter("informeAnual", informeAnual);
		return (Matricula) qry.getSingleResult();
	}
}