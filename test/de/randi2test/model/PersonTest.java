package de.randi2test.model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2test.utility.AbstractDomainTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring.xml", "/META-INF/subconfig/test.xml"})
public class PersonTest extends AbstractDomainTest<Person>{

	private Person validPerson;
	
	public PersonTest() {
		super(Person.class);
	}
	
	@Before
	public void setUp(){
		validPerson = super.factory.getPerson();
	}
	
	@Test
	public void testConstructor(){
		Person p = validPerson;
		assertEquals("", p.getFirstname());
		assertEquals("", p.getSurname());
		assertEquals("", p.getTitle());
		assertNull(p.getGender());
		
		assertEquals("", p.getEMail());
		assertEquals("", p.getPhone());
		assertEquals("", p.getMobile());
		assertEquals("", p.getFax());
		
		assertNull(p.getAssistant());
		assertNull(p.getCenter());
		
		assertNull(p.getLogin());
		assertEquals(0, p.getRoles().size());
	}
	
	@Test
	public void testSurname(){
		validPerson.setSurname(stringUtil.getWithLength(1));
		assertEquals(stringUtil.getLastString(), validPerson.getSurname());
		assertValid(validPerson);
		
		
		
		validPerson.setSurname(stringUtil.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH));
		assertEquals(stringUtil.getLastString(), validPerson.getSurname());
		assertValid(validPerson);
		
		validPerson.setSurname(stringUtil.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH+1));
		assertEquals(stringUtil.getLastString(), validPerson.getSurname());
		assertInvalid(validPerson);
		
		validPerson.setSurname("");
		assertEquals("", validPerson.getSurname());
		assertInvalid(validPerson);
		
		validPerson.setSurname(null);
		assertEquals("", validPerson.getSurname());
		assertInvalid(validPerson);
	}
	
	@Test
	public void testLogin(){
		Login l = factory.getLogin();
		validPerson.setSurname(stringUtil.getWithLength(20));
		l.setUsername(stringUtil.getWithLength(Login.MIN_USERNAME_LENGTH));
		validPerson.setLogin(l);
		assertNotNull(validPerson.getLogin());
		hibernateTemplate.saveOrUpdate(validPerson);
		
		Person p = (Person)hibernateTemplate.get(Person.class, validPerson.getId());
		assertNotNull(p);
		assertEquals(validPerson.getSurname(), p.getSurname());
		assertNotNull(p.getLogin());
		assertEquals(validPerson.getLogin().getId(), p.getLogin().getId());
		Person p1 = p.getLogin().getPerson();
		

	}

}
