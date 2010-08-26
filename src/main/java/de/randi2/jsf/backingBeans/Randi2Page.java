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
package de.randi2.jsf.backingBeans;

import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.Highlight;

import lombok.Getter;
import lombok.Setter;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.jsf.controllerBeans.SimulationHandler;
import de.randi2.jsf.controllerBeans.TrialHandler;
import de.randi2.jsf.controllerBeans.TrialSiteHandler;
import de.randi2.jsf.supportBeans.Popups;
import de.randi2.jsf.supportBeans.Randi2;
import de.randi2.model.Person;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;

/**
 * <p>
 * This class contains the logic of the randi2.jspx. The main view of the
 * application.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 * 
 */
public class Randi2Page {

	@Setter
	private TrialSiteHandler trialSiteHandler;

	@Setter
	private TrialHandler trialHandler;
	
	@Setter
	private LoginHandler loginHandler;
	
	@Setter
	private SimulationHandler simulationHandler;
	
	/**
	 * The active content Panel.
	 */
	@Getter @Setter
	private String activePanel = "welcomePanel";

	
	@Getter @Setter
	private Popups popups;
	
	@Setter
	private Effect trialChangeEffect;
	
	@Getter @Setter
	private boolean trialSelected = false;
	
	public Effect getTrialChangeEffect() {
		if(trialChangeEffect==null){
			trialChangeEffect = new Highlight("#fda505");
			trialChangeEffect.setFired(true);
		}
		return trialChangeEffect;
	}
	
	/**
	 * Time Zone for all calendar widgets.
	 */
	private TimeZone zone;

	public TimeZone getZone() {
		if (zone == null) {
			zone = GregorianCalendar
					.getInstance(loginHandler.getChosenLocale()).getTimeZone();
		}
		return zone;
	}

	@Getter
	private boolean aboutVisible = false;

	@Getter
	private boolean helpVisible = false;

	public void viewTrials(ActionEvent event) {
		activePanel = "trialsViewPanel";
		trialHandler.cancelEditing();
	}

	public void viewTrialSites(ActionEvent event) {
		activePanel = "trialSitesViewPanel";
		trialHandler.cancelEditing();
	}

	public void myTrialSite(ActionEvent event) {
		trialSiteHandler
				.setCurrentObject(loginHandler.getLoggedInUser().getPerson().getTrialSite());
		activePanel = "trialSiteEditPanel";
		trialHandler.cancelEditing();
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
		trialSiteHandler.setCurrentObject(tTrialSite);
		activePanel = "trialSiteEditPanel";
		trialHandler.cancelEditing();
	}

	/**
	 * This method shows the trial from the trials' table.
	 * 
	 * @param event
	 */
	public void showTrial(ActionEvent event) {
		Trial tTrial = (Trial) (((UIComponent) event.getComponent()
				.getChildren().get(0)).getValueExpression("value")
				.getValue(FacesContext.getCurrentInstance().getELContext()));
		trialChangeEffect.setFired(false);
		trialHandler.setCurrentObject(tTrial);
		activePanel = "trialShowPanel";
		trialSelected = true;
	}
	
	/**
	 * This method enables the trial editing.
	 * 
	 * @param event
	 */
	public void editCurrentTrial(ActionEvent event) {
		if(trialSelected){
			trialHandler.setEditing(true);
			activePanel = "trialShowPanel";
		}
	}
	
	/**
	 * This method shows the given trial.
	 *
	 * @param _trial
	 */
	public void showTrial(Trial _trial){
		trialHandler.setCurrentObject(_trial);
		activePanel = "trialShowPanel";
		trialHandler.cancelEditing();
		trialSelected = true;
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
		loginHandler.setCurrentObject(tPerson.getLogin());
		activePanel = "userEditPanel";
		trialHandler.cancelEditing();
		trialSelected = false;
	}

	public void createTrialSite(ActionEvent event) {
		trialSiteHandler.setCurrentObject(null);
		activePanel = "trialSiteEditPanel";
		trialHandler.cancelEditing();
		trialSelected = false;
	}

	public void myLogin(ActionEvent event) {
		loginHandler.setCurrentObject(loginHandler.getLoggedInUser());
		activePanel = "userEditPanel";
		trialHandler.cancelEditing();
		trialSelected = false;
	}

	public void createLogin(ActionEvent event) {
		loginHandler.setCurrentObject(null);
		activePanel = "userEditPanel";
		trialHandler.cancelEditing();
		trialSelected = false;
	}

	public void createTrial(ActionEvent event) {
		trialHandler.cancelEditing();
		trialHandler.setCurrentObject(null);
		activePanel = "trialCreatePanel";
		trialSelected = false;
	}
	
	public void simulateTrial(ActionEvent event) {
		simulationHandler.setSimFromTrialCreationFirst(true);
		simulationHandler.setDistributedCriterions(null);
		simulationHandler.setSimOnly(false);
		popups.hideSimulationCompletePopup();
		activePanel = "simulationTrialPanel";
	}
	
	public void simulationResult(ActionEvent event){
		activePanel = "simulationResultPanel";
	}
	
	public void simulateTrialOnly(ActionEvent event) {
		simulationHandler.setCurrentObject(null);
		simulationHandler.setDistributedCriterions(null);
		simulationHandler.setSimOnly(true);
		simulationHandler.getSimTrial();
		simulationHandler.setSimulationResults(null);
		popups.hideSimulationCompletePopup();
		activePanel = "simulationOnlyPanel";
		trialHandler.cancelEditing();
	}
	
	public void simulateTrialOnlyChange(ActionEvent event) {
		popups.hideSimulationCompletePopup();
		simulationHandler.setSimulationResults(null);
		activePanel = "simulationOnlyPanel";
	}
	
	public void simulateTrialBack(ActionEvent event) {
		activePanel = "trialCreatePanel";
	}
	
	public void simulate(ActionEvent event){
		SimulationHandler simulationHandler = ((SimulationHandler) FacesContext.getCurrentInstance()
				.getApplication().getELResolver().getValue(
						FacesContext.getCurrentInstance()
								.getELContext(), null, "simulationHandler"));
		simulationHandler.simTrial();
	}
	
	public void showCurrentTrial(ActionEvent event){
		if(trialSelected){
			trialHandler.cancelEditing();
			activePanel = "trialShowPanel";
		}
	}

	public void addTrialSubject(ActionEvent event) {
		if (trialSelected) {
			((TSubjectAdd) FacesContext.getCurrentInstance().getApplication()
					.getELResolver().getValue(
							FacesContext.getCurrentInstance().getELContext(),
							null, "tSubjectAdd")).setCurrentTrial(trialHandler
					.getCurrentObject());
			activePanel = "tSubjectAddPanel";
		}
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
	

}
