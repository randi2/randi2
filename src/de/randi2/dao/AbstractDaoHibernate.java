package de.randi2.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;

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
	
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=RuntimeException.class)
	public void save(E object){
		if (((AbstractDomainObject)object).getId()==AbstractDomainObject.NOT_YET_SAVED_ID){
			template.saveOrUpdate(object);
		}else template.merge(object);
		template.flush();
	}
	
	public List<E> findByExample(E object){
		return template.findByExample(object);
	}
	
	public List<E> getAll(){
		return template.loadAll(getModelClass());
	}
}
