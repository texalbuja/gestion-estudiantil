package com.gestionestudiantil.exception;

public class GestionEstudiantilPersistenceException extends Exception {
	
	private static final long serialVersionUID = -7023426391496270812L;

	public GestionEstudiantilPersistenceException(Throwable exception, String message) {
		super(message, exception);
	}
	public GestionEstudiantilPersistenceException(String message) {
		super(message);
	}
}