package com.gestionestudiantil.enums;

public enum EscalaCualitativa {
	DOMINA("Domina", "Domina los aprendizajes requeridos", "9-10", "DAR"), 
	ALCANZA("Alcanza", "Alcanza los aprendizajes requeridos", "7-8", "AAR"), 
	PROXIMO("Proximo", "Está próximo a alcanzar los aprendizajes requeridos","5-6", "PAR"), 
	NO_ALCANZA("No alcanza","No alcanza los aprendizajes requeridos.", "<=4", "NAR");
	
	String etiqueta;
	
	String cualitativa;
	
	String cuantitativa;
	
	String siglas;


	private EscalaCualitativa(String etiqueta, String cualitativa,
			String cuantitativa, String siglas) {
		this.etiqueta = etiqueta;
		this.cualitativa = cualitativa;
		this.cuantitativa = cuantitativa;
		this.siglas = siglas;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getCualitativa() {
		return cualitativa;
	}

	public void setCualitativa(String cualitativa) {
		this.cualitativa = cualitativa;
	}

	public String getCuantitativa() {
		return cuantitativa;
	}

	public String getSiglas() {
		return siglas;
	}

	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}

	public void setCuantitativa(String cuantitativa) {
		this.cuantitativa = cuantitativa;
	}

	public static EscalaCualitativa desdeCualitativa(Double calificacion)
			throws IllegalStateException {
		if (calificacion.doubleValue() <= 4.00D) {
			return NO_ALCANZA;
		}
		if (calificacion.doubleValue() <= 6.99D) {
			return PROXIMO;
		}
		if (calificacion.doubleValue() <= 8.99D) {
			return ALCANZA;
		}
		if (calificacion.doubleValue() <= 10.00D) {
			return DOMINA;
		}
		throw new IllegalStateException();
	}
}