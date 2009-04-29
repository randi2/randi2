package de.randi2.dao;

import de.randi2.model.Role;

public class RoleDaoHibernate extends AbstractDaoHibernate<Role> implements
		RoleDao {

	

	@Override
	public Class<Role> getModelClass() {
		return Role.class;
	}

}
