package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Usuario;

public class Demo09 {
	public static void main(String[] args) {
		//1. Especificar la conexion de BD - DAOFactory
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		// 2 . Obtener el DAO
		EntityManager em=fabrica.createEntityManager();
		
		//Listado con parametros -> listado de los usuarios x tipo
		//String sql="{call usp_validaAcceso (:xuser, :xclave)}";
		String sql="{call usp_validaAcceso (?,?)}";
		//TypedQuery<Usuario> query2= em.createQuery(sql, Usuario.class);JPA no soporta procedure
		Query query2=em.createNativeQuery(sql,Usuario.class);
		//query2.setParameter("xuser","U002@gmail.com");
		//query2.setParameter("xclave","10002");
		query2.setParameter(1,"U002@gmail.com");
		query2.setParameter(2,"10002");
		Usuario u=null;
		try {
			u = (Usuario)query2.getSingleResult();
			System.out.println("Bienvenido " + u.getNombre());
		} catch (NoResultException e) {
			System.out.println("Usuario no encontrado");
		}
		em.close();
	}	
}
