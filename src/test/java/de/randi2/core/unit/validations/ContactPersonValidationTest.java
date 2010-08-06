package de.randi2.core.unit.validations;

import org.hibernate.validator.ClassValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.TrialSite;
import de.randi2.testUtility.utility.DomainObjectFactory;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml"})
public class ContactPersonValidationTest {

	
	private ClassValidator<TrialSite> validator = new ClassValidator<TrialSite>(TrialSite.class);

	@Autowired private DomainObjectFactory factory;

	
	@Test
	public void testContactPersonIsValid(){
		assertEquals(1,validator.getPotentialInvalidValues("contactPerson", null).length);
		assertEquals(0,validator.getPotentialInvalidValues("contactPerson", factory.getPerson()).length);
		assertEquals(1,validator.getPotentialInvalidValues("contactPerson", factory.getLogin()).length);
		
	}
}
