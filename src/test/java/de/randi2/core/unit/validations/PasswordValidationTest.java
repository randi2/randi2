package de.randi2.core.unit.validations;

import static org.junit.Assert.assertEquals;

import org.hibernate.validator.ClassValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.Login;
import de.randi2.testUtility.utility.TestStringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml"})
public class PasswordValidationTest {

	@Autowired private TestStringUtil stringUtil;
	
	
	private ClassValidator<Login> loginValidator;

	
	@Test
	public void testPasswordIsValid(){
		loginValidator = new ClassValidator<Login>( Login.class );

		String[] validPasswords = {"secret0$secret","sad.al4h/ljhaslf",stringUtil.getWithLength(Login.MAX_PASSWORD_LENGTH-2)+";2", stringUtil.getWithLength(Login.MIN_PASSWORD_LENGTH-2)+",3", stringUtil.getWithLength(Login.HASH_PASSWORD_LENGTH)};
		
		for (String s: validPasswords){
			assertEquals( "Wrong password", 0, loginValidator.getPotentialInvalidValues( "password", s ).length );
		}

		
		
	}
	
	@Test
	public void testPasswordIsInvalid(){
		loginValidator = new ClassValidator<Login>( Login.class );

	String[] invalidPasswords = {"secret$secret",stringUtil.getWithLength(Login.MAX_PASSWORD_LENGTH),stringUtil.getWithLength(Login.MIN_PASSWORD_LENGTH-3)+";2", "0123456789"};
		for (String s: invalidPasswords){
			assertEquals( "Right password", 1, loginValidator.getPotentialInvalidValues( "password", s ).length );
			
		}
		
		
	}
}
