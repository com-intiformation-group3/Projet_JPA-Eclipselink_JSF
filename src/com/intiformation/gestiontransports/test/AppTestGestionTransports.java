package com.intiformation.gestiontransports.test;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.intiformation.gestiontransports.entity.Cargaison;
import com.intiformation.gestiontransports.entity.CargaisonAerienne;
import com.intiformation.gestiontransports.entity.CargaisonRoutiere;
import com.intiformation.gestiontransports.entity.Marchandise;
import com.intiformation.gestiontransports.entity.Utilisateur;
import com.intiformation.gestiontransports.tool.JPAUtil;

public class AppTestGestionTransports {

	public static void main(String[] args) {
		
		//1. Définition des utilisateurs
		Utilisateur utilisateur1 = new Utilisateur("J.Ducrocq", "J.Ducrocq@gmail.com", "123");
		Utilisateur utilisateur2 = new Utilisateur("A.Duchesse", "A.Duchesse@gmail.com", "456");
		Utilisateur utilisateur3 = new Utilisateur("A.Caron", "A.Caron@gmail.com", "123");
		
		
		//2. Définition des cargaisons
		Cargaison cargaison1 = new CargaisonRoutiere(1L, 4.0, "22/06/2020", 11.0);
		Cargaison cargaison2 = new CargaisonRoutiere(2L, 13.0, "05/05/2006", 11.0);
		
		Cargaison cargaison3 = new CargaisonAerienne(3L, 8892.0, "22/06/2020", 0.0);
		
		
		
		//3. Definittion des  marchandises
		Marchandise vin_rouge = new Marchandise("vin rouge", 1.0, 0.1);
		Marchandise vin_blanc = new Marchandise("vin blanc", 1.0, 0.1);
		Marchandise vin_rose = new Marchandise("vin rosé", 1.0, 0.1);
		
		Marchandise champagne_brut = new Marchandise("champagne brut", 1.0, 0.1);
		Marchandise champagne_millesime = new Marchandise("champagne millésimé", 1.0, 0.1);
		Marchandise champagne_prestige = new Marchandise("champagne prestige", 1.0, 0.1);
		
		Marchandise iphone_11 = new Marchandise("iphone 11", 0.194, 0.09);
		Marchandise iphone_x = new Marchandise("iphone X", 0.174, 0.084);
		Marchandise iphone_xr = new Marchandise("iphone xr", 0.194, 0.09);
		
		
		
		//4. association des marchandises aux cargaisons
		vin_rouge.setCargaison(cargaison1);
		vin_blanc.setCargaison(cargaison1);
		champagne_brut.setCargaison(cargaison1);
		
		champagne_millesime.setCargaison(cargaison2);
		champagne_prestige.setCargaison(cargaison2);
		
		iphone_11.setCargaison(cargaison3);
		
		
		
		//5. association des cargaisons aux utilisateurs
		cargaison1.setUtilisateur(utilisateur1);
		
		cargaison2.setUtilisateur(utilisateur2);
		
		cargaison3.setUtilisateur(utilisateur3);
		
		
		
		//6. Recup dde l'EM
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		//7. Recup d'une transction de EM + ouverture
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		//8 Ajout des Utilisateurs + Marchandises + Cargaisons dans la BDD
		entityManager.persist(utilisateur1);entityManager.persist(utilisateur2);entityManager.persist(utilisateur3);
		
		entityManager.persist(vin_rouge);entityManager.persist(vin_blanc);entityManager.persist(vin_rose);
		entityManager.persist(champagne_brut);entityManager.persist(champagne_millesime);entityManager.persist(champagne_prestige);
		entityManager.persist(iphone_11);entityManager.persist(iphone_x);entityManager.persist(iphone_xr);
		
		entityManager.persist(cargaison1);entityManager.persist(cargaison2);entityManager.persist(cargaison3);
		
		
		//9.Validation de la transaction
		transaction.commit();
		
		//10. Fermeture
		entityManager.close();		

	}

}
