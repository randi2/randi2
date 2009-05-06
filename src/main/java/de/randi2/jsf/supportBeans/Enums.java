package de.randi2.jsf.supportBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.model.enumerations.Gender;

public class Enums {

	private List<SelectItem> genderItems;
	
	public List<SelectItem> getGenderItems() {
		if (genderItems == null) {
			genderItems = new ArrayList<SelectItem>(Gender.values().length);
			for (Gender g : Gender.values()) {
				genderItems.add(new SelectItem(g, localizeGenderEntry(g)));
			}
		}
		return genderItems;
	}
	
	private String localizeGenderEntry(Gender g){
		return ResourceBundle.getBundle( "de.randi2.jsf.i18n.gender",((LoginHandler) FacesContext.getCurrentInstance()
			    .getApplication().getELResolver().getValue(
			    	      FacesContext.getCurrentInstance().getELContext(), null,
			    	      "loginHandler")).getChosenLocale()).getString(g.toString());
	}

}
