package de.randi2.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;

public abstract class AbstractDaoHibernate<E extends Object> implements AbstractDao<E>{
	@Autowired
	protected HibernateTemplate template;

	public abstract Class<E> getModelClass();

	public E get(long id){
		E element = (E) template.get(getModelClass(), id);
		if(element == null){
			throw new ObjectRetrievalFailureException(getModelClass(), id);
		}
		return element;
	}
	
	public void save(E object){
		template.saveOrUpdate(object);
	}
}
