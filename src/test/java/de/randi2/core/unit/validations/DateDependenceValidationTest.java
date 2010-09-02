package de.randi2.core.unit.validations;


import static org.junit.Assert.assertEquals;

import java.util.GregorianCalendar;

import javax.validation.Validation;
import javax.validation.Validator;

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


	
	
	private Validator trialValidator = Validation.buildDefaultValidatorFactory().getValidator();
	

	@Autowired private DomainObjectFactory factory;
	@Autowired private TestStringUtil stringUtil;

	
	@Test
	public void testDateDepenceIsValid(){
		stringUtil.getWithLength(20);
		
		Trial trial = factory.getTrial();
		
		trial.setStartDate(new GregorianCalendar(2006,0,1));
		trial.setEndDate(new GregorianCalendar());

		assertEquals(0, trialValidator.validate(trial).size()) ;
		
		trial.setStartDate(new GregorianCalendar());
		trial.setEndDate(new GregorianCalendar(2006,0,1));
		
		assertEquals(1, trialValidator.validate(trial).size()) ;
		
		trial.setStartDate(new GregorianCalendar(2006,0,1));
		trial.setEndDate(new GregorianCalendar(2006,0,1));
		
		assertEquals(1, trialValidator.validate(trial).size()) ;
		
		trial.setEndDate(null);
		
		assertEquals(1, trialValidator.validate(trial).size()) ;
		
		trial.setCreatedAt(null);
		
		assertEquals(1, trialValidator.validate(trial).size()) ;
		
		trial.setEndDate(new GregorianCalendar(2006,0,1));
		
		assertEquals(1, trialValidator.validate(trial).size()) ;
		
	}
	
}