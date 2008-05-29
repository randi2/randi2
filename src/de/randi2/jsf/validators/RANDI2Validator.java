package de.randi2.jsf.validators;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Person;
import de.randi2.model.exceptions.ValidationException;

public class RANDI2Validator implements Validator, Serializable{
	

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		
		Application application = arg0.getApplication();
		
		AbstractDomainObject dObject = (AbstractDomainObject) arg1.getAttributes().get("dObject");
		
//		String messageBundle = application.getMessageBundle();
//		Locale locale = arg0.getViewRoot().getLocale();
//		
//		ResourceBundle rb = ResourceBundle.getBundle(messageBundle, locale);
		
		try{
			dObject.checkValue(arg1.getId(), arg2);
		}catch(ValidationException exp){
			exp.printStackTrace();
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,exp.getLocalizedMessage(),null));
		}
		System.out.println("Validiert!");
		
	}

}
