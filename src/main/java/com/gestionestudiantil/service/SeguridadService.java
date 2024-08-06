package com.gestionestudiantil.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import com.gestionestudiantil.dao.BloqueoUsuarioDao;
import com.gestionestudiantil.dao.DocenteDao;
import com.gestionestudiantil.dao.EstudianteDao;
import com.gestionestudiantil.dao.InstitucionDao;
import com.gestionestudiantil.dao.MatriculaDao;
import com.gestionestudiantil.dao.RolDao;
import com.gestionestudiantil.dao.UsuarioDao;
import com.gestionestudiantil.enums.Roles;
import com.gestionestudiantil.exception.GestionEstudiantilException;
import com.gestionestudiantil.exception.GestionEstudiantilPersistenceException;
import com.gestionestudiantil.model.Autoridad;
import com.gestionestudiantil.model.BloqueoUsuario;
import com.gestionestudiantil.model.Docente;
import com.gestionestudiantil.model.Estudiante;
import com.gestionestudiantil.model.Institucion;
import com.gestionestudiantil.model.Matricula;
import com.gestionestudiantil.model.Rol;
import com.gestionestudiantil.model.RolUsuario;
import com.gestionestudiantil.model.Usuario;

@Stateless
public class SeguridadService {
	@Inject
	private UsuarioDao usuarioDao;
	@Inject
	private RolDao rolDao;
	@Inject
	private EstudianteDao estudianteDao;
	@Inject
	private DocenteDao docenteDao;
	@Inject
	private MatriculaDao matriculaDao;
	@Inject
	private CorreoService correoService;
	@Inject
	private BloqueoUsuarioDao bloqueoUsuarioDao;

	public Usuario obtenerUsuarioPorIdentificacion(String identificacion) throws GestionEstudiantilException {
		try {
			return usuarioDao.obtenerUsuarioPorIdentificacion(identificacion);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Usuario no existe", e);
		}
	}

	public Usuario obtenerFuncionarioPorIdentificacionEInstitucion(String identificacion, Institucion institucion)
			throws GestionEstudiantilException {
		try {
			return usuarioDao.obtenerFuncionarioPorIdentificacionEInstitucion(identificacion, institucion);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Usuario no existe", e);
		}
	}

	public Estudiante obtenerEstudiantePorIdentificacion(String identificacion) throws GestionEstudiantilException {
		try {
			return estudianteDao.obtenerEstudiantePorIdentificacion(identificacion);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Estudiante no existe");
		}
	}

	public Usuario guardarUsuario(Usuario usuario, List<Rol> roles) throws GestionEstudiantilException {
		try {
			List<RolUsuario> rolesUsuario = new ArrayList<RolUsuario>();
			for (Rol r : roles) {
				RolUsuario rolUsuario = new RolUsuario();
				rolUsuario.setRol(r);
				rolUsuario.setUsuario(usuario);
				rolesUsuario.add(rolUsuario);
			}
			usuario.setRolUsuarioList(rolesUsuario);
			usuario.setClave(cifrarCadenaEnSHA1(usuario.getIdentificacion()));

			return (Usuario) this.usuarioDao.guardar(usuario);
		} catch (GestionEstudiantilPersistenceException ex) {
			throw new GestionEstudiantilException("Usuario ya registrado", ex);
		}
	}

	public Usuario registrarUsuario(Usuario usuario, Roles rol) throws GestionEstudiantilPersistenceException {
		try {
			List<RolUsuario> rolesUsuario = new ArrayList<RolUsuario>();
			RolUsuario rolUsuario = new RolUsuario();
			rolUsuario.setRol(rolDao.obtenerPorId(rol.getId()));
			rolUsuario.setUsuario(usuario);
			rolesUsuario.add(rolUsuario);
			usuario.setRolUsuarioList(rolesUsuario);
			usuario.setClave(usuario.getIdentificacion());
			usuario.setClave(cifrarCadenaEnSHA1(usuario.getClave()));

			return (Usuario) usuarioDao.guardar(usuario);
		} catch (PersistenceException pe) {
			throw new GestionEstudiantilPersistenceException(pe, "Usuario ya registrado");
		}
	}

	@SuppressWarnings("deprecation")
	@Asynchronous
	public void recuperarCredenciales(String identificacion) throws GestionEstudiantilException {
		Usuario usuario;
		try {
			usuario = usuarioDao.obtenerUsuarioPorIdentificacion(identificacion);
			String clave = cifrarCadenaEnSHA1("seed" + new Date().getSeconds()).substring(4, 16);
			usuario.setClave(cifrarCadenaEnSHA1(clave));
			correoService.enviarCorreo(usuario.getCorreo(), "Recuperación de contraseña",
					"Estimado " + usuario.getNombre() + ",\nSu contraseña temporal es : " + clave
							+ "\n recuerde cambiar la contraseña lo más pronto posible.");
			usuarioDao.editar(usuario);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Usuario no existe", e);
		}

	}

	public String cifrarCadenaEnSHA1(String input) {
		try {
			MessageDigest mDigest = MessageDigest.getInstance("SHA1");

			byte[] result = mDigest.digest(input.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xFF) + 256, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		return input;
	}

	public List<Rol> obtenerTodosRoles() {
		return this.rolDao.obtenerTodos();
	}

	public void guardarRol(Rol rol) throws GestionEstudiantilException {
		try {
			this.rolDao.guardar(rol);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se puede crear rol", e);
		}
	}

	public List<Usuario> obtenerTodosUsuarios() {
		return this.usuarioDao.obtenerTodos();
	}

	public void cambiarClave(Long id, String clave, String claveAnterior) throws GestionEstudiantilException {
		Usuario usuario = usuarioDao.obtenerPorId(id);
		if (cifrarCadenaEnSHA1(claveAnterior).equals(usuario.getClave())) {
			usuario.setClave(cifrarCadenaEnSHA1(clave));
			try {
				usuarioDao.editar(usuario);
			} catch (GestionEstudiantilPersistenceException e) {
				throw new GestionEstudiantilException("Error de base de datos", e);
			}
		} else {
			throw new GestionEstudiantilException("Clave incorrecta");
		}
	}

	public Docente obtenerDocentePorIdentificacion(String identificacion) throws GestionEstudiantilException {
		try {
			return docenteDao.obtenerDocentePorIdentificacion(identificacion);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No existe docente para la identificación proporcionada");
		}
	}

	public Matricula obtenerMatriculaPorEstudiante(Estudiante estudiante, String periodo) {
		return matriculaDao.obtenerMatriculaActiva(estudiante, periodo);
	}

	public void editarUsuario(Usuario usuario) throws GestionEstudiantilException {

		try {
			usuarioDao.editar(usuario);

			usuario = usuarioDao.obtenerPorId(usuario.getId());
			usuario.setClave(cifrarCadenaEnSHA1(usuario.getIdentificacion()));
			usuarioDao.editar(usuario);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Clave incorrecta");
		}

	}

	public void reiniciarClave(Usuario usuario) throws GestionEstudiantilException {

		try {
			usuario = usuarioDao.obtenerPorId(usuario.getId());
			usuario.setClave(cifrarCadenaEnSHA1(usuario.getIdentificacion()));
			usuarioDao.editar(usuario);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Clave incorrecta");
		}

	}

	public void editarUsuarioNoCambiarClave(Autoridad autoridad) throws GestionEstudiantilException {

		try {
			Usuario usuario = autoridad.getUsuario();
			usuarioDao.editar(usuario);

		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("Clave incorrecta");
		}

	}

	public List<Usuario> obtenerUsuariosPorInstitucion(Institucion institucion) {
		return usuarioDao.obtenerUsuariosPorInstitucion(institucion);
	}

	public Rol obtenerRol(Long id) {
		return rolDao.obtenerPorId(id);
	}

	@Inject
	private InstitucionDao institucionDao;

	public void desvincularAutoridad(Autoridad autoridad) throws GestionEstudiantilException {
		try {
			Institucion institucion = institucionDao.obtenerPorId(autoridad.getUsuario().getInstitucion().getId());
			if (autoridad.getRol().equals(Roles.INSPECTOR))
				institucion.setInspector(null);

			if (autoridad.getRol().equals(Roles.RECTOR))
				institucion.setRector(null);

			if (autoridad.getRol().equals(Roles.SECRETARIA_PRIMARIA))
				institucion.setSecretariaPrimaria(null);

			if (autoridad.getRol().equals(Roles.SECRETARIA_SECUNDARIA))
				institucion.setSecretariaSecundaria(null);

			if (autoridad.getRol().equals(Roles.VICERECTOR))
				institucion.setVicerrector(null);

			if (autoridad.getRol().equals(Roles.RECTOR))
				institucion.setRector(null);
			institucionDao.editar(institucion);

		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se puede eliminar el usuario");
		}

	}

	public void agregarRol(Usuario usuario, Roles rol) throws GestionEstudiantilException {
		try {
			List<RolUsuario> rolesUsuario;
			if (usuario.getRolUsuarioList() == null) {
				rolesUsuario = new ArrayList<RolUsuario>();
			} else {
				rolesUsuario = usuario.getRolUsuarioList();
			}
			RolUsuario rolUsuario = new RolUsuario();
			rolUsuario.setRol(rolDao.obtenerPorId(rol.getId()));
			rolUsuario.setUsuario(usuario);
			rolesUsuario.add(rolUsuario);
			usuario.setRolUsuarioList(rolesUsuario);

			usuarioDao.editar(usuario);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se pudo agrear rol: " + e.getMessage());
		}

	}

	public Usuario obtenerUsuarioPorId(Long id) {
		return usuarioDao.obtenerPorId(id);
	}

	public List<BloqueoUsuario> obtenerBloqueosUsuarioPorInstitucion(Institucion institucion) {
		return bloqueoUsuarioDao.obtenerBloqueosUsuarioPorInstitucion(institucion);
	}

	public BloqueoUsuario bloquearUsuario(Usuario usuario) throws GestionEstudiantilException {
		try {
			bloqueoUsuarioDao.validarBloqueoUsuarioPorIdentificacion(usuario.getIdentificacion());
			BloqueoUsuario bloqueoUsuario = new BloqueoUsuario();
			usuario = usuarioDao.obtenerPorId(usuario.getId());
			bloqueoUsuario.setUsuario(usuario);
			return bloqueoUsuarioDao.guardar(bloqueoUsuario);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se ha podido bloquear al estudiante " + e.getMessage());
		}

	}

	public void desbloquearUsuario(BloqueoUsuario bloqueo) throws GestionEstudiantilException {
		try {
			bloqueoUsuarioDao.eliminar(bloqueo.getId());
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException("No se ha podido bloquear al estudiante");
		}

	}

	public void validarBloqueosUsuarioPorIdentificacion(String identificacion) throws GestionEstudiantilException {
		try {
			bloqueoUsuarioDao.validarBloqueoUsuarioPorIdentificacion(identificacion);
		} catch (GestionEstudiantilPersistenceException e) {
			throw new GestionEstudiantilException(e.getMessage());
		}
	}
}