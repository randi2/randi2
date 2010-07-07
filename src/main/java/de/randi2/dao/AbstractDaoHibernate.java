/* 
 * (c) 2008- RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
package de.randi2.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.security.access.annotation.Secured;

import de.randi2.model.AbstractDomainObject;

/**
 * The Class AbstractDaoHibernate.
 */
public abstract class AbstractDaoHibernate<E extends AbstractDomainObject> implements AbstractDao<E>{
	
	/** The logger. */
	protected Logger logger = Logger.getLogger(getClass());
	
	/** The session factory. */
	@Autowired protected SessionFactory sessionFactory;
	
	/**
	 * Gets the model class.
	 * 
	 * @return the model class
	 */
	public abstract Class<E> getModelClass();

	/* (non-Javadoc)
	 * @see de.randi2.dao.AbstractDao#get(long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Secured({"AFTER_ACL_READ"})
	public E get(long id){
		E element = (E) sessionFactory.getCurrentSession().get(getModelClass(), id);
		if(element == null){
			throw new ObjectRetrievalFailureException(getModelClass(), id);
		}
		return element;
	}
	
	/* (non-Javadoc)
	 * @see de.randi2.dao.AbstractDao#create(de.randi2.model.AbstractDomainObject)
	 */
	@Override
	public void create(E object){
		logger.debug("Save new " +getModelClass().getSimpleName()+ " object");
		sessionFactory.getCurrentSession().persist(object);
	}
	
	/* (non-Javadoc)
	 * @see de.randi2.dao.AbstractDao#findByExample(de.randi2.model.AbstractDomainObject)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Secured({"ROLE_USER","AFTER_ACL_COLLECTION_READ"})
	public List<E> findByExample(E object){
		return sessionFactory.getCurrentSession().createCriteria(getClass()).add(Example.create(object).ignoreCase()).list();
	}

	/* (non-Javadoc)
	 * @see de.randi2.dao.AbstractDao#getAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Secured({"ROLE_USER","ROLE_ANONYMOUS","AFTER_ACL_COLLECTION_READ"})
	public List<E> getAll(){
		return sessionFactory.getCurrentSession().createQuery("from " + getModelClass().getSimpleName()).list();
	}
	
	/* (non-Javadoc)
	 * @see de.randi2.dao.AbstractDao#update(de.randi2.model.AbstractDomainObject)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public E update(E object){
		logger.debug("Update " +getModelClass().getSimpleName()+ " object (id= "+object.getId()+")");
		return (E) sessionFactory.getCurrentSession().merge(object);
	}
}
