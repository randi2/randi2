package de.randi2.utility.validations;

import org.hibernate.validator.Validator;

import de.randi2.model.Person;

public class ContactPersonValidation implements Validator<ContactPerson>{

	@Override
	public void initialize(ContactPerson parameters) {}

	@Override
	public boolean isValid(Object value) {
		if(value==null) return true;
		if(!(value instanceof Person)) return false;
		Person person = (Person) value;
		if(person.getLogin()!=null) return false;
		
		return true;
	}

}
