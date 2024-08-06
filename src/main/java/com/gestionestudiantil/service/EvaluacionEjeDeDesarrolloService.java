package com.gestionestudiantil.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.gestionestudiantil.dao.EvaluacionDestrezaDeDesarrolloDao;
import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.exception.GestionEstudiantilPersistenceException;
import com.gestionestudiantil.model.Docente;
import com.gestionestudiantil.model.EvaluacionDestrezaDeDesarrollo;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Paralelo;

@Stateless
public class EvaluacionEjeDeDesarrolloService {
	@Inject
	private EvaluacionDestrezaDeDesarrolloDao evaluacionDestrezaDeDesarrolloDao;

	public List<EvaluacionDestrezaDeDesarrollo> obtenerEvaluacionesDestrezaDeDesarrolloPorMatricula(
			Matricula matricula) {

		return evaluacionDestrezaDeDesarrolloDao.obtenerEvaluacionesDestrezaDeDesarrolloPorMatricula(matricula);

	}
	
	public void guardarEvaluacionesDestrezaDeDesarrollo(
			List<EvaluacionDestrezaDeDesarrollo> evaluacionesDestrezaDeDesarrollo) throws GestionEstudiantilException {
		for (EvaluacionDestrezaDeDesarrollo evaluacionDestrezaDeDesarrollo : evaluacionesDestrezaDeDesarrollo) {
			try {
				evaluacionDestrezaDeDesarrolloDao.editar(evaluacionDestrezaDeDesarrollo);
			} catch (GestionEstudiantilPersistenceException e) {
				throw new GestionEstudiantilException("No se pudo guardar las evaluaciones");
			}
		}
	}

	public List<EvaluacionDestrezaDeDesarrollo> obtenerEvaluacionesDestrezaDeDesarrolloPorDocenteYMatricula(
			Docente docente, Paralelo paralelo, Matricula matricula) {
		return evaluacionDestrezaDeDesarrolloDao.obtenerEvaluacionesDestrezaDeDesarrolloPorDocenteYMatricula(docente, paralelo, matricula);
	}

}