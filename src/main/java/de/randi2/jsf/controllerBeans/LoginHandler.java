/* 
 * (c) 2008- RANDI2 Core Development Team
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
package de.randi2.jsf.controllerBeans;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import lombok.Getter;
import lombok.Setter;

import org.apache.log4j.Logger;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.springframework.security.core.context.SecurityContextHolder;

import de.randi2.jsf.backingBeans.RegisterPage;
import de.randi2.jsf.exceptions.RegistrationException;
import de.randi2.jsf.supportBeans.PermissionVerifier;
import de.randi2.jsf.supportBeans.Popups;
import de.randi2.jsf.supportBeans.Randi2;
import de.randi2.jsf.utility.AutoCompleteObject;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.model.TrialSite;
import de.randi2.services.TrialSiteService;
import de.randi2.services.UserService;
import de.randi2.utility.logging.LogEntry;
import de.randi2.utility.logging.LogEntry.ActionType;
import de.randi2.utility.logging.LogService;

/**
 * <p>
 * This class takes care of login objects and contains all UI-specific methods
 * needed for working with login and person objects.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 */
@ManagedBean(name="loginHandler")
@SessionScoped
public class LoginHandler extends AbstractHandler<Login> {

	/*
	 * Services classes to work with - provided via JSF/Spring brige.
	 */
	@ManagedProperty(value="#{userService}")
	@Setter
	private UserService userService;

	@ManagedProperty(value="#{logService}")
	@Setter
	private LogService logService;

	@ManagedProperty(value="#{trialSiteService}")
	@Setter
	private TrialSiteService siteService;

	/*
	 * Reference to the application popup logic.
	 */
	@ManagedProperty(value="#{popups}")
	@Setter
	private Popups popups;

	/*
	 * Current signed in user.
	 */

	@Setter
	private Login loggedInUser = null;

	/*
	 * Current (chosen) locale of the UI.
	 */

	@Setter
	private Locale chosenLocale = null;

	/*
	 * newUser - object for the self registration process tsPasswort - trail
	 * site password for the self registration process
	 */
	private Login newUser = null;
	@Getter
	@Setter
	private String tsPassword = null;

	/*
	 * The autocomplete objects used during the registrations process
	 * (trialSitesAC - with all stored trial sites) and rolesAC for the user
	 * creation and editing process with all defined roles.
	 */

	private AutoCompleteObject<TrialSite> trialSitesAC = null;
	private AutoCompleteObject<Role> rolesAC = null;

	/*
	 * UI logic
	 */

	/**
	 * ActionListener for the "add role" event.
	 * 
	 * @param event
	 */
	public void addRole(ActionEvent event) {
		assert (rolesAC.getSelectedObject() != null);
		assert (userService != null);
		userService.addRole(currentObject, rolesAC.getSelectedObject());
	}

	/**
	 * ActionListener for the "remove role" event.
	 * 
	 * @param event
	 */
	public void removeRole(ActionEvent event) {
		assert (userService != null);
		Role tRole = (Role) (((UIComponent) event.getComponent().getChildren()
				.get(0)).getValueExpression("value").getValue(FacesContext
				.getCurrentInstance().getELContext()));
		userService.removeRole(currentObject, tRole);
	}

	/**
	 * Action Method for the "change password" action.
	 * 
	 * @return
	 */
	public String changePassword() {
		this.saveObject();
		popups.hideChangePasswordPopup();
		return Randi2.SUCCESS;
	}

	/**
	 * Action Method for the "change trial site" action.
	 * 
	 * @return
	 */
	public String changeTrialSite() {
		if (trialSitesAC.getSelectedObject() != null) {
			currentObject.getPerson().setTrialSite(
					trialSitesAC.getSelectedObject());
			popups.hideChangeTrialSitePopup();
			this.saveObject();
			return Randi2.SUCCESS;
		}
		return Randi2.ERROR;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.jsf.handlers.AbstractHandler#saveObject()
	 */
	@Override
	public String saveObject() {
		assert (currentObject != null);
		try {
			currentObject = userService.update(currentObject);
			// Making the pop up visible
			popups.setUserSavedPVisible(true);
			return Randi2.SUCCESS;
		} catch (Exception exp) {
			Randi2.showMessage(exp);
			return Randi2.ERROR;
		} finally {
			if (currentObject.getId() == loggedInUser.getId())
				loggedInUser = currentObject;
			refresh();
		}
	}

	/**
	 * This method is responsible for the users registration.
	 * 
	 * @return Randi2.SUCCESS normally. Randi2.ERROR in case of an error.
	 */
	public String registerUser() {

		/*
		 * TODO We could try to move the newUser and tsPassword object as well
		 * as a part of this functionality into the RegisterPage bean.
		 */

		try {
			if (creatingMode) {
				// A new user was created by another logged in user
				newUser = currentObject;
			} else {
				// Normal self-registration - trial site password check!
				assert (newUser != null);
				try {
					if (!siteService.authorize(
							trialSitesAC.getSelectedObject(), tsPassword)) {
						throw new RegistrationException(
								RegistrationException.PASSWORD_ERROR);
					}
				} catch (NullPointerException exp1) {
					// No trial site selected
					throw new RegistrationException(
							RegistrationException.TRIAL_SITE_ERROR);
				}
			}
			/* Setting the data in the new object */
			newUser.setPrefLocale(getChosenLocale());
			newUser.setUsername(newUser.getPerson().getEmail());
			newUser.getPerson().setTrialSite(trialSitesAC.getSelectedObject());
			/* The new object will be saved */
			userService.register(newUser);
			// Making the successPopup visible (NORMAL REGISTRATION)
			if (!creatingMode) {
				((RegisterPage) FacesContext
						.getCurrentInstance()
						.getApplication()
						.getELResolver()
						.getValue(
								FacesContext.getCurrentInstance()
										.getELContext(), null, "registerPage"))
						.setRegPvisible(true);
				// Reseting the objects used for the registration process
				this.cleanUp();
				// Invalidate Reg-Session
				invalidateSession();
			}
			// Making the popup visible (CREATING AN USER BY ANOTHER USER)
			if (creatingMode) {
				popups.setUserSavedPVisible(true);
			}
			return Randi2.SUCCESS;
		} catch (InvalidStateException exp) {
			for (InvalidValue v : exp.getInvalidValues()) {
				Randi2.showMessage(v.getPropertyName() + " : " + v.getMessage());
			}
			return Randi2.ERROR;
		} catch (Exception e) {
			Randi2.showMessage(e);
			return Randi2.ERROR;
		}

	}

	/**
	 * This method saves the current loggedInUser-object and log it out.
	 * 
	 * @return Randi2.SUCCESS
	 */
	public String logoutUser() {
		loggedInUser = userService.update(loggedInUser);
		invalidateSession();
		logService.logChange(ActionType.LOGOUT, loggedInUser.getUsername(),
				loggedInUser);
		return Randi2.SUCCESS;
	}

	/**
	 * This method clean all references within the LoginHandler-object.
	 */
	public void cleanUp() {
		tsPassword = null;
		newUser = null;
		trialSitesAC = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.jsf.handlers.AbstractHandler#refreshShowedObject()
	 */
	@Override
	public String refreshShowedObject() {
		if (currentObject.getId() == AbstractDomainObject.NOT_YET_SAVED_ID)
			currentObject = null;
		else
			currentObject = userService.getObject(currentObject.getId());
		trialSitesAC = null;
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

	/*
	 * GET & SET Methods
	 */

	/**
	 * Provides the current logged in user.
	 * 
	 * @return
	 */
	public Login getLoggedInUser() {
		if (loggedInUser == null) {
			try {
				loggedInUser = (Login) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				/*
				 * Reloading the user from the database to ensure that the
				 * object is attached to the correct session
				 */
				loggedInUser = userService.getObject(loggedInUser.getId());
				loggedInUser.setLastLoggedIn(new GregorianCalendar());
			} catch (NullPointerException exp) {
				Logger.getLogger(this.getClass()).debug("NPE", exp);
			}
		}
		return this.loggedInUser;
	}

	/**
	 * This method provide the locale chosen by the logged user. If the user
	 * didn't choose anyone, but his standard browser-locale is supported, then
	 * it will be provided. Otherwise the applications default locale will be
	 * used. setTri
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
	 * This method specifies if the object that is currently showed in the UI,
	 * is editable or not.
	 * 
	 * @return
	 */
	public boolean isEditable() {
		PermissionVerifier permissionVerifier = ((PermissionVerifier) FacesContext
				.getCurrentInstance()
				.getApplication()
				.getELResolver()
				.getValue(FacesContext.getCurrentInstance().getELContext(),
						null, "permissionVerifier"));
		if (currentObject.equals(this.loggedInUser)
				|| permissionVerifier.isAllowedEditUser(currentObject)) {
			editable = true;
		} else {
			editable = creatingMode;
		}
		return editable;
	}

	/**
	 * This method provides a Login object for the registration process.
	 * 
	 * @return A Login object, which represents the new user.
	 */
	public Login getNewUser() {
		if (newUser == null) { // Starting the registration process
			assert (userService != null);
			newUser = userService.prepareInvestigator();
		}
		return newUser;
	}

	/**
	 * Returns the trial sites auto complete object (object is a singleton).
	 * 
	 * @return
	 */
	public AutoCompleteObject<TrialSite> getTrialSitesAC() {
		if (trialSitesAC == null)
			trialSitesAC = new AutoCompleteObject<TrialSite>(siteService);
		return trialSitesAC;
	}

	/**
	 * Returns the user roles auto complete object (object is a singleton).
	 * 
	 * @return
	 */
	public AutoCompleteObject<Role> getRolesAC() {
		if (rolesAC == null) {
			rolesAC = new AutoCompleteObject<Role>(userService.getAllRoles());
			ResourceBundle rb = ResourceBundle.getBundle(
					"de.randi2.jsf.i18n.roles", this.getChosenLocale());
			for (SelectItem si : rolesAC.getObjectList()) {
				si.setLabel(rb.getString(si.getLabel()));
			}
		}
		return rolesAC;
	}

	/**
	 * Use this method to invalidate the current HTTPSession and show the
	 * loginPage
	 */
	public void invalidateSession() {
		final HttpSession session = (HttpSession) FacesContext
				.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute(Randi2.RANDI2_END, "The end");
		try {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							FacesContext.getCurrentInstance()
									.getExternalContext()
									.getRequestContextPath()
									+ Randi2.SECURE_LOGOUT_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method for initial assistant creation
	 * 
	 * @param event
	 */
	public void createAssistant(ActionEvent event) {
		if (currentObject != null)
			currentObject.getPerson().setAssistant(new Person());
	}

	/**
	 * Provides the audit log entries for the showed object.
	 * 
	 * @return
	 */
	public List<LogEntry> getLogEntries() {
		return logService.getLogEntries(currentObject.getUsername());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.jsf.controllerBeans.AbstractHandler#createPlainObject()
	 */
	@Override
	protected Login createPlainObject() {
		Login l = new Login();
		l.setPerson(new Person());
		return l;
	}
}