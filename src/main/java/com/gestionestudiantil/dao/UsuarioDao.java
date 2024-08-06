package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.gestionestudiantil.exception.GestionEstudiantilPersistenceException;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Usuario;

public class UsuarioDao extends AbstractDao<Usuario, Long> {
	public UsuarioDao() {
		super(Usuario.class);
	}

	public Usuario obtenerUsuarioPorIdentificacion(String identificacion)
			throws GestionEstudiantilPersistenceException {
		try {
			Query qry = this.em
					.createQuery(
							"SELECT u FROM Usuario u WHERE u.identificacion = :identificacion",
							Usuario.class);
			qry.setParameter("identificacion", identificacion);
			return (Usuario) qry.getSingleResult();
		} catch (NoResultException e) {
			throw new GestionEstudiantilPersistenceException(
					"Usuario no existe");
		}

	}

	public Usuario obtenerFuncionarioPorIdentificacionEInstitucion(
			String identificacion, Institucion institucion)
			throws GestionEstudiantilPersistenceException {
		try {
			Query qry = this.em
					.createQuery(
							"SELECT u FROM Usuario u, Rol r, RolUsuario ru WHERE u.institucion = :institucion AND u.identificacion = :identificacion AND ru.usuario = u AND r = ru.rol AND r.id <> 3  ",
							Usuario.class);
			qry.setParameter("identificacion", identificacion);
			qry.setParameter("institucion", institucion);
			return (Usuario) qry.getSingleResult();
		} catch (NoResultException e) {
			throw new GestionEstudiantilPersistenceException(
					"Usuario no existe");
		}

	}

	public List<Usuario> obtenerUsuariosPorInstitucion(Institucion institucion) {

		Query qry = this.em
				.createQuery(
						"SELECT u FROM Usuario u WHERE u.institucion = :institucion ORDER BY u.nombre",
						Usuario.class);
		qry.setParameter("institucion", institucion);
		return qry.getResultList();

	}
}