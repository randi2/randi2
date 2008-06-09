package de.randi2test.model;

import static org.junit.Assert.*;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2test.utility.AbstractDomainTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml"})
public class LoginTest extends AbstractDomainTest<Login>{

	Login validLogin;
	
	public LoginTest() {
		super(Login.class);
	}
	
	@Before
	public void setUp(){
		validLogin = factory.getLogin();
	}
	
	@Test
	public void testConstructor(){
		Login l = new Login();
		
		Assert.assertEquals("", l.getUsername());
		Assert.assertNull(l.getPassword());
		Assert.assertNull(l.getLastLoggedIn());
		Assert.assertNull(l.getFirstLoggedIn());
		Assert.assertFalse(l.isActive());
		
		Assert.assertNull(l.getPerson());
	}
	
	@Test
	public void testUsername(){
		validLogin.setUsername(stringUtil.getWithLength(Login.MIN_USERNAME_LENGTH));
		assertEquals(stringUtil.getLastString(), validLogin.getUsername());
		assertValid(validLogin);
		
		validLogin.setUsername(stringUtil.getWithLength(Login.MIN_USERNAME_LENGTH-1));
		assertEquals(stringUtil.getLastString(), validLogin.getUsername());
		assertInvalid(validLogin);
		
		
		
		validLogin.setUsername(stringUtil.getWithLength(Login.MAX_USERNAME_LENGTH));
		assertEquals(stringUtil.getLastString(), validLogin.getUsername());
		assertValid(validLogin);
		
		validLogin.setUsername(stringUtil.getWithLength(Login.MAX_USERNAME_LENGTH+1));
		assertEquals(stringUtil.getLastString(), validLogin.getUsername());
		assertInvalid(validLogin);
		
		validLogin.setUsername("");
		assertEquals("", validLogin.getUsername());
		try {
			hibernateTemplate.saveOrUpdate(validLogin);
			fail("should throw exception");
		} catch (InvalidStateException e) {
			InvalidValue[] invalidValues = e.getInvalidValues();
			assertEquals(2, invalidValues.length);
		}
		
		
		validLogin.setUsername(stringUtil.getWithLength(Login.MAX_USERNAME_LENGTH));
		Login l1 = factory.getLogin();
		l1.setUsername(validLogin.getUsername());
		assertEquals(validLogin.getUsername(), l1.getUsername());
		assertValid(validLogin);
		try {
			hibernateTemplate.saveOrUpdate(l1);
			fail("should throw exception");
		} catch (DataIntegrityViolationException e) {}
		
		
//		validLogin.setUsername(null);
//		assertEquals("", validLogin.getUsername());
//		assertInvalid(validLogin);
	}
	
	
	@Test
	public void testPassword(){
		fail("not yet implemented");
	}
	
	@Test
	public void testPerson(){
		Person p = factory.getPerson();
		p.setSurname(stringUtil.getWithLength(20));
		validLogin.setUsername(stringUtil.getWithLength(Login.MIN_USERNAME_LENGTH));
		validLogin.setPerson(p);
		assertNotNull(validLogin.getPerson());
		hibernateTemplate.saveOrUpdate(validLogin);
		
		Login l = (Login)hibernateTemplate.get(Login.class, validLogin.getId());
		assertNotNull(l);
		assertEquals(validLogin.getUsername(), l.getUsername());
		assertNotNull(l.getPerson());
		assertEquals(validLogin.getPerson().getId(), l.getPerson().getId());
	
	}
	
	@Test
	public void testAktive(){
		validLogin.setActive(true);
		assertTrue(validLogin.isActive());
		
		validLogin.setActive(false);
		assertFalse(validLogin.isActive());
	}

}
