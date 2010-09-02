package de.randi2.core.unit.validations;

import static junit.framework.Assert.assertEquals;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.TrialSite;
import de.randi2.testUtility.utility.DomainObjectFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml"})
public class ContactPersonValidationTest {
	

	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	@Autowired private DomainObjectFactory factory;

	
	@Test
	public void testContactPersonIsValid(){
		assertEquals(1,validator.validateValue(TrialSite.class, "contactPerson", null).size());
		assertEquals(0,validator.validateValue(TrialSite.class, "contactPerson", factory.getPerson()).size());
		assertEquals(1,validator.validateValue(TrialSite.class, "contactPerson", factory.getLogin()).size());
		
	}
}
