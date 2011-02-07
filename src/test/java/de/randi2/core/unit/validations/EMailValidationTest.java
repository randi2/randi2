package de.randi2.core.unit.validations;

import static junit.framework.Assert.assertEquals;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Test;

import de.randi2.model.Person;

//import static junit.framework.Assert.*;
public class EMailValidationTest {

	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	
	@Test
	public void isEMailInvalid(){
	
		String[] invalidEmails = new String[] {  "without at","toomuch@@", "@test.org", "ab..c@de-dg.com",
			"without@domain" , "abc@def.abcde"};
		
		for (String s: invalidEmails){
			assertEquals( "Wrong EMail ("+ s +")",1, validator.validateValue(Person.class, "email", s ).size() );
		}
	
	}
	
	@Test
	public void isEMailValid(){
		
	String[] validEmails = {"abc@def.de", "h@alo.com", "info@2wikipedia.org", "mue5ller@gmx.net", "max-muster@raf.uk", "xyz@test.info"};
	
	for (String s: validEmails){
		assertEquals( "Wrong EMail",0, validator.validateValue(Person.class, "email", s ).size() );
	}
		
	
	}
}
