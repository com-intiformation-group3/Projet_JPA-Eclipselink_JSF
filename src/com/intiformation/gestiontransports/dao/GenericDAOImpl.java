package com.intiformation.gestiontransports.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
	public void ajouter(T entity) {

		EntityTransaction transaction = null;

		try {

			transaction = entityManager.getTransaction();
			transaction.begin();
			entityManager.persist(entity);
			transaction.commit();

		} catch (PersistenceException ex) {

			if (transaction != null) {
				transaction.rollback();
				JPAUtil.closeEntityManager();
				ex.printStackTrace();
			}

		}
		
	}


	public void modifier(T entity) {

		EntityTransaction transaction = null;

		try {

			transaction = entityManager.getTransaction();
			transaction.begin();
			entityManager.merge(entity);
			transaction.commit();

		} catch (PersistenceException ex) {

			if (transaction != null) {
				transaction.rollback();
				ex.printStackTrace();
			}

		}
		
	}

	public void supprimer(Long id) {

		EntityTransaction transaction = null;

		try {

			transaction = entityManager.getTransaction();
			transaction.begin();
			entityManager.remove(getById(id));
			transaction.commit();

		} catch (PersistenceException ex) {

			if (transaction != null) {
				transaction.rollback();
				ex.printStackTrace();
			}
		}
		
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

	
	
	public List<T> getAllByFK(String nomFK, int valeur) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		
		Root<T> clauseFROM = criteriaQuery.from(entityClass);
		
		
		Predicate conditionClauseWhere = criteriaBuilder.equal(clauseFROM.get(nomFK), valeur);
		
		CriteriaQuery<T> clauseWHERE = criteriaQuery.select(clauseFROM).where(conditionClauseWhere);
		
		TypedQuery<T> getAllQueryByFK = entityManager.createQuery(clauseWHERE);
		
		
		return getAllQueryByFK.getResultList();
	}

	public Long count() {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		cq.select(cb.count(cq.from(entityClass)));

		Query countQuery = entityManager.createQuery(cq);

		return (Long) countQuery.getSingleResult();
	
	}

}
