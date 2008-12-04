package de.randi2.dao;

import java.util.List;

public interface AbstractDao<E extends Object> {

	public E get(long id);
	
	public void save(E object);
	
	public List<E> findByExample(E object);
	
	public List<E> getAll();
}
