package com.gestionestudiantil.filter;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter({ "/evaluacion/*", "/informes/*", "/matriculacion/*", "/resumen/*", "/asistencia/*", "/resumen/*",
		"/seleccion/*", "/reportes/*", "/comportamiento/*" })
public class AuthorizationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;

		Principal userPrincipal = req.getUserPrincipal();
		if (userPrincipal != null) {
			// User is logged in, so just continue request.
			chain.doFilter(request, response);
		} else {
			// User is not logged in, so redirect to index.
			HttpServletResponse res = (HttpServletResponse) response;
			res.sendRedirect(req.getContextPath() + "/iniciarSesion.jsf");
		}
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
}