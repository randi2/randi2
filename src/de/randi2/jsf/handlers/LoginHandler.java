package de.randi2.jsf.handlers;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;

import de.randi2.dao.LoginDao;
import de.randi2.dao.PersonDao;
import de.randi2.jsf.pages.Register;
import de.randi2.model.Center;
import de.randi2.model.Login;
import de.randi2.model.Person;


public class LoginHandler {
	
	private Login login;
	
	private Person person;
	
	private Person userAssistant;
	
	private Center userCenter;
	
	
	private LoginDao loginDao;
	
	private PersonDao personDao;

	
	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

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

			//TODO Test
			((Register)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("register")).setRegPvisible(true);

		}catch(InvalidStateException exp){
			for(InvalidValue v:exp.getInvalidValues()){
				showMessage(v.getPropertyName()+" : "+v.getMessage());
			}
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
			login = loginDao.get(login.getUsername());
			if(login.getPassword().equals(pass))
				return ApplicationHandler.SUCCESS;
			else
				throw new Exception("Wrong login/password!");
		} catch (Exception e) {
			showMessage(e);
			return ApplicationHandler.ERROR;
		}
		
	}
	
	private void showMessage(Exception e){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(),e.toString()));
	}
	
	private void showMessage(String message){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,message,null));
	}

	public Person getUserAssistant() {
		if(userAssistant==null)
			userAssistant = new Person();
		return userAssistant;
	}

	public void setUserAssistant(Person userAssistant) {
		this.userAssistant = userAssistant;
	}

	public Center getUserCenter() {
		if(userCenter==null)
			userCenter = new Center();
		return userCenter;
	}

	public void setUserCenter(Center userCenter) {
		this.userCenter = userCenter;
	}
}
