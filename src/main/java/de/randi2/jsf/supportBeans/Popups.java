package de.randi2.jsf.supportBeans;

public class Popups {
	
	// Popup's flags
	private boolean userSavedPVisible = false;
	private boolean changePasswordPVisible = false;
	private boolean changeTrialSitePVisible = false;
	private boolean changeAssistantPVisible = false;

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
}
