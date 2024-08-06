package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.Docente;
import com.gestionestudiantil.model.Paralelo;
import com.gestionestudiantil.model.ProyectoEscolar;

public class ProyectoEscolarDao extends AbstractDao<ProyectoEscolar, Long> {
	public ProyectoEscolarDao() {
		super(ProyectoEscolar.class);
	}

	public List<ProyectoEscolar> obtenerProyectosEscolaresPorParalelo(
			Paralelo paralelo) {
		Query qry = this.em.createQuery(
				"SELECT p FROM ProyectoEscolar p WHERE p.paralelo= :paralelo",
				ProyectoEscolar.class);
		qry.setParameter("paralelo", paralelo);
		return qry.getResultList();

	}

	public List<ProyectoEscolar> obtenerProyectosEscolaresPorDocente(Docente docente) {
		Query qry = this.em.createQuery(
				"SELECT p FROM ProyectoEscolar p WHERE p.docente= :docente AND p.paralelo.grado.periodoLectivo.etiqueta = '2016-2017' ORDER BY p.paralelo.grado.nivel, p.etiqueta ASC",
				ProyectoEscolar.class);
		qry.setParameter("docente", docente);
		return qry.getResultList();
	}

	public List<ProyectoEscolar> obtenerProyectosEscolaresPorDocente(Docente docente, String periodo) {
		Query qry = this.em.createQuery(
				"SELECT p FROM ProyectoEscolar p WHERE p.docente= :docente AND p.paralelo.grado.periodoLectivo.etiqueta = :periodo ORDER BY p.paralelo.grado.nivel, p.etiqueta ASC",
				ProyectoEscolar.class);
		qry.setParameter("docente", docente);
		qry.setParameter("periodo", periodo);
		return qry.getResultList();
	}

}