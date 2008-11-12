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

import javax.faces.context.FacesContext;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;

import de.randi2.dao.TrialSiteDao;
import de.randi2.jsf.Randi2;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.TrialSite;

/**
 * <p>
 * This class cares about the trial site object or objects.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 */
public class TrialSiteHandler {

	//FIXME centerDao in trialDao -> why through xml and not with @Autowired
	private TrialSiteDao centerDao;

	private boolean trialSiteSavedPVisible = false;

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

	/**
	 * The current logged in user.
	 */
	private Login currentUser = null;

	private TrialSite showedTrialSite = null;

	private boolean editable = false;

	private boolean creatingMode = false;

	public TrialSite getShowedTrialSite() {
		if (showedTrialSite == null) {
			showedTrialSite = this.getCurrentUser().getPerson().getTrialSite();
		}
		return showedTrialSite;
	}

	public void setShowedTrialSite(TrialSite showedTrialSite) {
		if (showedTrialSite == null) {
			creatingMode = true;
			this.showedTrialSite = new TrialSite();
			this.showedTrialSite.setContactPerson(new Person());
		} else {
			creatingMode = false;
			this.showedTrialSite = showedTrialSite;
		}
	}

	public Login getCurrentUser() {
		if (currentUser == null) {
			currentUser = ((LoginHandler) FacesContext.getCurrentInstance()
					.getApplication().getELResolver().getValue(
							FacesContext.getCurrentInstance().getELContext(),
							null, "loginHandler")).getLoggedInUser();
		}
		return currentUser;
	}

	public void setCurrentUser(Login currentUser) {
		this.currentUser = currentUser;
	}

	public boolean isEditable() {
		// TODO if the user has the right to edit the center properties this
		// method should return true
		// Temporary I'll just look, if the current user is a member of this
		// center - if it is so, then he can edit it
		// properties.
		if (this.getShowedTrialSite().equals(
				this.getCurrentUser().getPerson().getTrialSite())) {
			editable = true;
		} else {
			editable = creatingMode;
		}
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String saveTrialSite() {
		try {
			centerDao.save(this.showedTrialSite);

			// Making the centerSavedPopup visible
			this.trialSiteSavedPVisible = true;

			this.creatingMode = false;

			return Randi2.SUCCESS;
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

	public boolean isCreatingMode() {
		return creatingMode;
	}

	public void setCreatingMode(boolean creatingMode) {
		this.creatingMode = creatingMode;
	}

	//FIXME Rename the method
	public TrialSiteDao getCenterDao() {
		return centerDao;
	}

	//FIXME Rename the method
	public void setCenterDao(TrialSiteDao centerDao) {
		this.centerDao = centerDao;
	}

	public int getTrialSitesAmount() {
		return centerDao.getAll().size();
	}

}
