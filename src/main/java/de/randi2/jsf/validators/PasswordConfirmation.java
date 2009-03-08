/* This file is part of RANDI2.
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
package de.randi2.jsf.validators;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * <p>
 * This class should be used for password confirmation - to check if the second
 * password is just like firstone. IMPORTANT: If you want to use this Validator
 * please take care, that the orginal password will be typed within a
 * <CODE>passwordGroup</CODE>
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 * 
 */
public class PasswordConfirmation implements Validator, Serializable {

	private static final long serialVersionUID = 3209827418681297534L;

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {

		// Application application = arg0.getApplication();

		UIComponent passwordInputSecret = arg1.getParent().getParent().findComponent("passwordGroup").findComponent("password");

		// String messageBundle = application.getMessageBundle();
		// Locale locale = arg0.getViewRoot().getLocale();
		//		
		// ResourceBundle rb = ResourceBundle.getBundle(messageBundle, locale);
		if (passwordInputSecret.getValueExpression("value").getValue(arg0.getELContext()) != null &&
				!passwordInputSecret.getValueExpression("value").getValue(arg0.getELContext()).equals(arg2)) {
			String message = "The second passwort doesn't match the first one.";
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, message, null));
		}
	}
}
