package com.gestionestudiantil.dao;

import javax.persistence.Query;

import com.gestionestudiantil.enums.Roles;
import com.gestionestudiantil.exception.GestionEstudiantilPersistenceException;
import com.gestionestudiantil.model.Rol;

public class RolDao extends AbstractDao<Rol, Long> {
	public RolDao() {
		super(Rol.class);
	}

	public Rol obtenerRol(Roles rol)
			throws GestionEstudiantilPersistenceException {
		Query qry = this.em.createQuery("SELECT r FROM Rol r WHERE r.id= :id");
		qry.setParameter("id", rol.getId());
		return (Rol) qry.getSingleResult();
	}
}