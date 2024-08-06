package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.PeriodoLectivo;

public class PeriodoLectivoDao extends AbstractDao<PeriodoLectivo, Long> {
	public PeriodoLectivoDao() {
		super(PeriodoLectivo.class);
	}

	public PeriodoLectivo getPeriodoLectivoActivo() {
		Query qry = this.em.createQuery("SELECT p FROM PeriodoLectivo p WHERE p.activo= :activo", PeriodoLectivo.class);
		qry.setParameter("activo", Boolean.TRUE);
		return (PeriodoLectivo) qry.getSingleResult();
	}

	public List<PeriodoLectivo> obtenerPeriodosLectivosPorInstitucion(Institucion institucion, String periodo) {
		Query qry = this.em.createQuery(
				"SELECT p FROM PeriodoLectivo p WHERE p.institucion= :institucion AND p.etiqueta = :periodo",
				PeriodoLectivo.class);
		qry.setParameter("institucion", institucion);
		qry.setParameter("periodo", periodo);
		return qry.getResultList();
	}

	public List<PeriodoLectivo> buscarPeriodoLectivo(String criterio) {
		Query qry = this.em.createQuery(
				"SELECT p FROM PeriodoLectivo p WHERE p.seccion LIKE :criterio or p.etiqueta LIKE :criterio",
				PeriodoLectivo.class);
		qry.setParameter("criterio", criterio);
		return qry.getResultList();
	}
}