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

import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import de.randi2.model.AbstractDomainObject;

/**
 * <p>
 * This class cares about the generall logic for the handler classes.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 */
public abstract class AbstractHandler<O extends AbstractDomainObject> {

	/**
	 * The currently showed object.
	 */
	protected O showedObject = null;

	/**
	 * Is the user currently creating a new object, or not.
	 */
	protected boolean creatingMode = false;

	/**
	 * Defines if the showedObject should can be edited by the user or not.
	 */
	protected boolean editable = false;
	
	public O getShowedObject() {
		if (showedObject == null)
			showedObject = createPlainObject();
		return showedObject;
	}

	public void setShowedObject(O _showedObject) {
		if (_showedObject == null) { // A new object is to be created
			creatingMode = true;
			showedObject = createPlainObject();
			refresh();
		} else { // A selected object will be shown
			creatingMode = false;
			showedObject = _showedObject;
			refresh();
		}

	}

	/**
	 * This methods creates plain objects, if the users choose to create them.
	 * 
	 * @return A new object with initialized depending objects.
	 */
	protected abstract O createPlainObject();
	
	/**
	 * This method saves the showedObject.
	 * @return Randi2.SUCCESS or RANDI2.ERROR
	 */
	// FIXME Please implement this using the DAO methods
	public abstract String saveObject();

	/**
	 * This methods should be used to update the view. (Cancel, Refresh, Reset
	 * Buttons)
	 * 
	 * @return
	 */
	public abstract String refreshShowedObject();

	/**
	 * This method should be used to refresh the current view.
	 */
	protected void refresh() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		UIViewRoot viewRoot = viewHandler.createView(context, context
				.getViewRoot().getViewId());
		context.setViewRoot(viewRoot);
		context.renderResponse(); // Optional
	}

	public boolean isCreatingMode() {
		return creatingMode;
	}

}
