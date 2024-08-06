package com.gestionestudiantil.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.gestionestudiantil.dao.EvaluacionAsignaturaDao;
import com.gestionestudiantil.dao.EvaluacionComportamientoDao;
import com.gestionestudiantil.dao.EvaluacionProyectoEscolarDao;
import com.gestionestudiantil.dao.GrupoDeEvaluacionDao;
import com.gestionestudiantil.dao.InformeAnualDao;
import com.gestionestudiantil.dao.MatriculaDao;
import com.gestionestudiantil.enums.Grados;
import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.exception.GestionEstudiantilPersistenceException;
import com.gestionestudiantil.model.Asignatura;
import com.gestionestudiantil.model.Estudiante;
import com.gestionestudiantil.model.EvaluacionAsignatura;
import com.gestionestudiantil.model.EvaluacionComportamiento;
import com.gestionestudiantil.model.EvaluacionProyectoEscolar;
import com.gestionestudiantil.model.Grado;
import com.gestionestudiantil.model.GrupoDeEvaluacion;
import com.gestionestudiantil.model.InformeAnual;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Paralelo;
import com.gestionestudiantil.model.PeriodoLectivo;
import com.gestionestudiantil.model.ProyectoEscolar;
import com.gestionestudiantil.util.NumberToLetterConverter;

@Stateless
public class EvaluacionService {

    @Inject
    private MatriculaDao matriculaDao;

    @Inject
    private EvaluacionAsignaturaDao evaluacionAsignaturaDao;

    @Inject
    private EvaluacionComportamientoDao evaluacionComportamientoDao;

    @Inject
    private GrupoDeEvaluacionDao grupoDeEvaluacionDao;

    @Inject
    private InformeAnualDao informeAnualDao;

    @Inject
    private EvaluacionProyectoEscolarDao evaluacionProyectoEscolarDao;

    public Matricula obtenerMatriculaActiva(Estudiante estudiante, String periodo) {
        return matriculaDao.obtenerMatriculaActiva(estudiante, periodo);
    }

    public List<Matricula> obtenerMatriculasPorPeriodoLectivo(PeriodoLectivo periodoLectivo) {
        return matriculaDao.obtenerMatriculasPorPeriodoLectivo(periodoLectivo);
    }

    public List<EvaluacionAsignatura> obtenerEvaluacionesPorAsignatura(Asignatura asignatura) {
        return evaluacionAsignaturaDao.obtenerEvaluacionesPorAsignatura(asignatura);
    }

    public void evaluarEvaluacionAsignatura(EvaluacionAsignatura evaluacionAsignatura)
            throws GestionEstudiantilException {
        try {
            if (evaluacionAsignatura.getAsignatura().getAsignaturas().size() > 0) {
                evaluarAsignaturaPrincipal(evaluacionAsignatura);
                return;
            }
            Integer parciales = evaluacionAsignatura.getInformeAnual().getMatricula().getGrado().getPeriodoLectivo().getParciales();
            evaluacionAsignatura = evaluarPrimerQuimestre(evaluacionAsignatura, parciales);
            evaluacionAsignatura = evaluarSegundoQuimestre(evaluacionAsignatura, parciales);
            evaluacionAsignatura = evaluarNotaFinal(evaluacionAsignatura);
            evaluacionAsignatura = evaluarResultado(evaluacionAsignatura);
            evaluacionAsignaturaDao.editar(evaluacionAsignatura);
            if (evaluacionAsignatura.getAsignatura().getAsignatura() != null) {
                EvaluacionAsignatura evaluacionAsignaturaPrincipal = evaluacionAsignaturaDao
                        .obtenerEvaluacionAsignaturaPorAsignaturaInformeAnual(
                                evaluacionAsignatura.getAsignatura().getAsignatura(),
                                evaluacionAsignatura.getInformeAnual());
                evaluarAsignaturaPrincipal(evaluacionAsignaturaPrincipal);
            }
        } catch (GestionEstudiantilPersistenceException e) {
            throw new GestionEstudiantilException("Error de base de datos", e);
        }
    }

    private EvaluacionAsignatura evaluarResultado(EvaluacionAsignatura evaluacionAsignatura) {
        String resultado = "";
        if (evaluacionAsignatura.getNotaPromedioQuimestre1Quimestre2() != null
                && evaluacionAsignatura.getNotaPromedioQuimestre1Quimestre2().compareTo(new BigDecimal(7)) < 0) {
            resultado = "Supletorio";
        }
        if (evaluacionAsignatura.getNotaPromedioQuimestre1Quimestre2() != null
                && evaluacionAsignatura.getNotaPromedioQuimestre1Quimestre2().compareTo(new BigDecimal(5)) < 0) {
            resultado = "Remedial";
        }
        if (evaluacionAsignatura.getNotaExamenSupletorio() != null
                && evaluacionAsignatura.getNotaExamenSupletorio().compareTo(new BigDecimal(7)) < 0) {
            resultado = "Remedial";
        }
        if (evaluacionAsignatura.getNotaExamenRemedial() != null
                && evaluacionAsignatura.getNotaExamenRemedial().compareTo(new BigDecimal(7)) < 0) {
            resultado = "Gracia";
        }
        if (evaluacionAsignatura.getNotaExamenGracia() != null
                && evaluacionAsignatura.getNotaExamenGracia().compareTo(new BigDecimal(7)) < 0) {
            resultado = "Reprobado";
        }

        if (evaluacionAsignatura.getNotaFinal() != null
                && evaluacionAsignatura.getNotaFinal().compareTo(new BigDecimal(7)) >= 0) {
            resultado = "Aprobado";
        }
        evaluacionAsignatura.setResultado(resultado);
        return evaluacionAsignatura;
    }

    public void evaluarPromediosYResultado(InformeAnual informeAnual) throws GestionEstudiantilPersistenceException {
        List<BigDecimal> promediosQuimestre1Parcial1 = evaluacionAsignaturaDao
                .obtenerPromediosQuimestre1Parcial1PorInformeAnual(informeAnual);
        informeAnual.setQuimestre1Parcial1Nota(obtenerPromedioConsideraNulos(promediosQuimestre1Parcial1));

        List<BigDecimal> promediosQuimestre1Parcial2 = evaluacionAsignaturaDao
                .obtenerPromediosQuimestre1Parcial2PorInformeAnual(informeAnual);
        informeAnual.setQuimestre1Parcial2Nota(obtenerPromedioConsideraNulos(promediosQuimestre1Parcial2));

        List<BigDecimal> promediosQuimestre1Parcial3 = evaluacionAsignaturaDao
                .obtenerPromediosQuimestre1Parcial3PorInformeAnual(informeAnual);
        informeAnual.setQuimestre1Parcial3Nota(obtenerPromedioConsideraNulos(promediosQuimestre1Parcial3));

        List<BigDecimal> promediosQuimestre1 = evaluacionAsignaturaDao
                .obtenerPromediosQuimestre1PorInformeAnual(informeAnual);
        informeAnual.setQuimestre1Nota(obtenerPromedioConsideraNulos(promediosQuimestre1));

        List<BigDecimal> promediosQuimestre2Parcial1 = evaluacionAsignaturaDao
                .obtenerPromediosQuimestre2Parcial1PorInformeAnual(informeAnual);
        informeAnual.setQuimestre2Parcial1Nota(obtenerPromedioConsideraNulos(promediosQuimestre2Parcial1));

        List<BigDecimal> promediosQuimestre2Parcial2 = evaluacionAsignaturaDao
                .obtenerPromediosQuimestre2Parcial2PorInformeAnual(informeAnual);
        informeAnual.setQuimestre2Parcial2Nota(obtenerPromedioConsideraNulos(promediosQuimestre2Parcial2));

        List<BigDecimal> promediosQuimestre2Parcial3 = evaluacionAsignaturaDao
                .obtenerPromediosQuimestre2Parcial3PorInformeAnual(informeAnual);
        informeAnual.setQuimestre2Parcial3Nota(obtenerPromedioConsideraNulos(promediosQuimestre2Parcial3));

        List<BigDecimal> promediosQuimestre2 = evaluacionAsignaturaDao
                .obtenerPromediosQuimestre2PorInformeAnual(informeAnual);
        informeAnual.setQuimestre2Nota(obtenerPromedioConsideraNulos(promediosQuimestre2));

        List<BigDecimal> promediosAnuales = evaluacionAsignaturaDao.obtenerPromediosAnuales(informeAnual);
        informeAnual.setNotaFinal(obtenerPromedioConsideraNulos(promediosAnuales));
        informeAnual.setEtiquetaNotaFinal(obtenerNumerosEnLetras(informeAnual.getNotaFinal()));
        Grado grado = informeAnual.getMatricula().getParalelo().getGrado();

        if (informeAnual.getMatricula().getParalelo().getGrado().getGrado() < 9) {
            if (informeAnual.getNotaFinal().compareTo(new BigDecimal(7)) >= 0) {
                informeAnual.setResultado(obtenerEtiquetaSuperior(grado));
            } else {
                informeAnual.setResultado("NO ES PROMOVIDO");
            }
        } else {
            informeAnual.setResultado(obtenerEtiquetaSuperior(grado));
            for (EvaluacionAsignatura evaluacionAsignatura : informeAnual.getInformesAnuales()) {
                if (evaluacionAsignatura.getResultado() != null
                        && evaluacionAsignatura.getResultado().equals("Reprobado")) {
                    informeAnual.setResultado("NO ES PROMOVIDO");
                }
            }

        }

        informeAnualDao.editar(informeAnual);
    }

    public static String obtenerEtiquetaSuperior(Grado grado) {
        if (grado.getPeriodoLectivo().getInstitucion().getId() == 24) {
            return obtenerEtiquetaSuperiorInstitucion24(grado);
        }

        String etiquetaSuperior = "es promovido al ";

        switch (grado.getGrado() + 1) {
            case 1:
                etiquetaSuperior += Grados.INI_1.getDescripcionGrado();
                break;
            case 2:
                etiquetaSuperior += Grados.INI_2.getDescripcionGrado();
                break;
            case 3:
                etiquetaSuperior += Grados.EGB_1.getDescripcionGrado();
                break;
            case 4:
                etiquetaSuperior += Grados.EGB_2.getDescripcionGrado();
                break;
            case 5:
                etiquetaSuperior += Grados.EGB_3.getDescripcionGrado();
                break;
            case 6:
                etiquetaSuperior += Grados.EGB_4.getDescripcionGrado();
                break;
            case 7:
                etiquetaSuperior += Grados.EGB_5.getDescripcionGrado();
                break;
            case 8:
                etiquetaSuperior += Grados.EGB_6.getDescripcionGrado();
                break;
            case 9:
                etiquetaSuperior += Grados.EGB_7.getDescripcionGrado();
                break;
            case 10:
                etiquetaSuperior += Grados.EGB_8.getDescripcionGrado();
                break;
            case 11:
                etiquetaSuperior += Grados.EGB_9.getDescripcionGrado();
                break;
            case 12:
                etiquetaSuperior += Grados.EGB_10.getDescripcionGrado();
                break;
            case 13:
                etiquetaSuperior += Grados.BAC_1.getDescripcionGrado();
                break;
            case 14:
                etiquetaSuperior += Grados.BAC_2.getDescripcionGrado();
                break;
            case 15:
                etiquetaSuperior += Grados.BAC_3.getDescripcionGrado();
                break;
            case 16:
                etiquetaSuperior += "Inmediato Superior";
                break;
        }
        return etiquetaSuperior;
    }

    private static String obtenerEtiquetaSuperiorInstitucion24(Grado grado) {
        String etiquetaSuperior = "es promovido/a a ";

        switch (grado.getGrado() + 1) {
            case 1:
                etiquetaSuperior += Grados.INI_1.getDescripcionGradoDeEducacionBasica();
                break;
            case 2:
                etiquetaSuperior += Grados.INI_2.getDescripcionGradoDeEducacionBasica();
                break;
            case 3:
                etiquetaSuperior += Grados.EGB_1.getDescripcionGradoDeEducacionBasica();
                break;
            case 4:
                etiquetaSuperior += Grados.EGB_2.getDescripcionGradoDeEducacionBasica();
                break;
            case 5:
                etiquetaSuperior += Grados.EGB_3.getDescripcionGradoDeEducacionBasica();
                break;
            case 6:
                etiquetaSuperior += Grados.EGB_4.getDescripcionGradoDeEducacionBasica();
                break;
            case 7:
                etiquetaSuperior += Grados.EGB_5.getDescripcionGradoDeEducacionBasica();
                break;
            case 8:
                etiquetaSuperior += Grados.EGB_6.getDescripcionGradoDeEducacionBasica();
                break;
            case 9:
                etiquetaSuperior += Grados.EGB_7.getDescripcionGradoDeEducacionBasica();
                break;
            case 10:
                etiquetaSuperior += Grados.EGB_8.getDescripcionGradoDeEducacionBasica();
                break;
            case 11:
                etiquetaSuperior += Grados.EGB_9.getDescripcionGradoDeEducacionBasica();
                break;
            case 12:
                etiquetaSuperior += Grados.EGB_10.getDescripcionGradoDeEducacionBasica();
                break;
            case 13:
                etiquetaSuperior += Grados.BAC_1.getDescripcionGradoDeEducacionBasica();
                break;
            case 14:
                etiquetaSuperior += Grados.BAC_2.getDescripcionGradoDeEducacionBasica();
                break;
            case 15:
                etiquetaSuperior += Grados.BAC_3.getDescripcionGradoDeEducacionBasica();
                break;
            case 16:
                etiquetaSuperior = "el/la estudiante ha culminado el Tercer Curso de Bachillerato General Unificado";
                break;
        }
        return etiquetaSuperior;
    }

    private EvaluacionAsignatura evaluarNotaFinal(EvaluacionAsignatura evaluacionAsignatura) {

        BigDecimal n = (evaluacionAsignatura.getQuimestre1Nota().add(evaluacionAsignatura.getQuimestre2Nota()));
        BigDecimal n2 = n.divide(BigDecimal.valueOf(2), 2, BigDecimal.ROUND_FLOOR);
        evaluacionAsignatura.setNotaFinal(n2);
        evaluacionAsignatura.setNotaPromedioQuimestre1Quimestre2(n2);

        if (evaluacionAsignatura.getNotaExamenRecuperacion() != null) {
            evaluacionAsignatura = evaluarExamenRecuperacion(evaluacionAsignatura);
        }
        if (evaluacionAsignatura.getNotaExamenSupletorio() != null) {
            evaluacionAsignatura = evaluarExamenSupletorio(evaluacionAsignatura);
        }
        if (evaluacionAsignatura.getNotaExamenRemedial() != null) {
            evaluacionAsignatura = evaluarExamenRemedial(evaluacionAsignatura);
        }
        if (evaluacionAsignatura.getNotaExamenGracia() != null) {
            evaluacionAsignatura = evaluarExamenGracia(evaluacionAsignatura);
        }
        evaluacionAsignatura.setEtiquetaNotaFinal(obtenerNumerosEnLetras(evaluacionAsignatura.getNotaFinal()));
        return evaluacionAsignatura;
    }

    private String obtenerNumerosEnLetras(BigDecimal notaFinal) {
        return NumberToLetterConverter.convertNumberToLetter(notaFinal);
    }

    private EvaluacionAsignatura evaluarSegundoQuimestre(EvaluacionAsignatura detalle, Integer parciales) {

        List<BigDecimal> evaluacionesQ2P1 = new ArrayList<BigDecimal>();
        evaluacionesQ2P1.add(detalle.getQuimestre2Parcial1Insumo1());
        evaluacionesQ2P1.add(detalle.getQuimestre2Parcial1Insumo2());
        evaluacionesQ2P1.add(detalle.getQuimestre2Parcial1Insumo3());
        evaluacionesQ2P1.add(detalle.getQuimestre2Parcial1Insumo4());
        evaluacionesQ2P1.add(detalle.getQuimestre2Parcial1RefuerzoAcademico1());
        evaluacionesQ2P1.add(detalle.getQuimestre2Parcial1RefuerzoAcademico2());
        evaluacionesQ2P1.add(detalle.getQuimestre2Parcial1RefuerzoAcademico3());
        evaluacionesQ2P1.add(detalle.getQuimestre2Parcial1RefuerzoAcademico4());

        BigDecimal q2p1 = obtenerPromedio(evaluacionesQ2P1);

        detalle.setQuimestre2Parcial1Nota(q2p1);

        List<BigDecimal> evaluacionesQ2P2 = new ArrayList<BigDecimal>();
        evaluacionesQ2P2.add(detalle.getQuimestre2Parcial2Insumo1());
        evaluacionesQ2P2.add(detalle.getQuimestre2Parcial2Insumo2());
        evaluacionesQ2P2.add(detalle.getQuimestre2Parcial2Insumo3());
        evaluacionesQ2P2.add(detalle.getQuimestre2Parcial2Insumo4());
        evaluacionesQ2P2.add(detalle.getQuimestre2Parcial2RefuerzoAcademico1());
        evaluacionesQ2P2.add(detalle.getQuimestre2Parcial2RefuerzoAcademico2());
        evaluacionesQ2P2.add(detalle.getQuimestre2Parcial2RefuerzoAcademico3());
        evaluacionesQ2P2.add(detalle.getQuimestre2Parcial2RefuerzoAcademico4());

        BigDecimal q2p2 = obtenerPromedio(evaluacionesQ2P2);

        detalle.setQuimestre2Parcial2Nota(q2p2);

        BigDecimal q2 = BigDecimal.ZERO;
        if (parciales == 3) {
            List<BigDecimal> evaluacionesQ2P3 = new ArrayList<BigDecimal>();
            evaluacionesQ2P3.add(detalle.getQuimestre2Parcial3Insumo1());
            evaluacionesQ2P3.add(detalle.getQuimestre2Parcial3Insumo2());
            evaluacionesQ2P3.add(detalle.getQuimestre2Parcial3Insumo3());
            evaluacionesQ2P3.add(detalle.getQuimestre2Parcial3Insumo4());
            evaluacionesQ2P3.add(detalle.getQuimestre2Parcial3RefuerzoAcademico1());
            evaluacionesQ2P3.add(detalle.getQuimestre2Parcial3RefuerzoAcademico2());
            evaluacionesQ2P3.add(detalle.getQuimestre2Parcial3RefuerzoAcademico3());
            evaluacionesQ2P3.add(detalle.getQuimestre2Parcial3RefuerzoAcademico4());

            BigDecimal q2p3 = obtenerPromedio(evaluacionesQ2P3);

            detalle.setQuimestre2Parcial3Nota(q2p3);

            q2 = q2p1.add(q2p2).add(q2p3).multiply(BigDecimal.valueOf(8)).divide(BigDecimal.valueOf(30), 2,
                    BigDecimal.ROUND_FLOOR);
        } else if (parciales == 2) {
            q2 = q2p1.add(q2p2).multiply(BigDecimal.valueOf(8)).divide(BigDecimal.valueOf(20), 2,
                    BigDecimal.ROUND_FLOOR);
        }

        detalle.setQuimestre2NotaPromedioParciales(q2);
        if (detalle.getQuimestre2NotaExamen() != null) {
            BigDecimal q2Ne = detalle.getQuimestre2NotaExamen().multiply(BigDecimal.valueOf(20))
                    .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR);
            detalle.setQuimestre2NotaPromedioExamen(q2Ne);
            detalle.setQuimestre2Nota(q2Ne.add(q2));
        } else {
            detalle.setQuimestre2Nota(q2);
        }
        return detalle;
    }

    private EvaluacionAsignatura evaluarPrimerQuimestre(EvaluacionAsignatura detalle, Integer parciales) {

        List<BigDecimal> evaluacionesQ1P1 = new ArrayList<BigDecimal>();
        evaluacionesQ1P1.add(detalle.getQuimestre1Parcial1Insumo1());
        evaluacionesQ1P1.add(detalle.getQuimestre1Parcial1Insumo2());
        evaluacionesQ1P1.add(detalle.getQuimestre1Parcial1Insumo3());
        evaluacionesQ1P1.add(detalle.getQuimestre1Parcial1Insumo4());
        evaluacionesQ1P1.add(detalle.getQuimestre1Parcial1RefuerzoAcademico1());
        evaluacionesQ1P1.add(detalle.getQuimestre1Parcial1RefuerzoAcademico2());
        evaluacionesQ1P1.add(detalle.getQuimestre1Parcial1RefuerzoAcademico3());
        evaluacionesQ1P1.add(detalle.getQuimestre1Parcial1RefuerzoAcademico4());

        BigDecimal q1p1 = obtenerPromedio(evaluacionesQ1P1);

        detalle.setQuimestre1Parcial1Nota(q1p1);

        List<BigDecimal> evaluacionesQ1P2 = new ArrayList<BigDecimal>();
        evaluacionesQ1P2.add(detalle.getQuimestre1Parcial2Insumo1());
        evaluacionesQ1P2.add(detalle.getQuimestre1Parcial2Insumo2());
        evaluacionesQ1P2.add(detalle.getQuimestre1Parcial2Insumo3());
        evaluacionesQ1P2.add(detalle.getQuimestre1Parcial2Insumo4());
        evaluacionesQ1P2.add(detalle.getQuimestre1Parcial2RefuerzoAcademico1());
        evaluacionesQ1P2.add(detalle.getQuimestre1Parcial2RefuerzoAcademico2());
        evaluacionesQ1P2.add(detalle.getQuimestre1Parcial2RefuerzoAcademico3());
        evaluacionesQ1P2.add(detalle.getQuimestre1Parcial2RefuerzoAcademico4());

        BigDecimal q1p2 = obtenerPromedio(evaluacionesQ1P2);

        detalle.setQuimestre1Parcial2Nota(q1p2);
        BigDecimal q1 = BigDecimal.ZERO;
        if (parciales == 3) {
            List<BigDecimal> evaluacionesQ1P3 = new ArrayList<BigDecimal>();
            evaluacionesQ1P3.add(detalle.getQuimestre1Parcial3Insumo1());
            evaluacionesQ1P3.add(detalle.getQuimestre1Parcial3Insumo2());
            evaluacionesQ1P3.add(detalle.getQuimestre1Parcial3Insumo3());
            evaluacionesQ1P3.add(detalle.getQuimestre1Parcial3Insumo4());
            evaluacionesQ1P3.add(detalle.getQuimestre1Parcial3RefuerzoAcademico1());
            evaluacionesQ1P3.add(detalle.getQuimestre1Parcial3RefuerzoAcademico2());
            evaluacionesQ1P3.add(detalle.getQuimestre1Parcial3RefuerzoAcademico3());
            evaluacionesQ1P3.add(detalle.getQuimestre1Parcial3RefuerzoAcademico4());

            BigDecimal q1p3 = obtenerPromedio(evaluacionesQ1P3);

            detalle.setQuimestre1Parcial3Nota(q1p3);

            q1 = q1p1.add(q1p2).add(q1p3).multiply(BigDecimal.valueOf(8)).divide(BigDecimal.valueOf(30), 2,
                    BigDecimal.ROUND_FLOOR);

            detalle.setQuimestre1NotaPromedioParciales(q1);
        } else if (parciales == 2) {

            q1 = q1p1.add(q1p2).multiply(BigDecimal.valueOf(8)).divide(BigDecimal.valueOf(20), 2,
                    BigDecimal.ROUND_FLOOR);

            detalle.setQuimestre1NotaPromedioParciales(q1);
        }

        if (detalle.getQuimestre1NotaExamen() != null) {
            BigDecimal q1Ne = detalle.getQuimestre1NotaExamen().multiply(BigDecimal.valueOf(2))
                    .divide(BigDecimal.valueOf(10), 2, BigDecimal.ROUND_FLOOR);
            detalle.setQuimestre1NotaPromedioExamen(q1Ne);
            detalle.setQuimestre1Nota(q1Ne.add(q1));
        } else {
            detalle.setQuimestre1Nota(q1);
        }
        return detalle;
    }

    public BigDecimal obtenerPromedio(List<BigDecimal> evaluaciones) {
        BigDecimal sum = BigDecimal.ZERO;
        int count = 0;
        for (BigDecimal evaluacion : evaluaciones) {
            if (null != evaluacion) {
                sum = sum.add(evaluacion);
                count++;
            }
        }
        return count != 0 ? sum.divide(new BigDecimal(count), 2, BigDecimal.ROUND_FLOOR) : BigDecimal.ZERO;
    }

    public BigDecimal obtenerPromedioConsideraNulos(List<BigDecimal> evaluaciones) {
        BigDecimal sum = BigDecimal.ZERO;
        int count = 0;
        for (BigDecimal evaluacion : evaluaciones) {
            if (null != evaluacion) {
                sum = sum.add(evaluacion);
            } else {
                sum = sum.add(BigDecimal.ZERO);
            }
            count++;
        }
        return count != 0 ? sum.divide(new BigDecimal(count), 2, BigDecimal.ROUND_FLOOR) : BigDecimal.ZERO;
    }

    private EvaluacionAsignatura evaluarExamenSupletorio(EvaluacionAsignatura detalle) {
        BigDecimal nS = detalle.getNotaExamenSupletorio();
        if (nS.compareTo(BigDecimal.valueOf(7L)) >= 0) {
            detalle.setNotaFinal(BigDecimal.valueOf(7L));
        }
        return detalle;
    }

    private EvaluacionAsignatura evaluarExamenRemedial(EvaluacionAsignatura detalle) {
        BigDecimal notaExamenRemedial = detalle.getNotaExamenRemedial();
        if (notaExamenRemedial.compareTo(BigDecimal.valueOf(7L)) >= 0) {
            detalle.setNotaFinal(BigDecimal.valueOf(7L));
        }
        return detalle;
    }

    private EvaluacionAsignatura evaluarExamenGracia(EvaluacionAsignatura detalle) {
        BigDecimal notaExamenGracia = detalle.getNotaExamenGracia();
        if (notaExamenGracia.compareTo(BigDecimal.valueOf(7L)) >= 0) {
            detalle.setNotaFinal(BigDecimal.valueOf(7L));
        }
        return detalle;
    }

    private EvaluacionAsignatura evaluarExamenRecuperacion(EvaluacionAsignatura detalle) {
        BigDecimal nR = detalle.getNotaExamenRecuperacion();
        BigDecimal nq1 = detalle.getQuimestre1Nota();
        BigDecimal nq2 = detalle.getQuimestre2Nota();

        if (nR.compareTo(nq1) > 0 && nq2.compareTo(nq1) > 0) {
            detalle.setQuimestre1Nota(nR);
        }
        if (nR.compareTo(nq2) > 0 && nq1.compareTo(nq2) > 0) {
            detalle.setQuimestre2Nota(nR);
        }
        BigDecimal n = (detalle.getQuimestre1Nota().add(detalle.getQuimestre2Nota()));
        BigDecimal n2 = n.divide(BigDecimal.valueOf(2), 2, BigDecimal.ROUND_FLOOR);
        detalle.setNotaFinal(n2);
        detalle.setNotaPromedioQuimestre1Quimestre2(n2);
        return detalle;
    }

    private void evaluarAsignaturaPrincipal(EvaluacionAsignatura evaluacionAsignaturaPrincipal)
            throws GestionEstudiantilPersistenceException {

        evaluacionAsignaturaPrincipal.setQuimestre1Parcial1Nota(obtenerPromedioConsideraNulos(evaluacionAsignaturaDao
                .obtenerPromediosQuimestre1Parcial1PorEvaluacionAsignaturaPrincipal(evaluacionAsignaturaPrincipal)));
        evaluacionAsignaturaPrincipal.setQuimestre1Parcial2Nota(obtenerPromedioConsideraNulos(evaluacionAsignaturaDao
                .obtenerPromediosQuimestre1Parcial2PorEvaluacionAsignaturaPrincipal(evaluacionAsignaturaPrincipal)));
        evaluacionAsignaturaPrincipal.setQuimestre1Parcial3Nota(obtenerPromedioConsideraNulos(evaluacionAsignaturaDao
                .obtenerPromediosQuimestre1Parcial3PorEvaluacionAsignaturaPrincipal(evaluacionAsignaturaPrincipal)));

        evaluacionAsignaturaPrincipal.setQuimestre1NotaExamen(obtenerPromedioConsideraNulos(evaluacionAsignaturaDao
                .obtenerPromediosQuimestre1NotaExamenPorEvaluacionAsignaturaPrincipal(evaluacionAsignaturaPrincipal)));
        evaluacionAsignaturaPrincipal.setQuimestre1NotaPromedioExamen(obtenerPromedioConsideraNulos(
                evaluacionAsignaturaDao.obtenerPromediosQuimestre1NotaPromedioExamenPorEvaluacionAsignaturaPrincipal(
                        evaluacionAsignaturaPrincipal)));
        evaluacionAsignaturaPrincipal.setQuimestre1NotaPromedioParciales(obtenerPromedioConsideraNulos(
                evaluacionAsignaturaDao.obtenerPromediosQuimestre1NotaPromedioParcialesPorEvaluacionAsignaturaPrincipal(
                        evaluacionAsignaturaPrincipal)));
        evaluacionAsignaturaPrincipal.setQuimestre1Nota(obtenerPromedioConsideraNulos(evaluacionAsignaturaDao
                .obtenerPromediosQuimestre1NotaPorEvaluacionAsignaturaPrincipal(evaluacionAsignaturaPrincipal)));

        evaluacionAsignaturaPrincipal.setQuimestre2Parcial1Nota(obtenerPromedioConsideraNulos(evaluacionAsignaturaDao
                .obtenerPromediosQuimestre2Parcial1PorEvaluacionAsignaturaPrincipal(evaluacionAsignaturaPrincipal)));
        evaluacionAsignaturaPrincipal.setQuimestre2Parcial2Nota(obtenerPromedioConsideraNulos(evaluacionAsignaturaDao
                .obtenerPromediosQuimestre2Parcial2PorEvaluacionAsignaturaPrincipal(evaluacionAsignaturaPrincipal)));
        evaluacionAsignaturaPrincipal.setQuimestre2Parcial3Nota(obtenerPromedioConsideraNulos(evaluacionAsignaturaDao
                .obtenerPromediosQuimestre2Parcial3PorEvaluacionAsignaturaPrincipal(evaluacionAsignaturaPrincipal)));

        evaluacionAsignaturaPrincipal.setQuimestre2NotaExamen(obtenerPromedioConsideraNulos(evaluacionAsignaturaDao
                .obtenerPromediosQuimestre2NotaExamenPorEvaluacionAsignaturaPrincipal(evaluacionAsignaturaPrincipal)));
        evaluacionAsignaturaPrincipal.setQuimestre2NotaPromedioExamen(obtenerPromedioConsideraNulos(
                evaluacionAsignaturaDao.obtenerPromediosQuimestre2NotaPromedioExamenPorEvaluacionAsignaturaPrincipal(
                        evaluacionAsignaturaPrincipal)));
        evaluacionAsignaturaPrincipal.setQuimestre2NotaPromedioParciales(obtenerPromedioConsideraNulos(
                evaluacionAsignaturaDao.obtenerPromediosQuimestre2NotaPromedioParcialesPorEvaluacionAsignaturaPrincipal(
                        evaluacionAsignaturaPrincipal)));
        evaluacionAsignaturaPrincipal.setQuimestre2Nota(obtenerPromedioConsideraNulos(evaluacionAsignaturaDao
                .obtenerPromediosQuimestre2NotaPorEvaluacionAsignaturaPrincipal(evaluacionAsignaturaPrincipal)));

        evaluacionAsignaturaPrincipal.setNotaPromedioQuimestre1Quimestre2(obtenerPromedioConsideraNulos(
                evaluacionAsignaturaDao.obtenerPromediosQuimestre1Quimestre2PorEvaluacionAsignaturaPrincipal(
                        evaluacionAsignaturaPrincipal)));
        evaluacionAsignaturaPrincipal.setNotaFinal(obtenerPromedioConsideraNulos(evaluacionAsignaturaDao
                .obtenerPromediosNotaFinalPorEvaluacionAsignaturaPrincipal(evaluacionAsignaturaPrincipal)));

        evaluacionAsignaturaPrincipal
                .setEtiquetaNotaFinal(obtenerNumerosEnLetras(evaluacionAsignaturaPrincipal.getNotaFinal()));
        evaluacionAsignaturaPrincipal = evaluarResultado(evaluacionAsignaturaPrincipal);
        evaluacionAsignaturaDao.editar(evaluacionAsignaturaPrincipal);

    }

    public List<EvaluacionComportamiento> obtenerEvaluacionesComportamientoPorParalelo(Paralelo paralelo) {
        return evaluacionComportamientoDao.obtenerEvaluacionesComportamientoPorParalelo(paralelo);
    }

    public void actualizarEvaluacionComportamiento(EvaluacionComportamiento evaluacionComportamiento)
            throws GestionEstudiantilException {
        try {
            evaluacionComportamientoDao.editar(evaluacionComportamiento);
        } catch (GestionEstudiantilPersistenceException e) {
            throw new GestionEstudiantilException("Error al actualizar la Evaluacion de Comportamiento", e);
        }

    }

    public List<EvaluacionComportamiento> obtenerEvaluacionesComportamientoPorMatricula(Matricula matricula) {
        return evaluacionComportamientoDao.obtenerEvaluacionesComportamientoPorMatricula(matricula);
    }

    public List<GrupoDeEvaluacion> obtenerGruposDeEvaluacionActivos(PeriodoLectivo periodoLectivo) {
        return grupoDeEvaluacionDao.obtenerGrupoDeEvaluacionActivosPorPeriodoLectivo(periodoLectivo);
    }

    public List<EvaluacionAsignatura> obtenerDetallesInformesAnualesSupletoriosPorAsignatura(Asignatura asignatura) {
        return evaluacionAsignaturaDao.obtenerDetallesInformesAnualesSupletoriosPorAsignatura(asignatura);
    }

    public List<EvaluacionAsignatura> obtenerDetallesInformesAnualesRemedialesPorAsignatura(Asignatura asignatura) {
        return evaluacionAsignaturaDao.obtenerEvaluacionesAsignaturaRemedialesPorAsignatura(asignatura);
    }

    public List<EvaluacionAsignatura> obtenerDetallesInformesAnualesMejoramientoPorAsignatura(Asignatura asignatura) {
        return evaluacionAsignaturaDao.obtenerEvaluacionesAsignaturasMejoramientoPorAsignatura(asignatura);
    }

    public List<EvaluacionAsignatura> obtenerDetallesInformesAnualesGraciaPorAsignatura(Asignatura asignatura) {
        return evaluacionAsignaturaDao.obtenerEvaluacionesAsignaturasGraciaPorAsignatura(asignatura);
    }

    public List<EvaluacionProyectoEscolar> obtenerEvaluacionesPorProyectoEscolar(ProyectoEscolar proyectoEscolar) {
        return evaluacionProyectoEscolarDao.obtenerEvaluacionesPorProyectoEscolar(proyectoEscolar);
    }

    public void evaluarEvaluacionProyectoEscolar(EvaluacionProyectoEscolar evaluacionProyectoEscolar)
            throws GestionEstudiantilException {
        try {
            evaluacionProyectoEscolarDao.editar(evaluacionProyectoEscolar);
        } catch (GestionEstudiantilPersistenceException e) {
            throw new GestionEstudiantilException("No se ha podido evaluar el proyecto escolar");
        }
    }

    public List<Matricula> obtenerMatriculasPorInstitucion(Institucion institucion, String periodo) {
        return matriculaDao.obtenerMatriculasPorInstitucion(institucion, periodo);
    }

    public void promediar(Paralelo paralelo) throws GestionEstudiantilException {
        Integer parciales = paralelo.getGrado().getPeriodoLectivo().getParciales();
        
        for (Matricula matricula : paralelo.getEstudiantesMatriculados()) {
            for (EvaluacionAsignatura evaluacionAsignatura : evaluacionAsignaturaDao
                    .obtenerEvaluacionesAsignaturaPorMatriculaOrdenarPorPrincipal(matricula)) {
                evaluarEvaluacionAsignatura(evaluacionAsignatura);
            }

            try {
                evaluarPromediosYResultado(matricula.getInformeAnual());
            } catch (GestionEstudiantilPersistenceException e) {
                throw new GestionEstudiantilException("Error al calcular el promedio");
            }
        }
        for (Matricula matricula : paralelo.getEstudiantesMatriculados()) {
            Integer totalEvaluaciones = 0;
            Integer evaluaciones = 0;
            for (EvaluacionComportamiento evaluacionComportamiento : matricula.getEvaluacionesComportamiento()) {
                String evaluacionComportamientoUltimoParcial = parciales == 3 ? evaluacionComportamiento.getB6() : evaluacionComportamiento.getB5();
                if (evaluacionComportamientoUltimoParcial != null) {
                    switch (evaluacionComportamientoUltimoParcial) {
                        case "A":
                            evaluaciones += 5;
                            break;
                        case "B":
                            evaluaciones += 4;
                            break;
                        case "C":
                            evaluaciones += 3;
                            break;
                        case "D":
                            evaluaciones += 2;
                            break;
                        case "E":
                            evaluaciones += 1;
                            break;
                        default:
                            break;
                    }
                    totalEvaluaciones += 1;
                }
            }

            Integer promedio = 1;

            if (totalEvaluaciones != 0) {
                promedio = evaluaciones / totalEvaluaciones;
            }
            String evaluacionComportamiento = "E";
            switch (promedio) {

                case 1:
                    evaluacionComportamiento = "E";
                    break;
                case 2:
                    evaluacionComportamiento = "D";
                    break;
                case 3:
                    evaluacionComportamiento = "C";
                    break;
                case 4:
                    evaluacionComportamiento = "B";
                    break;
                case 5:
                    evaluacionComportamiento = "A";
                    break;
                default:
                    break;

            }
            matricula.getInformeAnual().setEvaluacionComportamientoB6(evaluacionComportamiento);
            try {
                matriculaDao.editar(matricula);
            } catch (GestionEstudiantilPersistenceException e) {
                e.printStackTrace();
            }
        }
    }

    public List<GrupoDeEvaluacion> obtenerGruposDeEvaluacionDeComportamientoActivos(PeriodoLectivo periodoLectivo) {
        return grupoDeEvaluacionDao.obtenerGrupoDeEvaluacionDeComportamientoActivosPorPeriodoLectivo(periodoLectivo);
    }

    public List<GrupoDeEvaluacion> obtenerGruposDeEvaluacionDeDestrezasActivos(PeriodoLectivo periodoLectivo) {
        return grupoDeEvaluacionDao.obtenerGruposDeEvaluacionDeDestrezasActivos(periodoLectivo);
    }

    public List<GrupoDeEvaluacion> obtenerGruposDeEvaluacionDeProyectosEscolaresActivos(PeriodoLectivo periodoLectivo) {
        return grupoDeEvaluacionDao.obtenerGruposDeEvaluacionDeProyectosEscolaresActivos(periodoLectivo);
    }
}
