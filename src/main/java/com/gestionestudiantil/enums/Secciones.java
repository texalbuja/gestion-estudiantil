package com.gestionestudiantil.enums;

import com.gestionestudiantil.model.PeriodoLectivo;

public enum Secciones {
	
	MATUTINA("M", "Matutina"), 
	VESPERTINA("V", "Vespertina"), 
	NOCTURNA("N", "Nocturna");
	
	private String codigo;
	
	private String descripcion;

	private Secciones(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public static Secciones obtenerDesdePeriodoLectivo(
			PeriodoLectivo periodoLectivo) {
		for (Secciones s : Secciones.values()) {
			if (s.codigo.equals(periodoLectivo.getSeccion()))
				return s;
		}
		return null;
	}
}
