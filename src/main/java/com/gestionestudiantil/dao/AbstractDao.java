package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.gestionestudiantil.exception.GestionEstudiantilPersistenceException;

public class AbstractDao<T, U> {
	@PersistenceContext
	EntityManager em;
	private Class<T> type;

	public AbstractDao(Class<T> type) {
		this.type = type;
	}

	public T guardar(T t) throws GestionEstudiantilPersistenceException {
		try {
			em.persist(t);
			em.flush();
			em.refresh(t);
			return t;
		} catch (PersistenceException e) {
			throw new GestionEstudiantilPersistenceException(e,
					"Error al guardar " + type.getSimpleName());
		}

	}

	public T obtenerPorId(U id) {
		return (T) em.find(type, id);
	}

	public List<T> obtenerTodos() {
		return em.createQuery(
				"Select t from " + type.getSimpleName() + " t", type)
				.getResultList();
	}

	public void eliminar(U id) throws GestionEstudiantilPersistenceException {
		try {
			Object ref = em.getReference(type, id);
			em.remove(ref);
		} catch (PersistenceException e) {
			throw new GestionEstudiantilPersistenceException(e,
					"Error al eliminar " + type.getSimpleName());
		}
	}

	public T editar(T t) throws GestionEstudiantilPersistenceException {
		try {
			return (T) em.merge(t);
		} catch (PersistenceException e) {
			throw new GestionEstudiantilPersistenceException(e,
					"Error al actualizar " + type.getSimpleName());
		}

	}
}