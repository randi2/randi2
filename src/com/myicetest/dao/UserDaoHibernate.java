package com.myicetest.dao;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myicetest.models.User;
import com.myicetest.models.exceptions.UserException;


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
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public User get(String loginname) throws UserException{
		List<User> users = template.find("from User u where u.loginname = '"+loginname+"'");
		if(users.size()!=1)
			throw new UserException(UserException.Messages.NOT_FOUND);
		return users.get(0);
	}

}
