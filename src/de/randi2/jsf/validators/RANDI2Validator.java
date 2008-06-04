package de.randi2.jsf.validators;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.exceptions.ValidationException;

public class RANDI2Validator implements Validator, Serializable{
	

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		
//		Application application = arg0.getApplication();
		
		AbstractDomainObject dObject = (AbstractDomainObject) arg1.getAttributes().get("dObject");
		
//		String messageBundle = application.getMessageBundle();
//		Locale locale = arg0.getViewRoot().getLocale();
//		
//		ResourceBundle rb = ResourceBundle.getBundle(messageBundle, locale);
		
		try{
			System.out.println("Checking the value for: "+arg1.getId()+" Value: "+arg2.toString());
			dObject.checkValue(arg1.getId(), arg2);
		}catch(ValidationException exp){
			System.out.println("Fehler gefunden!");
			StringBuffer messages = new StringBuffer();
			for(String m:exp.getMessages()){
				messages.append(m+" ");
			}
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,messages.toString(),null));
		}
		System.out.println("Validiert!");
		
	}

}
