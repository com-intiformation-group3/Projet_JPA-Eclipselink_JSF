package com.intiformation.gestiontransports.dao;

import java.util.List;

public interface IGenericDAO<T> {

	public boolean ajouter(T entity);

	public boolean modifier(T entity);

	public boolean supprimer(Long id);

	public T getById(Long id);

	public List<T> getAll();
	
	public List<T> getAllByFKc(Long valeur);
	
	public List<T> getAllByFKm(Long valeur);
	
	public Long count();
	
}
