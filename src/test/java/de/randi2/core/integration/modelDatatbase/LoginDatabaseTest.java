package de.randi2.core.integration.modelDatatbase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.testUtility.utility.AbstractDomainDatabaseTest;

public class LoginDatabaseTest extends AbstractDomainDatabaseTest<Login> {

	
	Login validLogin;
	
	
	public LoginDatabaseTest() {
		super(Login.class);
	}

	@Before
	public void setUp(){
		super.setUp();
		validLogin = factory.getLogin();
	}
	
	
	@Test
	@Transactional
	public void testPerson(){
		Person p = factory.getPerson();
		validLogin.setPerson(p);
		assertNotNull(validLogin.getPerson());
		
		
		sessionFactory.getCurrentSession().saveOrUpdate(validLogin);
		
		Login l = (Login)sessionFactory.getCurrentSession().get(Login.class, validLogin.getId());
		assertNotNull(l);
		assertEquals(validLogin.getUsername(), l.getUsername());
		assertNotNull(l.getPerson());
		assertEquals(validLogin.getPerson().getId(), l.getPerson().getId());
	
	}
	
	
	@Test
	@Transactional
	public void testLocale(){
		validLogin.setPrefLocale(Locale.GERMAN);
		assertEquals(Locale.GERMAN, validLogin.getPrefLocale());
		
		sessionFactory.getCurrentSession().saveOrUpdate(validLogin);
		assertTrue(validLogin.getId()!=AbstractDomainObject.NOT_YET_SAVED_ID);
		Login l = (Login) sessionFactory.getCurrentSession().get(Login.class, validLogin.getId());
		assertEquals(validLogin.getId(), l.getId());
		assertEquals(Locale.GERMAN, l.getPrefLocale());
	}
	
	@Test
	@Transactional
	public void databaseIntegrationTest() {
		
		validLogin.setLockTime(new GregorianCalendar());
		validLogin.setNumberWrongLogins(Login.MAX_WRONG_LOGINS);
		validLogin.setPrefLocale(Locale.ENGLISH);
		GregorianCalendar dateL = new GregorianCalendar(2008,2,1);
		validLogin.setLastLoggedIn(dateL);
		sessionFactory.getCurrentSession().save(validLogin);
		assertTrue(validLogin.getId()>0);
		Login login = (Login)sessionFactory.getCurrentSession().get(Login.class, validLogin.getId());
		assertEquals(validLogin.getId(), login.getId());
		// FIXME: Clear out
		//assertEquals(Arrays.asList(validLogin.getAuthorities()), Arrays.asList(login.getAuthorities()));
		assertNotNull(login.getCreatedAt());
		assertNotNull(login.getUpdatedAt());
		assertEquals(validLogin.getUsername(), login.getUsername());
		assertEquals(validLogin.getPrefLocale(), login.getPrefLocale());
		assertEquals(validLogin.getUIName(), login.getUIName());
		assertEquals(validLogin.getRoles(), login.getRoles());
		assertEquals(validLogin.getNumberWrongLogins(), login.getNumberWrongLogins());
//		FIXME in test with mysql it is a difference of nearly 1 second
//		assertEquals(validLogin.getLockTime(), login.getLockTime());
		assertEquals(validLogin.getPerson().getId(), login.getPerson().getId());
	}
	
}
