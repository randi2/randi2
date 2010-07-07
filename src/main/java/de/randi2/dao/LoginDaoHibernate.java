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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import de.randi2.model.Login;
import de.randi2.model.Role;

/**
 * The Class LoginDaoHibernate.
 */
@Service("loginDao")
public class LoginDaoHibernate extends AbstractDaoHibernate<Login> implements
		LoginDao {

	/* (non-Javadoc)
	 * @see de.randi2.dao.LoginDao#get(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Secured( { "AFTER_ACL_READ" })
	public Login get(String username) {
		String query = "from de.randi2.model.Login login where "
				+ "login.username =?";

		List<Login> loginList = (List) sessionFactory.getCurrentSession()
				.createQuery(query).setParameter(0, username).list();
		if (loginList.size() == 1)
			return loginList.get(0);
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see de.randi2.dao.AbstractDaoHibernate#getModelClass()
	 */
	@Override
	public Class<Login> getModelClass() {
		return Login.class;
	}

	/* (non-Javadoc)
	 * @see de.randi2.dao.AbstractDaoHibernate#create(de.randi2.model.AbstractDomainObject)
	 */
	@Override
	public void create(Login object) {
		loadRoles(object);
		super.create(object);

	}

	/* (non-Javadoc)
	 * @see de.randi2.dao.AbstractDaoHibernate#update(de.randi2.model.AbstractDomainObject)
	 */
	@Override
	public Login update(Login object) {
		loadRoles(object);
		return super.update(object);
	}

	/**
	 * Load the roles for the given login object.
	 * 
	 * @param object
	 *            the object
	 */
	private void loadRoles(Login object) {
		Set<Role> roles = new HashSet<Role>();
		for (Role r : object.getRoles()) {
			roles.add((Role) sessionFactory.getCurrentSession().createQuery(
					"from Role where name = ?").setParameter(0, r.getName())
					.uniqueResult());
		}
		object.setRoles(roles);
	}

}
