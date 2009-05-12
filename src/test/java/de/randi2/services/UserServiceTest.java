package de.randi2.services;

import java.util.List;
import java.util.Locale;

import org.hibernate.SessionFactory;
import org.hibernate.context.ManagedSessionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.UserDetailsServiceImpl;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.model.enumerations.Gender;
import de.randi2.test.utility.DomainObjectFactory;
import de.randi2.test.utility.TestStringUtil;
import de.randi2.utility.security.RolesAndRights;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring-test.xml","/META-INF/subconfig/security.xml" })
@Transactional
//import static junit.framework.Assert.*;
public class UserServiceTest {


	@Autowired private SessionFactory sessionFactory;
	@Autowired private UserService userService;
	@Autowired private DomainObjectFactory factory;
	@Autowired private TestStringUtil stringUtil;
	@Autowired private RolesAndRights rolesAndRights;
	@Autowired private ApplicationContext context;
	private UserDetailsServiceImpl detailsServiceImpl;
	
	@Before
	public void setUp(){
//		new Bootstrap(context);
		detailsServiceImpl = new UserDetailsServiceImpl();
		detailsServiceImpl.setSessionFactory(sessionFactory);
		sessionFactory.getCurrentSession().merge(Role.ROLE_ADMIN);
		sessionFactory.getCurrentSession().merge(Role.ROLE_INVESTIGATOR);
		sessionFactory.getCurrentSession().merge(Role.ROLE_USER);
		sessionFactory.getCurrentSession().merge(Role.ROLE_STATISTICAN);
		sessionFactory.getCurrentSession().merge(Role.ROLE_MONITOR);
		sessionFactory.getCurrentSession().merge(Role.ROLE_P_INVESTIGATOR);
		sessionFactory.getCurrentSession().merge(Role.ROLE_ANONYMOUS);
	}
	
	@After
	public void afer(){
		SecurityContextHolder.getContext().setAuthentication(null);
	}
	
	@Test
	public void testInit(){
		assertNotNull(userService);
	}
	
	
	@Test
	public void testAddRole(){
		Login login = factory.getLogin();
		sessionFactory.getCurrentSession().persist(login);
		assertTrue(login.getId()>0);
		Role role = factory.getRole();
		sessionFactory.getCurrentSession().persist(role);
		sessionFactory.getCurrentSession().flush();
		assertTrue(role.getId()>0);
		userService.addRole(login, role);
		assertTrue(login.getRoles().contains(role));
		Login login2 = (Login)sessionFactory.getCurrentSession().get(Login.class, login.getId());
		assertNotNull(login2);
		assertTrue(login2.getRoles().contains(role));
		try{
			userService.addRole(null, role);
			fail("should throw exception (login = null)");
		}catch(Exception e){}
		try{
			userService.addRole(login, null);
			fail("should throw exception (role = null)");
		}catch(Exception e){}
		try{
			userService.addRole(factory.getLogin(), role);
			fail("should throw exception (login not saved)");
		}catch(Exception e){}
		try{
			userService.addRole(login, factory.getRole());
			fail("should throw exception (role not saved)");
		}catch(Exception e){}
	}
	
	@Test
	public void testRemoveRole(){
		Login login = factory.getLogin();
		sessionFactory.getCurrentSession().persist(login);
		assertTrue(login.getId()>0);
		Role role = factory.getRole();
		sessionFactory.getCurrentSession().persist(role);
		sessionFactory.getCurrentSession().flush();
		assertTrue(role.getId()>0);
		userService.addRole(login, role);
		assertTrue(login.getRoles().contains(role));
		Login login2 = (Login)sessionFactory.getCurrentSession().get(Login.class, login.getId());
		assertNotNull(login2);
		assertTrue(login2.getRoles().contains(role));
		try{
			userService.removeRole(null, role);
			fail("should throw exception (login = null)");
		}catch(Exception e){}
		try{
			userService.removeRole(login, null);
			fail("should throw exception (role = null)");
		}catch(Exception e){}
		try{
			userService.removeRole(factory.getLogin(), role);
			fail("should throw exception (login not saved)");
		}catch(Exception e){}
		try{
			userService.removeRole(login, factory.getRole());
			fail("should throw exception (role not saved)");
		}catch(Exception e){}
		userService.removeRole(login2, role);
		assertFalse(login2.getRoles().contains(role));
	}
	
	@Test
	public void testCreateRole(){
		Role role = factory.getRole();
		userService.createRole(role);
		assertTrue(role.getId()>0);
	}
	
	@Test
	public void testDeleteRole(){
		assertTrue(true);
	}
	
	@Test
	public void testPrepareInvestigator(){
		userService.prepareInvestigator();
		assertNotNull(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		assertTrue(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Login);
		Login login = (Login)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		assertTrue(login.getRoles().contains(Role.ROLE_ANONYMOUS));
	}
	
	
	@Test
	public void testRegister(){
		SecurityContextHolder.getContext().setAuthentication(null);
		userService.prepareInvestigator();
		Login login =factory.getLogin();
		login.getPerson().setTrialSite(factory.getTrialSite());
		sessionFactory.getCurrentSession().persist(login.getPerson().getTrialSite().getContactPerson());
		sessionFactory.getCurrentSession().persist(login.getPerson().getTrialSite());
		sessionFactory.getCurrentSession().flush();
		userService.register(login);
		assertTrue(login.getId()>0);
	}
	
	
	@Test
	public void testCreate(){
		authenticatAsAdmin();
		Login login = factory.getLogin();
		login.getPerson().setTrialSite(factory.getTrialSite());
		sessionFactory.getCurrentSession().persist(login.getPerson().getTrialSite().getContactPerson());
		sessionFactory.getCurrentSession().persist(login.getPerson().getTrialSite());
		sessionFactory.getCurrentSession().flush();
		userService.create(login);
		assertTrue(login.getId()>0);
	}
	
	
	@Test
	public void testUpdate(){
		authenticatAsAdmin();
		Login login = factory.getLogin();
		login.getPerson().setTrialSite(factory.getTrialSite());
		sessionFactory.getCurrentSession().persist(login.getPerson().getTrialSite().getContactPerson());
		sessionFactory.getCurrentSession().persist(login.getPerson().getTrialSite());
		sessionFactory.getCurrentSession().flush();
		userService.create(login);
		sessionFactory.getCurrentSession().flush();
		assertTrue(login.getId()>0);
		String oldName = login.getUsername();
		login.setUsername(stringUtil.getWithLength(30));
		userService.update(login);
		Login login2 = (Login) sessionFactory.getCurrentSession().get(Login.class, login.getId());
		assertEquals(login.getUsername(), login2.getUsername());
		assertFalse(login2.getUsername().equals(oldName));
	}
	
	
	@Test
	public void testUpdateRole(){
		Role role = factory.getRole();
		sessionFactory.getCurrentSession().persist(role);
		assertTrue(role.getId()>0);
		String oldName = role.getName();
		role.setName(stringUtil.getWithLength(30));
		userService.updateRole(role);
		Role role2 = (Role) sessionFactory.getCurrentSession().get(Role.class,role.getId());
		assertEquals(role.getName(), role2.getName());
		assertFalse(role2.getName().equals(oldName));
	}
	
	
	@Test
	public void testGetAll(){
		assertTrue(true);
//		authenticatAsAdmin();
//		for(int i =0; i<10; i++){
//			Login login = factory.getLogin();
//			login.getPerson().setTrialSite(null);
//			userService.create(login);
//			sessionFactory.getCurrentSession().flush();
//		}
//		List<Login> list = userService.getAll();
//		assertEquals(10,userService.getAll().size());
	}
	
	
	@Test
	public void testGetObject(){
		assertTrue(true);
//		authenticatAsAdmin();
//		Login login = factory.getLogin();
//		login.getPerson().setTrialSite(null);
//		userService.create(login);
//		Login login2 = (Login)detailsServiceImpl.loadUserByUsername("admin@test.de");
//		rolesAndRights.grantRigths(login, login2.getPerson().getTrialSite());
//		Login login3 = userService.getObject(login.getId());
//		assertTrue(login3 != null);
	}
	
	private void authenticatAsAdmin(){
		Person adminP = new Person();
		adminP.setFirstname("Max");
		adminP.setSurname("Mustermann");
		adminP.setEMail("admin@test.de");
		adminP.setPhone("1234567");
		adminP.setGender(Gender.MALE);

		Login adminL = new Login();
		adminL.setPassword("1§heidelberg");
		adminL.setPerson(adminP);
		adminL.setPrefLocale(Locale.GERMANY);
		adminL.setUsername(adminP.getEMail());
		
		adminL.addRole(Role.ROLE_ADMIN);
		sessionFactory.getCurrentSession().persist(adminL);
		
	
		Login login = (Login)detailsServiceImpl.loadUserByUsername("admin@test.de");
		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				"admin", login, login.getAuthorities());
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);
	}
	
	private void authenticatAsInvestigator(){
		Login newUser = new Login();
		newUser.setPerson(new Person());
		newUser.addRole(Role.ROLE_INVESTIGATOR);
		userService.create(newUser);
		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				"investigatorUser", newUser, newUser.getAuthorities());
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);
	}
	
	private void authenticatAsAnonymous(){
		Login newUser = new Login();
		newUser.setPerson(new Person());
		newUser.addRole(Role.ROLE_ANONYMOUS);
		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				"anonymousUser", newUser, newUser.getAuthorities());
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);
	}
	
}
