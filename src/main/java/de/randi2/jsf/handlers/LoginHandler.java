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

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.springframework.security.context.HttpSessionContextIntegrationFilter;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;

import de.randi2.dao.LoginDao;
import de.randi2.dao.PersonDao;
import de.randi2.dao.TrialSiteDao;
import de.randi2.jsf.Randi2;
import de.randi2.jsf.exceptions.RegistrationException;
import de.randi2.jsf.pages.RegisterPage;
import de.randi2.jsf.utility.AutoCompleteObject;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.model.TrialSite;
import de.randi2.utility.mail.MailServiceInterface;
import de.randi2.utility.mail.exceptions.MailErrorException;
import org.apache.log4j.Logger;

/**
 * <p>
 * This class cares about the loggedInUser object, which represents the logged
 * in user and contains all methods needed for working with loggedInUser and
 * person objects.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 */
public class LoginHandler extends AbstractHandler<Login> {

	public LoginHandler() {
	}

	// This Object is representing the current User
	private Login loggedInUser = null;

	// The locale chosen by the user.
	private Locale chosenLocale = null;

	private AutoCompleteObject<TrialSite> trialSitesAC = null;
	private AutoCompleteObject<Person> tsMembersAC = null;
	private AutoCompleteObject<Role> rolesAC = null;

	// Objects for User-Creating Process
	private Login newUser = null;
	private String tsPassword = null;
	// ---

	// DB Access
	private LoginDao loginDao;
	private PersonDao personDao;
	// FIXME Rename + Autowired ?
	private TrialSiteDao centerDao;
	// ---

	// Popup's flags
	private boolean userSavedPVisible = false;
	private boolean changePasswordPVisible = false;
	private boolean changeTrialSitePVisible = false;
	private boolean changeAssistantPVisible = false;

	// NewUserMailService
	private MailServiceInterface mailService;

	// POPUPS
	public boolean isChangeTrialSitePVisible() {
		return changeTrialSitePVisible;
	}

	public void setChangeTrialSitePVisible(boolean changeTrialSitePVisible) {
		this.changeTrialSitePVisible = changeTrialSitePVisible;
	}

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

	public String showChangePasswordPopup() {
		// Show the changePasswordPopup
		this.changePasswordPVisible = true;
		return Randi2.SUCCESS;
	}

	public String hideChangePasswordPopup() {
		// Hide the changePasswordPopup
		this.changePasswordPVisible = false;
		return Randi2.SUCCESS;
	}

	public String showChangeTrialSitePopup() {
		// Show the changeTrialSitePopup
		this.changeTrialSitePVisible = true;
		return Randi2.SUCCESS;
	}

	public String showChangeAssistantPopup() {
		// Show the changeTrialSitePopup
		this.changeAssistantPVisible = true;
		return Randi2.SUCCESS;
	}

	public String hideChangeTrialSitePopup() {
		// Hide the changeTrialSitePopup
		this.changeTrialSitePVisible = false;
		return Randi2.SUCCESS;
	}

	public boolean isChangeAssistantPVisible() {
		return changeAssistantPVisible;
	}

	public void setChangeAssistantPVisible(boolean _changeAssistantPVisible) {
		changeAssistantPVisible = _changeAssistantPVisible;
	}

	public String hideChangeAssistantPopup() {
		// Hide the changeAssistantPopup
		this.changeAssistantPVisible = false;
		return Randi2.SUCCESS;
	}

	// ----

	// Application logic

	public void addRole(ActionEvent event) {
		assert (rolesAC.getSelectedObject() != null);
		showedObject.addRole(rolesAC.getSelectedObject());
	}

	public void removeRole(ActionEvent event) {
		Role tRole = (Role) (((UIComponent) event.getComponent().getChildren()
				.get(0)).getValueExpression("value").getValue(FacesContext
				.getCurrentInstance().getELContext()));
		showedObject.getRoles().remove(tRole);
	}

	public String changePassword() {
		this.saveObject();
		this.hideChangePasswordPopup();
		return Randi2.SUCCESS;

	}

	public String changeTrialSite() {
		assert (trialSitesAC.getSelectedObject() != null);
		showedObject.getPerson().setTrialSite(trialSitesAC.getSelectedObject());
		this.saveObject();
		this.hideChangeTrialSitePopup();
		return Randi2.SUCCESS;
	}

	public String changeAssistant() {
		assert (tsMembersAC.getSelectedObject() != null);
		showedObject.getPerson()
				.setAssistant((tsMembersAC.getSelectedObject()));
		this.saveObject();
		this.hideChangeAssistantPopup();
		return Randi2.SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.jsf.handlers.AbstractHandler#saveObject()
	 */
	@Override
	public String saveObject() {
		assert (showedObject != null);
		try {
			// personDao.save(showedObject.getPerson());
			loginDao.save(showedObject);
			// Making the pop up visible
			userSavedPVisible = true;
			return Randi2.SUCCESS;
		} catch (Exception exp) {
			Randi2.showMessage(exp);
			return Randi2.ERROR;
		} finally {
			// FIXME Refreshing objects
			// Updating the Object ...
			showedObject = loginDao.get(showedObject.getId());
			// If the current loggedInUser user was saved with this method, the
			// loggedInUser
			// object will be reload from the DB
			if (showedObject.equals(loggedInUser))
				loggedInUser = showedObject;
			refresh();
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
			if (creatingMode) {
				// A new user was created by another logged in user
				newUser = showedObject;

			} else {
				// Normal self-registration
				assert (newUser != null);
				newUser.addRole(Role.ROLE_INVESTIGATOR);

				try {
					if (!tsPassword.equals(trialSitesAC.getSelectedObject()
							.getPassword())) {
						throw new RegistrationException(
								RegistrationException.PASSWORD_ERROR);
					}
				} catch (NullPointerException exp1) {
					// No trial site selected
					throw new RegistrationException(
							RegistrationException.TRIAL_SITE_ERROR);
				}

			}
			newUser.setUsername(newUser.getPerson().getEMail());
			newUser.getPerson().setTrialSite(trialSitesAC.getSelectedObject());
			if (tsMembersAC.getSelectedObject() != null)
				newUser.getPerson().setAssistant(
						tsMembersAC.getSelectedObject());
			newUser.setCreatedAt(new GregorianCalendar());
			// personDao.save(newUser.getPerson());
			loginDao.save(newUser);

			// Making the successPopup visible (NORMAL REGISTRATION)
			if (!creatingMode) {
				((RegisterPage) FacesContext.getCurrentInstance()
						.getApplication().getELResolver().getValue(
								FacesContext.getCurrentInstance()
										.getELContext(), null, "registerPage"))
						.setRegPvisible(true);
				// Reseting the objects used for the registration process
				this.cleanUp();
			}

			// Making the popup visible (CREATING AN USER BY ANOTHER USER)
			if (creatingMode) {
				this.userSavedPVisible = true;
			}

			return sendRegistrationMails(newUser, chosenLocale, mailService);

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
	 * Sends the registration mails to the new user and the contact person of
	 * the current trial site.
	 * 
	 * @param newUser
	 *            The new user.
	 * @param chosenLocale
	 *            The chosen Locale of the new user.
	 * @param mailService
	 * @return
	 */
	public String sendRegistrationMails(Login newUser, Locale chosenLocale,
			MailServiceInterface mailService) {

		try {

			// sending the registration mail via MailService

			// Map of variables for the message
			Map<String, Object> newUserMessageFields = new HashMap<String, Object>();
			newUserMessageFields.put("user", newUser);
			newUserMessageFields.put("url", "http://randi2.com/CHANGEME");
			// Map of variables for the subject
			Map<String, Object> newUserSubjectFields = new HashMap<String, Object>();
			newUserSubjectFields.put("firstname", newUser.getPerson()
					.getFirstname());

			Locale language = chosenLocale;

			mailService.sendMail(newUser.getUsername(), "NewUserMail",
					language, newUserMessageFields, newUserSubjectFields);

			if (newUser.getPerson().getTrialSite().getContactPerson() != null) {

				// send e-mail to contact person of current trial-site

				Person contactPerson = newUser.getPerson().getTrialSite()
						.getContactPerson();
				language = contactPerson.getLogin().getPrefLocale();

				// Map of variables for the message
				Map<String, Object> contactPersonMessageFields = new HashMap<String, Object>();
				contactPersonMessageFields.put("newUser", newUser);
				contactPersonMessageFields.put("contactPerson", contactPerson);

				// Map of variables for the subject
				Map<String, Object> contactPersonSubjectFields = new HashMap<String, Object>();
				contactPersonSubjectFields.put("newUserFirstname", newUser
						.getPerson().getFirstname());
				contactPersonSubjectFields.put("newUserLastname", newUser
						.getPerson().getSurname());

				mailService.sendMail(contactPerson.getEMail(),
						"NewUserNotifyContactPersonMail", language,
						contactPersonMessageFields, contactPersonSubjectFields);

			}

			return Randi2.SUCCESS;

		} catch (MailErrorException exp) {

			// TODO remove stack trace
			exp.printStackTrace();
			Randi2.showMessage(exp);
			return Randi2.ERROR;

		}

	}

	/**
	 * This method saves the current loggedInUser-object and log it out.
	 * 
	 * @return Randi2.SUCCESS
	 */
	public String logoutUser() {
		// Saving the current loggedInUser object, befor log out
		// TODO Problems trying to get the newst object
		if (this.loggedInUser.getVersion() >= this.loginDao.get(
				this.loggedInUser.getId()).getVersion())
			loginDao.save(this.loggedInUser);
		// Cleaning up
		this.cleanUp();
		// Invalidating the session
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.invalidate();
		// TODO Closing the Hibernate Session
		return Randi2.SUCCESS;
	}

	/**
	 * This method clean all references within the LoginHandler-object.
	 */
	public void cleanUp() {
		tsPassword = null;
		newUser = null;
		trialSitesAC = null;
		tsMembersAC = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.jsf.handlers.AbstractHandler#refreshShowedObject()
	 */
	@Override
	public String refreshShowedObject() {
		if (showedObject.getId() == AbstractDomainObject.NOT_YET_SAVED_ID)
			showedObject = null;
		else
			showedObject = loginDao.get(showedObject.getId());
		trialSitesAC = null;
		tsMembersAC = null;
		refresh();
		return Randi2.SUCCESS;
	}

	public void setUSEnglish(ActionEvent event) {
		this.loggedInUser.setPrefLocale(Locale.US);
		this.setChosenLocale(Locale.US);
	}

	public void setDEGerman(ActionEvent event) {
		this.loggedInUser.setPrefLocale(Locale.GERMANY);
		this.setChosenLocale(Locale.GERMANY);
	}

	// GET & SET Methods
	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	public void setLoggedInUser(Login user) {
		this.loggedInUser = user;
	}

	public Login getLoggedInUser() {
		if (loggedInUser == null) {
			try {
				loggedInUser = (Login) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				loggedInUser.setLastLoggedIn(new GregorianCalendar());
			} catch (NullPointerException exp) {
				// FIXME What should we do at this point?
				Logger.getLogger(this.getClass()).debug("NPE", exp);
			}
		}
		return this.loggedInUser;
	}

	public LoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	/**
	 * This method provide the locale chosen by the logged user. If the user
	 * didn't choose anyone, but his standard browser-locale is supported, then
	 * it will be provided. Otherwise the applications default locale will be
	 * used.
	 * 
	 * @return locale for the loged in user
	 */
	public Locale getChosenLocale() {
		if (this.loggedInUser != null) {
			if (this.loggedInUser.getPrefLocale() != null) {
				this.chosenLocale = this.loggedInUser.getPrefLocale();
			}
		} else {
			this.chosenLocale = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestLocale();
			Iterator<Locale> supportedLocales = FacesContext
					.getCurrentInstance().getApplication()
					.getSupportedLocales();
			while (supportedLocales.hasNext()) {
				if (supportedLocales.next().equals(this.chosenLocale))
					return this.chosenLocale;
			}
			this.chosenLocale = FacesContext.getCurrentInstance()
					.getApplication().getDefaultLocale();
		}
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

	public TrialSiteDao getCenterDao() {
		return centerDao;
	}

	public void setCenterDao(TrialSiteDao centerDao) {
		this.centerDao = centerDao;
	}

	public String getTsPassword() {
		return tsPassword;
	}

	public void setTsPassword(String password) {
		tsPassword = password;
	}

	public boolean isEditable() {
		// FIXME Rightsmanagement
		if (showedObject.equals(this.loggedInUser)) {
			editable = true;
		} else {
			editable = creatingMode;
		}
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * This method provides a Login object for the registration process.
	 * 
	 * @return A Login object, which represents the new user.
	 */
	public Login getNewUser() {
		if (newUser == null) { // Starting the registration process
			newUser = new Login();
			newUser.setPerson(new Person());
			newUser.addRole(Role.ROLE_ANONYMOUS);
			AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
					"anonymousUser", newUser, newUser.getAuthorities());
			// Perform authentication
			SecurityContextHolder.getContext().setAuthentication(authToken);
			SecurityContextHolder.getContext().getAuthentication()
					.setAuthenticated(true);

			// Put the context in the session
			((HttpServletRequest) FacesContext.getCurrentInstance()
					.getExternalContext().getRequest())
					.getSession()
					.setAttribute(
							HttpSessionContextIntegrationFilter.SPRING_SECURITY_CONTEXT_KEY,
							SecurityContextHolder.getContext());
		}
		return newUser;
	}

	public AutoCompleteObject<TrialSite> getTrialSitesAC() {
		if (trialSitesAC == null)
			trialSitesAC = new AutoCompleteObject<TrialSite>(centerDao);
		return trialSitesAC;
	}

	public AutoCompleteObject<Person> getTsMembersAC() {
		if (tsMembersAC == null) {
			assert (trialSitesAC != null);
			if (trialSitesAC.getSelectedObject() != null)
				tsMembersAC = new AutoCompleteObject<Person>(trialSitesAC
						.getSelectedObject().getMembers());
			else //FIXME - from time to time we've got a strange behavioure here ...
				tsMembersAC = new AutoCompleteObject<Person>(showedObject
						.getPerson().getTrialSite().getMembers());
		}
		return tsMembersAC;
	}

	public AutoCompleteObject<Role> getRolesAC() {
		// FIXME TEMP SOLUTION - switch to loginDAO.getPossibleRoles(true) when
		// ready
		ArrayList<Role> roles = new ArrayList<Role>();
		roles.add(Role.ROLE_INVESTIGATOR);
		roles.add(Role.ROLE_P_INVESTIGATOR);
		roles.add(Role.ROLE_STATISTICAN);
		roles.add(Role.ROLE_MONITOR);
		roles.add(Role.ROLE_ADMIN);
		// ---
		if (rolesAC == null)
			rolesAC = new AutoCompleteObject<Role>(roles);
		return rolesAC;
	}

	public MailServiceInterface getNewUserMailService() {
		return mailService;
	}

	public void setNewUserMailService(MailServiceInterface newUserMailService) {
		this.mailService = newUserMailService;
	}

	// ---

	@Override
	protected Login createPlainObject() {
		Login l = new Login();
		l.setPerson(new Person());
		return l;
	}
}
