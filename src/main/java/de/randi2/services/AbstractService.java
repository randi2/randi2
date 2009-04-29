package de.randi2.services;

import java.util.List;

import de.randi2.model.AbstractDomainObject;

/**
 * 
 * @author dschrimpf
 *
 * @param <V>
 */
public interface AbstractService<V extends AbstractDomainObject> {

	public List<V> getAll();
	
	public V getObject(long objectID);
}
