package com.gestionestudiantil.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;

import com.gestionestudiantil.dao.EstudianteDao;
import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.exception.GestionEstudiantilPersistenceException;
import com.gestionestudiantil.model.Estudiante;
import com.gestionestudiantil.model.Imagen;

@Stateless
public class CarnetizacionService {
	@Inject
	private EstudianteDao estudianteDao;

	public void cargarFotos() {

	}

	public Estudiante guardarCodigoEstudiante(Estudiante estudiante) throws GestionEstudiantilException {
		try {
			return estudianteDao.editar(estudiante);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("El estudiante ya ha sido registrado", e);
		}

	}

	public void cargarFotos(List<Estudiante> estudiantes) throws GestionEstudiantilException {
		try {
			for (Estudiante estudiante : estudiantes) {
				Imagen imagen = new Imagen();
				String so = System.getProperty("os.name");
				String ruta;
				if (so.equals("Windows 8.1"))
					ruta = "D:/opt/fotos/IMG_" + estudiante.getCodigoFoto()
							+ ".JPG";
				else
					ruta = "/opt/fotos/IMG_" + estudiante.getCodigoFoto() + ".JPG";

				FileInputStream fis;
				try {
					File fotografia = new File(ruta);
					fis = new FileInputStream(fotografia);
					imagen.setBytes(IOUtils.toByteArray(fis));
					estudiante.setImagen(imagen);
					estudianteDao.editar(estudiante);
					fotografia.delete();
					fis.close();
				} catch (IOException e) {
				}

			}
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Error de persistencia", e);
		}
	}

}