package de.randi2.jsf.handlers;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import de.randi2.model.Login;
import de.randi2.model.Person;


public class LoginHandler {
	
	private Login login;
	
	private Person person;

	
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
	
	public Person getPerson(){
		return this.person;
	}
	
	public void setPerson(Person person){
		this.person = person;
	}
	
	public String saveUser(){
		//Es fehlt noch ein DAO
		return ApplicationHandler.SUCCESS;
	}
	
	public String logoutUser(){
		System.out.println("LogoutUser wurde aufgerufen! (Bin drin!)");
		return ApplicationHandler.SUCCESS;
	}
	
	public String loginUser(){
		String pass = login.getPassword();
		try {
			//login = loginDao.get(login.getLoginname());
//			if(login.getPassword().equals(pass))
				return ApplicationHandler.SUCCESS;
//			else
//				throw new UserException(Messages.WRONG_LOGIN);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(),e.toString()));
			return ApplicationHandler.ERROR;
		}
		
	}
	
	public String registerUser(){
		return ApplicationHandler.SUCCESS;
	}
}
