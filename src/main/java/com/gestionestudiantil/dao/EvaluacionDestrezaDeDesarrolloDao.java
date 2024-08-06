package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.Docente;
import com.gestionestudiantil.model.EvaluacionDestrezaDeDesarrollo;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Paralelo;

public class EvaluacionDestrezaDeDesarrolloDao extends AbstractDao<EvaluacionDestrezaDeDesarrollo, Long> {
	public EvaluacionDestrezaDeDesarrolloDao() {
		super(EvaluacionDestrezaDeDesarrollo.class);
	}

	public List<EvaluacionDestrezaDeDesarrollo> obtenerEvaluacionesDestrezaDeDesarrolloPorMatricula(
			Matricula matricula) {
		Query qry = this.em.createQuery(
				"SELECT e FROM EvaluacionDestrezaDeDesarrollo e WHERE e.matricula= :matricula ORDER BY e.destrezaDeDesarrollo.ambitoDeDesarrollo.ejeDeDesarrollo.orden,e.destrezaDeDesarrollo.ambitoDeDesarrollo.orden, e.destrezaDeDesarrollo.orden ",
				EvaluacionDestrezaDeDesarrollo.class);
		qry.setParameter("matricula", matricula);
		return qry.getResultList();
	}

	public List<EvaluacionDestrezaDeDesarrollo> obtenerEvaluacionesDestrezaDeDesarrolloPorDocenteYMatricula(
			Docente docente, Paralelo paralelo, Matricula matricula) {
		Query qry = this.em.createQuery(
				"SELECT e FROM EvaluacionDestrezaDeDesarrollo e WHERE e.matricula.paralelo = :paralelo AND e.matricula = :matricula AND e.destrezaDeDesarrollo.docente = :docente ORDER BY e.matricula.estudiante.apellidos, e.matricula.estudiante.nombres, e.destrezaDeDesarrollo.ambitoDeDesarrollo.ejeDeDesarrollo.orden,e.destrezaDeDesarrollo.ambitoDeDesarrollo.orden, e.destrezaDeDesarrollo.orden ",
				EvaluacionDestrezaDeDesarrollo.class);
		qry.setParameter("docente", docente);
		qry.setParameter("paralelo", paralelo);
		qry.setParameter("matricula", matricula);
		return qry.getResultList();
	}

}