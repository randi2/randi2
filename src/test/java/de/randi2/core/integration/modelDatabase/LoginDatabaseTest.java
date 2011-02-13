package de.randi2.core.integration.modelDatabase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.model.TrialSite;
import de.randi2.testUtility.utility.AbstractDomainDatabaseTest;

@Transactional
public class LoginDatabaseTest extends AbstractDomainDatabaseTest<Login> {

	
	private Login validLogin;
	
	
	public LoginDatabaseTest() {
		super(Login.class);
	}

	@Before
	public void setUp(){
		super.setUp();
		validLogin = factory.getLogin();
	}
	
	
	@Test
	public void testPerson(){
		Person p = factory.getPerson();
		validLogin.setPerson(p);
		assertNotNull(validLogin.getPerson());
		
		
		entityManager.persist(validLogin);
		
		Login l = entityManager.find(Login.class, validLogin.getId());
		assertNotNull(l);
		assertEquals(validLogin.getUsername(), l.getUsername());
		assertNotNull(l.getPerson());
		assertEquals(validLogin.getPerson().getId(), l.getPerson().getId());
	
	}
	
	
	@Test
	public void testLocale(){
		validLogin.setPrefLocale(Locale.GERMAN);
		assertEquals(Locale.GERMAN, validLogin.getPrefLocale());
		
		entityManager.persist(validLogin);
		assertTrue(validLogin.getId()!=AbstractDomainObject.NOT_YET_SAVED_ID);
		Login l = entityManager.find(Login.class, validLogin.getId());
		assertEquals(validLogin.getId(), l.getId());
		assertEquals(Locale.GERMAN, l.getPrefLocale());
	}
	
	

	@Test
	public void testRole(){
		initializeRoles();
		validLogin.addRole(Role.ROLE_ADMIN);
		entityManager.persist(validLogin);
		entityManager.flush();
		entityManager.clear();
		assertTrue(validLogin.getId()!=AbstractDomainObject.NOT_YET_SAVED_ID);
		Login l = entityManager.find(Login.class, validLogin.getId());
		assertEquals(validLogin.getId(), l.getId());
		assertEquals(2, l.getRoles().size());
		assertTrue(l.getRoles().contains(Role.ROLE_USER));
		assertTrue(l.getRoles().contains(Role.ROLE_ADMIN));
		l.addRole(Role.ROLE_INVESTIGATOR);
		entityManager.merge(l);
		entityManager.flush();
		entityManager.clear();
		Login l1 = entityManager.find(Login.class, validLogin.getId());
		assertEquals(validLogin.getId(), l.getId());
		assertEquals(3, l1.getRoles().size());
		assertTrue(l1.getRoles().contains(Role.ROLE_USER));
		assertTrue(l1.getRoles().contains(Role.ROLE_ADMIN));
		assertTrue(l1.getRoles().contains(Role.ROLE_INVESTIGATOR));
		l1.removeRole(Role.ROLE_ADMIN);
		entityManager.merge(l1);
		entityManager.flush();
		entityManager.clear();
		Login l2 = entityManager.find(Login.class, validLogin.getId());
		assertEquals(validLogin.getId(), l.getId());
		assertEquals(2, l2.getRoles().size());
		assertTrue(l2.getRoles().contains(Role.ROLE_USER));
		assertTrue(l2.getRoles().contains(Role.ROLE_INVESTIGATOR));
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	private void initializeRoles(){
		entityManager.persist(Role.ROLE_INVESTIGATOR);
		entityManager.persist(Role.ROLE_USER);
		entityManager.persist(Role.ROLE_STATISTICAN);
		entityManager.persist(Role.ROLE_MONITOR);
		entityManager.persist(
				Role.ROLE_P_INVESTIGATOR);
		entityManager.persist(Role.ROLE_ANONYMOUS);
		entityManager.persist(Role.ROLE_ADMIN);
	}
	
	
	@Test
	public void databaseIntegrationTest() {
		
		validLogin.setLockTime(new GregorianCalendar());
		validLogin.setNumberWrongLogins(Login.MAX_WRONG_LOGINS);
		validLogin.setPrefLocale(Locale.ENGLISH);
		GregorianCalendar dateL = new GregorianCalendar(2008,2,1);
		validLogin.setLastLoggedIn(dateL);
		entityManager.persist(validLogin);
		entityManager.flush();
		assertTrue(validLogin.getId()>0);
		Login login = entityManager.find(Login.class, validLogin.getId());
		assertEquals(validLogin.getId(), login.getId());
		// FIXME: Clear out
		//assertEquals(Arrays.asList(validLogin.getAuthorities()), Arrays.asList(login.getAuthorities()));
		//FIXME: Depends on the SaveOrUpdate Listener
//		assertNotNull(login.getCreatedAt());
//		assertNotNull(login.getUpdatedAt());
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
	@Ignore
	public void namedQueryAllLoginsWithRolesAndNotTrialSiteScopeTest(){
		fail();
	}
	
	@Test
	@Ignore
	public void namedQueryLoginsWriteOtherUserTest(){
		fail();
	}
	
	@Test 
	@Ignore
	public void namedQueryLoginsWithPermissionTest(){
		fail();
	}
	
	@Test 
	public void namedQueryAllLoginsWithSpecificRoleTest(){
		/*
		 * generate test data
		 * 10 Logins with 2 roles
		 */
		List<Login> logins = new ArrayList<Login>();
		for(int i =0; i<10;i++){
			logins.add(factory.getLogin());
			entityManager.persist(logins.get(i));
		}
		Role role1 = new Role("ROLE_Name1", false, false, false, false, false,
				true, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, null);
		entityManager.persist(role1);
		Role role2 = new Role("ROLE_Name2", false, false, false, false, false,
				true, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, null);
		entityManager.persist(role2);
		
		//Logins 
		logins.get(0).getRoles().add(role1);
		logins.get(1).getRoles().add(role1);
		logins.get(2).getRoles().add(role2);
		logins.get(3).getRoles().add(role2);
		logins.get(4).getRoles().add(role1);//login 4 has two roles
		logins.get(4).getRoles().add(role2);
		//Logins
		logins.get(5).getRoles().add(role1);
		logins.get(6).getRoles().add(role1);
		logins.get(7).getRoles().add(role2);
		logins.get(8).getRoles().add(role2);
		logins.get(9).getRoles().add(role1);//login 9 has two roles
		logins.get(9).getRoles().add(role2);
		
		for(Login l: logins){
			l = entityManager.merge(l);
		}
		//Flush session and clear the entity manager
		entityManager.flush();
		entityManager.clear();
		//Test named query
		List<Login> result = entityManager.createNamedQuery("login.AllLoginsWithSpecificRole").setParameter(1, role1.getId()).getResultList();
		assertEquals(6, result.size());
		assertTrue(result.contains(logins.get(0)));
		assertTrue(result.contains(logins.get(1)));
		assertTrue(result.contains(logins.get(4)));
		assertTrue(result.contains(logins.get(5)));
		assertTrue(result.contains(logins.get(6)));
		assertTrue(result.contains(logins.get(9)));
		
		result = entityManager.createNamedQuery("login.AllLoginsWithSpecificRole").setParameter(1, role2.getId()).getResultList();
		assertEquals(6, result.size());
		assertTrue(result.contains(logins.get(2)));
		assertTrue(result.contains(logins.get(3)));
		assertTrue(result.contains(logins.get(4)));
		assertTrue(result.contains(logins.get(7)));
		assertTrue(result.contains(logins.get(8)));
		assertTrue(result.contains(logins.get(9)));


	}
	
	@Test 
	@Ignore(value="different behaviour if only the test class is executed")
	public void namedQueryAllLoginsWithSpecificRoleAndTrialSiteTest(){
		/*
		 * generate test data
		 * 10 Logins in two trial sites with 2 roles
		 * the first 5 logins in site1 the other in site2 
		 */
		List<Login> logins = new ArrayList<Login>();
		for(int i =0; i<10;i++){
			logins.add(factory.getLogin());
			entityManager.persist(logins.get(i));
		}
		TrialSite site1 = factory.getTrialSite();
		entityManager.persist(site1);
		TrialSite site2 = factory.getTrialSite();
		entityManager.persist(site2);
		Role role1 = new Role("ROLE_Name1", false, false, false, false, false,
				true, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, null);
		entityManager.persist(role1);
		Role role2 = new Role("ROLE_Name2", false, false, false, false, false,
				true, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, null);
		entityManager.persist(role2);
		
		for(int i=0;i< logins.size();i++){
			if(i<5){
				site1.getMembers().add(logins.get(i).getPerson());
			}else{
				site2.getMembers().add(logins.get(i).getPerson());
			}
		}
		site1 = entityManager.merge(site1);
		site2 = entityManager.merge(site2);
		//Logins site1
		logins.get(0).getRoles().add(role1);
		logins.get(1).getRoles().add(role1);
		logins.get(2).getRoles().add(role2);
		logins.get(3).getRoles().add(role2);
		logins.get(4).getRoles().add(role1);//login 4 has two roles
		logins.get(4).getRoles().add(role2);
		//Logins site2
		logins.get(5).getRoles().add(role1);
		logins.get(6).getRoles().add(role1);
		logins.get(7).getRoles().add(role2);
		logins.get(8).getRoles().add(role2);
		logins.get(9).getRoles().add(role1);//login 9 has two roles
		logins.get(9).getRoles().add(role2);
		
		for(Login l: logins){
			l = entityManager.merge(l);
		}
		//Flush session and clear the entity manager
		entityManager.flush();
		entityManager.clear();
		//Test named query
		List<Login> result = entityManager.createNamedQuery("login.AllLoginsWithSpecificRoleAndTrialSite").setParameter(1, role1.getId()).setParameter(2, site1.getId()).getResultList();
		assertEquals(3, result.size());
		assertTrue(result.contains(logins.get(0)));
		assertTrue(result.contains(logins.get(1)));
		assertTrue(result.contains(logins.get(4)));
		
		result = entityManager.createNamedQuery("login.AllLoginsWithSpecificRoleAndTrialSite").setParameter(1, role2.getId()).setParameter(2, site1.getId()).getResultList();
		assertEquals(3, result.size());
		assertTrue(result.contains(logins.get(2)));
		assertTrue(result.contains(logins.get(3)));
		assertTrue(result.contains(logins.get(4)));

		result = entityManager.createNamedQuery("login.AllLoginsWithSpecificRoleAndTrialSite").setParameter(1, role1.getId()).setParameter(2, site2.getId()).getResultList();
		assertEquals(3, result.size());
		assertTrue(result.contains(logins.get(5)));
		assertTrue(result.contains(logins.get(6)));
		assertTrue(result.contains(logins.get(9)));
		
		result = entityManager.createNamedQuery("login.AllLoginsWithSpecificRoleAndTrialSite").setParameter(1, role2.getId()).setParameter(2, site2.getId()).getResultList();
		assertEquals(3, result.size());
		assertTrue(result.contains(logins.get(7)));
		assertTrue(result.contains(logins.get(8)));
		assertTrue(result.contains(logins.get(9)));
		
	}
	
}
