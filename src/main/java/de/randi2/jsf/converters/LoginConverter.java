package de.randi2.jsf.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;

import de.randi2.model.Login;
import de.randi2.services.UserService;

@FacesConverter(value = "de.randi2.jsf.converters.LOGIN_CONVERTER")
public class LoginConverter implements Converter {

	private final UserService service;

	public LoginConverter(final UserService s) {
		service = s;
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		StringBuffer b = new StringBuffer(arg2);
		b.delete(0, b.indexOf("("));
		b.delete(b.indexOf(")"), b.length());
		return service.getUser(b.toString());
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		StringBuffer b = new StringBuffer();
		Login l = (Login) arg2;
		return b.append(l.getPerson().getFirstname()).append(" ")
				.append(l.getPerson().getSurname()).append("(")
				.append(l.getUsername()).append(")").toString();
	}

	public static SelectItem getAsSelectItem(Login l) {
		StringBuffer b = new StringBuffer();
		return new SelectItem(l, b.append(l.getPerson().getFirstname())
				.append(" ").append(l.getPerson().getSurname()).append("(")
				.append(l.getUsername()).append(")").toString());
	}

}
