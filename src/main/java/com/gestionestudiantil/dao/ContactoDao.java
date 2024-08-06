package com.gestionestudiantil.dao;

import javax.persistence.Query;

import com.gestionestudiantil.model.Contacto;

public class ContactoDao extends AbstractDao<Contacto, Long> {
	public ContactoDao() {
		super(Contacto.class);
	}

	public Contacto obtenerContactoPorIdentificacion(String identificacion) {
		Query qry = this.em
				.createQuery("SELECT c FROM Contacto c WHERE c.identificacion= :identificacion", Contacto.class);
		qry.setParameter("identificacion", identificacion);
		return (Contacto) qry.getSingleResult();
	}

}