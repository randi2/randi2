package de.randi2.jsf.handlers;

import javax.faces.context.FacesContext;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;

import de.randi2.dao.LoginDao;
import de.randi2.dao.PersonDao;
import de.randi2.jsf.Randi2;
import de.randi2.jsf.pages.Register;
import de.randi2.model.Center;
import de.randi2.model.Login;
import de.randi2.model.Person;


public class LoginHandler {
	
	//This Object ist representing the current User
	private Login login;
	
	
	// Objects for User-Creating Process
	private Person person;
	
	private Person userAssistant;
	
	private Center userCenter;
	
	@Autowired
	private LoginDao loginDao;
	@Autowired
	private PersonDao personDao;
	
	public LoginHandler(){
	}
	
	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
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

	public String saveUser(){
		try{
			this.loginDao.save(this.getLogin());
			return Randi2.SUCCESS;
		}catch(Exception exp){
			Randi2.showMessage(exp);
			return Randi2.ERROR;
		}	
	}
	
	public String registerUser(){
		try{
			this.getLogin().setPerson(this.getPerson());
			this.getLogin().setUsername(person.getEMail());
			loginDao.save(this.getLogin());

			//Making the successPopup visible
			((Register)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("register")).setRegPvisible(true);
			return Randi2.SUCCESS;
			
			//TODO Genereting an Activation E-Mail
		}catch(InvalidStateException exp){
			for(InvalidValue v:exp.getInvalidValues()){
				Randi2.showMessage(v.getPropertyName()+" : "+v.getMessage());
			}
			return Randi2.ERROR;
		}catch(Exception e){
			//TODO delete befor deployment - or log it properly
			e.printStackTrace();
			Randi2.showMessage(e);
			return Randi2.ERROR;
		}
		
	}
	
	public String logoutUser(){
		this.login = new Login();
		return Randi2.SUCCESS;
	}
	
	public String loginUser(){
		String pass = login.getPassword();
		try {
			login = loginDao.get(login.getUsername());
			if(login.getPassword().equals(pass))
				return Randi2.SUCCESS;
			else
				throw new Exception("Wrong login/password!");
		} catch (Exception e) {
			Randi2.showMessage(e);
			return Randi2.ERROR;
		}
		
	}

}
