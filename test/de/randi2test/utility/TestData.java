package de.randi2test.utility;

import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.dao.HibernateAclService;
import de.randi2.model.GrantedAuthorityEnum;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.TrialSite;
import de.randi2.model.enumerations.Gender;
import de.randi2.model.security.PermissionHibernate;


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
	private HibernateAclService aclService;
	@Autowired
	private DomainObjectFactory factory;
	@Autowired
	private HibernateTemplate template;
	
	//@Test
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
		adminL.addRole(GrantedAuthorityEnum.ROLE_ADMIN);
		template.saveOrUpdate(adminL);
		
		TrialSite trialSite = new TrialSite();
		trialSite.setCity("Heidelberg");
		trialSite.setCountry("Germany");
		trialSite.setName("DKFZ");
		trialSite.setPostcode("69120");
		trialSite.setStreet("INF");
		trialSite.setPassword("1$heidelberg");
		trialSite.setContactPerson(adminP);
		template.saveOrUpdate(trialSite);
		adminP.setTrialSite(trialSite);
		template.saveOrUpdate(adminP);
		
		aclService.createAclwithPermissions(trialSite, adminL.getUsername(), new PermissionHibernate[]{PermissionHibernate.READ,PermissionHibernate.WRITE});
		aclService.createAclwithPermissions(adminL, adminL.getUsername(), new PermissionHibernate[]{PermissionHibernate.READ,PermissionHibernate.WRITE});
		aclService.createAclwithPermissions(adminP, adminL.getUsername(), new PermissionHibernate[]{PermissionHibernate.READ,PermissionHibernate.WRITE});
		
		Person person = new Person();
		person.setFirstname("Maxi");
		person.setSurname("Musterfrau");
		person.setEMail("user@test.de");
		person.setPhone("1234567");
		person.setGender(Gender.FEMALE);
		person.setTrialSite(trialSite);
		
		Login login = new Login();
		login.setPassword("1$heidelberg");
		login.setRegistrationDate(new GregorianCalendar());
		login.setPerson(person);
		login.setPrefLocale(Locale.GERMANY);
		login.setUsername(person.getEMail());
		login.addRole(GrantedAuthorityEnum.ROLE_INVESTIGATOR);
		template.saveOrUpdate(login);
		
		aclService.createAclwithPermissions(trialSite, login.getUsername(), new PermissionHibernate[]{PermissionHibernate.READ});
		aclService.createAclwithPermissions(login, login.getUsername(), new PermissionHibernate[]{PermissionHibernate.READ,PermissionHibernate.WRITE});
		aclService.createAclwithPermissions(person, login.getUsername(), new PermissionHibernate[]{PermissionHibernate.READ,PermissionHibernate.WRITE});
		aclService.createAclwithPermissions(login, adminL.getUsername(), new PermissionHibernate[]{PermissionHibernate.READ,PermissionHibernate.WRITE});
		aclService.createAclwithPermissions(person, adminL.getUsername(), new PermissionHibernate[]{PermissionHibernate.READ,PermissionHibernate.WRITE});
		
		for(int i =0; i <10; i++){
			TrialSite center = factory.getCenter();
			center.setContactPerson(adminP);
			template.saveOrUpdate(center);
			aclService.createAclwithPermissions(center, adminL.getUsername(), new PermissionHibernate[]{PermissionHibernate.READ,PermissionHibernate.WRITE});
		}
	}

}
