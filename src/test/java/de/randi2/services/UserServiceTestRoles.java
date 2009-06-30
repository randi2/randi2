package de.randi2.services;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;

import de.randi2.dao.LoginDao;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.test.utility.DomainObjectFactory;
import de.randi2.test.utility.TestStringUtil;

public class UserServiceTestRoles extends AbstractServiceTest {

	@Autowired
	private UserService userService;
	@Autowired
	private DomainObjectFactory factory;
	@Autowired
	private TestStringUtil stringUtil;
	@Autowired
	private ApplicationContext context;
	@Autowired
	private LoginDao loginDao;

	@Override
	public void setUp() {
		super.setUp();
		for (int i = 0; i < 10; i++) {
			Login login = factory.getLogin();
			login.getPerson().setTrialSite(null);
			loginDao.create(login);
		}
	}

	@Test
	public void testGetAllAdmin() {

		List<Login> loginsTemp = sessionFactory.getCurrentSession()
				.createQuery("from Login").list();
		List<Login> logins = userService.getAll();
		assertEquals(loginsTemp.size(), logins.size());
		assertTrue(loginsTemp.containsAll(logins));
		assertTrue(logins.containsAll(loginsTemp));
	}
	
	
	@Test
	public void testGetAllInvestigator() {
		List<Login> loginsTemp = sessionFactory.getCurrentSession()
		.createQuery("from Login").list();
		authenticatAsInvestigator();
		List<Login> logins = userService.getAll();
		assertEquals(2, logins.size());
		Login user = findLogin("investigator@test.de");
		authenticatAsAdmin();
		for(int i = 0 ; i< 10 ;i++ ){
			Login l = factory.getLogin();
			l.getPerson().setTrialSite(user.getPerson().getTrialSite());
			userService.create(l);
		}
		authenticatAsInvestigator();
		logins = userService.getAll();
		assertEquals(12,logins.size());
		authenticatAsAdmin();
		logins = userService.getAll();
		//-1 admin is in both results
		assertEquals(12+loginsTemp.size()-1,logins.size());
	}
	
	
	private void authenticatAsInvestigator() {
	
		Login user = findLogin("investigator@test.de");
		if(user  == null){
			authenticatAsAdmin();
		Login newUser = factory.getLogin();
		newUser.setUsername("investigator@test.de");
		newUser.setPerson(factory.getPerson());
		newUser.getPerson().setTrialSite(findLogin("admin@test.de").getPerson().getTrialSite());
		newUser.addRole(Role.ROLE_INVESTIGATOR);
		userService.create(newUser);
		user = newUser;
		}
		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				"investigatorUser", user, user.getAuthorities());
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);
	}

	private void authenticatAsAnonymous() {
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
