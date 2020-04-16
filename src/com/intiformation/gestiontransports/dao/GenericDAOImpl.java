package com.intiformation.gestiontransports.dao;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.intiformation.gestiontransports.entity.Marchandise;
import com.intiformation.gestiontransports.tool.JPAUtil;

public class GenericDAOImpl<T> implements IGenericDAO<T>{

	//props
	protected Class<T> entityClass;

	protected EntityManager entityManager = JPAUtil.getEntityManager();
	
	
	
	
	//ctor
	public GenericDAOImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	
	
	
	
	//méthodes
	public boolean ajouter(T entity) {

		EntityTransaction transaction = null;

		try {

			transaction = entityManager.getTransaction();
			transaction.begin();
			entityManager.persist(entity);
			transaction.commit();
			
			return true;

		} catch (PersistenceException ex) {

			if (transaction != null) {
				transaction.rollback();
				ex.printStackTrace();
			}

		}
		return false;
		
	}


	public boolean modifier(T entity) {

		EntityTransaction transaction = null;

		try {

			transaction = entityManager.getTransaction();
			transaction.begin();
			entityManager.merge(entity);
			transaction.commit();

			return true;
			
		} catch (PersistenceException ex) {

			if (transaction != null) {
				transaction.rollback();
				ex.printStackTrace();
			}

		}
		return false;
		
	}

	public boolean supprimer(Long id) {

		EntityTransaction transaction = null;

		try {

			transaction = entityManager.getTransaction();
			transaction.begin();
			entityManager.remove(getById(id));
			transaction.commit();

			return true;
			
		} catch (PersistenceException ex) {

			if (transaction != null) {
				transaction.rollback();
				ex.printStackTrace();
			}
		}
		return false;
	}

	public T getById(Long id) {
		return entityManager.find(entityClass, id);
	}

	public List<T> getAll() {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);

		Root<T> clauseFROM = criteriaQuery.from(entityClass);

		CriteriaQuery<T> clauseSELECT = criteriaQuery.select(clauseFROM);

		TypedQuery<T> getAllQuery = entityManager.createQuery(clauseSELECT);

		return getAllQuery.getResultList();
	}
	
	
	
	public List<T> getAllByFKc(Long valeur) {
		
		//1. récup de l'entity manager
		EntityManager em = Persistence.createEntityManagerFactory("pu_gestion_transports").createEntityManager();
		
		//2. récup d'une transaction à partir de l'EM
		EntityTransaction transaction = em.getTransaction();
		
		//2.1 ouverture de la transaction
		transaction.begin();
		
		//3. contenu de la requete JPQL pour la modif
		String requeteUpdate = "SELECT e FROM cargaison e "
							+ " WHERE e.utilisateur.idUtilisateur = :pEns";
		
		//4. création de la requete
		Query selectQuery = em.createQuery(requeteUpdate);
		
		//4.1 passage de params
		selectQuery.setParameter("pEns", valeur);
		
		//1.4 exécution et récup du résultat de la requête
		List<T> liste_marchandises = selectQuery.getResultList();
	
		
		
		return liste_marchandises;
	}
	
	
	public List<T> getAllByFKm(Long valeur) {
		
		//1. récup de l'entity manager
		EntityManager em = Persistence.createEntityManagerFactory("pu_gestion_transports").createEntityManager();
		
		//2. récup d'une transaction à partir de l'EM
		EntityTransaction transaction = em.getTransaction();
		
		//2.1 ouverture de la transaction
		transaction.begin();
		
		//3. contenu de la requete JPQL pour la modif
		String requeteUpdate = "SELECT e FROM marchandise e "
							+ " WHERE e.cargaison.reference = :pEns";
		
		//4. création de la requete
		Query selectQuery = em.createQuery(requeteUpdate);
		
		//4.1 passage de params
		selectQuery.setParameter("pEns", valeur);
		
		//1.4 exécution et récup du résultat de la requête
		List<T> liste_marchandises = selectQuery.getResultList();
	
		
		
		return liste_marchandises;
	}	
	
	
	

	



	public Long count() {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		cq.select(cb.count(cq.from(entityClass)));

		Query countQuery = entityManager.createQuery(cq);

		return (Long) countQuery.getSingleResult();
	
	}

}
