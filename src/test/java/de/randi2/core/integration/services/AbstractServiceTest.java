package de.randi2.core.integration.services;

import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.context.ManagedSessionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.dao.SystemWideSaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.Login;
import de.randi2.testUtility.utility.DomainObjectFactory;
import de.randi2.testUtility.utility.InitializeDatabaseUtil;
import de.randi2.utility.security.RolesAndRights;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/service-test.xml",
		"/META-INF/subconfig/test.xml" })
public abstract class AbstractServiceTest {

	@Autowired
	protected SessionFactory sessionFactory;
	@Autowired
	protected DomainObjectFactory factory;
	@Autowired
	protected RolesAndRights rolesAndRights;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private SystemWideSaltSource saltSource;

	@Autowired
	private InitializeDatabaseUtil databaseUtil;
	
	protected Login user;

	@Before
	public void setUp() {
		ManagedSessionContext.bind(sessionFactory.openSession());
		try {
			databaseUtil.setUpDatabaseUserAndTrialSites();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@After
	public void afterTest() {
		SecurityContextHolder.getContext().setAuthentication(null);
		ManagedSessionContext.unbind(sessionFactory);
	}

	protected void authenticatAsAdmin() {
		user = findLogin("admin@trialsite1.de");
		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				"admin@trialsite1.de", user, new ArrayList<GrantedAuthority>(
						user.getAuthorities()));
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);
	}
	
	protected void authenticatAsPrincipalInvestigator() {
		user = findLogin("p_investigator@trialsite1.de");
		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				"admin@trialsite1.de", user, new ArrayList<GrantedAuthority>(
						user.getAuthorities()));
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);
	}
	
	protected void authenticatAsInvestigator() {
		user = findLogin("investigator@trialsite1.de");
		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				"admin@trialsite1.de", user, new ArrayList<GrantedAuthority>(
						user.getAuthorities()));
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);
	}

	@SuppressWarnings("unchecked")
	protected Login findLogin(String username) {
		String query = "from de.randi2.model.Login login where "
				+ "login.username =?";

		List<Login> loginList = (List) sessionFactory.getCurrentSession()
				.createQuery(query).setParameter(0, username).list();
		if (loginList.size() == 1)
			return loginList.get(0);
		else
			return null;
	}
}
