package de.randi2test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.PersonRole;
import de.randi2.model.Righ;
import de.randi2test.model.util.AbstractDomainTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring.xml", "/META-INF/subconfig/test.xml"})
public class PersonRoleTest extends AbstractDomainTest<PersonRole>{

	public PersonRole validPersonRole;
	
	public PersonRoleTest() {
		super(PersonRole.class);
	}
	
	@Before
	public void setUp(){
		validPersonRole = factory.getPersonRole();
	}

	@Test
	public void testConstructor(){
		PersonRole pr = new PersonRole();
		
		assertNull(pr.getPerson());
		assertNull(pr.getTrial());
		assertNull(pr.getRole());
	}
	
	@Test
	public void rightManagement(){
		
		assertNotNull(validPersonRole);
		assertNotNull(validPersonRole.getRights());
		assertEquals(0, validPersonRole.getRights().size());
		assertFalse(validPersonRole.hasRight(Righ.EDIT_TRIAL));
		assertFalse(validPersonRole.hasRight(Righ.EDIT_TRIAL, factory.getTrial()));
	}
	
}
