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

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;
import de.randi2.jsf.backingBeans.AlgorithmConfig;
import de.randi2.jsf.backingBeans.SubjectPropertiesConfig;
import de.randi2.jsf.converters.LoginConverter;
import de.randi2.jsf.supportBeans.Randi2;
import de.randi2.model.Login;
import de.randi2.model.Role;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.TrialSubject;
import de.randi2.model.enumerations.TrialStatus;
import de.randi2.model.exceptions.TrialStateException;
import de.randi2.services.TrialService;
import de.randi2.services.TrialSiteService;
import de.randi2.utility.logging.LogEntry;
import de.randi2.utility.logging.LogService;

/**
 * <p>
 * This class cares about the newTrial object and contains all the needed
 * methods to work with this object for the UI.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net> & ds@randi2.de
 */
@ManagedBean(name = "trialHandler")
@SessionScoped
public class TrialHandler extends AbstractTrialHandler {

	/*
	 * Services which this class needs to work with. (provided via spring)
	 */
	@ManagedProperty(value = "#{trialSiteService}")
	@Setter
	private TrialSiteService siteService;
	@ManagedProperty(value = "#{trialService}")
	@Setter
	private TrialService trialService;
	@ManagedProperty(value = "#{logService}")
	@Setter
	private LogService logService;

	@Setter
	/**
	 * Defindes if the randomization is possible or not.
	 */
	private boolean addingSubjectsEnabled = false;

	/**
	 * Defines if the current trial is currently edited or not.
	 */
	@Setter
	@Getter
	private boolean editing = false;

	@Getter
	@Setter
	private TrialSite leadingSite;
	@Getter
	@Setter
	private Login sponsorInvestigator;

	public List<SelectItem> getSponsorInvestigators() {
		ArrayList<SelectItem> list = new ArrayList<SelectItem>();
		if (leadingSite == null && currentObject.getLeadingSite()==null)
			return list;
		else if(leadingSite == null && currentObject.getLeadingSite()!=null)
			leadingSite = currentObject.getLeadingSite();
			leadingSite.setMembers(siteService.getMembers(leadingSite));
		for (Login l : leadingSite
				.getMembersWithSpecifiedRole(Role.ROLE_P_INVESTIGATOR))
			list.add(LoginConverter.getAsSelectItem(l));
		return list;
	}
	
	@Getter @Setter
	private TrialSite selectedTrialSite;


	/*
	 * GET & SET methods
	 */

	/**
	 * Specifies if the user can add subject to the current study.
	 * 
	 * @return
	 */
	public boolean isAddingSubjectsEnabled() {
		// TODO Inject the trial state logic
		addingSubjectsEnabled = !creatingMode && currentObject != null;
		return addingSubjectsEnabled;
	}

	public boolean isEditable() {
		// TODO Check if it's possible do declare LoginHandler as a member of
		// this bean with JSF2.0
		Login currentUser = loginHandler.getLoggedInUser();
		/*
		 * Checking if the current user is an principal investigator and if he
		 * is defined as the principal investigator of the study. Last part
		 * checks if the trial state enables any checks.
		 */
		return currentUser.hasRole(Role.ROLE_P_INVESTIGATOR)
				&& currentUser.getPerson().equals(
						currentObject.getSponsorInvestigator())
				&& currentObject.getStatus() != TrialStatus.FINISHED;
	}

	/**
	 * Provides the l16ed "tial state" select items.
	 * 
	 * @return
	 */
	public List<SelectItem> getStateItems() {
		List<SelectItem> stateItems = new ArrayList<SelectItem>(
				TrialStatus.values().length);
		ResourceBundle tempRB = ResourceBundle
				.getBundle("de.randi2.jsf.i18n.trialState",
						loginHandler.getChosenLocale());
		for (TrialStatus s : TrialStatus.values()) {
			stateItems.add(new SelectItem(s, tempRB.getString(s.toString())));
		}
		return stateItems;
	}

	/**
	 * Action method for adding a participating study.
	 * 
	 * @param event
	 */
	public void addTrialSite(ActionEvent event) {
		if (selectedTrialSite != null) {
			currentObject.getParticipatingSites().add(selectedTrialSite);
		}
	}

	/**
	 * Action method for removing a participating study.
	 * 
	 * @param event
	 */
	public void removeTrialSite(ActionEvent event) {
		/*
		 * The trial site object is determined by retrieving the selected row
		 * from the participating sites' table.
		 */
		TrialSite tTrialSite = (TrialSite) (((UIComponent) event.getComponent()
				.getChildren().get(0)).getValueExpression("value")
				.getValue(FacesContext.getCurrentInstance().getELContext()));
		currentObject.getParticipatingSites().remove(tTrialSite);

	}

	/**
	 * Action method for the trial creation.
	 * 
	 * @return
	 */
	public String createTrial() {
		try {
			/* Leading Trial Site & Sponsor Investigator */
			currentObject.setLeadingSite(leadingSite);
			if (sponsorInvestigator != null)
				currentObject.setSponsorInvestigator(sponsorInvestigator.getPerson());

			// TODO Protokoll
			// TODO Status
			currentObject.setStatus(TrialStatus.ACTIVE);
			// configure subject properties
			currentObject.setCriteria(configureCriteriaStep4());
			// create trial
			trialService.create(currentObject);

			getPopups().showTrialCreatedPopup();

			clean();

			return Randi2.SUCCESS;
		} catch (Exception e) {
			Randi2.showMessage(e);
			return Randi2.ERROR;
		}
	}

	/**
	 * Action method which saves the current trial
	 * 
	 * @return Either {@link Randi2#ERROR} or {@link Randi2#SUCCESS}
	 */
	public String saveTrial() {
		try {
			currentObject = trialService.update(currentObject);
			getPopups().showTrialCreatedPopup();
			editing = false;
		} catch (IllegalArgumentException e) {
			Randi2.showMessage(e);
			cancelEditing();
			return Randi2.ERROR;
		} catch (TrialStateException e) {
			Randi2.showMessage(e);
			cancelEditing();
			return Randi2.ERROR;
		}
		return Randi2.SUCCESS;
	}

	public String changeLeadingSite() {
		currentObject.setLeadingSite(leadingSite);
		sponsorInvestigator = null;
		getPopups().hideChangeLeadingSitePopup();
		return Randi2.SUCCESS;
	}

	public String changePInvestigator() {
		currentObject.setSponsorInvestigator(sponsorInvestigator.getPerson());
		getPopups().hideChangePInvestigatorPopup();
		return Randi2.SUCCESS;
	}

	public String cancelEditing() {
		if (editing) {
			currentObject = trialService.getObject(currentObject.getId());
			editing = false;
		}
		return Randi2.SUCCESS;
	}

	public String startEditing() {
		editing = true;
		return Randi2.SUCCESS;
	}

	/**
	 * Returns the amount of stored trials which can be accessed by the current
	 * user.
	 * 
	 * @return
	 */
	public int getTrialsAmount() {
		return trialService.getAll().size();
	}

	/**
	 * Returns all trials which can be accessed by the current user.
	 * 
	 * @return
	 */
	public List<Trial> getAllTrials() {
		return trialService.getAll();
	}

	/**
	 * Provieds the audit log entries for the current trial object.
	 * 
	 * @return
	 */
	public List<LogEntry> getLogEntries() {
		return logService.getLogEntries(currentObject.getClass(),
				currentObject.getId());
	}

	public List<TrialSubject> getSubjectsList() {
		if (currentObject != null) {
			return trialService.getSubjects(currentObject,
					loginHandler.getLoggedInUser());
		}
		return null;
	}

	@Override
	public void setCurrentObject(Trial _currentObject) {
		if (_currentObject != null && _currentObject.getId() > 0)
			_currentObject = trialService.getObject(_currentObject.getId());
		super.setCurrentObject(_currentObject);
	}

	private void clean() {
		ValueExpression ve1 = FacesContext
				.getCurrentInstance()
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{subjectPropertiesConfig}",
						SubjectPropertiesConfig.class);
		SubjectPropertiesConfig currentStep4 = (SubjectPropertiesConfig) ve1
				.getValue(FacesContext.getCurrentInstance().getELContext());
		currentStep4.clean();

		ValueExpression ve2 = FacesContext
				.getCurrentInstance()
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{algorithmConfig}", AlgorithmConfig.class);
		AlgorithmConfig currentStep5 = (AlgorithmConfig) ve2
				.getValue(FacesContext.getCurrentInstance().getELContext());
		currentStep5.clean();

		// setRandomizationConfig(null);
		currentObject = null;
		leadingSite = null;
		sponsorInvestigator = null;
	}

}
