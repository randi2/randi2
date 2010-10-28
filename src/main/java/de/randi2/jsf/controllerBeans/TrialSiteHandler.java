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

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import lombok.Setter;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;

import de.randi2.jsf.supportBeans.PermissionVerifier;
import de.randi2.jsf.supportBeans.Popups;
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
@ManagedBean(name="trialSiteHandler")
@SessionScoped
public class TrialSiteHandler extends AbstractHandler<TrialSite> {
	
	/*
	 * Services which are provided by spring and this class works with.
	 */
	@ManagedProperty(value="#{trialSiteService}")
	@Setter
	private TrialSiteService siteService = null;

	@ManagedProperty(value="#{permissionVerifier}")
	@Setter
	private PermissionVerifier permissionVerifier;
	
	/*
	 * Reference to the popups bean for the popups functionality. 
	 */
	@ManagedProperty(value="#{popups}")
	@Setter
	private Popups popups;
	
	/*
	 * Reference to the LoginHandler 
	 */
	@ManagedProperty(value="#{loginHandler}")
	@Setter
	private LoginHandler loginHandler;

	/**
	 * Returns the currently signed in user.
	 * @return
	 */
	public Login getCurrentUser() {
		return loginHandler.getLoggedInUser();
	}

	/**
	 * Checks if the current user can edit the currently shown center.
	 * @return
	 */
	public boolean isEditable() {
		if (currentObject != null
				&& permissionVerifier.isAllowedEditTrialSite(currentObject)) {
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
			if (creatingMode) {
				siteService.create(currentObject);
			} else {
				currentObject = siteService.update(currentObject);
			}
			// Making the centerSavedPopup visible
			popups.showTrialSiteSavedPopup();
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
		} finally {
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
		if (currentObject.getId() == AbstractDomainObject.NOT_YET_SAVED_ID)
			currentObject = null;
		else
			currentObject = siteService.getObject(currentObject.getId());
		refresh();
		return Randi2.SUCCESS;
	}

	public int getTrialSitesAmount() {
		return siteService.getAll().size();
	}

	/**
	 * Returns all defined trial sites.
	 * 
	 * @return
	 */
	public List<TrialSite> getAllTrialSites() {
		return siteService.getAll();
	}

	/* (non-Javadoc)
	 * @see de.randi2.jsf.controllerBeans.AbstractHandler#createPlainObject()
	 */
	@Override
	protected TrialSite createPlainObject() {
		TrialSite ts = new TrialSite();
		ts.setContactPerson(new Person());
		return ts;
	}

}
