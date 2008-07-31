/* This file is part of RANDI2.
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
package de.randi2.jsf.handlers;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.springframework.security.context.SecurityContextHolder;

import de.randi2.dao.CenterDao;
import de.randi2.dao.LoginDao;
import de.randi2.dao.PersonDao;
import de.randi2.jsf.Randi2;
import de.randi2.jsf.exceptions.LoginException;
import de.randi2.jsf.exceptions.RegistrationException;
import de.randi2.jsf.pages.RegisterPage;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.TrialSite;
import de.randi2.model.Login;
import de.randi2.model.Person;

/**
 * <p>
 * This class cares about the login object, which represents the logged in user
 * and contains all methodes needed for working wiht login and person objects.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 */
public class LoginHandler {

	// This Object ist representing the current User
	private Login login = null;

	// The Locale chosen by the user.
	private Locale chosenLocale = null;

	private Login showedLogin = null;

	// Objects for User-Creating Process
	private Person person = null;

	private Person userAssistant = null;

	private TrialSite userCenter = null;

	private String cPassword = null;
	// ---

	// DB Access
	private LoginDao loginDao;

	private PersonDao personDao;

	private CenterDao centerDao;
	// ---

	private boolean creatingMode = false;

	private boolean editable = false;

	// Popup's flags
	private boolean userSavedPVisible = false;

	private boolean changePasswordPVisible = false;

	public boolean isChangePasswordPVisible() {
		return changePasswordPVisible;
	}

	public void setChangePasswordPVisible(boolean changePasswordPVisible) {
		this.changePasswordPVisible = changePasswordPVisible;
	}

	public boolean isUserSavedPVisible() {
		return userSavedPVisible;
	}

	public void setUserSavedPVisible(boolean userSavedPVisible) {
		this.userSavedPVisible = userSavedPVisible;
	}

	public String hideUserSavedPopup() {
		this.userSavedPVisible = false;
		return Randi2.SUCCESS;
	}

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
		if (login == null) {
			try {
				if (!SecurityContextHolder.getContext().getAuthentication()
						.isAuthenticated()) // Registration Process
					this.login = new Login();
				else
					// Normal Log in
					this.login = (Login) SecurityContextHolder.getContext()
							.getAuthentication().getPrincipal();
			} catch (NullPointerException exp) {
				// No Security Context - must be request for registration process
				this.login = new Login();
			}
		}
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

	public TrialSite getUserCenter() {
		if (userCenter == null)
			userCenter = new TrialSite();
		return userCenter;
	}

	public void setUserCenter(TrialSite userCenter) {
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

	public void showChangePasswordPopup() {
		// Show the changePasswordPopup
		this.changePasswordPVisible = true;
	}

	public String hideChangePasswordPopup() {
		// Hide the changePasswordPopup
		this.changePasswordPVisible = false;
		return Randi2.SUCCESS;
	}

	public void changePassword() {
		System.out.println("Change Password");
		// Delete the old password
		// this.showedLogin.setPassword("");
		this.saveLogin();
		this.hideChangePasswordPopup();
	}

	/**
	 * Method for saving the showed user.
	 * 
	 * @return Randi2.SUCCESS normally. Randi2.ERROR in case of an error.
	 */
	public String saveLogin() {
		try {
			// TODO Problem with the saved object!
			// System.out.println("S_ID:"+this.showedLogin.getId());
			// System.out.println("S_VER " + this.showedLogin.getVersion());
			this.loginDao.save(this.showedLogin);
			// System.out.println("S_ID:"+this.showedLogin.getId());
			// System.out.println("S_VER " + this.showedLogin.getVersion());

			// TODO Updating the Objetct ... TEMP SOLUTION
			this.showedLogin = this.loginDao.get(this.showedLogin.getId());

			// Making the centerSavedPopup visible
			this.userSavedPVisible = true;

			// If the current login user was saved with this method, the login
			// object will be reload from the DB
			if (this.showedLogin.equals(this.login))
				this.login = this.loginDao.get(this.login.getId());

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
		Login objectToRegister = null;
		if (creatingMode) { // A new user was created by another logged
			// in user
			objectToRegister = showedLogin;
			objectToRegister.setUsername(showedLogin.getPerson().getEMail());
		} else { // Normal self-registration
			objectToRegister = this.getLogin();
			objectToRegister.setPerson(this.getPerson());
			objectToRegister.setUsername(this.getPerson().getEMail());
		}

		try {
			// TODO password !
			if (creatingMode || userCenter.getPassword().equals(cPassword)) {
				// Setting the user's center
				if (userCenter == null
						|| userCenter.getId() == AbstractDomainObject.NOT_YET_SAVED_ID) {
					throw new RegistrationException(
							RegistrationException.CENTER_ERROR);
				}
				objectToRegister.getPerson().setCenter(userCenter);
				// Setting the registration date
				objectToRegister.setRegistrationDate(new GregorianCalendar());
				// System.out.println("S_ID:" + objectToRegister.getId());
				// System.out.println("S_VER " + objectToRegister.getVersion());
				loginDao.save(objectToRegister);
				// System.out.println("S_ID:" + objectToRegister.getId());
				// System.out.println("S_VER " + objectToRegister.getVersion());
				// Making the successPopup visible (NORMAL REGISTRATION)
				if (!creatingMode) {
					((RegisterPage) FacesContext.getCurrentInstance()
							.getApplication().getVariableResolver()
							.resolveVariable(FacesContext.getCurrentInstance(),
									"registerPage")).setRegPvisible(true);
				}
				// Making the popup visible (CREATING AN USER BY ANOTHER USER)
				if (creatingMode) {
					this.userSavedPVisible = true;
				}
				// Reseting the objects used for the registration process
				if (!creatingMode)
					this.cleanUp();
				// TODO Genereting & sending an Activation E-Mail
				return Randi2.SUCCESS;
			} else {
				throw new RegistrationException(
						RegistrationException.PASSWORD_ERROR);
			}

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
		// Saving the current login object, befor log out
		// TODO Problems trying to get the newst object
		if (this.login.getVersion() >= this.loginDao.get(this.login.getId())
				.getVersion())
			loginDao.save(this.login);
		// Cleaning up
		this.cleanUp();
		// Invalidating the session
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.invalidate();
		// TODO Closing the Hibernate Session
		return Randi2.SUCCESS;
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

	public void setUSEnglish(ActionEvent event) {
		this.login.setPrefLocale(Locale.US);
		this.setChosenLocale(Locale.US);
	}

	public void setDEGerman(ActionEvent event) {
		this.login.setPrefLocale(Locale.GERMANY);
		this.setChosenLocale(Locale.GERMANY);
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
			if (this.login.getPrefLocale() != null) {
				this.chosenLocale = this.login.getPrefLocale();
			}
		} else {
			this.chosenLocale = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestLocale();
			Iterator<Locale> supportedLocales = FacesContext
					.getCurrentInstance().getApplication()
					.getSupportedLocales();
			while (supportedLocales.hasNext()) {
				if (supportedLocales.next().equals(this.chosenLocale))
					// TODO Sysout
					System.out.println(this.chosenLocale.toString());
				return this.chosenLocale;
			}
			this.chosenLocale = FacesContext.getCurrentInstance()
					.getApplication().getDefaultLocale();
		}
		// TODO Sysout
		System.out.println(this.chosenLocale.toString());
		return this.chosenLocale;
	}

	/**
	 * Simple set method for the users locale.
	 * 
	 * @param chosenLocale
	 */
	public void setChosenLocale(Locale chosenLocale) {
		this.chosenLocale = chosenLocale;
	}

	public CenterDao getCenterDao() {
		return centerDao;
	}

	public void setCenterDao(CenterDao centerDao) {
		this.centerDao = centerDao;
	}

	public String getCPassword() {
		return cPassword;
	}

	public void setCPassword(String password) {
		cPassword = password;
	}

	public boolean isEditable() {
		// TODO Simple rightsmanagement
		if (showedLogin.equals(this.login)) {
			editable = true;
		} else {
			editable = creatingMode;
		}
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	// TODO I don't know, if it's the best idea ... so probably only temp.
	// solution
	public Login getShowedLogin() {
		if (this.showedLogin == null) {
			this.showedLogin = new Login();
			this.showedLogin.setPerson(new Person());
		}
		return this.showedLogin;
	}

	public void setShowedLogin(Login showedLogin) {
		// TODO At this point we must check the users rights!
		if (showedLogin == null) {
			this.creatingMode = true;
			if (this.showedLogin.getId() != AbstractDomainObject.NOT_YET_SAVED_ID) {
				this.showedLogin = new Login();
				this.showedLogin.setPerson(new Person());
			}
		} else {
			this.creatingMode = false;
			this.showedLogin = showedLogin;
		}

	}

	public boolean isCreatingMode() {
		return creatingMode;
	}

	public void setCreatingMode(boolean creatingMode) {
		this.creatingMode = creatingMode;
	}
}
