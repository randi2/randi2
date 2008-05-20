package com.myicetest.handlers;

import com.myicetest.dao.UserDaoHibernate;
import com.myicetest.models.User;
import com.myicetest.models.exceptions.UserException;
import com.myicetest.models.exceptions.UserException.Messages;

public class UserHandler {
	
	private User user;
	
	
	private UserDaoHibernate userDao;
	

	public UserDaoHibernate getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDaoHibernate userDao) {
		this.userDao = userDao;
	}
	
	

	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser(){
		if(user==null)
			this.user = new User();
		return this.user;
	}
	
	public String saveUser(){
		userDao.save(user);
		return "Success";
	}
	
	public String loginUser(){
		try {
			this.setUser(this.search(user.getLoginname(), user.getPassword()));
			saveUser();
			return "success";
		} catch (UserException e) {
			return e.getLocalizedMessage();
		}
		
	}
	
	private User search(String login, String pass) throws UserException{
		if(login.equals("lukasz")&&pass.equals("secret")){
			return new User(){{
				setFirstname("Lukasz");
				setLoginname("lukasz");
				setPassword("secret");
				setSurname("Plotnicki");
			}};
		}else
			throw new UserException(Messages.NOT_FOUND);
	}
	
}
