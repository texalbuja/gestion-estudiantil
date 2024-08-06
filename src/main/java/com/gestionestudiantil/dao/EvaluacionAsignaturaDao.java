package com.gestionestudiantil.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.Asignatura;
import com.gestionestudiantil.model.EvaluacionAsignatura;
import com.gestionestudiantil.model.InformeAnual;
import com.gestionestudiantil.model.Matricula;

public class EvaluacionAsignaturaDao extends AbstractDao<EvaluacionAsignatura, Long> {
	public EvaluacionAsignaturaDao() {
		super(EvaluacionAsignatura.class);
	}

	public List<EvaluacionAsignatura> obtenerEvaluacionesPorAsignatura(Asignatura asignatura) {
		Query qry = this.em.createQuery(
				"SELECT d FROM EvaluacionAsignatura d WHERE d.asignatura= :asignatura ORDER BY d.informeAnual.matricula.estudiante.apellidos ",
				EvaluacionAsignatura.class);
		qry.setParameter("asignatura", asignatura);
		return qry.getResultList();
	}

	public EvaluacionAsignatura obtenerEvaluacionAsignaturaPorAsignaturaInformeAnual(Asignatura asignatura,
			InformeAnual informeAnual) {
		Query qry = this.em.createQuery(
				"SELECT d FROM EvaluacionAsignatura d WHERE d.asignatura= :asignatura AND d.informeAnual = :informeAnual",
				EvaluacionAsignatura.class);
		qry.setParameter("asignatura", asignatura);
		qry.setParameter("informeAnual", informeAnual);
		return (EvaluacionAsignatura) qry.getSingleResult();
	}

	public List<EvaluacionAsignatura> obtenerEvaluacionesAsignaturasPorAsignaturaYInformeAnual(Asignatura asignatura,
			InformeAnual informeAnual) {
		Query qry = this.em.createQuery(
				"SELECT d FROM EvaluacionAsignatura d WHERE d.asignatura.asignatura= :asignatura AND d.informeAnual = :informeAnual ",
				EvaluacionAsignatura.class);
		qry.setParameter("asignatura", asignatura);
		qry.setParameter("informeAnual", informeAnual);
		return qry.getResultList();
	}

	public List<EvaluacionAsignatura> obtenerDetallesInformesAnualesSupletoriosPorAsignatura(Asignatura asignatura) {

		Query qry = this.em.createQuery(
				"SELECT d FROM EvaluacionAsignatura d WHERE d.asignatura= :asignatura AND ((d.notaFinal < 7.00 AND d.notaFinal >= 5.00 ) OR d.notaExamenSupletorio is not null) ORDER BY d.informeAnual.matricula.estudiante.apellidos ",
				EvaluacionAsignatura.class);
		qry.setParameter("asignatura", asignatura);
		return qry.getResultList();
	}

	public List<EvaluacionAsignatura> obtenerEvaluacionesAsignaturaRemedialesPorAsignatura(Asignatura asignatura) {
		Query qry = this.em.createQuery(
				"SELECT d FROM EvaluacionAsignatura d WHERE d.asignatura= :asignatura AND ((d.notaFinal <= 5.00 or d.notaExamenSupletorio < 7.00) or d.notaExamenRemedial is not null) ORDER BY d.informeAnual.matricula.estudiante.apellidos ",
				EvaluacionAsignatura.class);
		qry.setParameter("asignatura", asignatura);
		return qry.getResultList();
	}

	public List<EvaluacionAsignatura> obtenerEvaluacionesAsignaturasMejoramientoPorAsignatura(Asignatura asignatura) {
		Query qry = this.em.createQuery(
				"SELECT d FROM EvaluacionAsignatura d WHERE d.asignatura= :asignatura AND ((d.notaFinal >= 7.00 AND d.notaFinal <= 10.00) or d.notaExamenRecuperacion is not null) AND (d.notaExamenRemedial is null and d.notaExamenSupletorio is null) ORDER BY d.informeAnual.matricula.estudiante.apellidos ",
				EvaluacionAsignatura.class);
		qry.setParameter("asignatura", asignatura);
		return qry.getResultList();
	}

	public List<EvaluacionAsignatura> obtenerEvaluacionesAsignaturasGraciaPorAsignatura(Asignatura asignatura) {
		Query qry = this.em.createQuery(
				"SELECT d FROM EvaluacionAsignatura d WHERE d.asignatura= :asignatura AND ((d.notaFinal <= 5.00 or d.notaExamenRemedial < 7.00) or d.notaExamenGracia is not null) ORDER BY d.informeAnual.matricula.estudiante.apellidos ",
				EvaluacionAsignatura.class);
		qry.setParameter("asignatura", asignatura);
		return qry.getResultList();
	}

	public List<EvaluacionAsignatura> obtenerEvaluacionesAsignaturaPorMatricula(Matricula matricula) {
		Query qry = this.em.createQuery(
				"SELECT d FROM EvaluacionAsignatura d WHERE d.informeAnual.matricula = :matricula ORDER BY d.asignatura.orden",
				EvaluacionAsignatura.class);
		qry.setParameter("matricula", matricula);
		return qry.getResultList();
	}
	
	public List<EvaluacionAsignatura> obtenerEvaluacionesAsignaturaPorMatriculaOrdenarPorPrincipal(Matricula matricula) {
		Query qry = this.em.createQuery(
				"SELECT d FROM EvaluacionAsignatura d WHERE d.informeAnual.matricula = :matricula ORDER BY d.asignatura.asignatura DESC",
				EvaluacionAsignatura.class);
		qry.setParameter("matricula", matricula);
		return qry.getResultList();
	}

	public List<BigDecimal> obtenerPromediosQuimestre1Parcial1PorInformeAnual(InformeAnual informeAnual) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre1Parcial1Nota FROM EvaluacionAsignatura d WHERE d.informeAnual.matricula = :matricula AND d.asignatura.logicaEvaluacion = 'SP' ORDER BY d.asignatura.etiqueta");
		qry.setParameter("matricula", informeAnual.getMatricula());
		return qry.getResultList();
	}

	public List<BigDecimal> obtenerPromediosQuimestre1Parcial2PorInformeAnual(InformeAnual informeAnual) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre1Parcial2Nota FROM EvaluacionAsignatura d WHERE d.informeAnual.matricula = :matricula AND d.asignatura.logicaEvaluacion = 'SP' ORDER BY d.asignatura.etiqueta");
		qry.setParameter("matricula", informeAnual.getMatricula());
		return qry.getResultList();
	}

	public List<BigDecimal> obtenerPromediosQuimestre1Parcial3PorInformeAnual(InformeAnual informeAnual) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre1Parcial3Nota FROM EvaluacionAsignatura d WHERE d.informeAnual.matricula = :matricula AND d.asignatura.logicaEvaluacion = 'SP' ORDER BY d.asignatura.etiqueta");
		qry.setParameter("matricula", informeAnual.getMatricula());
		return qry.getResultList();
	}

	public List<BigDecimal> obtenerPromediosQuimestre1PorInformeAnual(InformeAnual informeAnual) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre1Nota FROM EvaluacionAsignatura d WHERE d.informeAnual.matricula = :matricula AND d.asignatura.logicaEvaluacion = 'SP' ORDER BY d.asignatura.etiqueta");
		qry.setParameter("matricula", informeAnual.getMatricula());
		return qry.getResultList();
	}

	public List<BigDecimal> obtenerPromediosQuimestre2Parcial1PorInformeAnual(InformeAnual informeAnual) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre2Parcial1Nota FROM EvaluacionAsignatura d WHERE d.informeAnual.matricula = :matricula AND d.asignatura.logicaEvaluacion = 'SP' ORDER BY d.asignatura.etiqueta");
		qry.setParameter("matricula", informeAnual.getMatricula());
		return qry.getResultList();
	}

	public List<BigDecimal> obtenerPromediosQuimestre2Parcial2PorInformeAnual(InformeAnual informeAnual) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre2Parcial2Nota FROM EvaluacionAsignatura d WHERE d.informeAnual.matricula = :matricula AND d.asignatura.logicaEvaluacion = 'SP' ORDER BY d.asignatura.etiqueta");
		qry.setParameter("matricula", informeAnual.getMatricula());
		return qry.getResultList();
	}

	public List<BigDecimal> obtenerPromediosQuimestre2Parcial3PorInformeAnual(InformeAnual informeAnual) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre2Parcial3Nota FROM EvaluacionAsignatura d WHERE d.informeAnual.matricula = :matricula AND d.asignatura.logicaEvaluacion = 'SP' ORDER BY d.asignatura.etiqueta");
		qry.setParameter("matricula", informeAnual.getMatricula());
		return qry.getResultList();
	}

	public List<BigDecimal> obtenerPromediosQuimestre2PorInformeAnual(InformeAnual informeAnual) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre2Nota FROM EvaluacionAsignatura d WHERE d.informeAnual.matricula = :matricula AND d.asignatura.logicaEvaluacion = 'SP' ORDER BY d.asignatura.etiqueta");
		qry.setParameter("matricula", informeAnual.getMatricula());
		return qry.getResultList();
	}

	public EvaluacionAsignatura obtenerEvaluacionesAsignaturaPorMatriculaYAsignatura(Matricula matricula,
			Asignatura asignatura) {
		Query qry = this.em.createQuery(
				"SELECT d FROM EvaluacionAsignatura d WHERE d.asignatura= :asignatura AND d.informeAnual.matricula = :matricula ",
				EvaluacionAsignatura.class);
		qry.setParameter("asignatura", asignatura);
		qry.setParameter("matricula", matricula);
		return (EvaluacionAsignatura) qry.getSingleResult();
	}

	public List<BigDecimal> obtenerPromediosAnuales(InformeAnual informeAnual) {
		Query qry = this.em.createQuery(
				"SELECT d.notaFinal FROM EvaluacionAsignatura d WHERE d.informeAnual= :informeAnual AND d.asignatura.logicaEvaluacion = 'SP' ORDER BY d.asignatura.orden ");
		qry.setParameter("informeAnual", informeAnual);
		return qry.getResultList();
	}
	

	public List<BigDecimal> obtenerPromediosQuimestre1Parcial1PorEvaluacionAsignaturaPrincipal(EvaluacionAsignatura evaluacionAsignaturaPrincipal) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre1Parcial1Nota FROM EvaluacionAsignatura d WHERE d.informeAnual = :informeAnual AND d.asignatura.asignatura = :asignaturaPrincipal");
		qry.setParameter("informeAnual", evaluacionAsignaturaPrincipal.getInformeAnual());
		qry.setParameter("asignaturaPrincipal", evaluacionAsignaturaPrincipal.getAsignatura());
		return qry.getResultList();
	}
	
	public List<BigDecimal> obtenerPromediosQuimestre1Parcial2PorEvaluacionAsignaturaPrincipal(EvaluacionAsignatura evaluacionAsignaturaPrincipal) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre1Parcial2Nota FROM EvaluacionAsignatura d WHERE d.informeAnual = :informeAnual AND d.asignatura.asignatura = :asignaturaPrincipal");
		qry.setParameter("informeAnual", evaluacionAsignaturaPrincipal.getInformeAnual());
		qry.setParameter("asignaturaPrincipal", evaluacionAsignaturaPrincipal.getAsignatura());
		return qry.getResultList();
	}

	
	public List<BigDecimal> obtenerPromediosQuimestre1Parcial3PorEvaluacionAsignaturaPrincipal(EvaluacionAsignatura evaluacionAsignaturaPrincipal) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre1Parcial3Nota FROM EvaluacionAsignatura d WHERE d.informeAnual = :informeAnual AND d.asignatura.asignatura = :asignaturaPrincipal");
		qry.setParameter("informeAnual", evaluacionAsignaturaPrincipal.getInformeAnual());
		qry.setParameter("asignaturaPrincipal", evaluacionAsignaturaPrincipal.getAsignatura());
		return qry.getResultList();
	}
	

	
	public List<BigDecimal> obtenerPromediosQuimestre1NotaExamenPorEvaluacionAsignaturaPrincipal(EvaluacionAsignatura evaluacionAsignaturaPrincipal) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre1NotaExamen FROM EvaluacionAsignatura d WHERE d.informeAnual = :informeAnual AND d.asignatura.asignatura = :asignaturaPrincipal");
		qry.setParameter("informeAnual", evaluacionAsignaturaPrincipal.getInformeAnual());
		qry.setParameter("asignaturaPrincipal", evaluacionAsignaturaPrincipal.getAsignatura());
		return qry.getResultList();
	}

	
	public List<BigDecimal> obtenerPromediosQuimestre1NotaPromedioExamenPorEvaluacionAsignaturaPrincipal(EvaluacionAsignatura evaluacionAsignaturaPrincipal) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre1NotaPromedioExamen FROM EvaluacionAsignatura d WHERE d.informeAnual = :informeAnual AND d.asignatura.asignatura = :asignaturaPrincipal");
		qry.setParameter("informeAnual", evaluacionAsignaturaPrincipal.getInformeAnual());
		qry.setParameter("asignaturaPrincipal", evaluacionAsignaturaPrincipal.getAsignatura());
		return qry.getResultList();
	}
	
	public List<BigDecimal> obtenerPromediosQuimestre1NotaPromedioParcialesPorEvaluacionAsignaturaPrincipal(EvaluacionAsignatura evaluacionAsignaturaPrincipal) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre1NotaPromedioParciales FROM EvaluacionAsignatura d WHERE d.informeAnual = :informeAnual AND d.asignatura.asignatura = :asignaturaPrincipal");
		qry.setParameter("informeAnual", evaluacionAsignaturaPrincipal.getInformeAnual());
		qry.setParameter("asignaturaPrincipal", evaluacionAsignaturaPrincipal.getAsignatura());
		return qry.getResultList();
	}

	
	public List<BigDecimal> obtenerPromediosQuimestre1NotaPorEvaluacionAsignaturaPrincipal(EvaluacionAsignatura evaluacionAsignaturaPrincipal) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre1Nota FROM EvaluacionAsignatura d WHERE d.informeAnual = :informeAnual AND d.asignatura.asignatura = :asignaturaPrincipal");
		qry.setParameter("informeAnual", evaluacionAsignaturaPrincipal.getInformeAnual());
		qry.setParameter("asignaturaPrincipal", evaluacionAsignaturaPrincipal.getAsignatura());
		return qry.getResultList();
	}

	public List<BigDecimal> obtenerPromediosQuimestre2Parcial1PorEvaluacionAsignaturaPrincipal(EvaluacionAsignatura evaluacionAsignaturaPrincipal) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre2Parcial1Nota FROM EvaluacionAsignatura d WHERE d.informeAnual = :informeAnual AND d.asignatura.asignatura = :asignaturaPrincipal");
		qry.setParameter("informeAnual", evaluacionAsignaturaPrincipal.getInformeAnual());
		qry.setParameter("asignaturaPrincipal", evaluacionAsignaturaPrincipal.getAsignatura());
		return qry.getResultList();
	}

	
	public List<BigDecimal> obtenerPromediosQuimestre2Parcial2PorEvaluacionAsignaturaPrincipal(EvaluacionAsignatura evaluacionAsignaturaPrincipal) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre2Parcial2Nota FROM EvaluacionAsignatura d WHERE d.informeAnual = :informeAnual AND d.asignatura.asignatura = :asignaturaPrincipal");
		qry.setParameter("informeAnual", evaluacionAsignaturaPrincipal.getInformeAnual());
		qry.setParameter("asignaturaPrincipal", evaluacionAsignaturaPrincipal.getAsignatura());
		return qry.getResultList();
	}

	
	public List<BigDecimal> obtenerPromediosQuimestre2Parcial3PorEvaluacionAsignaturaPrincipal(EvaluacionAsignatura evaluacionAsignaturaPrincipal) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre2Parcial3Nota FROM EvaluacionAsignatura d WHERE d.informeAnual = :informeAnual AND d.asignatura.asignatura = :asignaturaPrincipal");
		qry.setParameter("informeAnual", evaluacionAsignaturaPrincipal.getInformeAnual());
		qry.setParameter("asignaturaPrincipal", evaluacionAsignaturaPrincipal.getAsignatura());
		return qry.getResultList();
	}

	
	public List<BigDecimal> obtenerPromediosQuimestre2NotaExamenPorEvaluacionAsignaturaPrincipal(EvaluacionAsignatura evaluacionAsignaturaPrincipal) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre2NotaExamen FROM EvaluacionAsignatura d WHERE d.informeAnual = :informeAnual AND d.asignatura.asignatura = :asignaturaPrincipal");
		qry.setParameter("informeAnual", evaluacionAsignaturaPrincipal.getInformeAnual());
		qry.setParameter("asignaturaPrincipal", evaluacionAsignaturaPrincipal.getAsignatura());
		return qry.getResultList();
	}

	
	public List<BigDecimal> obtenerPromediosQuimestre2NotaPromedioExamenPorEvaluacionAsignaturaPrincipal(EvaluacionAsignatura evaluacionAsignaturaPrincipal) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre2NotaPromedioExamen FROM EvaluacionAsignatura d WHERE d.informeAnual = :informeAnual AND d.asignatura.asignatura = :asignaturaPrincipal");
		qry.setParameter("informeAnual", evaluacionAsignaturaPrincipal.getInformeAnual());
		qry.setParameter("asignaturaPrincipal", evaluacionAsignaturaPrincipal.getAsignatura());
		return qry.getResultList();
	}
	
	public List<BigDecimal> obtenerPromediosQuimestre2NotaPromedioParcialesPorEvaluacionAsignaturaPrincipal(EvaluacionAsignatura evaluacionAsignaturaPrincipal) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre2NotaPromedioParciales FROM EvaluacionAsignatura d WHERE d.informeAnual = :informeAnual AND d.asignatura.asignatura = :asignaturaPrincipal");
		qry.setParameter("informeAnual", evaluacionAsignaturaPrincipal.getInformeAnual());
		qry.setParameter("asignaturaPrincipal", evaluacionAsignaturaPrincipal.getAsignatura());
		return qry.getResultList();
	}

	
	public List<BigDecimal> obtenerPromediosQuimestre2NotaPorEvaluacionAsignaturaPrincipal(EvaluacionAsignatura evaluacionAsignaturaPrincipal) {
		Query qry = this.em.createQuery(
				"SELECT d.quimestre2Nota FROM EvaluacionAsignatura d WHERE d.informeAnual = :informeAnual AND d.asignatura.asignatura = :asignaturaPrincipal");
		qry.setParameter("informeAnual", evaluacionAsignaturaPrincipal.getInformeAnual());
		qry.setParameter("asignaturaPrincipal", evaluacionAsignaturaPrincipal.getAsignatura());
		return qry.getResultList();
	}
	
	public List<BigDecimal> obtenerPromediosQuimestre1Quimestre2PorEvaluacionAsignaturaPrincipal(EvaluacionAsignatura evaluacionAsignaturaPrincipal) {
		Query qry = this.em.createQuery(
				"SELECT d.notaPromedioQuimestre1Quimestre2 FROM EvaluacionAsignatura d WHERE d.informeAnual = :informeAnual AND d.asignatura.asignatura = :asignaturaPrincipal");
		qry.setParameter("informeAnual", evaluacionAsignaturaPrincipal.getInformeAnual());
		qry.setParameter("asignaturaPrincipal", evaluacionAsignaturaPrincipal.getAsignatura());
		return qry.getResultList();
	}
	
	public List<BigDecimal> obtenerPromediosNotaFinalPorEvaluacionAsignaturaPrincipal(EvaluacionAsignatura evaluacionAsignaturaPrincipal) {
		Query qry = this.em.createQuery(
				"SELECT d.notaFinal FROM EvaluacionAsignatura d WHERE d.informeAnual = :informeAnual AND d.asignatura.asignatura = :asignaturaPrincipal");
		qry.setParameter("informeAnual", evaluacionAsignaturaPrincipal.getInformeAnual());
		qry.setParameter("asignaturaPrincipal", evaluacionAsignaturaPrincipal.getAsignatura());
		return qry.getResultList();
	}


}