package com.gestionestudiantil.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.gestionestudiantil.dao.ImagenDao;
import com.gestionestudiantil.model.Imagen;

@Stateless
public class ImagenService {

	@Inject
	private ImagenDao imagenDao;

	public Imagen obtenerImagenPorId(Long id) {
		return imagenDao.obtenerPorId(id);
	}
}