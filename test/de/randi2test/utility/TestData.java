package de.randi2test.utility;

import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.dao.HibernateAclService;
import de.randi2.dao.LoginDaoHibernate;
import de.randi2.dao.TrialSiteDaoHibernate;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role2;
import de.randi2.model.TrialSite;
import de.randi2.model.enumerations.Gender;
import de.randi2.utility.security.RolesAndRights;


/**
 * Only test data for GUI
 * 
 * 
 * @author Daniel Schrimpf
 *
 */
// FIXME #2249688 Replace by bootstrap Mechanism
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:/META-INF/spring.xml", "classpath:/META-INF/spring-test.xml" })
public class TestData {
	
	

	@Autowired
	private HibernateTemplate template;
	@Autowired
	private RolesAndRights rolesAndRights;
	
	@Autowired
	private LoginDaoHibernate loginDao;
	
	@Autowired
	private TrialSiteDaoHibernate trialSiteDao;
	
//	@Test
	public void init(){
		Person adminP = new Person();
		adminP.setFirstname("Max");
		adminP.setSurname("Mustermann");
		adminP.setEMail("admin@test.de");
		adminP.setPhone("1234567");
		adminP.setGender(Gender.MALE);
		
		Login adminL = new Login();
		adminL.setPassword("1$heidelberg");
		adminL.setRegistrationDate(new GregorianCalendar());
		adminL.setPerson(adminP);
		adminL.setPrefLocale(Locale.GERMANY);
		adminL.setUsername(adminP.getEMail());
		adminL.addRole(Role2.ROLE_ADMIN);
		template.saveOrUpdate(adminL);
		
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

		template.save(trialSite);
		adminP.setTrialSite(trialSite);
		template.saveOrUpdate(adminP);
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
		userL.setRegistrationDate(new GregorianCalendar());
		userL.setPerson(userP);
		userL.setPrefLocale(Locale.GERMANY);
		userL.setUsername(userP.getEMail());
		userL.addRole(Role2.ROLE_INVESTIGATOR);
		
		loginDao.save(userL);
		
		TrialSite trialSite1 = new TrialSite();
		trialSite1.setCity("Heidelberg");
		trialSite1.setCountry("Germany");
		trialSite1.setName("NCT");
		trialSite1.setPostcode("69120");
		trialSite1.setStreet("INF");
		trialSite1.setPassword("1$heidelberg");
		trialSite1.setContactPerson(adminP);
		
		trialSiteDao.save(trialSite1);
		
		
		
	}

}
