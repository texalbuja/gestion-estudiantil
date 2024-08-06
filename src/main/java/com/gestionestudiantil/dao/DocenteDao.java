package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.gestionestudiantil.exception.GestionEstudiantilPersistenceException;
import com.gestionestudiantil.model.Docente;
import com.gestionestudiantil.model.Institucion;

public class DocenteDao extends AbstractDao<Docente, Long> {

	public DocenteDao() {
		super(Docente.class);
	}

	public List<Docente> obtenerDocentesPorInstitucion(Institucion institucion) {
		Query qry = em.createQuery("SELECT d FROM Docente d WHERE d.institucion = :institucion ORDER BY d.apellidos");
		qry.setParameter("institucion", institucion);
		return qry.getResultList();
	}

	public Docente obtenerDocentePorIdentificacion(String identificacion)
			throws GestionEstudiantilPersistenceException {
		try {
			Query qry = em.createQuery("SELECT d FROM Docente d WHERE d.usuario.identificacion = :identificacion");
			qry.setParameter("identificacion", identificacion);
			return (Docente) qry.getSingleResult();
		} catch (PersistenceException pe) {
			throw new GestionEstudiantilPersistenceException("No existe docente para la identificación");
		}
	}

	public List<Docente> obtenerDocentesPorCriterio(String criterio, Institucion institucion) {

		Query qry = em.createQuery(
				"SELECT d FROM Docente d WHERE d.institucion = :institucion AND (d.identificacion = :identificacion OR d.apellidos like :apellidos)");
		qry.setParameter("identificacion", criterio);
		qry.setParameter("institucion", institucion);
		qry.setParameter("apellidos", "%" + criterio + "%");
		return qry.getResultList();
	}

	public List<Docente> buscarDocentes(String apellidos, String identificacion, Institucion institucion)
			throws GestionEstudiantilPersistenceException {
		try {
			String sql = "SELECT d FROM Docente d WHERE d.institucion = :institucion";

			if (apellidos != null && !apellidos.isEmpty()) {
				sql += " AND d.apellidos LIKE :apellidos";
			}
			if (identificacion != null && !identificacion.isEmpty()) {
				sql += " AND d.identificacion = :identificacion";
			}
			sql += " order by d.apellidos";

			Query qry = this.em.createQuery(sql);

			qry.setParameter("institucion", institucion);

			if (apellidos != null && !apellidos.isEmpty()) {
				qry.setParameter("apellidos", "%" + apellidos + "%");
			}

			if (identificacion != null && !identificacion.isEmpty()) {
				qry.setParameter("identificacion", identificacion);
			}

			return qry.getResultList();
		} catch (PersistenceException ex) {
			throw new GestionEstudiantilPersistenceException(ex.getMessage());
		}
	}

}
