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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.security.access.annotation.Secured;

import de.randi2.model.AbstractDomainObject;

/**
 * The Class AbstractDaoHibernate.
 */
public abstract class AbstractDaoHibernate<E extends AbstractDomainObject> implements AbstractDao<E>{
	
	/** The logger. */
	protected Logger logger = Logger.getLogger(getClass());
	
	
	protected EntityManager entityManager;

	@PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
	        this. entityManager = entityManager;
	}

	
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
	@Secured({"AFTER_ACL_READ"})
	public E get(long id){
		E element = entityManager.find(getModelClass(), id);
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
		entityManager.persist(object);
	}
	
	/* (non-Javadoc)
	 * @see de.randi2.dao.AbstractDao#getAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Secured({"ROLE_USER","ROLE_ANONYMOUS","AFTER_ACL_COLLECTION_READ"})
	public List<E> getAll(){
		return entityManager.createQuery("from " + getModelClass().getSimpleName()).getResultList();
	}
	
	/* (non-Javadoc)
	 * @see de.randi2.dao.AbstractDao#update(de.randi2.model.AbstractDomainObject)
	 */
	@Override
	public E update(E object){
		logger.debug("Update " +getModelClass().getSimpleName()+ " object (id= "+object.getId()+")");
		return entityManager.merge(object);
	}
	
}
