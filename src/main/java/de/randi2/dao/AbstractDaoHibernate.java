package de.randi2.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;

public abstract class AbstractDaoHibernate<E extends AbstractDomainObject> implements AbstractDao<E>{
	@Autowired
	protected HibernateTemplate template;
	
	public abstract Class<E> getModelClass();

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public E get(long id){
		E element = (E) template.get(getModelClass(), id);
		if(element == null){
			throw new ObjectRetrievalFailureException(getModelClass(), id);
		}
		return element;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=RuntimeException.class)
	public void save(E object){
		if (((AbstractDomainObject)object).getId()==AbstractDomainObject.NOT_YET_SAVED_ID){
			template.persist(object);
		}else template.update(object);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<E> findByExample(E object){
		return template.findByExample(object);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<E> getAll(){
		return template.loadAll(getModelClass());
	}
}
