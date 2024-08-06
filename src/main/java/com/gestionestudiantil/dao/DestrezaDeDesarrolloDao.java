package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.DestrezaDeDesarrollo;
import com.gestionestudiantil.model.Paralelo;

public class DestrezaDeDesarrolloDao extends AbstractDao<DestrezaDeDesarrollo, Long> {
	public DestrezaDeDesarrolloDao() {
		super(DestrezaDeDesarrollo.class);
	}

	public List<DestrezaDeDesarrollo> obtenerDestrezasDeDesarrolloPorParalelo(Paralelo paralelo) {
		Query qry = this.em.createQuery("SELECT o FROM DestrezaDeDesarrollo o WHERE o.ambitoDeDesarrollo.ejeDeDesarrollo.paralelo= :paralelo",
				DestrezaDeDesarrollo.class);
		qry.setParameter("paralelo", paralelo);
		return qry.getResultList();
	}
}