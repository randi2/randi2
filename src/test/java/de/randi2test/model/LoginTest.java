package de.randi2test.model;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.GrantedAuthority;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2test.utility.AbstractDomainTest;


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
		Assert.assertNull(l.getRegistrationDate());
		Assert.assertFalse(l.isActive());
		
		Assert.assertNull(l.getPerson());
	}
	
//	@Ignore
	@Test
	// FIXME: The test generates an exception
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
		
//		validLogin = factory.getLogin();
//		Login l1 = factory.getLogin();
//		l1.setUsername(validLogin.getUsername());
//		assertEquals(validLogin.getUsername(), l1.getUsername());
//		try {
//			hibernateTemplate.saveOrUpdate(validLogin);
//			hibernateTemplate.saveOrUpdate(l1);
//			fail("should throw exception");
//		} catch (DataIntegrityViolationException e) {}
//		
	}
	
	
	@Test
	public void testPassword(){
		String[] validPasswords = {"secret0$secret","sad.al4h/ljhaslf",stringUtil.getWithLength(Login.MAX_PASSWORD_LENGTH-2)+";2", stringUtil.getWithLength(Login.MIN_PASSWORD_LENGTH-2)+",3", stringUtil.getWithLength(Login.HASH_PASSWORD_LENGTH)};
		for (String s: validPasswords){
			validLogin.setPassword(s);
			assertValid(validLogin);
		}
		
	String[] invalidPasswords = {"secret$secret",stringUtil.getWithLength(Login.MAX_PASSWORD_LENGTH),stringUtil.getWithLength(Login.MIN_PASSWORD_LENGTH-3)+";2", "0123456789", null, ""};
		for (String s: invalidPasswords){
			validLogin.setPassword(s);
			assertInvalid(validLogin);
		}
	}
	
	@Test
	public void testPerson(){
		Person p = factory.getPerson();
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
	
	@Test
	public void testLocale(){
		validLogin.setPrefLocale(Locale.GERMAN);
		assertEquals(Locale.GERMAN, validLogin.getPrefLocale());
		
		hibernateTemplate.saveOrUpdate(validLogin);
		assertTrue(validLogin.getId()!=AbstractDomainObject.NOT_YET_SAVED_ID);
		Login l = (Login) hibernateTemplate.get(Login.class, validLogin.getId());
		assertEquals(validLogin.getId(), l.getId());
		assertEquals(Locale.GERMAN, l.getPrefLocale());
	}
	
	@Test
	public void testRoleAndGrantedAuthority(){
		assertEquals(0, validLogin.getRoles().size());
		
		validLogin.addRole(Role.ROLE_ANONYMOUS);
		assertEquals(1, validLogin.getRoles().size());
		assertTrue(validLogin.getRoles().contains(Role.ROLE_ANONYMOUS));
		
		validLogin.addRole(Role.ROLE_USER);
		assertEquals(1, validLogin.getRoles().size());
		assertTrue(validLogin.getRoles().contains(Role.ROLE_USER));
		
		validLogin = factory.getLogin();
		validLogin.addRole(Role.ROLE_ADMIN);
		assertEquals(2, validLogin.getRoles().size());
		assertTrue(validLogin.getRoles().containsAll(asList(Role.ROLE_ADMIN,Role.ROLE_USER)));
		
		assertEquals(validLogin.getRoles().contains(Role.ROLE_ADMIN), validLogin.hasRole(Role.ROLE_ADMIN));
		assertEquals(validLogin.getRoles().contains(Role.ROLE_INVESTIGATOR), validLogin.hasRole(Role.ROLE_INVESTIGATOR));
		
		Set<Role> roles = new HashSet<Role>();
		roles.add(Role.ROLE_MONITOR);
		roles.add(Role.ROLE_P_INVESTIGATOR);
		
		validLogin.setRoles(roles);
		assertEquals(validLogin.getRoles(), roles);
		GrantedAuthority[] authorities = validLogin.getAuthorities();
		for (Role r: validLogin.getRoles()){
			boolean match = false;
			int i=0;
			while(!match && i < authorities.length){
				if(authorities[i].toString().equals(Role.ROLE_MONITOR.getName()) || authorities[i].toString().equals(Role.ROLE_P_INVESTIGATOR.getName()) ){
					match = true;
				}
				i++;
			}
			if(!match){
				fail("granted authorities names should be equals role names");
			}
		}
	}
	
	
	@Test
	public void databaseIntegrationTest() {
		hibernateTemplate.save(validLogin);
		assertTrue(validLogin.getId()>0);
		Login login = (Login)hibernateTemplate.get(Login.class, validLogin.getId());
		assertEquals(validLogin, login);
	}
	
}
