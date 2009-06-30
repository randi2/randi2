package de.randi2.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.security.annotation.Secured;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;

public abstract class AbstractDaoHibernate<E extends AbstractDomainObject> implements AbstractDao<E>{
	
	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired protected SessionFactory sessionFactory;
	
	public abstract Class<E> getModelClass();

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	@Secured({"AFTER_ACL_READ"})
	public E get(long id){
		E element = (E) sessionFactory.getCurrentSession().get(getModelClass(), id);
		if(element == null){
			throw new ObjectRetrievalFailureException(getModelClass(), id);
		}
		return element;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void create(E object){
		logger.debug("Save new " +getModelClass().getSimpleName()+ " object");
		sessionFactory.getCurrentSession().persist(object);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	@Secured({"ROLE_USER","AFTER_ACL_COLLECTION_READ"})
	public List<E> findByExample(E object){
		return sessionFactory.getCurrentSession().createCriteria(getClass()).add(Example.create(object).ignoreCase()).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	@Secured({"ROLE_USER","ROLE_ANONYMOUS","AFTER_ACL_COLLECTION_READ"})
	public List<E> getAll(){
		return sessionFactory.getCurrentSession().createQuery("from " + getModelClass().getSimpleName()).list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public E update(E object){
		logger.debug("Update " +getModelClass().getSimpleName()+ " object (id= "+object.getId()+")");
		return (E) sessionFactory.getCurrentSession().merge(object);
	}
}
