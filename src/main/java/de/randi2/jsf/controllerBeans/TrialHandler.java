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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.springframework.security.core.context.SecurityContextHolder;

import lombok.Getter;
import lombok.Setter;

import com.icesoft.faces.context.ByteArrayResource;
import com.icesoft.faces.context.FileResource;
import com.icesoft.faces.context.Resource;

import de.randi2.jsf.backingBeans.Step4;
import de.randi2.jsf.backingBeans.Step5;
import de.randi2.jsf.supportBeans.Randi2;
import de.randi2.jsf.utility.AutoCompleteObject;
import de.randi2.model.Login;
import de.randi2.model.Role;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.TrialSubject;
import de.randi2.model.enumerations.TrialStatus;
import de.randi2.services.TrialService;
import de.randi2.services.TrialSiteService;
import de.randi2.utility.BoxedException;
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
public class TrialHandler extends AbstractTrialHandler {

	/*
	 * Services which this class needs to work with. (provided via spring)
	 */

	@Setter
	private TrialSiteService siteService = null;

	@Setter
	private TrialService trialService = null;

	@Setter
	private LogService logService;

	/**
	 * Defindes if the randomization is possible or not.
	 */
	private boolean addingSubjectsEnabled = false;

	/**
	 * Defines if the current trial is currently edited or not.
	 */
	@Setter @Getter
	private boolean editing = false;

	/*
	 * Auto complete objects for the trial creation process.
	 */
	private AutoCompleteObject<TrialSite> trialSitesAC = null;
	private AutoCompleteObject<Login> sponsorInvestigatorsAC = null;
	private AutoCompleteObject<TrialSite> participatingSitesAC = null;

	/*
	 * GET & SET methods
	 */

	/**
	 * Returns an autocomplete object with all stored trial sites (singleton)
	 * 
	 * @return
	 */
	public AutoCompleteObject<TrialSite> getTrialSitesAC() {
		if (trialSitesAC == null)
			trialSitesAC = new AutoCompleteObject<TrialSite>(siteService);
		return trialSitesAC;

	}

	/**
	 * Returns the principal investigators of the selected leading trial site.
	 * 
	 * @return
	 */
	public AutoCompleteObject<Login> getSponsorInvestigatorsAC() {
		if (sponsorInvestigatorsAC != null && trialSitesAC.isObjectSelected())
			return sponsorInvestigatorsAC;
		if (trialSitesAC.isObjectSelected())
			sponsorInvestigatorsAC = new AutoCompleteObject<Login>(trialSitesAC
					.getSelectedObject().getMembersWithSpecifiedRole(
							Role.ROLE_P_INVESTIGATOR));
		else
			sponsorInvestigatorsAC = new AutoCompleteObject<Login>(
					new ArrayList<Login>());
		return sponsorInvestigatorsAC;
	}

	/**
	 * Return the autocomplete object with possible participating sites.
	 * 
	 * @return
	 */
	public AutoCompleteObject<TrialSite> getParticipatingSitesAC() {
		if (participatingSitesAC == null)
			participatingSitesAC = new AutoCompleteObject<TrialSite>(
					siteService);
		return participatingSitesAC;
	}

	// FIXME dummy method for the protocol download
	public Resource getTempProtocol() {
		if (showedObject != null && showedObject.getProtocol() != null)
			return new FileResource(showedObject.getProtocol());
		else
			try {
				return new ByteArrayResource(toByteArray(FacesContext
						.getCurrentInstance().getExternalContext()
						.getResourceAsStream("/protocol.pdf")));
			} catch (MalformedURLException e) {
				BoxedException.throwBoxed(e);
			} catch (IOException e) {
				BoxedException.throwBoxed(e);
			}
		return null;
	}

	// TODO Probably not the best place for this method ... after the decision
	// about the protocol files has been made, rethink this solution
	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buf = new byte[4096];
		int len = 0;
		while ((len = input.read(buf)) > -1)
			output.write(buf, 0, len);
		return output.toByteArray();
	}

	/**
	 * Specifies if the user can add subject to the current study.
	 * 
	 * @return
	 */
	public boolean isAddingSubjectsEnabled() {
		// TODO Inject the trial state logic
		addingSubjectsEnabled = !creatingMode && showedObject != null;
		return addingSubjectsEnabled;
	}

	public boolean isEditable() {
		Login currentUser = ((Login) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal());
		/*
		 * Checking if the current user is an principal investigator and if he
		 * is defined as the principal investigator of the study. Last part
		 * checks if the trial state enables any checks.
		 */
		return currentUser.hasRole(Role.ROLE_P_INVESTIGATOR)
				&& currentUser.getPerson().equals(
						showedObject.getSponsorInvestigator())
				&& showedObject.getStatus() != TrialStatus.FINISHED;
	}

	/**
	 * Provides the l16ed "tial state" select items.
	 * 
	 * @return
	 */
	public List<SelectItem> getStateItems() {
		List<SelectItem> stateItems = new ArrayList<SelectItem>(
				TrialStatus.values().length);
		ResourceBundle tempRB = ResourceBundle.getBundle(
				"de.randi2.jsf.i18n.trialState",
				((LoginHandler) FacesContext
						.getCurrentInstance()
						.getApplication()
						.getELResolver()
						.getValue(
								FacesContext.getCurrentInstance()
										.getELContext(), null, "loginHandler"))
						.getChosenLocale());
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
		if (participatingSitesAC.getSelectedObject() != null) {
			showedObject.getParticipatingSites().add(
					participatingSitesAC.getSelectedObject());
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
		showedObject.getParticipatingSites().remove(tTrialSite);

	}

	/**
	 * Action method for the trial creation.
	 * 
	 * @return
	 */
	public String createTrial() {
		try {
			/* Leading Trial Site & Sponsor Investigator */
			showedObject.setLeadingSite(trialSitesAC.getSelectedObject());
			if (sponsorInvestigatorsAC.getSelectedObject() != null)
				showedObject.setSponsorInvestigator(sponsorInvestigatorsAC
						.getSelectedObject().getPerson());

			// TODO Protokoll
			// TODO Status
			showedObject.setStatus(TrialStatus.ACTIVE);
			// configure subject properties
			showedObject.setCriteria(configureCriteriaStep4());
			// configure algorithm
			configureAlgorithmWithStep5();

			// create trial
			trialService.create(showedObject);

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
		System.out.println("Called");
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
		return logService.getLogEntries(showedObject.getClass(),
				showedObject.getId());
	}

	public List<TrialSubject> getSubjectsList() {
		if (showedObject != null) {
			return trialService.getSubjects(showedObject, getLoginHandler()
					.getLoggedInUser());
		}
		return null;
	}

	private void clean() {
		ValueExpression ve1 = FacesContext
				.getCurrentInstance()
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{step4}", Step4.class);
		Step4 currentStep4 = (Step4) ve1.getValue(FacesContext
				.getCurrentInstance().getELContext());
		currentStep4.clean();

		ValueExpression ve2 = FacesContext
				.getCurrentInstance()
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{step5}", Step5.class);
		Step5 currentStep5 = (Step5) ve2.getValue(FacesContext
				.getCurrentInstance().getELContext());
		currentStep5.clean();

		setRandomizationConfig(null);
		trialSitesAC = null;
		sponsorInvestigatorsAC = null;
	}

}
