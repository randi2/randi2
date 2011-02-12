package de.randi2.jsf.converters;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import de.randi2.model.criteria.AbstractCriterion;

@FacesConverter(value = "de.randi2.jsf.converters.CRITERION_CONVERTER")
public class CriterionConverter implements Converter {

	private final Locale locale;

	public CriterionConverter(final Locale l) {
		locale = l;
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		try {
			return findGenderForl16edValue(arg2);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		String result = null;
		if (value != null && value instanceof AbstractCriterion<?, ?>) {
			result = ResourceBundle.getBundle("de.randi2.jsf.i18n.criteria",
					locale).getString(value.getClass().getCanonicalName());
		}
		return result;
	}

	private AbstractCriterion<?, ?> findGenderForl16edValue(String l16edValue)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		ResourceBundle rb = ResourceBundle.getBundle(
				"de.randi2.jsf.i18n.criteria", locale);
		for (String key : rb.keySet()) {
			if (rb.getString(key).equals(l16edValue))
				return (AbstractCriterion<?, ?>) Class.forName(key)
						.newInstance();
		}
		System.out.println("no role found!!!!");
		return null;
	}
}
