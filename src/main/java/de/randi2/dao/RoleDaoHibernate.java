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

import de.randi2.model.Role;

/**
 * The Class RoleDaoHibernate.
 */
@Service("roleDao")
public class RoleDaoHibernate extends AbstractDaoHibernate<Role> implements
		RoleDao {

	/* (non-Javadoc)
	 * @see de.randi2.dao.AbstractDaoHibernate#getModelClass()
	 */
	@Override
	public Class<Role> getModelClass() {
		return Role.class;
	}

	/* (non-Javadoc)
	 * @see de.randi2.dao.AbstractDaoHibernate#getAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	//FIXME We should create the ACLs for Roles
	public List<Role> getAll() {
		return sessionFactory.getCurrentSession().createQuery(
				"from " + getModelClass().getSimpleName()).list();
	}
}
