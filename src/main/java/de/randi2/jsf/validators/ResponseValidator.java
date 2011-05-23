/**
 * 
 */
package de.randi2.jsf.validators;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import de.randi2.jsf.backingBeans.ResponseAdd;

/**
 * @author Natalie Waskowzow
 *
 */
@FacesValidator(value="de.randi2.jsf.validators.RESPONSE_VALIDATOR")
public class ResponseValidator implements Validator, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1993336743706608592L;

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		ResponseAdd bean = (ResponseAdd) arg1.getAttributes().get(
				"responseAdd");
		
		if (bean.isResponseAdded(arg2.toString()) && !bean.istSubjectIdentified()) {
			String message = "Response for this trial subject has been already added";
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, message, null));
		}
	}

}
