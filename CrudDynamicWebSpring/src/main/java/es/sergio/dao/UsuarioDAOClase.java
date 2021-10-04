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
public class UsuarioDAOClase implements UsuarioDAO {

	@Autowired
	SessionFactory sf;

	@Override
	@Transactional
	public void insertarUsuario(Usuario usuario) {
		System.out.println("SYSO DE INSERTAR USUARIO");
		Session s = sf.getCurrentSession();

		s.saveOrUpdate(usuario);
	}

	@Override
	@Transactional
	public void insertarCliente(Cliente cliente) {
		Session s = sf.getCurrentSession();

		s.saveOrUpdate(cliente);

	}

	@Override
	@Transactional
	public void insertarTienda(Tienda tienda) {
		// TODO Auto-generated method stub
		Session s = sf.getCurrentSession();

		s.saveOrUpdate(tienda);
	}

}
