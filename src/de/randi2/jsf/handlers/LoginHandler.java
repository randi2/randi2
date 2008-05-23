package de.randi2.jsf.handlers;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import de.randi2.model.Login;


public class LoginHandler {
	private Login login;

	
	public LoginHandler(){
	}
	
	

	public void setLogin(Login login) {
		this.login = login;
	}
	
	public Login getLogin(){
		if(login==null)
			this.login = new Login();
		return this.login;
	}
	
	public String saveUser(){
		//Es fehlt noch ein DAO
		return "success";
	}
	
	public String logoutUser(){
		if(login.getId() == login.NOT_YET_SAVED_ID){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","User can't be logout!"));
			return "error";
		}
		login = new Login();
		return "success";
	}
	
	public String loginUser(){
		String pass = login.getPassword();
		try {
			//login = loginDao.get(login.getLoginname());
//			if(login.getPassword().equals(pass))
				return "success";
//			else
//				throw new UserException(Messages.WRONG_LOGIN);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(),e.toString()));
			return "error";
		}
		
	}
}
