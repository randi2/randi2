package de.randi2.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.annotation.Secured;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Role;


public class LoginDaoHibernate extends AbstractDaoHibernate<Login> implements LoginDao{

	@Override
	@SuppressWarnings("unchecked")
	@Secured({"AFTER_ACL_READ"})
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public Login get(String username) {
		String query = "from de.randi2.model.Login login where "
			+ "login.username =?";
	 
		List<Login>  loginList = (List) sessionFactory.getCurrentSession().createQuery(query).setParameter(0, username).list();
		if (loginList.size() ==1)	return loginList.get(0);
		else return null;
	}

	@Override
	public Class<Login> getModelClass() {
		return Login.class;
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void create(Login object) {
		loadRoles(object);
		super.create(object);
			
			
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Login update(Login object) {
		loadRoles(object);
		return super.update(object);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	private void loadRoles(Login object){
		for(Role r: object.getRoles()){
				Set<Role> roles = new HashSet<Role>();
				roles.add((Role)sessionFactory.getCurrentSession().createQuery("from Role where name = ?").setParameter(0, r.getName()).uniqueResult());
				object.setRoles(roles);
		}
	}

}
