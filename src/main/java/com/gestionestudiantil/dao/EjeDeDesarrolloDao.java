package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.EjeDeDesarrollo;
import com.gestionestudiantil.model.Paralelo;

public class EjeDeDesarrolloDao extends AbstractDao<EjeDeDesarrollo, Long> {
	public EjeDeDesarrolloDao() {
		super(EjeDeDesarrollo.class);
	}

	public List<EjeDeDesarrollo> obtenerEjesDeDesarrolloPorParalelo(Paralelo paralelo) {
		Query qry = this.em.createQuery("SELECT e FROM EjeDeDesarrollo e WHERE e.paralelo= :paralelo ORDER BY e.orden",
				EjeDeDesarrollo.class);
		qry.setParameter("paralelo", paralelo);
		return qry.getResultList();
	}
}