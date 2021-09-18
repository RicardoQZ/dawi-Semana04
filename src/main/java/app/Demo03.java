package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Usuario;

public class Demo03 {
	public static void main(String[] args) {
		//1. Especificar la conexion de BD - DAOFactory
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		// 2 . Obtener el DAO
		EntityManager em=fabrica.createEntityManager();
		//procesos ... eliminar usuario con codigo 12
		Usuario u = new Usuario();
		u.setCodigo(12);
		/*u.setNombre("Eren");
		u.setApellido("Jeager");
		u.setUsuario("tatakae@gmail.com");
		u.setClave("titan");
		u.setFnac("2010/08/27");
		u.setTipo(1);
		u.setEstado(1);*/
		// reg, act, elim -> Transacciones
		try {
			em.getTransaction().begin();
			em.remove(u); //eliminar
			em.getTransaction().commit();
			System.out.println("Eliminacion OK");
		} catch (Exception e) {
			System.out.println("Error : " +e.getClass().getName());
		}
		em.close();
	}
}
