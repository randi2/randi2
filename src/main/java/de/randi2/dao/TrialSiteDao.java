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

import de.randi2.model.TrialSite;

/**
 * The Interface TrialSiteDao.
 */
public interface TrialSiteDao extends AbstractDao<TrialSite> {
	

	
	/**
	 * Get the trial site with the given name or null.
	 * 
	 * @param name
	 *            the name
	 * 
	 * @return the trial site
	 */
	public TrialSite get(String name);

	/* (non-Javadoc)
	 * @see de.randi2.dao.AbstractDao#create(de.randi2.model.AbstractDomainObject)
	 */
	@Override
	public void create(TrialSite object);
}
