package com.gestionestudiantil.enums;

public enum Roles {
	
	ADMINISTRADOR("Administrador del sistema",1L),
	SECRETARIA_PRIMARIA("Secretaria de Primaria",2L),
	ESTUDIANTE("Estudiante",3L),
	DOCENTE("Docente",4L),
	INSPECTOR("Inspector",5L),
	RECTOR("Rector",6L),
	SECRETARIA_SECUNDARIA("Secretaria de Secundaria",7L),
	DIRECTOR("Director",8L),
	VICERECTOR("Vicerrector",9L);
	
	private String descripcion;
	
	private Long id;

	private Roles(String descripcion, Long id) {
		this.descripcion = descripcion;
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public static Roles[] obtenerRolesFuncionarios(){
		return new Roles[]{DIRECTOR, SECRETARIA_PRIMARIA, RECTOR, SECRETARIA_SECUNDARIA, VICERECTOR, INSPECTOR};
	}

}