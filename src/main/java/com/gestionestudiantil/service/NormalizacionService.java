package com.gestionestudiantil.service;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class NormalizacionService {
	
	@PostConstruct
	public void init()  {
	}
}