package de.randi2.model;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

import de.randi2.model.exceptions.ValidationException;
import de.randi2.test.utility.AbstractDomainTest;
import edu.emory.mathcs.backport.java.util.Arrays;


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
			assertEquals(3, invalidValues.length);
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
	public void testCheckValuePassword(){
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
	public void testCheckValueUsername(){
		validLogin.setUsername("");
		try{
			validLogin.checkValue("username", validLogin.getUsername());
			fail("Username is empty");
		}catch (ValidationException e) {}
		validLogin.setUsername(stringUtil.getWithLength(Login.MIN_USERNAME_LENGTH-1));
		try{
			validLogin.checkValue("username", validLogin.getUsername());
			fail("Username is too short");
		}catch (ValidationException e) {
			
		}
		validLogin.setUsername(stringUtil.getWithLength(Login.MAX_USERNAME_LENGTH+1));
		try{
			validLogin.checkValue("username", validLogin.getUsername());
			fail("Username is too long");
		}catch (ValidationException e) {
		}
		validLogin = factory.getLogin();
		try{
			validLogin.checkValue("username", validLogin.getUsername());
		}catch (ValidationException e) {
			fail("Username is ok");
		}
		
	}
	
	@Test
	public void testCheckValuePerson(){
		validLogin.setPerson(null);
		try{
			validLogin.checkValue("person", validLogin.getPerson());
			fail("Person is null");
		}catch (ValidationException e) {
			
		}
		validLogin = factory.getLogin();
		try{
			validLogin.checkValue("person", validLogin.getPerson());
		}catch (ValidationException e) {
			fail("Person is ok");
		}
		
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
	public void testAccountNonExpired(){
		assertTrue(validLogin.isAccountNonExpired());
	}
	
	@Test
	public void testCredentialsNonExpired(){
		assertTrue(validLogin.isCredentialsNonExpired());
	}
	
	@Test
	public void testEnabled(){
		assertTrue(validLogin.isEnabled());
	}
	
	
	@Test
	public void testAccountNonLocked(){
		assertTrue(validLogin.isAccountNonLocked());
		validLogin.setNumberWrongLogins(Login.MAX_WRONG_LOGINS);
		assertFalse(validLogin.isAccountNonLocked());
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
		GrantedAuthority[] authorities = validLogin.getAuthorities().toArray(new GrantedAuthority[]{});
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
		
		validLogin.setLockTime(new GregorianCalendar());
		validLogin.setNumberWrongLogins(Login.MAX_WRONG_LOGINS);
		validLogin.setPrefLocale(Locale.ENGLISH);
		GregorianCalendar dateL = new GregorianCalendar(2008,2,1);
		validLogin.setLastLoggedIn(dateL);
		hibernateTemplate.save(validLogin);
		assertTrue(validLogin.getId()>0);
		Login login = (Login)hibernateTemplate.get(Login.class, validLogin.getId());
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
		assertEquals(validLogin.getLockTime(), login.getLockTime());
		
		assertEquals(validLogin.getPerson().getId(), login.getPerson().getId());
	}
	
}
