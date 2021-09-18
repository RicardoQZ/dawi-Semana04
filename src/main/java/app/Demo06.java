package app;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Usuario;

public class Demo06 {
	public static void main(String[] args) {
		//1. Especificar la conexion de BD - DAOFactory
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		// 2 . Obtener el DAO
		EntityManager em=fabrica.createEntityManager();
		
		//listado de usuarios
		TypedQuery<Usuario>query = em.createQuery("Select u from Usuario u",Usuario.class);
		
		List<Usuario> lstUsuarios = query.getResultList();
		
		System.out.println("Cantidad de Usuarios : " +lstUsuarios.size());
		
		if(lstUsuarios.size()==0) {
			System.out.println("No hay Usuarios");
		}else {
			for(Usuario u : lstUsuarios) {
				System.out.println(">>> " + u.getNombre());
			}
		}
		
		//Listado con parametros -> listado de los usuarios x tipo
		String sql="Select u from Usuario u where u.tipo= :xtipo";
		TypedQuery<Usuario> query2= em.createQuery(sql, Usuario.class);
		query2.setParameter("xtipo",1);
		
		List<Usuario> lstUsuarios2 =  query2.getResultList();
		
		System.out.println("Cantidad de Usuarios : " +lstUsuarios2.size());
		
		if(lstUsuarios2.size()==0) {
			System.out.println("No hay Usuarios");
		}else {
			System.out.println("Listado x Tipo");
			for(Usuario u : lstUsuarios2) {
				System.out.println(">>> " + u.getNombre());
			}
		}
		em.close();
	}	
}
