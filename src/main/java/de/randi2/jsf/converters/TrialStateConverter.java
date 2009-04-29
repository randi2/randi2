/*
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
 * RANDI2. If not, see <http:// www.gnu.org/licenses/>.
 */
package de.randi2.jsf.converters;

import java.util.Collections;
import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.model.enumerations.TrialStatus;

/**
 * <p>
 * A JSF converter for the trial's state.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 */
public class TrialStateConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		ResourceBundle tempRB = ResourceBundle.getBundle("de.randi2.jsf.i18n.trialState",
				((LoginHandler) FacesContext.getCurrentInstance()
						.getApplication().getELResolver().getValue(
								FacesContext.getCurrentInstance()
										.getELContext(), null, "loginHandler"))
						.getChosenLocale());
		for (String key : Collections.list(tempRB.getKeys())) {
			if(arg2.equals(tempRB.getString(key)))
				return getEnumElement(key);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		ResourceBundle tempRB = ResourceBundle.getBundle("de.randi2.jsf.i18n.trialState",
				((LoginHandler) FacesContext.getCurrentInstance()
						.getApplication().getELResolver().getValue(
								FacesContext.getCurrentInstance()
										.getELContext(), null, "loginHandler"))
						.getChosenLocale());
		return tempRB.getString(arg2.toString());
	}
	
	private TrialStatus getEnumElement(String _elementName){
		for(TrialStatus ts : TrialStatus.values()){
			if(_elementName.equals(ts.toString()))
				return ts;
		}
		return null;
	}
}
