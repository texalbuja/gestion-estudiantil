package com.gestionestudiantil.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.gestionestudiantil.model.Asistencia;
import com.gestionestudiantil.model.EvaluacionAsignatura;
import com.gestionestudiantil.model.EvaluacionComportamiento;
import com.gestionestudiantil.model.GrupoDeEvaluacion;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Observacion;
import com.gestionestudiantil.model.PeriodoLectivo;
import com.gestionestudiantil.service.ComportamientoService;
import com.gestionestudiantil.service.InformesService;

@ManagedBean
@ViewScoped
public class InformeParcialController implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{matricula}")
    private Matricula matricula;

    @ManagedProperty("#{grupoDeEvaluacion}")
    private GrupoDeEvaluacion grupoDeEvaluacion;

    @ManagedProperty("#{periodoLectivo}")
    private PeriodoLectivo periodoLectivo;

    @Inject
    private FacesContext facesContext;

    @Inject
    private InformesService informesService;

    @Inject
    private ComportamientoService comportamientoService;

    private List<EvaluacionAsignatura> evaluaciones;

    private List<Observacion> observaciones;

    private List<EvaluacionComportamiento> evaluacionesComportamiento;

    private List<Asistencia> asistencias;

    @PostConstruct
    public void init() {

        evaluaciones = informesService.obtenerEvaluacionesPorMatricula(matricula);
        observaciones = informesService.obtenerObservacionesPorMatriculaYGrupoDeEvaluacion(matricula,
                grupoDeEvaluacion);
        asistencias = comportamientoService.obtenerAsistenciasPorMatriculaYBloque(matricula, grupoDeEvaluacion);
        evaluacionesComportamiento = informesService.obtenerEvaluacionesComportamientoPorMatricula(matricula);

    }
    public Boolean getRenderizarParciales() {
		return periodoLectivo.getParciales()!= null && periodoLectivo.getParciales() == 3;
    }
        

    public String exportarReporteLibretasPrimerBloque() {
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("reporte", "/reportes/libreta_q1_b1_estudiante.jasper");
        parametros.put("matricula_id", matricula.getId());
        facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
        return "/reportes/pdf/pdf.jsf?faces-redirect=true";
    }

    public String exportarReporteLibretasSegundoQuimestrePrimerBloque() {
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("reporte", "/reportes/libreta_q2_b1_estudiante.jasper");
        parametros.put("matricula_id", matricula.getId());
        facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
        return "/reportes/pdf/pdf.jsf?faces-redirect=true";
    }

    public String exportarReporteLibretaQuimestre1() {
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("reporte", "/reportes/libreta_q1_estudiante.jasper");
        parametros.put("matricula_id", matricula.getId());
        facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
        return "/reportes/pdf/pdf.jsf?faces-redirect=true";
    }

    public String exportarReporteLibretaQuimestre2() {
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("reporte", "/reportes/libreta_q2_estudiante.jasper");
        parametros.put("matricula_id", matricula.getId());
        facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
        return "/reportes/pdf/pdf.jsf?faces-redirect=true";
    }

    public String exportarReporteLibretaQuimestre1Parcial2() {
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("reporte", "/reportes/libreta_q1_b2_estudiante.jasper");
        parametros.put("matricula_id", matricula.getId());
        facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
        return "/reportes/pdf/pdf.jsf?faces-redirect=true";
    }

    public String exportarReporteLibretaQuimestre1Parcial3() {
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("reporte", "/reportes/libreta_q1_b3_estudiante.jasper");
        parametros.put("matricula_id", matricula.getId());
        facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
        return "/reportes/pdf/pdf.jsf?faces-redirect=true";
    }

    public String exportarReporteLibretaQuimestre2Parcial1() {
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("reporte", "/reportes/libreta_q2_b1_estudiante.jasper");
        parametros.put("matricula_id", matricula.getId());
        facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
        return "/reportes/pdf/pdf.jsf?faces-redirect=true";
    }

    public String exportarReporteLibretaQuimestre2Parcial2() {
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("reporte", "/reportes/libreta_q2_b2_estudiante.jasper");
        parametros.put("matricula_id", matricula.getId());
        facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
        return "/reportes/pdf/pdf.jsf?faces-redirect=true";
    }

    public String exportarReporteLibretaQuimestre2Parcial3() {
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("reporte", "/reportes/libreta_q2_b3_estudiante.jasper");
        parametros.put("matricula_id", matricula.getId());
        facesContext.getExternalContext().getSessionMap().put("parametros", parametros);
        return "/reportes/pdf/pdf.jsf?faces-redirect=true";
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public List<EvaluacionAsignatura> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(List<EvaluacionAsignatura> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    public List<Observacion> getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(List<Observacion> observaciones) {
        this.observaciones = observaciones;
    }

    public List<EvaluacionComportamiento> getEvaluacionesComportamiento() {
        return evaluacionesComportamiento;
    }

    public void setEvaluacionesComportamiento(List<EvaluacionComportamiento> evaluacionesComportamiento) {
        this.evaluacionesComportamiento = evaluacionesComportamiento;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }

    public GrupoDeEvaluacion getGrupoDeEvaluacion() {
        return grupoDeEvaluacion;
    }

    public void setGrupoDeEvaluacion(GrupoDeEvaluacion grupoDeEvaluacion) {
        this.grupoDeEvaluacion = grupoDeEvaluacion;
    }

    public PeriodoLectivo getPeriodoLectivo() {
        return periodoLectivo;
    }

    public void setPeriodoLectivo(PeriodoLectivo periodoLectivo) {
        this.periodoLectivo = periodoLectivo;
    }
}
