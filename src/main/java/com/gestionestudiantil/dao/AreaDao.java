package com.gestionestudiantil.dao;

import java.util.ArrayList;

import javax.persistence.Query;

import com.gestionestudiantil.model.Area;
import com.gestionestudiantil.model.PeriodoLectivo;

public class AreaDao extends AbstractDao<Area, Long> {
	public AreaDao() {
		super(Area.class);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Area> obtenerAreasPorPeriodoLectivo(
			PeriodoLectivo periodoLectivo) {
		Query qry = this.em
				.createQuery(
						"SELECT a FROM Area a WHERE a.periodoLectivo= :periodoLectivo",
						Area.class);
		qry.setParameter("periodoLectivo", periodoLectivo);

		return (ArrayList<Area>) qry.getResultList();
	}
}