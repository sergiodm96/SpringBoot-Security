package es.sergio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import es.sergio.dao.UsuarioDAO;
import es.sergio.entity.Tienda;
import es.sergio.entity.Usuario;

@Controller
public class ControladorPrincipal {

	@Autowired
	@Qualifier("usuarioDAOClase")
	UsuarioDAO usuarioDAO;
	
	@RequestMapping("insertar")
	@Transactional
	public String insertar() {
		Usuario usu=new Usuario("Sergio");
		Tienda tienda=new Tienda("Carrefour");
		
		usuarioDAO.insertarUsuario(usu);
//		System.out.println("SYSOUT DE DESPUÉS DE INSERTAR USUARIO.");
		usuarioDAO.insertarTienda(tienda);

		return "insertado";
	}
	
	@RequestMapping("insertar/insertado2")
	public String insertado2() {
		
		return "insertado2";
	}
}
