package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.gestionestudiantil.exception.GestionEstudiantilPersistenceException;
import com.gestionestudiantil.model.BloqueoUsuario;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Usuario;

public class BloqueoUsuarioDao extends AbstractDao<BloqueoUsuario, Long> {
	public BloqueoUsuarioDao() {
		super(BloqueoUsuario.class);
	}

	public List<BloqueoUsuario> obtenerBloqueosUsuarioPorInstitucion(
			Institucion institucion) {
		Query qry = this.em
				.createQuery(
						"SELECT b FROM BloqueoUsuario b WHERE b.usuario.institucion = :institucion order by b.usuario.nombre",
						BloqueoUsuario.class);
		qry.setParameter("institucion", institucion);
		return qry.getResultList();
	}

	public BloqueoUsuario obtenerBloqueoUsuarioPorUsuario(Usuario usuario) {
		Query qry = this.em.createQuery(
				"SELECT b FROM BloqueoUsuario b WHERE b.usuario = :usuario",
				BloqueoUsuario.class);
		qry.setParameter("usuario", usuario);
		return (BloqueoUsuario) qry.getSingleResult();
	}

	public void validarBloqueoUsuarioPorIdentificacion(String identificacion)
			throws GestionEstudiantilPersistenceException {
		try {
			Query qry = this.em
					.createQuery(
							"SELECT b FROM BloqueoUsuario b WHERE b.usuario.identificacion = :identificacion",
							BloqueoUsuario.class);
			qry.setParameter("identificacion", identificacion);
			BloqueoUsuario bloqueoUsuario = (BloqueoUsuario) qry
					.getSingleResult();
			throw new GestionEstudiantilPersistenceException(
					"El usuario se encuentra bloqueado");
		} catch (NoResultException ex) {

		}
	}
}