package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Usuario;

public class Demo08 {
	public static void main(String[] args) {
		//1. Especificar la conexion de BD - DAOFactory
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		// 2 . Obtener el DAO
		EntityManager em=fabrica.createEntityManager();
		
		//Listado con parametros -> listado de los usuarios x tipo
		String sql="Select u from Usuario u where u.usuario= :xuser and u.clave= :xclave";
		TypedQuery<Usuario> query2= em.createQuery(sql, Usuario.class);
		query2.setParameter("xuser","U002@gmail.com");
		query2.setParameter("xclave","10002");
		
		Usuario u=null;
		try {
			u = query2.getSingleResult();
			System.out.println("Bienvenido " + u.getNombre());
		} catch (NoResultException e) {
			System.out.println("Usuario no encontrado");
		}
		em.close();
	}	
}
