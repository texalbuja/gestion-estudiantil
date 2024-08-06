package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.EvaluacionProyectoEscolar;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.ProyectoEscolar;

public class EvaluacionProyectoEscolarDao extends
		AbstractDao<EvaluacionProyectoEscolar, Long> {
	public EvaluacionProyectoEscolarDao() {
		super(EvaluacionProyectoEscolar.class);
	}

	public List<EvaluacionProyectoEscolar> obtenerEvaluacionesPorProyectoEscolar(
			ProyectoEscolar proyectoEscolar) {
		Query qry = this.em
				.createQuery(
						"SELECT e FROM EvaluacionProyectoEscolar e WHERE e.proyectoEscolar= :proyectoEscolar ORDER BY e.matricula.estudiante.apellidos ",
						EvaluacionProyectoEscolar.class);
		qry.setParameter("proyectoEscolar", proyectoEscolar);
		return qry.getResultList();
	}

	public EvaluacionProyectoEscolar obtenerEvaluacionProyectoEscolarPorMatriculaYProyectoEscolar(
			Matricula matricula, ProyectoEscolar proyectoEscolar) {
		Query qry = this.em
				.createQuery(
						"SELECT e FROM EvaluacionProyectoEscolar e WHERE e.proyectoEscolar= :proyectoEscolar AND e.matricula = :matricula",
						EvaluacionProyectoEscolar.class);
		qry.setParameter("proyectoEscolar", proyectoEscolar);
		qry.setParameter("matricula", matricula);
		return (EvaluacionProyectoEscolar) qry.getSingleResult();
	}

}