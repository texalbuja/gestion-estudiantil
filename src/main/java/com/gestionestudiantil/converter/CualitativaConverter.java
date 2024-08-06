package com.gestionestudiantil.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.gestionestudiantil.enums.EscalaCualitativa;

@FacesConverter(value = "cualitativaConverter")
public class CualitativaConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return arg2;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return EscalaCualitativa.desdeCualitativa(Double.valueOf(arg2.toString())).getSiglas();
	}

}
