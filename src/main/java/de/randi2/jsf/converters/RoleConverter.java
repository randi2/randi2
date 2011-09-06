package de.randi2.jsf.converters;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import de.randi2.model.Role;
import de.randi2.services.UserService;

@FacesConverter(value = "de.randi2.jsf.converters.ROLE_CONVERTER")
public class RoleConverter implements Converter {

	private final UserService service;
	private final Locale locale;

	public RoleConverter(final UserService s, final Locale l) {
		service = s;
		locale = l;
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if(value.equals("please select")) return null;
		return findGenderForl16edValue(value);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if(value == null) return "please select";
		String result = null;
		if (value != null && value instanceof Role) {
			result = ResourceBundle.getBundle("de.randi2.jsf.i18n.roles",
					locale).getString(((Role) value).getUIName());
		}
		return result;
	}

	private Role findGenderForl16edValue(String l16edValue) {
		ResourceBundle rb = ResourceBundle.getBundle(
				"de.randi2.jsf.i18n.roles", locale);
		for (String key : rb.keySet()) {
			if (rb.getString(key).equals(l16edValue))
				return service.getRole(key);
		}
		return null;
	}

}
