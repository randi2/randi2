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
			if (second == null || first == null) {
				return false;
			} else if (first.before(second)) {
				return true;
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
