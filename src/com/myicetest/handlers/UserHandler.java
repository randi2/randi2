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
		User temp = new User();
		temp.setFirstname("Lukasz");
		temp.setSurname("Plotnicki");
		temp.setLoginname("luki");
		temp.setPassword("secret");
		System.out.println("ID "+temp.getId());
		userDao.save(temp);
		return "Success";
	}
	
	public String loginUser(){
		try {
			this.setUser(this.search(user.getLoginname(), user.getPassword()));
			saveUser();
			return "success";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(e.getLocalizedMessage(), new FacesMessage(FacesMessage.SEVERITY_ERROR,e.toString(),e.getLocalizedMessage()));
			return "error";
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
