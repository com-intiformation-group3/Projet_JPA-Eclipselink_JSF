package com.intiformation.gestiontransports.tool;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

/**
 * utilitaire qui permet de récupérer l'entityManager de JPA
 *
 */
public class JPAUtil {

	
	private static EntityManager entityManager;

	
	public static EntityManager getEntityManager() {

		if (entityManager == null) {

			try {

				entityManager = Persistence.createEntityManagerFactory("pu_gestion_transports")
						                   .createEntityManager();

			} catch (PersistenceException e) {
				System.out.println("La récupération de l'entity manager à échouée");
				e.printStackTrace();
			}

		}
		return entityManager;
	}
	
	
	public static EntityManager closeEntityManager() {

		if (entityManager != null) {
			System.out.println("... EntityManager is being closed ...");
			try {

				entityManager.close();

			} catch (PersistenceException e) {
				System.out.println("La récupération de l'entity manager à échouée");
				e.printStackTrace();
			}

		}
		return entityManager;
	}
	
	
	
	
}
