package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.EvaluacionComportamiento;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Paralelo;

public class EvaluacionComportamientoDao extends
		AbstractDao<EvaluacionComportamiento, Long> {

	public EvaluacionComportamientoDao() {
		super(EvaluacionComportamiento.class);
	}

	public List<EvaluacionComportamiento> obtenerEvaluacionesComportamientoPorParalelo(
			Paralelo paralelo) {
		Query qry = this.em
				.createQuery(
						"SELECT e FROM EvaluacionComportamiento e WHERE e.matricula.paralelo= :paralelo ORDER BY e.criterioDeEvaluacion.orden, e.matricula.estudiante.apellidos, e.matricula.estudiante.nombres",
						EvaluacionComportamiento.class);
		qry.setParameter("paralelo", paralelo);
		return qry.getResultList();
	}

	public List<EvaluacionComportamiento> obtenerEvaluacionesComportamientoPorMatricula(
			Matricula matricula) {
		Query qry = this.em
				.createQuery(
						"SELECT e FROM EvaluacionComportamiento e WHERE e.matricula= :matricula ORDER BY e.criterioDeEvaluacion.orden",
						EvaluacionComportamiento.class);
		qry.setParameter("matricula", matricula);
		return qry.getResultList();
	}
}
