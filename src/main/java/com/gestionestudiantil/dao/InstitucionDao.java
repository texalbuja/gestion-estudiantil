package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.Institucion;

public class InstitucionDao extends AbstractDao<Institucion, Long> {
	public InstitucionDao() {
		super(Institucion.class);
	}

	public List<Institucion> buscar(String criterio) {
		Query qry = this.em
				.createQuery("Select i from Institucion i where i.nombre like :criterio");
		qry.setParameter("criterio", criterio);
		return qry.getResultList();
	}
}