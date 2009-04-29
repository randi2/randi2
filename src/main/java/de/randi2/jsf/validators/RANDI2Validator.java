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

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.exceptions.ValidationException;

/**
 * <p>
 * This class represents the RANDI2 custom validator, which is used to validate
 * all users inputs in the application. This validator is a a kind of a brigde
 * between the presantation and the backend, cause the validation takes place in
 * the backend - made by Hibernate.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 *
 */
public class RANDI2Validator implements Validator, Serializable {

	private static final long serialVersionUID = 8338311891734408441L;

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {

		// Application application = arg0.getApplication();

		AbstractDomainObject dObject = (AbstractDomainObject) arg1
				.getAttributes().get("dObject");

		// String messageBundle = application.getMessageBundle();
		// Locale locale = arg0.getViewRoot().getLocale();
		//		
		// ResourceBundle rb = ResourceBundle.getBundle(messageBundle, locale);

		try {
			dObject.checkValue(arg1.getId(), arg2);
		} catch (ValidationException exp) {
			//TODO Befor deploy - delete this sysout
			exp.printStackTrace();
			StringBuffer messages = new StringBuffer();
			for (String m : exp.getMessages()) {
				messages.append(m + " ");
			}
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, messages.toString(), null));
		}catch(Exception exp1){
			//TODO Befor deploy - delete this sysout
			exp1.printStackTrace();
		}

	}

}
