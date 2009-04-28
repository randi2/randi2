package de.randi2.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;

public abstract class AbstractDaoHibernate<E extends AbstractDomainObject> implements AbstractDao<E>{
	
	@Autowired protected SessionFactory sessionFactory;
	
	public abstract Class<E> getModelClass();

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public E get(long id){
		E element = (E) sessionFactory.getCurrentSession().get(getModelClass(), id);
		if(element == null){
			throw new ObjectRetrievalFailureException(getModelClass(), id);
		}
		return element;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(E object){
		if (((AbstractDomainObject)object).getId()==AbstractDomainObject.NOT_YET_SAVED_ID){
			sessionFactory.getCurrentSession().persist(object);
		}else sessionFactory.getCurrentSession().update(object);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<E> findByExample(E object){
		return sessionFactory.getCurrentSession().createCriteria(getClass()).add(Example.create(object).ignoreCase()).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<E> getAll(){
		return sessionFactory.getCurrentSession().createCriteria(getModelClass()).list();
	}
}
