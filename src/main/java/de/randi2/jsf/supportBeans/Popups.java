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
package de.randi2.jsf.supportBeans;

import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;
import de.randi2.jsf.backingBeans.Randi2Page;
import de.randi2.jsf.backingBeans.TSubjectAdd;
import de.randi2.model.Trial;

public class Popups {
	
	// Popup's flags
	@Setter @Getter
	private boolean userSavedPVisible = false;
	@Setter @Getter
	private boolean changePasswordPVisible = false;
	@Setter @Getter
	private boolean changeTrialSitePVisible = false;
	@Setter @Getter
	private boolean trialCreatedPVisible = false;
	@Setter @Getter
	private boolean trialSiteSavedPVisible = false;
	@Setter @Getter
	private boolean subjectAddedPVisible = false;
	@Setter @Getter
	private boolean simulationCompletePVisible = false;
	@Setter @Getter
	private boolean changeLeadingSitePVisible = false;
	@Setter @Getter
	private boolean changePInvestigatorPVisible = false;
	
	
	// POPUPS
	public String hideTrialCreatedPopup() {
		this.trialCreatedPVisible = false;
		Randi2Page rPage = ((Randi2Page) FacesContext.getCurrentInstance()
				.getApplication().getELResolver().getValue(
						FacesContext.getCurrentInstance()
								.getELContext(), null, "randi2Page"));
		rPage.viewTrials(null);
		return Randi2.SUCCESS;
	}

	public String showTrialCreatedPopup() {
		this.trialCreatedPVisible= true;
		return Randi2.SUCCESS;
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


	public String hideChangeTrialSitePopup() {
		// Hide the changeTrialSitePopup
		this.changeTrialSitePVisible = false;
		return Randi2.SUCCESS;
	}

	public String hideTrialSiteSavedPopup() {
		this.trialSiteSavedPVisible = false;
		return Randi2.SUCCESS;
	}
	
	public String showTrialSiteSavedPopup(){
		this.trialSiteSavedPVisible = true;
		return Randi2.SUCCESS;
	}
	
	public String hideSubjectAddedPopup() {
		this.subjectAddedPVisible = false;
		TSubjectAdd tsa = ((TSubjectAdd) FacesContext.getCurrentInstance()
				.getApplication().getELResolver().getValue(
						FacesContext.getCurrentInstance()
								.getELContext(), null, "tSubjectAdd"));
		Trial trial = tsa.getCurrentTrial();
		Randi2Page rPage = ((Randi2Page) FacesContext.getCurrentInstance()
				.getApplication().getELResolver().getValue(
						FacesContext.getCurrentInstance()
								.getELContext(), null, "randi2Page"));
		rPage.showTrial(trial);
		return Randi2.SUCCESS;
	}
	
	public String showSimulationCompletePopup(){
		this.simulationCompletePVisible = true;
		return Randi2.SUCCESS;
	}
	
	
	public String hideChangeLeadingSitePopup(){
		this.changeLeadingSitePVisible = false;
		return Randi2.SUCCESS;
	}
	
	public String showChangeLeadingSitePopup(){
		this.changeLeadingSitePVisible = true;
		return Randi2.SUCCESS;
	}
	
	public String hideChangePInvestigatorPopup(){
		this.changePInvestigatorPVisible = false;
		return Randi2.SUCCESS;
	}
	
	public String showChangePInvestigatorPopup(){
		this.changePInvestigatorPVisible = true;
		return Randi2.SUCCESS;
	}
	
	public String hideSimulationCompletePopup(){
		this.simulationCompletePVisible = false;
		return Randi2.SUCCESS;
	}
	
	public String showSubjectAddedPopup(){
		this.subjectAddedPVisible = true;
		return Randi2.SUCCESS;
	}
	
	// ----
}
