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
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.LoginDao;
import de.randi2.model.Login;
import de.randi2.model.Role;
import de.randi2.model.TrialSite;
import de.randi2.services.UserService;
import de.randi2.testUtility.utility.DomainObjectFactory;

@Transactional
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
			loginDao.create(login);
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllAdmin() {
		authenticatAsAdmin(); 
		List<Login> loginsTemp = entityManager
				.createQuery("from Login").getResultList();
		List<Login> logins = userService.getAll();
		assertEquals(loginsTemp.size(), logins.size());
		assertTrue(loginsTemp.containsAll(logins));
		assertTrue(logins.containsAll(loginsTemp));
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllInvestigator() {
		TrialSite site = factory.getTrialSite();
		entityManager.persist(site);
		entityManager.flush();
		authenticatAsAdmin();
		List<Login> loginsTemp = entityManager
		.createQuery("from Login").getResultList();
		authenticatAsInvestigator();
		List<Login> logins = userService.getAll();
		assertEquals(loginsTemp.size(), logins.size());
		authenticatAsAdmin();
		for(int i = 0 ; i< 10 ;i++ ){
			Login l = factory.getLogin();
			userService.create(l, site);
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
