package de.randi2.services;

import java.util.List;
import java.util.Locale;

import org.hibernate.SessionFactory;
import org.hibernate.context.ManagedSessionContext;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.acls.sid.PrincipalSid;
import org.springframework.security.acls.sid.Sid;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;
import org.springframework.security.providers.dao.salt.SystemWideSaltSource;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.vote.AffirmativeBased;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.HibernateAclService;
import de.randi2.dao.UserDetailsServiceImpl;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.model.TrialSite;
import de.randi2.model.enumerations.Gender;
import de.randi2.model.security.ObjectIdentityHibernate;
import de.randi2.model.security.PermissionHibernate;
import de.randi2.test.utility.DomainObjectFactory;
import de.randi2.utility.security.RolesAndRights;

import static junit.framework.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/subconfig/service-test.xml","/META-INF/subconfig/test.xml" })
@Transactional(propagation=Propagation.REQUIRES_NEW)
//import static junit.framework.Assert.*;
public class TrialSiteServiceTest {

	
	@Autowired private TrialSiteService service;
	@Autowired private DomainObjectFactory factory;
	@Autowired private SessionFactory sessionFactory;
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private SystemWideSaltSource saltSource;
	@Autowired private RolesAndRights rolesAndRights;
	@Autowired private ApplicationContext context;
	
	
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
	
	
	@Test
	public void testInit(){
		assertNotNull(service);
	}
	
	@Test
	public void testCreate(){
		authenticatAsAdmin();
		TrialSite site = factory.getTrialSite();
		service.create(site);
		assertTrue(site.getId()>0);
		
	}
	
	@Test
	public void testAuthorize(){
		authenticatAsAnonymous();
		TrialSite site = factory.getTrialSite();
		site.setPassword(passwordEncoder.encodePassword("1$heidelberg", saltSource.getSystemWideSalt()));
		assertTrue(service.authorize(site, "1$heidelberg"));
		assertFalse(service.authorize(site, "234§heidelberg"));
		
	}
	
	
	@Test
	public void testUpdate(){
		authenticatAsAdmin();
		TrialSite site = factory.getTrialSite();
		service.create(site);
		assertTrue(site.getId()>0);
//		aclService.createAclwithPermissions(site, "admin@test.de",new PermissionHibernate[]{PermissionHibernate.ADMINISTRATION},null);
//		aclService.readAclById(new ObjectIdentityHibernate(TrialSite.class,site.getId()), new Sid[]{new PrincipalSid("admin@test.de")});
		site.setName("Name23");
		service.update(site);
		TrialSite site2 = (TrialSite) sessionFactory.getCurrentSession().get(TrialSite.class, site.getId());
		assertEquals(site.getName(), site2.getName());
	}
	
	@Test
	public void testgetAll(){
		authenticatAsAdmin();
		Login login = findLogin("admin@test.de");
		for(int i=0;i<10;i++){
			TrialSite site = factory.getTrialSite();
			site.setContactPerson(login.getPerson());
			service.create(site);
			rolesAndRights.grantRigths(site,site);
			assertTrue(site.getId()>0);
		}
		
		assertTrue(service.getAll().size()>=10);

	}
	
	
	@Test
	public void testGetObject(){
		authenticatAsAdmin();
		((AffirmativeBased)context.getBean("methodAccessDecisionManager")).setAllowIfAllAbstainDecisions(true);
		TrialSite site = factory.getTrialSite();
		
		Login login = findLogin("admin@test.de");
		site.setContactPerson(login.getPerson());
		service.create(site);
		assertTrue(site.getId()>0);
		rolesAndRights.grantRigths(site,site);
		TrialSite site2 = service.getObject(site.getId());
		assertEquals(site.getName(), site2.getName());
		assertEquals(site.getId(), site2.getId());
		assertEquals(site.getCity(), site2.getCity());
	}
	
	private void authenticatAsAdmin(){
		Login login = findLogin("admin@test.de");
		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				"admin@test.de", login, login.getAuthorities());
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
	
	private Login findLogin(String username){
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
