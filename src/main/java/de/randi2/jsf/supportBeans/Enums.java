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
package de.randi2.jsf.supportBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.model.enumerations.Gender;
import de.randi2.model.randomization.BlockRandomizationConfig;
@ManagedBean(name="enums")
@ApplicationScoped
public class Enums {

	private LoginHandler loginHandler;
	
	@Setter @Getter
	private List<SelectItem> genderItems;

	public Enums() {
		
		loginHandler =  ((LoginHandler) FacesContext.getCurrentInstance()
				.getApplication().getELResolver().getValue(
						FacesContext.getCurrentInstance()
								.getELContext(), null, "loginHandler"));
		
		genderItems = new ArrayList<SelectItem>(Gender.values().length);
		for (Gender g : Gender.values()) {
			genderItems.add(new SelectItem(g, localizeGenderEntry(g)));
		}

		blockSizeTypeItems = new ArrayList<SelectItem>(
				BlockRandomizationConfig.TYPE.values().length);
		for (BlockRandomizationConfig.TYPE t : BlockRandomizationConfig.TYPE
				.values()) {
			blockSizeTypeItems.add(new SelectItem(t,
					localizeBlockSizeTypeItemsEntry(t)));
		}
	}

	private String localizeGenderEntry(Gender g) {
		return ResourceBundle.getBundle("de.randi2.jsf.i18n.gender",
				loginHandler.getChosenLocale()).getString(g.toString());
	}

	@Getter
	private List<SelectItem> blockSizeTypeItems;

	private String localizeBlockSizeTypeItemsEntry(
			BlockRandomizationConfig.TYPE t) {
		return ResourceBundle.getBundle("de.randi2.jsf.i18n.algorithms",
				loginHandler.getChosenLocale()).getString(
				BlockRandomizationConfig.class.getCanonicalName() + "."
						+ t.toString());
	}

}
