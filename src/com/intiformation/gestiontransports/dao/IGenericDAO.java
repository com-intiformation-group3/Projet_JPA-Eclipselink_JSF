package com.intiformation.gestiontransports.dao;

import java.util.List;

public interface IGenericDAO<T> {

	public void ajouter(T entity);

	public void modifier(T entity);

	public void supprimer(Long id);

	public T getById(Long id);

	public List<T> getAll();
	
	public List<T> getAllByFK(String nomFK, int valeur);
	
	public Long count();
	
}
