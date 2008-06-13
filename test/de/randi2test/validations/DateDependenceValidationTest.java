package de.randi2test.validations;


import static org.junit.Assert.assertEquals;

import java.util.GregorianCalendar;

import org.hibernate.validator.ClassValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.Login;
import de.randi2test.utility.DomainObjectFactory;
import de.randi2test.utility.TestStringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring.xml", "/META-INF/subconfig/test.xml"})
public class DateDependenceValidationTest {


	
	
	private ClassValidator<Login> loginValidator;

	@Autowired private DomainObjectFactory factory;
	@Autowired private TestStringUtil stringUtil;

	
	@Test
	public void testDateDepenceIsValid(){
		loginValidator = new ClassValidator<Login>( Login.class );
		stringUtil.getWithLength(20);
		
		Login l = factory.getLogin();
		
		l.setFirstLoggedIn(new GregorianCalendar(2006,0,1));
		l.setLastLoggedIn(new GregorianCalendar());

		assertEquals(0, loginValidator.getInvalidValues(l).length) ;
		
		l.setFirstLoggedIn(new GregorianCalendar());
		l.setLastLoggedIn(new GregorianCalendar(2006,0,1));
		
		assertEquals(1, loginValidator.getInvalidValues(l).length) ;
		
		l.setFirstLoggedIn(new GregorianCalendar(2006,0,1));
		l.setLastLoggedIn(new GregorianCalendar(2006,0,1));
		
		assertEquals(1, loginValidator.getInvalidValues(l).length) ;
		
		l.setLastLoggedIn(null);
		
		assertEquals(0, loginValidator.getInvalidValues(l).length) ;
		
		l.setFirstLoggedIn(null);
		
		assertEquals(1, loginValidator.getInvalidValues(l).length) ;
		
		
		
	}
}
