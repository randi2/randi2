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

import org.springframework.stereotype.Service;

import de.randi2.model.TrialSite;

/**
 * The Class TrialSiteDaoHibernate.
 */
@Service("trialSiteDao")
public class TrialSiteDaoHibernate extends AbstractDaoHibernate<TrialSite> implements TrialSiteDao {

	/* (non-Javadoc)
	 * @see de.randi2.dao.AbstractDaoHibernate#getModelClass()
	 */
	@Override
	public Class<TrialSite> getModelClass() {
		return TrialSite.class;
	}
	
	

	/* (non-Javadoc)
	 * @see de.randi2.dao.TrialSiteDao#get(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public TrialSite get(String name) {
		String query = "from de.randi2.model.TrialSite trialSite where "
			+ "trialSite.name =?";
		List<TrialSite>  list =(List) sessionFactory.getCurrentSession().createQuery(query).setParameter(0, name).list();
		if (list.size() ==1)	return list.get(0);
		else return null;
	}
	
	

	
}
