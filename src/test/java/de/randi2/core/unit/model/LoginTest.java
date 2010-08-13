package de.randi2.core.unit.model;

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
import org.springframework.security.core.GrantedAuthority;

import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.TrialSubject;
import de.randi2.model.exceptions.ValidationException;
import de.randi2.model.security.PermissionHibernate;
import de.randi2.testUtility.utility.AbstractDomainTest;

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
	
	
	@Test
	public void testUsernameNotNull(){
		validLogin.setUsername(null);
		assertInvalid(validLogin);
	}
	
	@Test
	public void testUsernameNotEmpty(){
		validLogin.setUsername("");
		assertInvalid(validLogin);
	}
	
	@Test
	public void testUsernameNotShorterThan5(){
		assertEquals(5, Login.MIN_USERNAME_LENGTH);
		String[] invalidUsernames = {"a@d.d","sada", stringUtil.getWithLength(Login.MIN_USERNAME_LENGTH), null, ""};
		for (String s: invalidUsernames){
			validLogin.setUsername(s);
			assertInvalid(validLogin,s);
		}
	}
	
	
	@Test
	public void testUsernameNotLongerThan100(){
		assertEquals(100, Login.MAX_USERNAME_LENGTH);
		String[] invalidUsernames = {stringUtil.getWithLength(95)+"@da.de", stringUtil.getWithLength(120), stringUtil.getWithLength(Login.MAX_USERNAME_LENGTH+1)};
		for (String s: invalidUsernames){
			validLogin.setUsername(s);
			assertInvalid(validLogin,s);
		}
	}
	
	
	
	
	
	@Test
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
	public void testPasswordNotNull(){
		validLogin.setPassword(null);
		assertInvalid(validLogin);
	}
	
	@Test
	public void testPasswordNotEmpty(){
		validLogin.setPassword("");
		assertInvalid(validLogin);
	}
	
	
	@Test
	public void testPasswordNotShorterThan8(){
		assertEquals(8, Login.MIN_PASSWORD_LENGTH);
		String[] invalidPasswords = {"ecet0$s","sad.a", stringUtil.getWithLength(Login.MIN_PASSWORD_LENGTH-3)+",3", null, ""};
		for (String s: invalidPasswords){
			validLogin.setPassword(s);
			assertInvalid(validLogin,s);
		}
	}
	
	@Test
	public void testPasswordNotLongerThan30(){
		assertEquals(30, Login.MAX_PASSWORD_LENGTH);
		String[] invalidPasswords = {stringUtil.getWithLength(Login.MAX_PASSWORD_LENGTH)+ "$1", stringUtil.getWithLength(28)+"$t3"};
		for (String s: invalidPasswords){
			validLogin.setPassword(s);
			assertInvalid(validLogin, s);
		}
	}
	
	@Test
	public void testPasswordLengthHashedValueEquals64(){
		assertEquals(64, Login.HASH_PASSWORD_LENGTH);
		String[] validPasswords = {stringUtil.getWithLength(Login.HASH_PASSWORD_LENGTH), stringUtil.getWithLength(64)};
		for (String s: validPasswords){
			validLogin.setPassword(s);
			assertValid(validLogin);
		}
	}
	
	@Test
	public void testPasswordLengthHashedValueUnequals64(){
		assertEquals(64, Login.HASH_PASSWORD_LENGTH);
		String[] invalidPasswords = {stringUtil.getWithLength(Login.HASH_PASSWORD_LENGTH+1), stringUtil.getWithLength(150), stringUtil.getWithLength(65), stringUtil.getWithLength(63), stringUtil.getWithLength(34), stringUtil.getWithLength(20)};
		for (String s: invalidPasswords){
			validLogin.setPassword(s);
			assertInvalid(validLogin, s);
		}
	}
	
	
	@Test
	public void testPasswordWithCorrectLengthAndWithoutSpecialSign(){
		String[] invalidPasswords = {"secret0secret","sad.alhljhaslf",stringUtil.getWithLength(Login.MAX_PASSWORD_LENGTH-2)+"z2", stringUtil.getWithLength(Login.MIN_PASSWORD_LENGTH-2)+"h3"};
		for (String s: invalidPasswords){
			validLogin.setPassword(s);
			assertInvalid(validLogin,s);
		}
	}
	
	@Test
	public void testPasswordWithCorrectLengthAndSpecialSigns(){
		String[] validPasswords = {"secret0$secret","sad.alhl3jhaslf",stringUtil.getWithLength(Login.MAX_PASSWORD_LENGTH-2)+";2", stringUtil.getWithLength(Login.MIN_PASSWORD_LENGTH-2)+"/3"};
		for (String s: validPasswords){
			validLogin.setPassword(s);
			assertValid(validLogin);
		}
	}
	
	@Test
	public void testPersonNotNull(){
		validLogin.setPerson(null);
		assertInvalid(validLogin);
	}
	
	@Test
	public void testCorrectPerson(){
		Person p = factory.getPerson();
		validLogin.setPerson(p);
		assertNotNull(validLogin.getPerson());
		assertEquals(p,validLogin.getPerson());
		assertValid(validLogin);
	}
	
	
	
	@Test
	public void testActiveSetTrueAndFalse(){
		validLogin.setActive(true);
		assertTrue(validLogin.isActive());
		
		validLogin.setActive(false);
		assertFalse(validLogin.isActive());
	}
	
	
	@Test
	public void testPrefLocale(){
		validLogin.setPrefLocale(Locale.GERMAN);
		assertEquals(Locale.GERMAN, validLogin.getPrefLocale());
		assertValid(validLogin);
		validLogin.setPrefLocale(null);
		assertValid(validLogin);
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
	public void testLastLoggedIn(){
		GregorianCalendar calendar = new GregorianCalendar();
		validLogin.setLastLoggedIn(calendar);
		assertEquals(calendar, validLogin.getLastLoggedIn());
	}
	
	@Test
	public void testLockTime(){
		GregorianCalendar calendar = new GregorianCalendar();
		validLogin.setLockTime(calendar);
		assertEquals(calendar, validLogin.getLockTime());
	}
	
	@Test
	public void testNumberWrongLogins(){
		assertEquals(0, validLogin.getNumberWrongLogins());
		validLogin.setNumberWrongLogins((byte)5);
		assertEquals(5, validLogin.getNumberWrongLogins());
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
		assertTrue(validLogin.hasRole(Role.ROLE_ADMIN));
		assertTrue(validLogin.hasRole(Role.ROLE_INVESTIGATOR));
		assertTrue(validLogin.hasRole(Role.ROLE_P_INVESTIGATOR));
		assertTrue(validLogin.hasRole(Role.ROLE_USER));
		
		validLogin.removeRole(Role.ROLE_INVESTIGATOR);
		assertEquals(3, validLogin.getRoles().size());
		assertTrue(validLogin.hasRole(Role.ROLE_ADMIN));
		assertTrue(validLogin.hasRole(Role.ROLE_P_INVESTIGATOR));
		assertTrue(validLogin.hasRole(Role.ROLE_USER));
		
		validLogin.removeRole(Role.ROLE_P_INVESTIGATOR);
		assertEquals(2, validLogin.getRoles().size());
		assertTrue(validLogin.hasRole(Role.ROLE_ADMIN));
		assertTrue(validLogin.hasRole(Role.ROLE_USER));
		
		validLogin.removeRole(Role.ROLE_ANONYMOUS);
		assertEquals(2, validLogin.getRoles().size());
		assertTrue(validLogin.getRoles().contains(Role.ROLE_ADMIN));
		assertTrue(validLogin.getRoles().contains(Role.ROLE_USER));
	}
	
	
	@Test
	public void testAddNormalRoleEmptyRoleSet(){
		assertTrue(validLogin.getRoles().isEmpty());
		validLogin.addRole(Role.ROLE_ADMIN);
		assertEquals(2, validLogin.getRoles().size());
		assertTrue(validLogin.getRoles().contains(Role.ROLE_ADMIN));
		assertTrue(validLogin.getRoles().contains(Role.ROLE_USER));
	}
	
	@Test
	public void testAddNormalRoleWithAnonymousRoleInRoleSet(){
		assertTrue(validLogin.getRoles().isEmpty());
		validLogin.addRole(Role.ROLE_ANONYMOUS);
		assertEquals(1, validLogin.getRoles().size());
		assertTrue(validLogin.getRoles().contains(Role.ROLE_ANONYMOUS));
		validLogin.addRole(Role.ROLE_ADMIN);
		assertEquals(2, validLogin.getRoles().size());
		assertTrue(validLogin.getRoles().contains(Role.ROLE_ADMIN));
		assertTrue(validLogin.getRoles().contains(Role.ROLE_USER));
	}
	
	@Test
	public void testAddNormalRole(){
		assertTrue(validLogin.getRoles().isEmpty());
		validLogin.addRole(Role.ROLE_ADMIN);
		validLogin.addRole(Role.ROLE_INVESTIGATOR);
		assertEquals(3, validLogin.getRoles().size());
		assertTrue(validLogin.getRoles().contains(Role.ROLE_ADMIN));
		assertTrue(validLogin.getRoles().contains(Role.ROLE_USER));
		assertTrue(validLogin.getRoles().contains(Role.ROLE_INVESTIGATOR));
	}
	
	
	@Test
	public void testAddAnonymousRole(){
		assertTrue(validLogin.getRoles().isEmpty());
		validLogin.addRole(Role.ROLE_ANONYMOUS);
		assertEquals(1, validLogin.getRoles().size());
		assertTrue(validLogin.getRoles().contains(Role.ROLE_ANONYMOUS));
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
	
	@Test
	public void testUIName(){
		assertEquals(validLogin.getPerson().getSurname() + ", " + validLogin.getPerson().getFirstname(), validLogin.getUIName());
	}
}
