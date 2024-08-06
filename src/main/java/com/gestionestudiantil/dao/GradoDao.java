package com.gestionestudiantil.dao;

import java.util.ArrayList;

import javax.persistence.Query;

import com.gestionestudiantil.model.Grado;
import com.gestionestudiantil.model.PeriodoLectivo;

public class GradoDao extends AbstractDao<Grado, Long> {
	public GradoDao() {
		super(Grado.class);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Grado> obtenerGradosPorPeriodoLectivo(
			PeriodoLectivo periodoLectivo) {
		Query qry = this.em
				.createQuery(
						"SELECT g FROM Grado g WHERE g.periodoLectivo= :periodoLectivo",
						Grado.class);
		qry.setParameter("periodoLectivo", periodoLectivo);

		return (ArrayList<Grado>) qry.getResultList();
	}
}