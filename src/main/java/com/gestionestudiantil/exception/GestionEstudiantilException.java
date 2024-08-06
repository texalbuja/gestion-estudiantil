package com.gestionestudiantil.exception;

public class GestionEstudiantilException extends Exception {

	private static final long serialVersionUID = -6407575232700710831L;

	public GestionEstudiantilException(String message) {
		super(message);
	}

	public GestionEstudiantilException(String message, Exception e) {
		super(message, e);
	}

}
