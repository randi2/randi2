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
package de.randi2.jsf.converters;

import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.model.randomization.BlockRandomizationConfig;

public class BlockRandomizationTypeConverter  implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if( value == null || value.length() < 1 ){ 
 			return null;
 		}
 		return findBlockTypeForl16edValue(value);
		
	}

	private BlockRandomizationConfig.TYPE findBlockTypeForl16edValue(String value) {
		ResourceBundle rb = ResourceBundle.getBundle( "de.randi2.jsf.i18n.algorithms",((LoginHandler) FacesContext.getCurrentInstance()
			    .getApplication().getELResolver().getValue(
			    	      FacesContext.getCurrentInstance().getELContext(), null,
			    	      "loginHandler")).getChosenLocale());
		for(String key : rb.keySet()){
			if(rb.getString(key).equals(value)){
				String[] results = key.split("\\.");
			return BlockRandomizationConfig.TYPE.valueOf(results[results.length-1]);
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		String result = null;
 		if( value != null && value instanceof BlockRandomizationConfig.TYPE ){
 			result = ResourceBundle.getBundle( "de.randi2.jsf.i18n.algorithms",((LoginHandler) FacesContext.getCurrentInstance()
 				    .getApplication().getELResolver().getValue(
 				    	      FacesContext.getCurrentInstance().getELContext(), null,
 				    	      "loginHandler")).getChosenLocale()).getString(BlockRandomizationConfig.class.getCanonicalName()+ "."+((BlockRandomizationConfig.TYPE)value).toString());
 		}
 		return result;
	}

}
