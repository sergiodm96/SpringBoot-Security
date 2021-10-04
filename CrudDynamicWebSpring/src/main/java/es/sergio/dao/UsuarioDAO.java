package es.sergio.dao;

import es.sergio.entity.Cliente;
import es.sergio.entity.Tienda;
import es.sergio.entity.Usuario;

public interface UsuarioDAO {

	
	public void insertarUsuario(Usuario usuario);
	
	public void insertarCliente(Cliente cliente);
	
	public void insertarTienda(Tienda tienda);

}
