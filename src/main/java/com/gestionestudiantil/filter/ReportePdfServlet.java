package com.gestionestudiantil.filter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.gestionestudiantil.model.Institucion;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

@WebServlet(urlPatterns = { "/reportes/pdf/*" })
public class ReportePdfServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Resource(mappedName = "java:jboss/datasources/gestudDS")
	private DataSource ds;

	@SuppressWarnings("unchecked")
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Content-Disposition", "inline; filename=\"reporte.pdf\";");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);

		response.setContentType("application/pdf");

		ServletOutputStream servletOutputStream = response.getOutputStream();
		try (Connection connection = ds.getConnection()) {
			HttpSession session = request.getSession(true);

			HashMap<String, Object> parametros = (HashMap<String, Object>) session.getAttribute("parametros");
			String path = getServletContext().getRealPath(parametros.get("reporte").toString());

			Institucion institucion = (Institucion) session.getAttribute("institucion");
			if (institucion != null) {
				String pathAlterno = getServletContext().getRealPath(
						parametros.get("reporte").toString().replace("reportes", "reportes/" + institucion.getId()));
				File reporteAlterno = new File(pathAlterno);
				if (reporteAlterno.exists() && !reporteAlterno.isDirectory()) {
					path = pathAlterno;
				}
			}

			String pathImagenes = getServletContext().getRealPath("/resources/imagenes");
			parametros.put("IMAGE_DIR", pathImagenes);
			String pathSubReporte = getServletContext().getRealPath("/reportes");
			parametros.put("SUBREPORT_DIR", pathSubReporte);
			Locale locale = new Locale("es", "EC");
			parametros.put(JRParameter.REPORT_LOCALE, locale);
			JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
			byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, connection);
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (JRException | SQLException e) {
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
			response.getOutputStream().print(stringWriter.toString());
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}
}