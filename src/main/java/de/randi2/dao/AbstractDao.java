/* 
 * (c) 2008-2009 RANDI2 Core Development Team
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

import de.randi2.model.AbstractDomainObject;

/**
 * The Interface AbstractDao.
 */
public interface AbstractDao<E extends AbstractDomainObject> {

	/**
	 * Get the domain object.
	 * 
	 * @param id
	 *            the id
	 * 
	 * @return the e
	 */
	public E get(long id);
	
	/**
	 * Creates the.
	 * 
	 * @param object
	 *            the object
	 */
	public void create(E object);
	
	/**
	 * Find by example.
	 * 
	 * @param object
	 *            the object
	 * 
	 * @return the list< e>
	 */
	public List<E> findByExample(E object);
	
	/**
	 * Gets the all.
	 * 
	 * @return the all
	 */
	public List<E> getAll();
	
	/**
	 * Update.
	 * 
	 * @param object
	 *            the object
	 * 
	 * @return the e
	 */
	public E update(E object);
}
