package de.randi2.jsf.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import de.randi2.model.TrialSite;
import de.randi2.services.TrialSiteService;

@FacesConverter(value = "de.randi2.jsf.converters.TRIALSITE_CONVERTER")
public class TrialSiteConverter implements Converter {

	private final TrialSiteService service;

	public TrialSiteConverter(final TrialSiteService s) {
		service = s;
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(arg2.equals("please select")) return null;
		return service.get(arg2);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2 == null) return "please select";
		return  ((TrialSite)arg2).getName();
	}

}
