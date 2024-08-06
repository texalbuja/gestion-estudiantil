package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.AmbitoDeDesarrollo;
import com.gestionestudiantil.model.EjeDeDesarrollo;

public class AmbitoDeDesarrolloDao extends AbstractDao<AmbitoDeDesarrollo, Long> {
	public AmbitoDeDesarrolloDao() {
		super(AmbitoDeDesarrollo.class);
	}

	public List<AmbitoDeDesarrollo> obtenerEjesDeDesarrolloPorEjeDeDesarrollo(EjeDeDesarrollo ejeDeDesarrollo) {
		Query qry = this.em.createQuery(
				"SELECT a FROM AmbitoDeDesarrollo a WHERE a.ejeDeDesarrollo= :ejeDeDesarrollo ORDER BY a.orden",
				AmbitoDeDesarrollo.class);
		qry.setParameter("ejeDeDesarrollo", ejeDeDesarrollo);
		return qry.getResultList();
	}
}