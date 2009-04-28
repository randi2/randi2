package de.randi2.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Role;


public class LoginDaoHibernate extends AbstractDaoHibernate<Login> implements LoginDao{

	@Override
	@SuppressWarnings("unchecked")
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
	public void save(Login object) {
		for(Role r: object.getRoles()){
			if(r.getId()== AbstractDomainObject.NOT_YET_SAVED_ID){
				sessionFactory.getCurrentSession().persist(r);
			}
		}
		if(object.getId() == AbstractDomainObject.NOT_YET_SAVED_ID){
			sessionFactory.getCurrentSession().persist(object);
		}else sessionFactory.getCurrentSession().update(object);
	}

}
