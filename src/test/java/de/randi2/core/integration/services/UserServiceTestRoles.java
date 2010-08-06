package de.randi2.core.integration.services;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import de.randi2.dao.LoginDao;
import de.randi2.model.Login;
import de.randi2.model.Role;
import de.randi2.services.UserService;
import de.randi2.testUtility.utility.DomainObjectFactory;

public class UserServiceTestRoles extends AbstractServiceTest {

	@Autowired
	private UserService userService;
	@Autowired
	private DomainObjectFactory factory;
	@Autowired
	private LoginDao loginDao;

	@Override
	public void setUp() {
		super.setUp();
		authenticatAsAdmin();
		for (int i = 0; i < 10; i++) {
			Login login = factory.getLogin();
			login.getPerson().setTrialSite(null);
			loginDao.create(login);
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllAdmin() {
		authenticatAsAdmin();
		List<Login> loginsTemp = sessionFactory.getCurrentSession()
				.createQuery("from Login").list();
		List<Login> logins = userService.getAll();
		assertEquals(loginsTemp.size(), logins.size());
		assertTrue(loginsTemp.containsAll(logins));
		assertTrue(logins.containsAll(loginsTemp));
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllInvestigator() {
		authenticatAsAdmin();
		List<Login> loginsTemp = sessionFactory.getCurrentSession()
		.createQuery("from Login").list();
		authenticatAsInvestigator();
		List<Login> logins = userService.getAll();
		assertEquals(loginsTemp.size(), logins.size());
		authenticatAsAdmin();
		for(int i = 0 ; i< 10 ;i++ ){
			Login l = factory.getLogin();
			l.getPerson().setTrialSite(user.getPerson().getTrialSite());
			userService.create(l);
		}
		authenticatAsInvestigator();
		logins = userService.getAll();
		assertEquals(10+loginsTemp.size(),logins.size());
		authenticatAsAdmin();
		logins = userService.getAll();
		assertEquals(10+loginsTemp.size(),logins.size());
	}
	

//	private void authenticatAsAnonymous() {
//		Login newUser = new Login();
//		newUser.setPerson(new Person());
//		newUser.addRole(Role.ROLE_ANONYMOUS);
//		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
//				"anonymousUser", newUser, newUser.getAuthorities());
//		// Perform authentication
//		SecurityContextHolder.getContext().setAuthentication(authToken);
//		SecurityContextHolder.getContext().getAuthentication()
//				.setAuthenticated(true);
//	}
}
