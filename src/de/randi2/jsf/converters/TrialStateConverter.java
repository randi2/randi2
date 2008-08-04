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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

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
		if (arg2.toString().equals("ACTIVE"))
			return TrialStatus.ACTIVE;
		else if(arg2.toString().equals("FINISHED"))
			return TrialStatus.FINISHED;
		else if(arg2.toString().equals("IN_PREPARATION"))
			return TrialStatus.IN_PREPARATION;
		else 
			return TrialStatus.PAUSED;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return arg2.toString();
	}

}
