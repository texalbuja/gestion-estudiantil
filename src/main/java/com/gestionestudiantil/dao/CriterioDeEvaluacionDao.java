package com.gestionestudiantil.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.CriterioDeEvaluacion;
import com.gestionestudiantil.model.PeriodoLectivo;

public class CriterioDeEvaluacionDao extends
		AbstractDao<CriterioDeEvaluacion, Long> {
	public CriterioDeEvaluacionDao() {
		super(CriterioDeEvaluacion.class);
	}

	public List<CriterioDeEvaluacion> obtenerPorPeriodoLectivo(
			PeriodoLectivo periodoLectivo) {
		Query qry = this.em
				.createQuery(
						"SELECT c FROM CriterioDeEvaluacion c WHERE c.periodoLectivo= :periodoLectivo order by c.orden",
						CriterioDeEvaluacion.class);
		qry.setParameter("periodoLectivo", periodoLectivo);

		return (ArrayList<CriterioDeEvaluacion>) qry.getResultList();
	}
}