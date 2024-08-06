package com.gestionestudiantil.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.gestionestudiantil.dao.CatalogoDao;
import com.gestionestudiantil.model.Catalogo;

@Stateless
public class UtilService {
	@Inject
	private CatalogoDao catalogoDao;

	public List<Catalogo> obtenerCatalogosPorClave(String clave) {
		return this.catalogoDao.obtenerCatalogosPorClave(clave);
	}

	public List<Catalogo> obtenerCatalogosPorClaveYValorPadre(String clave,
			String valorPadre) {
		return this.catalogoDao.obtenerCatalogosPorClaveYValorPadre(clave, valorPadre);
	}
}