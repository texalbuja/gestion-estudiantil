package com.gestionestudiantil.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.service.MatriculacionService;

@FacesConverter("matriculaConverter")
public class MatriculaConverter implements Converter {

	@Inject
	private MatriculacionService matriculacionService;

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return matriculacionService.obtenerMatriculaPorId(Long
						.parseLong(value));
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "No es una matrícula válida",
						"No es una matrícula válida"));
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((Matricula) object).getId());
		} else {
			return "";
		}
	}
}