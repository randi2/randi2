package de.randi2.jsf.handlers;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Vector;

import javax.faces.context.FacesContext;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;

import de.randi2.dao.CenterDao;
import de.randi2.dao.LoginDao;
import de.randi2.dao.PersonDao;
import de.randi2.jsf.Randi2;
import de.randi2.jsf.exceptions.LoginException;
import de.randi2.jsf.exceptions.RegistrationException;
import de.randi2.jsf.pages.RegisterPage;
import de.randi2.model.Center;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.enumerations.Gender;

/**
 * <p>
 * This class cares about the login object, which represents the logged in user
 * and contains all methodes needed for working wiht login and person objects.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
public class LoginHandler {

	// This Object ist representing the current User
	private Login login = null;

	// The Locale chosen by the user.
	private Locale chosenLocale = null;

	// Objects for User-Creating Process
	private Person person = null;

	private Person userAssistant = null;

	private Center userCenter = null;

	private LoginDao loginDao;

	private PersonDao personDao;

	private CenterDao centerDao;

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
		if (userCenter != null) {
			this.userCenter = userCenter;
			((RegisterPage) FacesContext.getCurrentInstance().getApplication()
					.getVariableResolver().resolveVariable(
							FacesContext.getCurrentInstance(), "registerPage"))
					.setCenterSelected(true);
		} else {
			((RegisterPage) FacesContext.getCurrentInstance().getApplication()
					.getVariableResolver().resolveVariable(
							FacesContext.getCurrentInstance(), "registerPage"))
					.setCenterSelected(false);
		}
	}

	/**
	 * Method for saving the current user.
	 * 
	 * @return Randi2.SUCCESS normally. Randi2.ERROR in case of an error.
	 */
	public String saveUser() {
		try {
			this.loginDao.save(this.getLogin());
			return Randi2.SUCCESS;
		} catch (Exception exp) {
			Randi2.showMessage(exp);
			return Randi2.ERROR;
		}
	}

	/**
	 * This method is conducted for the users registration. It saves the entered
	 * values.
	 * 
	 * @return Randi2.SUCCESS normally. Randi2.ERROR in case of an error.
	 */
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
			// TODO for a stable release delete the following stacktrace
			exp.printStackTrace();
			for (InvalidValue v : exp.getInvalidValues()) {
				Randi2
						.showMessage(v.getPropertyName() + " : "
								+ v.getMessage());
			}
			return Randi2.ERROR;
		} catch (Exception e) {
			// TODO for a stable release delete the following stacktrace
			e.printStackTrace();
			Randi2.showMessage(e);
			return Randi2.ERROR;
		}

	}

	/**
	 * This method saves the current login-object and log it out.
	 * 
	 * @return Randi2.SUCCESS
	 */
	public String logoutUser() {
		// TODO Save the login object
		// loginDao.save(this.login);
		this.cleanUp();
		return Randi2.SUCCESS;
	}

	/**
	 * Method to log in a user.
	 * 
	 * @return if the procces was successful then: randi2.SUCCESS. Otherwise:
	 *         randi2.ERROR
	 */
	public String loginUser() {
		String pass = login.getPassword();
		try {
			login = loginDao.get(login.getUsername());
			if (login == null)
				throw new LoginException(LoginException.LOGIN_PASS_INCORRECT);

			// TODO Temporary solution
			fulfillLoginObject();
			// END

			if (login.getPassword().equals(pass)) {
				if (login.getRegistrationDate() == null)
					login.setRegistrationDate(new GregorianCalendar());
				login.setLastLoggedIn(new GregorianCalendar());
				return Randi2.SUCCESS;
			} else
				throw new LoginException(LoginException.LOGIN_PASS_INCORRECT);
		} catch (Exception e) {
			Randi2.showMessage(e);
			return Randi2.ERROR;
		}

	}

	/*
	 * For development only!
	 */
	public String testLogin() {
		Properties testProperties = new Properties();
		try {
			testProperties.load(FacesContext.getCurrentInstance()
					.getExternalContext().getResourceAsStream(
							"/test.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		login = loginDao.get(testProperties.getProperty("username"));
		if (login.getRegistrationDate() == null)
			login.setRegistrationDate(new GregorianCalendar());
		login.setLastLoggedIn(new GregorianCalendar());
		// TODO Temporary solution
		fulfillLoginObject();
		// END
		return Randi2.SUCCESS;
	}

	/**
	 * This method clean all references within the LoginHandler-object.
	 */
	public void cleanUp() {
		this.login = null;
		this.person = null;
		this.userAssistant = null;
		this.userCenter = null;
	}

	public String setUSEnglish() {
		chosenLocale = new Locale("en", "US");
		login.setPrefLocale(chosenLocale);
		System.out.println("US Locale");
		return Randi2.SUCCESS;
	}

	public String setDEGerman() {
		chosenLocale = new Locale("de", "DE");
		login.setPrefLocale(chosenLocale);
		System.out.println("DE Locale");
		return Randi2.SUCCESS;
	}

	/*
	 * Temporary method for fulfilling the login object.
	 */
	private void fulfillLoginObject() {
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

	/**
	 * This method provide the locale chosen by the logged user. If the user
	 * didn't choose anyone, but his standard browser-locale is supported, then
	 * it will be provided. Otherwise the applications default locale will be
	 * used.
	 * 
	 * @return locale for the loged in user
	 */
	@SuppressWarnings("unchecked")
	public Locale getChosenLocale() {
		if (this.login != null) {
			System.out.println("Login not null");
			if (this.login.getPrefLocale() != null) {
				System.out.println("PrefLocale not null");
				chosenLocale = this.login.getPrefLocale();
			}
		} else {
			chosenLocale = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestLocale();
			Iterator<Locale> supportedLocales = FacesContext
					.getCurrentInstance().getApplication()
					.getSupportedLocales();
			while (supportedLocales.hasNext()) {
				if (supportedLocales.next().equals(chosenLocale))
					return chosenLocale;
			}
			chosenLocale = FacesContext.getCurrentInstance().getApplication()
					.getDefaultLocale();
		}
		return chosenLocale;
	}

	/**
	 * Simple set method for the users locale.
	 * 
	 * @param chosenLocale
	 */
	public void setChosenLocale(Locale chosenLocale) {
		this.chosenLocale = chosenLocale;
	}

	// public void updateUserCenter(String cName) throws RegistrationException{
	// System.out.println(cName);
	// this.userCenter = centerDao.get(cName);
	// if(this.userCenter==null)
	// throw new RegistrationException(RegistrationException.CENTER_ERROR);
	// }

	public CenterDao getCenterDao() {
		return centerDao;
	}

	public void setCenterDao(CenterDao centerDao) {
		this.centerDao = centerDao;
	}
}
