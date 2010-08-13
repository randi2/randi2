package de.randi2.core.unit.validations;

import org.hibernate.validator.ClassValidator;
import org.junit.Test;

import de.randi2.model.Person;
import static junit.framework.Assert.*;

//import static junit.framework.Assert.*;
public class EMailValidationTest {

	private ClassValidator<Person> validator;
	
	@Test
	public void isEMailInvalid(){
		validator = new ClassValidator<Person>( Person.class );
	
		String[] invalidEmails = new String[] {  "without at","toomuch@@", "@test.org", "ab..c@de-dg.com",
			"without@domain" , "abc@def.abcde"};
		
		for (String s: invalidEmails){
			assertEquals( "Wrong EMail ("+ s +")",1, validator.getPotentialInvalidValues( "email", s ).length );
		}
	
	}
	
	@Test
	public void isEMailValid(){
		validator = new ClassValidator<Person>(Person.class );
		
	String[] validEmails = {"abc@def.de", "h@alo.com", "info@2wikipedia.org", "mue5ller@gmx.net", "max-muster@raf.uk", "xyz@test.info"};
	
	for (String s: validEmails){
		assertEquals( "Wrong EMail",0, validator.getPotentialInvalidValues( "email", s ).length );
	}
		
	
	}
}
