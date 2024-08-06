package com.gestionestudiantil.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.gestionestudiantil.model.Imagen;
import com.gestionestudiantil.service.ImagenService;

@ManagedBean
@ApplicationScoped
public class ImagenUtil {
	@EJB
	private ImagenService imagenService;

	@Inject
	private FacesContext facesContext;

	public StreamedContent getImage() throws IOException {

		if (facesContext.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		}

		String imageId = (String) facesContext.getExternalContext()
				.getRequestParameterMap().get("id");
		Imagen image = imagenService.obtenerImagenPorId(Long.valueOf(imageId));
		return new DefaultStreamedContent(new ByteArrayInputStream(
				image.getBytes()));
	}
}