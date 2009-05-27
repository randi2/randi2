package de.randi2.services;

import java.util.List;
import java.util.Locale;

import org.hibernate.SessionFactory;
import org.hibernate.context.ManagedSessionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;
import org.springframework.security.providers.dao.salt.SystemWideSaltSource;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.model.TrialSite;
import de.randi2.model.enumerations.Gender;
import de.randi2.test.utility.DomainObjectFactory;
import de.randi2.utility.security.RolesAndRights;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/subconfig/service-test.xml","/META-INF/subconfig/test.xml" })
public abstract class AbstractServiceTest {


	@Autowired protected SessionFactory sessionFactory;
	@Autowired protected DomainObjectFactory factory;
	@Autowired protected RolesAndRights rolesAndRights;
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private SystemWideSaltSource saltSource;
	
	protected Login admin;
	
	
	@Before
	public void setUp(){
		ManagedSessionContext.bind(sessionFactory.openSession());
		if(sessionFactory.getCurrentSession().createCriteria(Role.class).list().size()<7){
		sessionFactory.getCurrentSession().merge(Role.ROLE_ADMIN);
		sessionFactory.getCurrentSession().merge(Role.ROLE_INVESTIGATOR);
		sessionFactory.getCurrentSession().merge(Role.ROLE_USER);
		sessionFactory.getCurrentSession().merge(Role.ROLE_STATISTICAN);
		sessionFactory.getCurrentSession().merge(Role.ROLE_MONITOR);
		sessionFactory.getCurrentSession().merge(Role.ROLE_P_INVESTIGATOR);
		sessionFactory.getCurrentSession().merge(Role.ROLE_ANONYMOUS);
		
		Person adminP = new Person();
		adminP.setFirstname("Max");
		adminP.setSurname("Mustermann");
		adminP.setEmail("admin@test.de");
		adminP.setPhone("1234567");
		adminP.setGender(Gender.MALE);

		Login adminL = new Login();
		adminL.setPassword("1§heidelberg");
		adminL.setPerson(adminP);
		adminL.setPrefLocale(Locale.GERMANY);
		adminL.setUsername(adminP.getEmail());
		
		adminL.addRole(Role.ROLE_ADMIN);
		adminL.addRole(Role.ROLE_P_INVESTIGATOR);
		sessionFactory.getCurrentSession().persist(adminL);
		
		TrialSite trialSite = new TrialSite();
		trialSite.setCity("Heidelberg");
		trialSite.setCountry("Germany");
		trialSite.setName("DKFZ");
		trialSite.setPostcode("69120");
		trialSite.setStreet("INF");
		trialSite.setPassword(passwordEncoder.encodePassword("1$heidelberg",saltSource.getSystemWideSalt()));
		trialSite.setContactPerson(adminP);
		rolesAndRights.registerPerson(adminL);
		rolesAndRights.grantRigths(adminL, trialSite);

		sessionFactory.getCurrentSession().persist(trialSite);
		sessionFactory.getCurrentSession().refresh(adminP);
		adminP.setTrialSite(trialSite);
		sessionFactory.getCurrentSession().update(adminP);
		rolesAndRights.grantRigths(trialSite, trialSite);
		
		
		
		}
		authenticatAsAdmin();
		
		
		
	}
	
	@After
	public void afterTest(){
		SecurityContextHolder.getContext().setAuthentication(null);
//		ManagedSessionContext.unbind(sessionFactory);
		sessionFactory.getCurrentSession().close();
	}
	
	protected void authenticatAsAdmin(){
		admin = findLogin("admin@test.de");
		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				"admin@test.de", admin, admin.getAuthorities());
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);
	}
	
	protected Login findLogin(String username){
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
