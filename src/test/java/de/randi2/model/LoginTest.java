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
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.exceptions.ValidationException;
import de.randi2.model.security.PermissionHibernate;
import de.randi2.test.utility.AbstractDomainTest;

public class LoginTest extends AbstractDomainTest<Login>{

	Login validLogin;
	
	public LoginTest() {
		super(Login.class);
	}
	
	@Before
	public void setUp(){
		super.setUp();
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
	
	
	@Test
	@Transactional
	public void testUsername(){
		validLogin.setUsername(stringUtil.getWithLength(Login.MIN_USERNAME_LENGTH)+"@abc.de");
		assertEquals(stringUtil.getLastString()+"@abc.de", validLogin.getUsername());
		assertValid(validLogin);
		
		validLogin.setUsername(stringUtil.getWithLength(Login.MIN_USERNAME_LENGTH-8)+"@abc.de");
		assertEquals(stringUtil.getLastString()+"@abc.de", validLogin.getUsername());
		assertInvalid(validLogin);
		
		
		
		validLogin.setUsername(stringUtil.getWithLength(Login.MAX_USERNAME_LENGTH-7)+"@abc.de");
		assertEquals(stringUtil.getLastString()+"@abc.de", validLogin.getUsername());
		assertValid(validLogin);
		
		validLogin.setUsername(stringUtil.getWithLength(Login.MAX_USERNAME_LENGTH)+"@abc.de");
		assertEquals(stringUtil.getLastString()+"@abc.de", validLogin.getUsername());
		assertInvalid(validLogin);
		
		validLogin.setUsername("");
		assertEquals("", validLogin.getUsername());
		assertInvalid(validLogin);
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
	
	@Test
	public void testGetRequieredFields(){
		Map<String, Boolean> map = validLogin.getRequiredFields();
		for(String key : map.keySet()){
			if(key.equals("active")) {assertFalse(map.get(key));} 
			else if(key.equals("numberWrongLogins")) {assertFalse(map.get(key));} 
			else if(key.equals("prefLocale")) {assertFalse(map.get(key));}  
			else if(key.equals("person")) {assertTrue(map.get(key));} 
			else if(key.equals("username")) {assertTrue(map.get(key));} 
			else if(key.equals("password")) {assertTrue(map.get(key)); }
			else if(key.equals("lastLoggedIn")) {assertFalse(map.get(key));} 
			else if(key.equals("lockTime")) {assertFalse(map.get(key));} 
			else if(key.equals("roles")) {assertFalse(map.get(key)); }
			else if(key.equals("MAX_WRONG_LOGINS")) {assertFalse(map.get(key));}  
			else if(key.equals("MILIS_TO_LOCK_USER")) {assertFalse(map.get(key));} 
			else if(key.equals("MAX_USERNAME_LENGTH")) {assertFalse(map.get(key));} 
			else if(key.equals("MIN_USERNAME_LENGTH")) {assertFalse(map.get(key));}
			else if(key.equals("MAX_PASSWORD_LENGTH")) {assertFalse(map.get(key));} 
			else if(key.equals("MIN_PASSWORD_LENGTH")) {assertFalse(map.get(key));} 
			else if(key.equals("HASH_PASSWORD_LENGTH")) {assertFalse(map.get(key));} 
			else if(key.equals("serialVersionUID")) {assertFalse(map.get(key));}
			else if(key.equals("$VRc")) {assertFalse(map.get(key));}
			else fail(key + " not checked");
		}
	}
	
	@Test
	public void testHasPermissionAll(){
		validLogin.addRole(Role.ROLE_ADMIN);
		validLogin.addRole(Role.ROLE_P_INVESTIGATOR);
		validLogin.addRole(Role.ROLE_INVESTIGATOR);
		
		assertTrue(validLogin.hasPermission(Login.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(Login.class, PermissionHibernate.READ));
		assertTrue(validLogin.hasPermission(Login.class, PermissionHibernate.WRITE));
		assertTrue(validLogin.hasPermission(Login.class, PermissionHibernate.CREATE));
		
		assertTrue(validLogin.hasPermission(Person.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(Person.class, PermissionHibernate.READ));
		assertTrue(validLogin.hasPermission(Person.class, PermissionHibernate.WRITE));
		assertTrue(validLogin.hasPermission(Person.class, PermissionHibernate.CREATE));
		
		assertTrue(validLogin.hasPermission(TrialSite.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(TrialSite.class, PermissionHibernate.READ));
		assertTrue(validLogin.hasPermission(TrialSite.class, PermissionHibernate.WRITE));
		assertTrue(validLogin.hasPermission(TrialSite.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(Trial.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(Trial.class, PermissionHibernate.READ));
		assertTrue(validLogin.hasPermission(Trial.class, PermissionHibernate.WRITE));
		assertTrue(validLogin.hasPermission(Trial.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.WRITE));
		assertTrue(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.CREATE));
	}
	
	
	@Test
	public void testHasPermissionsAdmin(){
		validLogin.setRoles(new HashSet<Role>());
		validLogin.addRole(Role.ROLE_ADMIN);
		
		assertTrue(validLogin.hasPermission(Login.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(Login.class, PermissionHibernate.READ));
		assertTrue(validLogin.hasPermission(Login.class, PermissionHibernate.WRITE));
		assertTrue(validLogin.hasPermission(Login.class, PermissionHibernate.CREATE));
		
		assertTrue(validLogin.hasPermission(Person.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(Person.class, PermissionHibernate.READ));
		assertTrue(validLogin.hasPermission(Person.class, PermissionHibernate.WRITE));
		assertTrue(validLogin.hasPermission(Person.class, PermissionHibernate.CREATE));
		
		assertTrue(validLogin.hasPermission(TrialSite.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(TrialSite.class, PermissionHibernate.READ));
		assertTrue(validLogin.hasPermission(TrialSite.class, PermissionHibernate.WRITE));
		assertTrue(validLogin.hasPermission(TrialSite.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(Trial.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(Trial.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(Trial.class, PermissionHibernate.WRITE));
		assertFalse(validLogin.hasPermission(Trial.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.ADMINISTRATION));
		assertFalse(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.WRITE));
		assertFalse(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.CREATE));
	}
	
	
	@Test
	public void testHasPermissionsInvestigator(){
		validLogin.setRoles(new HashSet<Role>());
		validLogin.addRole(Role.ROLE_INVESTIGATOR);
		
		assertFalse(validLogin.hasPermission(Login.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(Login.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(Login.class, PermissionHibernate.WRITE));
		assertFalse(validLogin.hasPermission(Login.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(Person.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(Person.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(Person.class, PermissionHibernate.WRITE));
		assertFalse(validLogin.hasPermission(Person.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(TrialSite.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(TrialSite.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(TrialSite.class, PermissionHibernate.WRITE));
		assertFalse(validLogin.hasPermission(TrialSite.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(Trial.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(Trial.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(Trial.class, PermissionHibernate.WRITE));
		assertFalse(validLogin.hasPermission(Trial.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.ADMINISTRATION));
		assertFalse(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.WRITE));
		assertTrue(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.CREATE));
	}
	
	@Test
	public void testHasPermissionsPInvestigator(){
		validLogin.setRoles(new HashSet<Role>());
		validLogin.addRole(Role.ROLE_P_INVESTIGATOR);
		
		assertFalse(validLogin.hasPermission(Login.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(Login.class, PermissionHibernate.READ));
		assertTrue(validLogin.hasPermission(Login.class, PermissionHibernate.WRITE));
		assertTrue(validLogin.hasPermission(Login.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(Person.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(Person.class, PermissionHibernate.READ));
		assertTrue(validLogin.hasPermission(Person.class, PermissionHibernate.WRITE));
		assertTrue(validLogin.hasPermission(Person.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(TrialSite.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(TrialSite.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(TrialSite.class, PermissionHibernate.WRITE));
		assertFalse(validLogin.hasPermission(TrialSite.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(Trial.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(Trial.class, PermissionHibernate.READ));
		assertTrue(validLogin.hasPermission(Trial.class, PermissionHibernate.WRITE));
		assertTrue(validLogin.hasPermission(Trial.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.WRITE));
		assertFalse(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.CREATE));
	}
	
	
	@Test
	public void testHasPermissionsMonitor(){
		validLogin.setRoles(new HashSet<Role>());
		validLogin.addRole(Role.ROLE_MONITOR);
		
		assertFalse(validLogin.hasPermission(Login.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(Login.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(Login.class, PermissionHibernate.WRITE));
		assertFalse(validLogin.hasPermission(Login.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(Person.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(Person.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(Person.class, PermissionHibernate.WRITE));
		assertFalse(validLogin.hasPermission(Person.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(TrialSite.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(TrialSite.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(TrialSite.class, PermissionHibernate.WRITE));
		assertFalse(validLogin.hasPermission(TrialSite.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(Trial.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(Trial.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(Trial.class, PermissionHibernate.WRITE));
		assertFalse(validLogin.hasPermission(Trial.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.WRITE));
		assertTrue(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.WRITE));
		assertFalse(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.CREATE));
	}
	
	
	@Test
	public void testHasPermissionsStatistican(){
		validLogin.setRoles(new HashSet<Role>());
		validLogin.addRole(Role.ROLE_STATISTICAN);
		
		assertFalse(validLogin.hasPermission(Login.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(Login.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(Login.class, PermissionHibernate.WRITE));
		assertFalse(validLogin.hasPermission(Login.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(Person.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(Person.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(Person.class, PermissionHibernate.WRITE));
		assertFalse(validLogin.hasPermission(Person.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(TrialSite.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(TrialSite.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(TrialSite.class, PermissionHibernate.WRITE));
		assertFalse(validLogin.hasPermission(TrialSite.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(Trial.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(Trial.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(Trial.class, PermissionHibernate.WRITE));
		assertFalse(validLogin.hasPermission(Trial.class, PermissionHibernate.CREATE));
		
		assertFalse(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.ADMINISTRATION));
		assertTrue(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.READ));
		assertFalse(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.WRITE));
		assertFalse(validLogin.hasPermission(TrialSubject.class, PermissionHibernate.CREATE));
	}
	
	
	@Test
	public void testRemoveRole(){
		assertEquals(0, validLogin.getRoles().size());		
		validLogin.addRole(Role.ROLE_ADMIN);
		validLogin.addRole(Role.ROLE_INVESTIGATOR);
		validLogin.addRole(Role.ROLE_P_INVESTIGATOR);
		//Role User is added automatically
		assertEquals(4, validLogin.getRoles().size());
		validLogin.removeRole(Role.ROLE_INVESTIGATOR);
		assertEquals(3, validLogin.getRoles().size());
		validLogin.removeRole(Role.ROLE_P_INVESTIGATOR);
		assertEquals(2, validLogin.getRoles().size());
		validLogin.removeRole(Role.ROLE_ANONYMOUS);
		assertEquals(2, validLogin.getRoles().size());
		assertTrue(validLogin.getRoles().contains(Role.ROLE_ADMIN));
		assertTrue(validLogin.getRoles().contains(Role.ROLE_USER));
	}
	
	
	@Test
	public void testToString(){
		assertNotNull(validLogin.toString());
	}
	
	@Test
	public void testEqualsHashCode(){
		Login login1 = new Login();
		Login login2 = new Login();
		login1.setId(0);
		login2.setId(0);
		login1.setVersion(0);
		login2.setVersion(0);
		assertEquals(login1, login2);
		assertEquals(login1.hashCode(), login2.hashCode());
		login1.setId(1);
		
		assertFalse(login1.equals(login2));
		login1.setId(0);
		assertEquals(login1, login2);
		assertEquals(login1.hashCode(), login2.hashCode());
		
		login1.setVersion(1);
		assertFalse(login1.equals(login2));
		login1.setVersion(0);
		assertEquals(login1, login2);
		assertEquals(login1.hashCode(), login2.hashCode());
		
		login1.setUsername("test");
		assertFalse(login1.equals(login2));
		login2.setUsername("test");
		assertEquals(login1, login2);
		assertEquals(login1.hashCode(), login2.hashCode());
		
		assertFalse(login1.equals(null));
		assertFalse(login1.equals(new TreatmentArm()));
	}
}
