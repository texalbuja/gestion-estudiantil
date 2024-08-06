package com.gestionestudiantil.enums;

import com.gestionestudiantil.model.Grado;

public enum Grados {

	INI_1(1, 1, 1,"Inicial", "Inicial I", "Inicial I", "Inicial I"), 
	INI_2(1, 1, 2,"Inicial", "Inicial II", "Inicial II", "Inicial II"),
	EGB_1(2, 1, 3,"Básica","Preparatoria", "Primero de Básica", "Primer Grado de Educación Básica"),
	EGB_2(2, 2, 4,"Básica","Básica Elemental", "Segundo de Básica", "Segundo Grado de Educación Básica"),
	EGB_3(2, 2, 5,"Básica","Básica Elemental", "Tercero de Básica", "Tercer Grado de Educación Básica"),
	EGB_4(2, 2, 6,"Básica","Básica Elemental", "Cuarto de Básica", "Cuarto Grado de Educación Básica"),
	EGB_5(2, 3, 7,"Básica", "Básica Media","Quinto de Básica", "Quinto Grado de Educación Básica"),
	EGB_6(2, 3, 8,"Básica","Básica Media", "Sexto de Básica", "Sexto Grado de Educación Básica"), 
	EGB_7(2, 3, 9,"Básica","Básica Media", "Séptimo de Básica", "Séptimo Grado de Educación Básica"),
	EGB_8(2, 4, 10,"Básica","Básica Superior", "Octavo de Básica", "Octavo Grado de Educación Básica"),
	EGB_9(2, 4, 11,"Básica","Básica Superior",  "Noveno de Básica", "Noveno Grado de Educación Básica"), 
	EGB_10(2, 4, 12,"Básica","Básica Superior",  "Décimo de Básica", "Décimo Grado de Educación Básica"),
	BAC_1(3, 5, 13,"Bachillerato","Bachillerato", "Primero de Bachillerato", "Primer Curso de Bachillerato General Unificado"),
	BAC_2(3, 5, 14,"Bachillerato","Bachillerato",	"Segundo de Bachillerato", "Segundo Curso de Bachillerato General Unificado"),
	BAC_3(3, 5, 15,"Bachillerato","Bachillerato",	"Tercero de Bachillerato", "Tercer Curso de Bachillerato General Unificado");

	private Integer nivel;

	private Integer subNivel;

	private Integer grado;

	private String descripcionNivel;
	
	private String descripcionSubNivel;
	
	private String descripcionGrado;
	
	private String descripcionGradoDeEducacionBasica;

	private Grados(Integer nivel, Integer subNivel, Integer grado, String descripcionNivel, String descripcionSubNivel, String descripcionGrado, String descripcionGradoDeEducacionBasica) {
		this.nivel = nivel;
		this.subNivel = subNivel;
		this.grado = grado;
		this.descripcionNivel = descripcionNivel;
		this.descripcionSubNivel = descripcionSubNivel;
		this.descripcionGrado = descripcionGrado;
		this.descripcionGradoDeEducacionBasica = descripcionGradoDeEducacionBasica;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public Integer getSubNivel() {
		return subNivel;
	}

	public void setSubNivel(Integer subNivel) {
		this.subNivel = subNivel;
	}

	public Integer getGrado() {
		return grado;
	}

	public void setGrado(Integer grado) {
		this.grado = grado;
	}
	
	public String getDescripcionNivel() {
		return descripcionNivel;
	}

	public void setDescripcionNivel(String descripcionNivel) {
		this.descripcionNivel = descripcionNivel;
	}

	public String getDescripcionSubNivel() {
		return descripcionSubNivel;
	}

	public void setDescripcionSubNivel(String descripcionSubNivel) {
		this.descripcionSubNivel = descripcionSubNivel;
	}

	public String getDescripcionGrado() {
		return descripcionGrado;
	}

	public void setDescripcionGrado(String descripcionGrado) {
		this.descripcionGrado = descripcionGrado;
	}
	
	public String getDescripcionGradoDeEducacionBasica() {
		return descripcionGradoDeEducacionBasica;
	}

	public void setDescripcionGradoDeEducacionBasica(String descripcionGradoDeEducacionBasica) {
		this.descripcionGradoDeEducacionBasica = descripcionGradoDeEducacionBasica;
	}

	public static Grados obtenerDesdeGrado(Grado grado) {
		for (Grados g : Grados.values()) {
			if (g.nivel.equals(grado.getNivel())
					&& g.subNivel.equals(grado.getSubNivel())
					&& g.grado.equals(grado.getGrado())) {
				return g;
			}
		}
		return null;
	}
}
