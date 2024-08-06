package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.Docente;
import com.gestionestudiantil.model.Grado;
import com.gestionestudiantil.model.Paralelo;
import com.gestionestudiantil.model.PeriodoLectivo;

public class ParaleloDao extends AbstractDao<Paralelo, Long> {
	public ParaleloDao() {
		super(Paralelo.class);
	}

	public List<Paralelo> obtenerParalelosPorGrado(Grado grado) {
		Query qry = this.em.createQuery("SELECT p FROM Paralelo p WHERE p.grado= :grado", Paralelo.class);
		qry.setParameter("grado", grado);
		return qry.getResultList();
	}

	public List<Paralelo> obtenerParalelosPorTutor(Docente docente) {
		Query qry = this.em.createQuery(
				"SELECT p FROM Paralelo p WHERE p.tutor = :docente AND p.grado.periodoLectivo.etiqueta = '2016-2017'",
				Paralelo.class);
		qry.setParameter("docente", docente);
		return qry.getResultList();
	}

	public List<Paralelo> obtenerParalelosPorPeriodoLectivo(PeriodoLectivo periodoLectivo) {
		Query qry = this.em.createQuery("SELECT p FROM Paralelo p WHERE p.grado.periodoLectivo = :periodoLectivo ORDER BY p.grado.grado, p.etiqueta",
				Paralelo.class);
		qry.setParameter("periodoLectivo", periodoLectivo);
		return qry.getResultList();
	}

	public List<Paralelo> obtenerParalelosConDestrezasDeDesarrolloPorDocente(Docente docente) {
		Query qry = this.em.createQuery(
				"SELECT DISTINCT p FROM DestrezaDeDesarrollo o JOIN o.ambitoDeDesarrollo a JOIN a.ejeDeDesarrollo e JOIN e.paralelo p WHERE o.docente = :docente AND p.grado.periodoLectivo.etiqueta = '2016-2017'",
				Paralelo.class);
		qry.setParameter("docente", docente);
		return qry.getResultList();
	}

	public List<Paralelo> obtenerParalelosPorTutor(Docente docente, String periodo) {
		Query qry = this.em.createQuery(
				"SELECT p FROM Paralelo p WHERE p.tutor = :docente AND p.grado.periodoLectivo.etiqueta = :periodo",
				Paralelo.class);
		qry.setParameter("docente", docente);
		qry.setParameter("periodo", periodo);
		return qry.getResultList();
	}

	public List<Paralelo> obtenerParalelosConDestrezasDeDesarrolloPorDocente(Docente docente, String periodo) {
		Query qry = this.em.createQuery(
				"SELECT DISTINCT p FROM DestrezaDeDesarrollo o JOIN o.ambitoDeDesarrollo a JOIN a.ejeDeDesarrollo e JOIN e.paralelo p WHERE o.docente = :docente AND p.grado.periodoLectivo.etiqueta = :periodo",
				Paralelo.class);
		qry.setParameter("docente", docente);
		qry.setParameter("periodo", periodo);
		return qry.getResultList();
	}
}