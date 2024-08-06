package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.InformeAnual;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Paralelo;

public class InformeAnualDao extends AbstractDao<InformeAnual, Long> {
	public InformeAnualDao() {
		super(InformeAnual.class);
	}

	public InformeAnual obtenerPorMatricula(Matricula matricula) {

		Query qry = this.em.createQuery(
				"SELECT i FROM InformeAnual i WHERE i.matricula= :matricula",
				InformeAnual.class);
		qry.setParameter("matricula", matricula);
		return (InformeAnual) qry.getSingleResult();
	}

	public List<InformeAnual> obtenerInformesAnualesPorParalelo(
			Paralelo paralelo) {
		Query qry = this.em
				.createQuery(
						"SELECT i FROM InformeAnual i WHERE i.matricula.paralelo= :paralelo",
						InformeAnual.class);
		qry.setParameter("paralelo", paralelo);
		return qry.getResultList();
	}

}