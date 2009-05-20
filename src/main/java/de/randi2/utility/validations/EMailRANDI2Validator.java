package de.randi2.utility.validations;

import org.hibernate.validator.Validator;

public class EMailRANDI2Validator implements Validator<EMailRANDI2> {

	@Override
	public void initialize(EMailRANDI2 parameters) {
	}

	@Override
	public boolean isValid(Object value) {
		if (value == null)
			return false;
		else if (!(value instanceof String))
			return false;
		else {
			String mail = (String) value;
			return mail
					.matches("[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-]+(\\.)?)+\\.([a-zA-Z]){2,4}");
		}
	}
}
