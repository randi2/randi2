package de.randi2.utility;

import java.util.Locale;

import org.hibernate.SessionFactory;
import org.hibernate.context.ManagedSessionContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;

import de.randi2.dao.LoginDaoHibernate;
import de.randi2.dao.TrialSiteDaoHibernate;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.model.TrialSite;
import de.randi2.model.enumerations.Gender;
import de.randi2.utility.security.RolesAndRights;

/**
 * This class insert two user and two trial sites (name: DKFZ and NCT). 
 * 
 * First user:
 * <ul>
 * 	<li>role: Administrator</li>
 * 	<li>login: admin@test.de</li>
 * 	<li>password: 1$heidelberg</li>
 *</ul>
 *
 * Second user:
 * <ul>
 * 	<li>role: Investigator</li>
 * 	<li>login: user@test.de</li>
 * 	<li>password: 1$heidelberg</li>
 *</ul>
 *  
 * 
 * 
 * @author Daniel Schrimpf <dschrimpf@users.sourceforge.net>
 * 
 */
public class Bootstrap {

	private RolesAndRights rolesAndRights;
	private LoginDaoHibernate loginDao;
	private TrialSiteDaoHibernate trialSiteDao;
	private SessionFactory sessionFactory;

	public void init() {
		ManagedSessionContext.bind(sessionFactory.openSession());
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_ADMIN);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_INVESTIGATOR);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_USER);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_STATISTICAN);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_MONITOR);
		sessionFactory.getCurrentSession().saveOrUpdate(Role.ROLE_P_INVESTIGATOR);
		
		Person adminP = new Person();
		adminP.setFirstname("Max");
		adminP.setSurname("Mustermann");
		adminP.setEMail("admin@test.de");
		adminP.setPhone("1234567");
		adminP.setGender(Gender.MALE);

		Login adminL = new Login();
		adminL.setPassword("1$heidelberg");
		adminL.setPerson(adminP);
		adminL.setPrefLocale(Locale.GERMANY);
		adminL.setUsername(adminP.getEMail());
		
		adminL.addRole(Role.ROLE_ADMIN);
		sessionFactory.getCurrentSession().persist(adminL);

		TrialSite trialSite = new TrialSite();
		trialSite.setCity("Heidelberg");
		trialSite.setCountry("Germany");
		trialSite.setName("DKFZ");
		trialSite.setPostcode("69120");
		trialSite.setStreet("INF");
		trialSite.setPassword("1$heidelberg");
		trialSite.setContactPerson(adminP);
		rolesAndRights.registerPerson(adminL);
		rolesAndRights.grantRigths(adminL, trialSite);

		sessionFactory.getCurrentSession().persist(trialSite);

		sessionFactory.getCurrentSession().refresh(adminP);
		adminP.setTrialSite(trialSite);
		sessionFactory.getCurrentSession().update(adminP);
		rolesAndRights.grantRigths(trialSite, trialSite);

		AnonymousAuthenticationToken authToken = new AnonymousAuthenticationToken(
				"anonymousUser", adminL, adminL.getAuthorities());
		// Perform authentication
		SecurityContextHolder.getContext().setAuthentication(authToken);
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(true);

		Person userP = new Person();
		userP.setFirstname("Maxi");
		userP.setSurname("Musterfrau");
		userP.setEMail("user@test.de");
		userP.setPhone("1234567");
		userP.setGender(Gender.FEMALE);
		userP.setTrialSite(trialSite);

		Login userL = new Login();
		userL.setPassword("1$heidelberg");
		userL.setPerson(userP);
		userL.setPrefLocale(Locale.GERMANY);
		userL.setUsername(userP.getEMail());
		userL.addRole(Role.ROLE_INVESTIGATOR);
//		template.saveOrUpdate(Role.ROLE_INVESTIGATOR);
		
		loginDao.create(userL);

		TrialSite trialSite1 = new TrialSite();
		trialSite1.setCity("Heidelberg");
		trialSite1.setCountry("Germany");
		trialSite1.setName("NCT");
		trialSite1.setPostcode("69120");
		trialSite1.setStreet("INF");
		trialSite1.setPassword("1$heidelberg");
		trialSite1.setContactPerson(adminP);

		trialSiteDao.create(trialSite1);
		
		/*P_Investigator role*/
		adminL = (Login) sessionFactory.getCurrentSession().get(Login.class, adminL.getId());
		adminL.addRole(Role.ROLE_P_INVESTIGATOR);
//		template.saveOrUpdate(Role.ROLE_INVESTIGATOR);
		loginDao.create(adminL);
		rolesAndRights.grantRigths(adminL, trialSite);
	}

	public Bootstrap() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"classpath:/META-INF/spring.xml");
		loginDao = (LoginDaoHibernate) ctx.getBean("loginDAO");
		rolesAndRights = (RolesAndRights) ctx.getBean("rolesAndRights");
		trialSiteDao = (TrialSiteDaoHibernate) ctx.getBean("trialSiteDAO");
		sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
		init();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Bootstrap();
	}

}
