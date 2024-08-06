package com.gestionestudiantil.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.gestionestudiantil.exception.GestionEstudiantilPersistenceException;
import com.gestionestudiantil.model.Estudiante;
import com.gestionestudiantil.model.Grado;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Paralelo;

public class EstudianteDao extends AbstractDao<Estudiante, Long> {

	public EstudianteDao() {
		super(Estudiante.class);
	}

	public List<Estudiante> obtenerEstudiantesPorInstitucion(Institucion institucion) {
		Query qry = this.em
				.createQuery("SELECT e FROM Estudiante e WHERE e.institucion= :institucion order by e.apellidos");
		qry.setParameter("institucion", institucion);
		return qry.getResultList();
	}

	public Estudiante obtenerEstudiantePorIdentificacion(String identificacion)
			throws GestionEstudiantilPersistenceException {
		Estudiante result = null;
		try {
			Query qry = this.em.createQuery("SELECT e FROM Estudiante e WHERE e.identificacion= :identificacion",
					Estudiante.class);
			qry.setParameter("identificacion", identificacion);

			result = (Estudiante) qry.getSingleResult();
		} catch (PersistenceException e) {
			throw new GestionEstudiantilPersistenceException("Estudiante no existe");
		}
		return result;
	}

	public List<Estudiante> obtenerEstudiantesAprobadosPorGrado(Grado grado) {
		List<Estudiante> estudiantesAnteriores = new ArrayList<Estudiante>();
		List<Matricula> matriculasAnteriores = new ArrayList<Matricula>();
		List<Matricula> matriculasActuales = new ArrayList<Matricula>();
		Integer periodoActual = Integer.parseInt(grado.getPeriodoLectivo().getEtiqueta().substring(0, 4));
		String etiquetaPeriodoAnterior = (periodoActual - 1) + "-" + periodoActual;

		Query qryGradosAnteriores = em.createQuery(
				"SELECT g FROM Grado g WHERE g.periodoLectivo.institucion = :institucion AND g.grado = :gradoAnterior AND g.periodoLectivo.etiqueta = :etiquetaPeriodoAnterior");
		qryGradosAnteriores.setParameter("gradoAnterior", grado.getGrado() - 1);
		qryGradosAnteriores.setParameter("institucion", grado.getPeriodoLectivo().getInstitucion());
		qryGradosAnteriores.setParameter("etiquetaPeriodoAnterior", etiquetaPeriodoAnterior);
		List<Grado> gradosAnteriores;
		try {
			gradosAnteriores = (List<Grado>) qryGradosAnteriores.getResultList();
			if(gradosAnteriores.size() == 0){
				return new ArrayList<Estudiante>();
			}
		} catch (NoResultException nre) {
			return new ArrayList<Estudiante>();
		}

		Query qryMatriculasAnteriores = em
				.createQuery("SELECT m FROM Matricula m join fetch m.estudiante WHERE m.grado IN :grados");
		qryMatriculasAnteriores.setParameter("grados", gradosAnteriores);

		matriculasAnteriores = (List<Matricula>) qryMatriculasAnteriores.getResultList();

		Query qryMatriculasActuales = em.createQuery("SELECT m FROM Matricula m WHERE m.grado= :grado");
		qryMatriculasActuales.setParameter("grado", grado);
		matriculasActuales = (List<Matricula>) qryMatriculasActuales.getResultList();

		for (Matricula matricula : matriculasAnteriores) {
			estudiantesAnteriores.add(matricula.getEstudiante());
			for (Matricula matriculaActual : matriculasActuales) {
				if (matriculaActual.getEstudiante().equals(matricula.getEstudiante())) {
					estudiantesAnteriores.remove(matricula.getEstudiante());
					break;
				}
			}
		}
		Collections.sort(estudiantesAnteriores, new Comparator<Estudiante>() {
			public int compare(Estudiante e1, Estudiante e2) {
				return e1.getApellidos().concat(" " + e1.getNombres())
						.compareTo(e2.getApellidos().concat(" " + e2.getNombres()));
			}
		});
		return estudiantesAnteriores;

	}

	public List<Estudiante> obtenerEstudiantesPorParalelo(Paralelo paralelo) {
		Query qry = this.em.createQuery(
				"SELECT e FROM Matricula m JOIN m.estudiante e WHERE m.paralelo = :paralelo order by e.apellidos");
		qry.setParameter("paralelo", paralelo);
		return qry.getResultList();
	}

	public List<Estudiante> buscarEstudiantes(String apellidos, String identificacion, Institucion institucion)
			throws GestionEstudiantilPersistenceException {
		try {
			String sql = "SELECT e FROM Estudiante e WHERE e.institucion = :institucion";

			if (apellidos != null && !apellidos.isEmpty()) {
				sql += " AND e.apellidos LIKE :apellidos";
			}
			if (identificacion != null && !identificacion.isEmpty()) {
				sql += " AND e.identificacion = :identificacion";
			}
			sql+= " order by e.apellidos";
			
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
