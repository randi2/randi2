package de.randi2.utility.validations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.hibernate.validator.Validator;

public class DateDependenceValidation implements Validator<DateDependence> {

	private String firstDate;
	private String secondDate;

	@Override
	public void initialize(DateDependence dateDependence) {
		firstDate = dateDependence.firstDate();
		secondDate = dateDependence.secondDate();

	}

	@Override
	public boolean isValid(Object object) {
		try {
			Method firstMethod = object.getClass().getMethod("get" + firstDate.substring(0, 1).toUpperCase(Locale.getDefault()) + firstDate.substring(1), new Class[0]);
			Method secondMethod = object.getClass().getMethod("get" + secondDate.substring(0, 1).toUpperCase(Locale.getDefault()) + secondDate.substring(1), new Class[0]);
			GregorianCalendar first = (GregorianCalendar) firstMethod.invoke(object, new Object[0]);
			GregorianCalendar second = (GregorianCalendar) secondMethod.invoke(object, new Object[0]);
			if (second == null) {
				return true;
			} else if (first != null && first.before(second)) {
				return true;
			} else {
				return false;
			}
		} catch (IllegalAccessException ex) {
			Logger.getLogger(DateDependenceValidation.class).error(null, ex);
		} catch (InvocationTargetException ex) {
			Logger.getLogger(DateDependenceValidation.class).error(null, ex);
		} catch (NoSuchMethodException ex) {
			Logger.getLogger(DateDependenceValidation.class).error(null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(DateDependenceValidation.class).error(null, ex);
		}
		return false;
	}
}
