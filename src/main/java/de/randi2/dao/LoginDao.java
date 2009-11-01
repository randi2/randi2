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


import de.randi2.model.Login;


/**
 * The Interface LoginDao.
 */
public interface LoginDao extends AbstractDao<Login> {

	/**
	 * This method returns an login object for a given username.
	 * 
	 * @param username
	 *            - unique username.
	 * 
	 * @return - a complete login object or <code>null</code> if no such login
	 *         is available.
	 */
	public Login get(String username);
	
	/* (non-Javadoc)
	 * @see de.randi2.dao.AbstractDao#create(de.randi2.model.AbstractDomainObject)
	 */
	@Override
	public void create(Login object);

}
