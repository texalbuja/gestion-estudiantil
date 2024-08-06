package com.gestionestudiantil.dao;

import java.util.List;

import javax.persistence.Query;

import com.gestionestudiantil.model.Catalogo;

public class CatalogoDao extends AbstractDao<Catalogo, Long> {
	public CatalogoDao() {
		super(Catalogo.class);
	}

	public List<Catalogo> obtenerCatalogosPorClave(String clave) {
		Query qry = this.em.createQuery(
				"SELECT c FROM Catalogo c WHERE c.clave= :clave", Catalogo.class);
		qry.setParameter("clave", clave);
		return qry.getResultList();
	}

	public String obtenerEtiquetaPorClaveValor(String clave, Integer valor) {
		Query qry = this.em
				.createQuery("SELECT c.etiqueta FROM Catalogo c WHERE c.valor= :valor and c.clave = :clave");
		qry.setParameter("valor", valor);
		qry.setParameter("clave", clave);
		return (String) qry.getSingleResult();
	}

	public List<Catalogo> obtenerCatalogosPorClaveYValorPadre(String clave,
			String valorPadre) {
		Query qry = this.em.createQuery(
				"SELECT c FROM Catalogo c WHERE c.clave= :clave AND c.valorPadre = :valorPadre", Catalogo.class);
		qry.setParameter("clave", clave);
		qry.setParameter("valorPadre", valorPadre);
		return qry.getResultList();
	}
}