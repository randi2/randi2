package de.randi2.jsf.handlers;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import de.randi2.dao.LoginDao;
import de.randi2.model.Login;
import de.randi2.model.Person;


public class LoginHandler {
	
	private Login login;
	
	private Person person;
	
	private LoginDao loginDao;

	
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
		if(person==null)
			this.person = new Person();
		return this.person;
	}
	
	public void setPerson(Person person){
		this.person = person;
	}
	
	public LoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	public String saveUser(){
		//Es fehlt noch ein DAO
		return ApplicationHandler.SUCCESS;
	}
	
	public String registerUser(){
		System.out.println("registerUser");
		try{
			this.getLogin().setPerson(this.getPerson());
			this.getLogin().setUsername(person.getEMail());
			this.getLogin().setPassword("test");
			loginDao.save(this.getLogin());
		}catch(Exception exp){
			exp.printStackTrace();
			showMessage(exp);
			return ApplicationHandler.ERROR;
		}
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
			showMessage(e);
			return ApplicationHandler.ERROR;
		}
		
	}
	
	private void showMessage(Exception e){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(),e.toString()));
	}
}
