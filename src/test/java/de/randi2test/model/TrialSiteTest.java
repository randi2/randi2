package de.randi2test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2test.utility.AbstractDomainTest;


public class TrialSiteTest extends AbstractDomainTest<TrialSite> {

	private TrialSite validCenter;
@Autowired private SessionFactory sessionFactory;

private Session getCurrentSession(){
	return sessionFactory.getCurrentSession();
}
	
	public TrialSiteTest() {
		super(TrialSite.class);
	}

	@Before
	public void setUp() {
		validCenter = factory.getTrialSite();
		hibernateTemplate.save(validCenter.getContactPerson());
	}

	@Test
	public void testConstuctor() {
		TrialSite c = new TrialSite();
		assertEquals("", c.getName());
		assertEquals("", c.getStreet());
		assertEquals("", c.getPostcode());
		assertEquals("", c.getCity());
	}

	@Test
	public void testName() {
		final String nameOK1 = "K";
		final String nameOK2 = stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH);

		final String nameToLong = stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH + 1);
		final String nameEmpty = "";
		final String nameNull = null;

		validCenter.setName(nameOK1);
		assertEquals(nameOK1, validCenter.getName());
		assertValid(validCenter);

		validCenter.setName(nameOK2);
		assertEquals(nameOK2, validCenter.getName());
		assertValid(validCenter);

		validCenter.setName(nameToLong);
		assertEquals(nameToLong, validCenter.getName());
		assertInvalid(validCenter, new String[] { "" });

		validCenter.setName(nameEmpty);
		assertEquals(nameEmpty, validCenter.getName());
		assertInvalid(validCenter, new String[] { "" });

		validCenter.setName(nameNull);
		assertEquals("", validCenter.getName());
		assertInvalid(validCenter, new String[] { "" });
	}

	@Test
	public void testStreet() {
		// Street
		validCenter.setStreet(null);
		assertEquals("", validCenter.getStreet());
		assertValid(validCenter);

		validCenter.setStreet("");
		assertEquals("", validCenter.getStreet());
		assertValid(validCenter);

		validCenter.setStreet("Oxford-Street 212");
		assertEquals("Oxford-Street 212", validCenter.getStreet());
		assertValid(validCenter);

		String ok = stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH);
		validCenter.setStreet(ok);
		assertEquals(ok, validCenter.getStreet());
		assertValid(validCenter);

		String iv = stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH + 1);
		validCenter.setStreet(iv);
		assertEquals(iv, validCenter.getStreet());
		assertInvalid(validCenter);

	}

	@Test
	public void testPostcode() {
		// Postcode
		validCenter.setPostcode(null);
		assertEquals("", validCenter.getPostcode());
		assertValid(validCenter);

		validCenter.setPostcode("");
		assertEquals("", validCenter.getPostcode());
		assertValid(validCenter);

		validCenter.setPostcode("97321");
		assertEquals("97321", validCenter.getPostcode());
		assertValid(validCenter);

		String ok = stringUtil.getWithLength(TrialSite.MAX_LENGTH_POSTCODE);
		validCenter.setPostcode(ok);
		assertEquals(ok, validCenter.getPostcode());
		assertValid(validCenter);

		String iv = stringUtil.getWithLength(TrialSite.MAX_LENGTH_POSTCODE + 1);
		validCenter.setPostcode(iv);
		assertEquals(iv, validCenter.getPostcode());
		assertInvalid(validCenter);

	}

	@Test
	public void testCity() {
		// City
		validCenter.setCity(null);
		assertEquals("", validCenter.getCity());
		assertValid(validCenter);

		validCenter.setCity("");
		assertEquals("", validCenter.getCity());
		assertValid(validCenter);

		validCenter.setCity("New Hamburger");
		assertEquals("New Hamburger", validCenter.getCity());
		assertValid(validCenter);

		String ok = stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH);
		validCenter.setCity(ok);
		assertEquals(ok, validCenter.getCity());
		assertValid(validCenter);

		String iv = stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH + 1);
		validCenter.setCity(iv);
		assertEquals(iv, validCenter.getCity());
		assertInvalid(validCenter);
	}
	
	@Test
	@Transactional(propagation=Propagation.SUPPORTS)
	public void testTrials(){
		List<Trial> tl = new ArrayList<Trial>();
		
		tl.add(factory.getTrial());
		tl.add(factory.getTrial());
		tl.add(factory.getTrial());
		

		hibernateTemplate.saveOrUpdate(validCenter);
		hibernateTemplate.flush();
		assertTrue(validCenter.getId()!= AbstractDomainObject.NOT_YET_SAVED_ID);
		for(Trial trial: tl){
			trial.addParticipatingSite(validCenter);
			trial.setLeadingSite(validCenter);
			Login login = factory.getLogin();
			hibernateTemplate.persist(login);
			trial.setSponsorInvestigator(login.getPerson());
			assertEquals(1, trial.getParticipatingSites().size());
			assertEquals(validCenter.getId(), ((AbstractDomainObject) trial.getParticipatingSites().toArray()[0]).getId());
			hibernateTemplate.persist(trial);
			hibernateTemplate.flush();
		}
		TrialSite center = (TrialSite) hibernateTemplate.get(TrialSite.class, validCenter.getId());
		assertEquals(validCenter.getId(), center.getId());
		
		hibernateTemplate.refresh(validCenter);
		validCenter.getTrials();
		assertEquals(tl.size(), center.getTrials().size());
	}
	
	@Test
	public void testCountry(){
		validCenter.setCountry("UK");
		assertEquals("UK", validCenter.getCountry());
		hibernateTemplate.saveOrUpdate(validCenter);
		assertTrue(validCenter.getId()!=AbstractDomainObject.NOT_YET_SAVED_ID);
		TrialSite c = (TrialSite) hibernateTemplate.get(TrialSite.class, validCenter.getId());
		
		assertEquals(validCenter.getId(), c.getId());
		assertEquals("UK", c.getCountry());
		validCenter.setCountry(null);
		assertEquals("", validCenter.getCountry());
		
	}
	
	@Test
	public void testContactPerson(){
		Person p = factory.getPerson();
		hibernateTemplate.save(p);
		validCenter.setContactPerson(p);
		assertEquals(p.getSurname(), validCenter.getContactPerson().getSurname());
		hibernateTemplate.saveOrUpdate(validCenter);
		assertTrue(validCenter.getId()!=AbstractDomainObject.NOT_YET_SAVED_ID);
		assertTrue(p.getId()!=AbstractDomainObject.NOT_YET_SAVED_ID);
		
		TrialSite c = (TrialSite) hibernateTemplate.get(TrialSite.class, validCenter.getId());
		assertEquals(p.getId(), c.getContactPerson().getId());
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void testMembers(){
	
		List<Person> members = new ArrayList<Person>();
		//hibernateTemplate.getSessionFactory().getCurrentSession().saveOrUpdate(validCenter);
		hibernateTemplate.saveOrUpdate(validCenter);
		
		for(int i=0;i<100;i++){
			Person p = factory.getPerson();
			p.setTrialSite(validCenter);
			assertEquals(validCenter.getId(), p.getTrialSite().getId());
			hibernateTemplate.saveOrUpdate(p);
		//	hibernateTemplate.getSessionFactory().getCurrentSession().saveOrUpdate(p);
			members.add(p);
		}
		hibernateTemplate.flush();
	//	hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		//List<Person> persons = (List<Person>) hibernateTemplate.findByNamedQueryAndNamedParam("center.findAllMembers", "center", validCenter);
		
		TrialSite c = (TrialSite) hibernateTemplate.get(TrialSite.class, validCenter.getId());
//	Center c = (Center) hibernateTemplate.getSessionFactory().getCurrentSession().get(Center.class, validCenter.getId());		
		assertEquals(validCenter.getId(), c.getId());
			List<Person> mem = c.getMembers();
			hibernateTemplate.refresh(c);
//			hibernateTemplate.getSessionFactory().getCurrentSession().refresh(c);
			assertEquals(members.size(), c.getMembers().size());
	}
	
	
	
	@Test
	public void testPassword(){
		String[] validPasswords = {"secret0$secret","sad.al4h/ljhaslf",stringUtil.getWithLength(Login.MAX_PASSWORD_LENGTH-2)+";2", stringUtil.getWithLength(Login.MIN_PASSWORD_LENGTH-2)+",3", stringUtil.getWithLength(Login.HASH_PASSWORD_LENGTH)};
		for (String s: validPasswords){
			validCenter.setPassword(s);
			assertValid(validCenter);
		}
		
	String[] invalidPasswords = {"secret$secret",stringUtil.getWithLength(Login.MAX_PASSWORD_LENGTH),stringUtil.getWithLength(Login.MIN_PASSWORD_LENGTH-3)+";2", "0123456789", null, ""};
		for (String s: invalidPasswords){
			validCenter.setPassword(s);
			assertInvalid(validCenter);
		}
	}
	
	@Test
	public void testGetMembersWithSpecifiedRole(){
		List<Person> members = new ArrayList<Person>();
		Login l = factory.getLogin();
		l.addRole(Role.ROLE_ADMIN);
		members.add(l.getPerson());
		l = factory.getLogin();
		l.addRole(Role.ROLE_ADMIN);
		members.add(l.getPerson());
		l = factory.getLogin();
		l.addRole(Role.ROLE_ADMIN);
		members.add(l.getPerson());
		l = factory.getLogin();
		l.addRole(Role.ROLE_ADMIN);
		members.add(l.getPerson());
		
		
		l = factory.getLogin();
		l.addRole(Role.ROLE_USER);
		members.add(l.getPerson());
		l = factory.getLogin();
		l.addRole(Role.ROLE_USER);
		members.add(l.getPerson());
		l = factory.getLogin();
		l.addRole(Role.ROLE_P_INVESTIGATOR);
		members.add(l.getPerson());
		
		validCenter.setMembers(members);
		
		List<Login> logins = validCenter.getMembersWithSpecifiedRole(Role.ROLE_USER);
		assertEquals(members.size(), logins.size());
		
		logins = validCenter.getMembersWithSpecifiedRole(Role.ROLE_ADMIN);
		assertEquals(4, logins.size());
		for(Person p: members.subList(0, 3)){
			assertTrue(logins.contains(p.getLogin()));
		}
		logins = validCenter.getMembersWithSpecifiedRole(Role.ROLE_P_INVESTIGATOR);
		assertEquals(1, logins.size());
		assertTrue(logins.contains(members.get(6).getLogin()));
	}

}
