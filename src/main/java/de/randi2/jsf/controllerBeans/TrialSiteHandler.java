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
package de.randi2.jsf.controllerBeans;

import java.util.List;

import javax.faces.context.FacesContext;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;

import de.randi2.jsf.supportBeans.Randi2;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.TrialSite;
import de.randi2.services.TrialSiteService;

/**
 * <p>
 * This class cares about the trial site object or objects.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 */
public class TrialSiteHandler extends AbstractHandler<TrialSite> {

	private TrialSiteService siteService = null;

	public void setSiteService(TrialSiteService siteService) {
		this.siteService = siteService;
	}
	
	public TrialSiteHandler() {
	}

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

	public Login getCurrentUser() {
		return ((LoginHandler) FacesContext.getCurrentInstance()
				.getApplication().getELResolver().getValue(
						FacesContext.getCurrentInstance().getELContext(), null,
						"loginHandler")).getLoggedInUser();
	}

	public boolean isEditable() {
		// TODO if the user has the right to edit the center properties this
		// method should return true
		// Temporary I'll just look, if the current user is a member of this
		// center - if it is so, then he can edit it
		// properties.
		if (showedObject != null
				&& showedObject.equals(this.getCurrentUser().getPerson()
						.getTrialSite())) {
			editable = true;
		} else {
			editable = creatingMode;
		}
		return editable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.jsf.handlers.AbstractHandler#saveObject()
	 */
	@Override
	public String saveObject() {
		try {
			if(creatingMode){
				siteService.create(showedObject);
			}else{
				showedObject = siteService.update(showedObject);
			}
			// Making the centerSavedPopup visible
			this.trialSiteSavedPVisible = true;
			this.creatingMode = false;
			return Randi2.SUCCESS;
		} catch (InvalidStateException exp) {
			for (InvalidValue v : exp.getInvalidValues()) {
				Randi2
						.showMessage(v.getPropertyName() + " : "
								+ v.getMessage());
			}
			return Randi2.ERROR;
		} catch (Exception e) {
			Randi2.showMessage(e);
			return Randi2.ERROR;
		} 
		finally {
			refreshShowedObject();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.jsf.handlers.AbstractHandler#refreshShowedObject()
	 */
	@Override
	public String refreshShowedObject() {
		if (showedObject.getId() == AbstractDomainObject.NOT_YET_SAVED_ID)
			showedObject = null;
		else
			showedObject = siteService.getObject(showedObject.getId());
		refresh();
		return Randi2.SUCCESS;
	}

	public int getTrialSitesAmount() {
		return siteService.getAll().size();
	}
	
	/**
	 * Returns all defined trial sites.
	 * @return
	 */
	public List<TrialSite> getAllTrialSites(){
		return siteService.getAll();
	}

	@Override
	protected TrialSite createPlainObject() {
		TrialSite ts = new TrialSite();
		ts.setContactPerson(new Person());
		return ts;
	}

}
