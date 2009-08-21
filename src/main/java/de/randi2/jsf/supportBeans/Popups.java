package de.randi2.jsf.supportBeans;

import javax.faces.context.FacesContext;

import de.randi2.jsf.backingBeans.Randi2Page;
import de.randi2.jsf.backingBeans.TSubjectAdd;
import de.randi2.model.Trial;

public class Popups {
	
	// Popup's flags
	private boolean userSavedPVisible = false;
	private boolean changePasswordPVisible = false;
	private boolean changeTrialSitePVisible = false;
	private boolean trialCreatedPVisible = false;
	private boolean trialSiteSavedPVisible = false;
	private boolean subjectAddedPVisible = false;
	
	// POPUPS
	public boolean isTrialCreatedPVisible() {
		return trialCreatedPVisible;
	}
	
	public void setTrialCreatedPVisible(boolean trialCreatedPVisible) {
		this.trialCreatedPVisible = trialCreatedPVisible;
	}
	
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


	public String hideChangeTrialSitePopup() {
		// Hide the changeTrialSitePopup
		this.changeTrialSitePVisible = false;
		return Randi2.SUCCESS;
	}

	public boolean isTrialSiteSavedPVisible() {
		return trialSiteSavedPVisible;
	}

	public void setTrialSiteSavedPVisible(boolean trialSiteSavedPVisible) {
		this.trialSiteSavedPVisible = trialSiteSavedPVisible;
	}

	public String hideTrialSiteSavedPopup() {
		this.trialSiteSavedPVisible = false;
		return Randi2.SUCCESS;
	}
	
	public String showTrialSiteSavedPopup(){
		this.trialSiteSavedPVisible = true;
		return Randi2.SUCCESS;
	}
	
	public boolean isSubjectAddedPVisible() {
		return subjectAddedPVisible;
	}
	
	public void setSubjectAddedPVisible(boolean subjectAddedPVisible) {
		this.subjectAddedPVisible = subjectAddedPVisible;
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
