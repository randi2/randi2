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
package de.randi2.jsf.pages;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import de.randi2.jsf.Randi2;
import de.randi2.jsf.handlers.LoginHandler;
import de.randi2.jsf.handlers.TrialHandler;
import de.randi2.jsf.handlers.TrialSiteHandler;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;

/**
 * <p>
 * This class contains the logic of the randi2.jspx. The main view of the
 * application.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 * 
 */
public class Randi2Page {

	private TrialSiteHandler trialSiteHandler;

	private TrialHandler trialHandler;

	private LoginHandler loginHandler;

	/**
	 * The active content Panel.
	 */
	private String activePanel = "welcomePanel";

	/**
	 * The current logged in user.
	 */
	private Login currentUser = null;

	private boolean aboutVisible = false;

	private boolean helpVisible = false;

	public Randi2Page() {
		loginHandler = ((LoginHandler) FacesContext.getCurrentInstance()
				.getApplication().getELResolver().getValue(
						FacesContext.getCurrentInstance().getELContext(), null,
						"loginHandler"));
		trialSiteHandler = (TrialSiteHandler) FacesContext.getCurrentInstance()
				.getApplication().getELResolver().getValue(
						FacesContext.getCurrentInstance().getELContext(), null,
						"trialSiteHandler");

		trialHandler = (TrialHandler) FacesContext.getCurrentInstance()
				.getApplication().getELResolver().getValue(
						FacesContext.getCurrentInstance().getELContext(), null,
						"trialHandler");

		currentUser = loginHandler.getLoggedInUser();
	}

	public String getActivePanel() {
		return activePanel;
	}

	public void setActivePanel(String activePanel) {
		this.activePanel = activePanel;
	}

	public void viewTrials(ActionEvent event) {
		activePanel = "trialsViewPanel";
	}

	public void viewTrialSites(ActionEvent event) {
		activePanel = "trialSitesViewPanel";
	}

	public void myTrialSite(ActionEvent event) {
		trialSiteHandler
				.setShowedObject(currentUser.getPerson().getTrialSite());
		activePanel = "trialSiteEditPanel";
	}

	/**
	 * This method shows the trial site from the trial sites' table.
	 * 
	 * @param event
	 */
	public void showTrialSite(ActionEvent event) {
		TrialSite tTrialSite = (TrialSite) (((UIComponent) event.getComponent()
				.getChildren().get(0)).getValueExpression("value")
				.getValue(FacesContext.getCurrentInstance().getELContext()));
		trialSiteHandler.setShowedObject(tTrialSite);
		activePanel = "trialSiteEditPanel";
	}

	/**
	 * This method shows the trial site from the trial sites' table.
	 * 
	 * @param event
	 */
	public void showTrial(ActionEvent event) {
		Trial tTrial = (Trial) (((UIComponent) event.getComponent()
				.getChildren().get(0)).getValueExpression("value")
				.getValue(FacesContext.getCurrentInstance().getELContext()));
		trialHandler.setShowedObject(tTrial);
		activePanel = "trialShowPanel";
		
	}

	/**
	 * This method shows the user from the users' table.
	 * 
	 * @param event
	 */
	public void showUser(ActionEvent event) {
		Person tPerson = (Person) (((UIComponent) event.getComponent()
				.getChildren().get(0)).getValueExpression("value")
				.getValue(FacesContext.getCurrentInstance().getELContext()));
		assert (tPerson.getLogin() != null);
		loginHandler.setShowedObject(tPerson.getLogin());
		activePanel = "userEditPanel";
	}

	public void createTrialSite(ActionEvent event) {
		trialSiteHandler.setShowedObject(null);
		activePanel = "trialSiteEditPanel";
	}

	public void myLogin(ActionEvent event) {
		loginHandler.setShowedObject(currentUser);
		activePanel = "userEditPanel";
	}

	public void createLogin(ActionEvent event) {
		loginHandler.setShowedObject(null);
		activePanel = "userEditPanel";
	}

	public void createTrial(ActionEvent event) {
		activePanel = "trialCreatePanel";
	}

	public String showAbout() {
		this.aboutVisible = true;
		return Randi2.SUCCESS;
	}

	public String hideAbout() {
		this.aboutVisible = false;
		return Randi2.SUCCESS;
	}

	public String showHelp() {
		this.helpVisible = true;
		return Randi2.SUCCESS;
	}

	public String hideHelp() {
		this.helpVisible = false;
		return Randi2.SUCCESS;
	}

	public boolean isAboutVisible() {
		return aboutVisible;
	}

	public boolean isHelpVisible() {
		return helpVisible;
	}

}
