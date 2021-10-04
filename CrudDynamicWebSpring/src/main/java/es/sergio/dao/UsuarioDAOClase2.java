package es.sergio.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.sergio.entity.Cliente;
import es.sergio.entity.Tienda;
import es.sergio.entity.Usuario;

@Repository
public class UsuarioDAOClase2 implements UsuarioDAO {

	@Autowired
	SessionFactory sf;
	
	@Override
	public void insertarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		System.out.println("SYSO DE INSERTAR USUARIO");

	}

	@Override
	public void insertarCliente(Cliente cliente) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public void insertarTienda(Tienda tienda) {
		// TODO Auto-generated method stub
		Session s = sf.getCurrentSession();

//		s.saveOrUpdate(tienda);
	}
}
