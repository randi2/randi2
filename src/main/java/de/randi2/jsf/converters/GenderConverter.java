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

import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.model.enumerations.Gender;

/**
 * <p>
 * A JSF converter for the gender-property of a person.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 */
public class GenderConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		Gender result = null;
 		if( value == null || value.length() < 1 ){ 
 			result =  null;
 		}
 		result = findGenderForl16edValue(value);
 		return result;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		String result = null;
 		if( value != null && value instanceof Gender ){
 			result = ResourceBundle.getBundle( "de.randi2.jsf.i18n.gender",((LoginHandler) FacesContext.getCurrentInstance()
 				    .getApplication().getELResolver().getValue(
 				    	      FacesContext.getCurrentInstance().getELContext(), null,
 				    	      "loginHandler")).getChosenLocale()).getString(((Gender)value).toString());
 		}
 		return result;
	}
	
	private Gender findGenderForl16edValue(String l16edValue){
		ResourceBundle rb = ResourceBundle.getBundle( "de.randi2.jsf.i18n.gender",((LoginHandler) FacesContext.getCurrentInstance()
			    .getApplication().getELResolver().getValue(
			    	      FacesContext.getCurrentInstance().getELContext(), null,
			    	      "loginHandler")).getChosenLocale());
		for(String key : rb.keySet()){
			if(rb.getString(key).equals(l16edValue))
				return Gender.valueOf(key);
		}
		return Gender.MALE;
	}

}
