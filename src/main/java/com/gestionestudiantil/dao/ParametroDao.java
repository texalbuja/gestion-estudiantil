package com.gestionestudiantil.dao;

import javax.persistence.Query;

import com.gestionestudiantil.exception.GestionEstudiantilPersistenceException;
import com.gestionestudiantil.model.Parametro;

public class ParametroDao extends AbstractDao<Parametro, Long> {
	public ParametroDao() {
		super(Parametro.class);
	}

	public Parametro obtenerParametroPorCodigo(String codigo) throws GestionEstudiantilPersistenceException {

		Query qry = this.em.createQuery(
				"SELECT p FROM Parametro p WHERE p.codigo = :codigo",
				Parametro.class);
		qry.setParameter("codigo", codigo);
		try {
			return (Parametro) qry.getSingleResult();
		} catch (Exception e) {
			throw new GestionEstudiantilPersistenceException(
					"No existe el parámetro");
		}

	}
}