package com.myicetest.handlers;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.myicetest.dao.UserDaoHibernate;
import com.myicetest.models.User;
import com.myicetest.models.exceptions.UserException;
import com.myicetest.models.exceptions.UserException.Messages;

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
	
	public String logoutUser(){
		if(user.getId() == user.NOT_YET_SAVED_ID){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","User can't be logout!"));
			return "error";
		}
		user = new User();
		return "success";
	}
	
	public String loginUser(){
		String pass = user.getPassword();
		try {
			user = userDao.get(user.getLoginname());
			if(user.getPassword().equals(pass))
				return "success";
			else
				throw new UserException(Messages.WRONG_LOGIN);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(),e.toString()));
			return "error";
		}
		
	}
	
}
