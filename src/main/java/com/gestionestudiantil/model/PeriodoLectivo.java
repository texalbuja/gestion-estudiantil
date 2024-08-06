package com.gestionestudiantil.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.gestionestudiantil.enums.Secciones;

@Entity
@Table(name = "periodo_lectivo")
public class PeriodoLectivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String etiqueta;
    @Column
    private String estado;
    @ManyToOne(fetch = FetchType.LAZY)
    private Institucion institucion;
    @Column
    private String seccion;
    @Column
    private Integer contador;

    @Column(name = "codigo_certificado")
    private Integer codigoCertificado;

    @Column(name = "insumos")
    private Integer insumos;
    
    @Column(name = "parciales")
    private Integer parciales;

    @Column(name = "insumo_1")
    private String insumo1;

    @Column(name = "insumo_2")
    private String insumo2;
    
    @Column(name = "insumo_3")
    private String insumo3;
    
    @Column(name = "insumo_4")
    private String insumo4;
    
    @Column(name = "insumo_5")
    private String insumo5;
    
    @Column(name = "insumo_6")
    private String insumo6;
    
    @Column(name = "insumo_7")
    private String insumo7;
    
    @Column(name = "insumo_8")
    private String insumo8;
   
    @OneToMany(mappedBy = "periodoLectivo", cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @OrderBy("id")
    private List<Grado> grados;

    @OneToMany(mappedBy = "periodoLectivo", cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @OrderBy("id")
    private List<CriterioDeEvaluacion> criterioDeEvaluacion;

    @OneToMany(mappedBy = "periodoLectivo", cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @OrderBy("id")
    private List<GrupoDeEvaluacion> gruposDeEvaluacion;

    @OneToMany(mappedBy = "periodoLectivo", cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @OrderBy("id")
    private List<Area> areas;

    @Transient
    private Secciones secciones;

    public Secciones getSecciones() {
        if (secciones == null) {
            secciones = Secciones.obtenerDesdePeriodoLectivo(this);
        }
        return secciones;

    }

    public void setSecciones(Secciones secciones) {
        this.secciones = secciones;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEtiqueta() {
        return this.etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public Institucion getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    public List<Grado> getGrados() {
        return grados;
    }

    public void setGrados(List<Grado> grados) {
        this.grados = grados;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    public List<CriterioDeEvaluacion> getCriterioDeEvaluacion() {
        return criterioDeEvaluacion;
    }

    public void setCriterioDeEvaluacion(
            List<CriterioDeEvaluacion> criterioDeEvaluacion) {
        this.criterioDeEvaluacion = criterioDeEvaluacion;
    }

    public List<GrupoDeEvaluacion> getGruposDeEvaluacion() {
        return gruposDeEvaluacion;
    }

    public void setGruposDeEvaluacion(List<GrupoDeEvaluacion> gruposDeEvaluacion) {
        this.gruposDeEvaluacion = gruposDeEvaluacion;
    }

    public Integer getCodigoCertificado() {
        return codigoCertificado;
    }

    public void setCodigoCertificado(Integer codigoCertificado) {
        this.codigoCertificado = codigoCertificado;
    }

    public Integer getInsumos() {
        return insumos;
    }

    public void setInsumos(Integer insumos) {
        this.insumos = insumos;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public String getInsumo1() {
        return insumo1;
    }

    public void setInsumo1(String insumo1) {
        this.insumo1 = insumo1;
    }

    public String getInsumo2() {
        return insumo2;
    }

    public void setInsumo2(String insumo2) {
        this.insumo2 = insumo2;
    }

    public String getInsumo3() {
        return insumo3;
    }

    public void setInsumo3(String insumo3) {
        this.insumo3 = insumo3;
    }

    public String getInsumo4() {
        return insumo4;
    }

    public void setInsumo4(String insumo4) {
        this.insumo4 = insumo4;
    }

    public String getInsumo5() {
        return insumo5;
    }

    public void setInsumo5(String insumo5) {
        this.insumo5 = insumo5;
    }

    public String getInsumo6() {
        return insumo6;
    }

    public void setInsumo6(String insumo6) {
        this.insumo6 = insumo6;
    }

    public String getInsumo7() {
        return insumo7;
    }

    public void setInsumo7(String insumo7) {
        this.insumo7 = insumo7;
    }

    public String getInsumo8() {
        return insumo8;
    }

    public void setInsumo8(String insumo8) {
        this.insumo8 = insumo8;
    }

    public Integer getParciales() {
        return parciales;
    }

    public void setParciales(Integer parciales) {
        this.parciales = parciales;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof PeriodoLectivo)) {
            return false;
        }
        PeriodoLectivo other = (PeriodoLectivo) object;
        if ((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestionestudiantil.model.PeriodoLectivo[ id=" + id + " ]";
    }

}
