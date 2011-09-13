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

import de.randi2.jsf.controllerBeans.TrialHandler;

/**
 * <p>
 * This class should be used for response adaptive randomization configuration -
 * to check the compatibility of the algorithm's parameters
 * </p>
 * 
 * @author Natalie Waskowzow
 * 
 */

@FacesValidator(value="de.randi2.jsf.validators.RESPONSE_ADAPTIVE_R_VALIDATOR")
public class ResponseAdaptiveRValidator implements Serializable,
		Validator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4270214269032195594L;

	/**
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {

		TrialHandler handler = (TrialHandler) arg1.getAttributes().get(
				"trialHandler");
		Integer countSuccess = (Integer) handler
				.getCountBallsResponseSuccessInput().getValue();
		Integer countTreatmentArms = handler.getCurrentObject()
				.getTreatmentArms().size();
		if(countTreatmentArms>1){
		if (arg2 instanceof Integer) {
			if (!((Integer) arg2
					* (handler.getCurrentObject().getTreatmentArms().size() - 1) >= 0)
					|| ((Integer) arg2
							* (handler.getCurrentObject().getTreatmentArms()
									.size() - 1) > countSuccess)
					|| !((Integer) arg2
							% (handler.getCurrentObject().getTreatmentArms()
									.size() - 1) == 0)
					|| !(countSuccess
							% (handler.getCurrentObject().getTreatmentArms()
									.size() - 1) == 0)) {
				String message = "Count of additional balls if response is success must be >= count of additional balls if response is failure*(number of treatment arms-1)";
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, message, null));
			}
		} else
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"the required data must be a number", null));
		}else
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"please fill the information about treatment arms (Step 3)", null));
	}

}
