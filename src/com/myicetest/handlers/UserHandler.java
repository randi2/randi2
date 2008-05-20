package com.myicetest.handlers;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.myicetest.dao.UserDaoHibernate;
import com.myicetest.models.User;

public class UserHandler {
	
	private User user;
	
	private UserDaoHibernate userDao;
	
	public UserHandler(){
	}
	

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
		userDao.save(this.user);
		return "success";
	}
	
	public String loginUser(){
		String pass = user.getPassword();
		try {
			user = userDao.search(user.getLoginname());
			if(user.getPassword().equals(pass))
				return "success";
			else
				throw new Exception("Wrong login/password!");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(e.getLocalizedMessage(), new FacesMessage(FacesMessage.SEVERITY_ERROR,e.toString(),e.getLocalizedMessage()));
			return "error";
		}
		
	}
	
}
