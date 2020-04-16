package com.intiformation.gestiontransports.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.intiformation.gestiontransports.entity.Utilisateur;
import com.intiformation.gestiontransports.tool.JPAUtil;

public class UtilisateurDAOImpl extends GenericDAOImpl<Utilisateur> implements IUtilisateurDAO{

	//props
	protected EntityManager entityManager = JPAUtil.getEntityManager();	
	
	//ctor
	public UtilisateurDAOImpl() {
		super(Utilisateur.class);
	}

	
	
	//méthode
	public boolean isUtilisateurExists(String pEmail, String pMdp) {	//vérif si un utilisateur existe avec mail et mdp
		
		try {
			
			//requete 
			Query query = this.entityManager.createNamedQuery("Utilisateur.isExists");
			query.setParameter(1, pEmail);
			query.setParameter(2, pMdp);
			
			// exec + récup du résultat de la requete 
			Long isExistsVerif =  (Long) query.getSingleResult();
			
			return (isExistsVerif ==1);
			
		} catch (PersistenceException e) {
			System.out.println("... Erreur lors de la vérif de l'existance de l'utilisateur ....");
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Long getIdByEmail(String pEmail) {
		
		try {
			
			Utilisateur u = entityManager.find(Utilisateur.class, pEmail);
			long id = u.getIdUtilisateur();
			
			return id;
			
		} catch (PersistenceException e) {
			System.out.println("... Erreur lors de la récup de l'id de l'utilisateur by son email ....");
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
