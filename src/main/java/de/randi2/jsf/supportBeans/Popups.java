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
	
	public String showSubjectAddedPopup(){
		this.subjectAddedPVisible = true;
		return Randi2.SUCCESS;
	}
	// ----
}
