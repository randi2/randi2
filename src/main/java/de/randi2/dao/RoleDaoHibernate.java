package de.randi2.dao;

import java.util.List;

import org.springframework.security.annotation.Secured;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.Role;

public class RoleDaoHibernate extends AbstractDaoHibernate<Role> implements
		RoleDao {

	@Override
	public Class<Role> getModelClass() {
		return Role.class;
	}

	//FIXME We should create the ACLs for Roles
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Secured( { "ROLE_USER"})
	public List<Role> getAll() {
		return sessionFactory.getCurrentSession().createQuery(
				"from " + getModelClass().getSimpleName()).list();
	}
}
