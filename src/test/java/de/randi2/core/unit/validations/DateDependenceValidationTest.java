package de.randi2.core.unit.validations;


import static org.junit.Assert.assertEquals;

import java.util.GregorianCalendar;

import org.hibernate.validator.ClassValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.Trial;
import de.randi2.testUtility.utility.DomainObjectFactory;
import de.randi2.testUtility.utility.TestStringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml"})
public class DateDependenceValidationTest {


	
	
	private ClassValidator<Trial>  trialValidator;

	@Autowired private DomainObjectFactory factory;
	@Autowired private TestStringUtil stringUtil;

	
	@Test
	public void testDateDepenceIsValid(){
		trialValidator = new ClassValidator<Trial>( Trial.class );
		stringUtil.getWithLength(20);
		
		Trial trial = factory.getTrial();
		
		trial.setStartDate(new GregorianCalendar(2006,0,1));
		trial.setEndDate(new GregorianCalendar());

		assertEquals(0, trialValidator.getInvalidValues(trial).length) ;
		
		trial.setStartDate(new GregorianCalendar());
		trial.setEndDate(new GregorianCalendar(2006,0,1));
		
		assertEquals(1, trialValidator.getInvalidValues(trial).length) ;
		
		trial.setStartDate(new GregorianCalendar(2006,0,1));
		trial.setEndDate(new GregorianCalendar(2006,0,1));
		
		assertEquals(1, trialValidator.getInvalidValues(trial).length) ;
		
		trial.setEndDate(null);
		
		assertEquals(1, trialValidator.getInvalidValues(trial).length) ;
		
		trial.setCreatedAt(null);
		
		assertEquals(1, trialValidator.getInvalidValues(trial).length) ;
		
		trial.setEndDate(new GregorianCalendar(2006,0,1));
		
		assertEquals(1, trialValidator.getInvalidValues(trial).length) ;
		
	}
	
}