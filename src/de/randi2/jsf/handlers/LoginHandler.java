package de.randi2.jsf.handlers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Vector;

import javax.faces.context.FacesContext;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;

import de.randi2.dao.LoginDao;
import de.randi2.dao.PersonDao;
import de.randi2.jsf.Randi2;
import de.randi2.jsf.exceptions.LoginException;
import de.randi2.jsf.pages.RegisterPage;
import de.randi2.model.Center;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.enumerations.Gender;

/**
 * <p> This class cares about the login object, which represents the logged in user and contains all methodes needed for working wiht login and person objects.</p>
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 * 
 */
public class LoginHandler {

	// This Object ist representing the current User
	private Login login = null;

	// Objects for User-Creating Process
	private Person person = null;

	private Person userAssistant = null;

	private Center userCenter = null;

	@Autowired
	private LoginDao loginDao;
	@Autowired
	private PersonDao personDao;

	public LoginHandler() {
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

	public Login getLogin() {
		if (login == null)
			this.login = new Login();
		return this.login;
	}

	public Person getPerson() {
		if (person == null)
			this.person = new Person();
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public LoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	public Person getUserAssistant() {
		if (userAssistant == null)
			userAssistant = new Person();
		return userAssistant;
	}

	public void setUserAssistant(Person userAssistant) {
		this.userAssistant = userAssistant;
	}

	public Center getUserCenter() {
		if (userCenter == null)
			userCenter = new Center();
		return userCenter;
	}

	public void setUserCenter(Center userCenter) {
		this.userCenter = userCenter;
	}

	public String saveUser() {
		try {
			this.loginDao.save(this.getLogin());
			return Randi2.SUCCESS;
		} catch (Exception exp) {
			Randi2.showMessage(exp);
			return Randi2.ERROR;
		}
	}

	public String registerUser() {
		try {
			this.getLogin().setPerson(this.getPerson());
			this.getLogin().setUsername(person.getEMail());
			loginDao.save(this.getLogin());

			// Making the successPopup visible
			((RegisterPage) FacesContext.getCurrentInstance().getApplication()
					.getVariableResolver().resolveVariable(
							FacesContext.getCurrentInstance(), "registerPage"))
					.setRegPvisible(true);

			// Reseting the objects
			this.cleanUp();
			// TODO Genereting an Activation E-Mail
			return Randi2.SUCCESS;
		} catch (InvalidStateException exp) {
			for (InvalidValue v : exp.getInvalidValues()) {
				Randi2
						.showMessage(v.getPropertyName() + " : "
								+ v.getMessage());
			}
			return Randi2.ERROR;
		} catch (Exception e) {
			Randi2.showMessage(e);
			return Randi2.ERROR;
		}

	}

	public String logoutUser() {
		//TODO Save the login object
		//loginDao.save(this.login);
		this.login = new Login();
		return Randi2.SUCCESS;
	}

	public String loginUser() {
		String pass = login.getPassword();
		try {
			login = loginDao.get(login.getUsername());
			if (login == null)
				throw new LoginException(LoginException.LOGIN_PASS_INCORRECT);

			// TODO Temporary solution
			fulfillLoginObject();
			// END

			if (login.getPassword().equals(pass)){
				if(login.getFirstLoggedIn()==null)
					login.setFirstLoggedIn(new GregorianCalendar());
				login.setLastLoggedIn(new GregorianCalendar());
				return Randi2.SUCCESS;
			}else
				throw new LoginException(LoginException.LOGIN_PASS_INCORRECT);
		} catch (Exception e) {
			Randi2.showMessage(e);
			return Randi2.ERROR;
		}

	}
	
	public String testLogin(){
		Properties testProperties = new Properties();
		try {
			testProperties.load(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/test.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		login = loginDao.get(testProperties.getProperty("username"));
		if(login.getFirstLoggedIn()==null)
			login.setFirstLoggedIn(new GregorianCalendar());
		login.setLastLoggedIn(new GregorianCalendar());
		// TODO Temporary solution
		fulfillLoginObject();
		// END
		return Randi2.SUCCESS;
	}

	public void cleanUp() {
		this.login = null;
		this.person = null;
		this.userAssistant = null;
		this.userCenter = null;
	}
	
	/*
	* Temporary method for fulfilling the login object.
	*/
	private void fulfillLoginObject(){
		Center tCenter = new Center();
		tCenter.setCity("Heidelberg");
		tCenter.setName("RANDI2 Development by DKFZ");
		tCenter.setPostcode("69120");
		tCenter.setStreet("Im Neuenheimer Feld 1");
		tCenter.setPassword("password");

		Person tPerson = new Person();
		tPerson.setCenter(tCenter);
		tPerson.setEMail("randi@randi2.dev");
		tPerson.setFirstname("Lukasz");
		tPerson.setSurname("Plotnicki");
		tPerson.setGender(Gender.MALE);
		tPerson.setMobile("0176/26157884");
		tPerson.setPhone("06221/39193");
		tCenter.setContactPerson(tPerson);

		Vector<Person> members = new Vector<Person>();
		members.add(login.getPerson());
		tCenter.setMembers(members);

		login.getPerson().setCenter(tCenter);
	}
}
