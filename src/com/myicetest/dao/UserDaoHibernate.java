package com.myicetest.dao;

import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myicetest.models.User;


public class UserDaoHibernate extends AbstractDaoHibernate<User> implements UserDao {

	@Override
	public User get(long id) {
		User u = (User) template.get(User.class, id);
		if(u == null){
			throw new ObjectRetrievalFailureException(User.class, id);
		}
		return u;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void save(User user) {
		System.out.println("SAVE");
		template.saveOrUpdate(user);
	}

}
