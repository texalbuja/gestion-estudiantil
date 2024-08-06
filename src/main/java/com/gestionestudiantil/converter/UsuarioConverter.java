package com.gestionestudiantil.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.gestionestudiantil.model.Usuario;
import com.gestionestudiantil.service.SeguridadService;

@FacesConverter("usuarioConverter")
public class UsuarioConverter implements Converter {

	@Inject
	private SeguridadService matriculacionService;

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return matriculacionService.obtenerUsuarioPorId(Long
						.parseLong(value));
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "No es una usuario válida",
						"No es un usuario válida"));
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((Usuario) object).getId());
		} else {
			return "";
		}
	}
}