package de.randi2.model;

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

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.test.utility.AbstractDomainTest;


public class TrialSiteTest extends AbstractDomainTest<TrialSite> {

	private TrialSite validTrialSite;
@Autowired private SessionFactory sessionFactory;

private Session getCurrentSession(){
	return sessionFactory.getCurrentSession();
}
	
	public TrialSiteTest() {
		super(TrialSite.class);
	}

	@Before
	public void setUp() {
		validTrialSite = factory.getTrialSite();
		hibernateTemplate.save(validTrialSite.getContactPerson());
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

		validTrialSite.setName(nameOK1);
		assertEquals(nameOK1, validTrialSite.getName());
		assertValid(validTrialSite);

		validTrialSite.setName(nameOK2);
		assertEquals(nameOK2, validTrialSite.getName());
		assertValid(validTrialSite);

		validTrialSite.setName(nameToLong);
		assertEquals(nameToLong, validTrialSite.getName());
		assertInvalid(validTrialSite, new String[] { "" });

		validTrialSite.setName(nameEmpty);
		assertEquals(nameEmpty, validTrialSite.getName());
		assertInvalid(validTrialSite, new String[] { "" });

		validTrialSite.setName(nameNull);
		assertEquals("", validTrialSite.getName());
		assertInvalid(validTrialSite, new String[] { "" });
	}

	@Test
	public void testStreet() {
		// Street
		validTrialSite.setStreet(null);
		assertEquals("", validTrialSite.getStreet());
		assertValid(validTrialSite);

		validTrialSite.setStreet("");
		assertEquals("", validTrialSite.getStreet());
		assertValid(validTrialSite);

		validTrialSite.setStreet("Oxford-Street 212");
		assertEquals("Oxford-Street 212", validTrialSite.getStreet());
		assertValid(validTrialSite);

		String ok = stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH);
		validTrialSite.setStreet(ok);
		assertEquals(ok, validTrialSite.getStreet());
		assertValid(validTrialSite);

		String iv = stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH + 1);
		validTrialSite.setStreet(iv);
		assertEquals(iv, validTrialSite.getStreet());
		assertInvalid(validTrialSite);

	}

	@Test
	public void testPostcode() {
		// Postcode
		validTrialSite.setPostcode(null);
		assertEquals("", validTrialSite.getPostcode());
		assertValid(validTrialSite);

		validTrialSite.setPostcode("");
		assertEquals("", validTrialSite.getPostcode());
		assertValid(validTrialSite);

		validTrialSite.setPostcode("97321");
		assertEquals("97321", validTrialSite.getPostcode());
		assertValid(validTrialSite);

		String ok = stringUtil.getWithLength(TrialSite.MAX_LENGTH_POSTCODE);
		validTrialSite.setPostcode(ok);
		assertEquals(ok, validTrialSite.getPostcode());
		assertValid(validTrialSite);

		String iv = stringUtil.getWithLength(TrialSite.MAX_LENGTH_POSTCODE + 1);
		validTrialSite.setPostcode(iv);
		assertEquals(iv, validTrialSite.getPostcode());
		assertInvalid(validTrialSite);

	}

	@Test
	public void testCity() {
		// City
		validTrialSite.setCity(null);
		assertEquals("", validTrialSite.getCity());
		assertValid(validTrialSite);

		validTrialSite.setCity("");
		assertEquals("", validTrialSite.getCity());
		assertValid(validTrialSite);

		validTrialSite.setCity("New Hamburger");
		assertEquals("New Hamburger", validTrialSite.getCity());
		assertValid(validTrialSite);

		String ok = stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH);
		validTrialSite.setCity(ok);
		assertEquals(ok, validTrialSite.getCity());
		assertValid(validTrialSite);

		String iv = stringUtil
				.getWithLength(AbstractDomainObject.MAX_VARCHAR_LENGTH + 1);
		validTrialSite.setCity(iv);
		assertEquals(iv, validTrialSite.getCity());
		assertInvalid(validTrialSite);
	}
	
	@Test
	@Transactional(propagation=Propagation.SUPPORTS)
	public void testTrials(){
		List<Trial> tl = new ArrayList<Trial>();
		
		tl.add(factory.getTrial());
		tl.add(factory.getTrial());
		tl.add(factory.getTrial());
		

		hibernateTemplate.saveOrUpdate(validTrialSite);
		hibernateTemplate.flush();
		assertTrue(validTrialSite.getId()!= AbstractDomainObject.NOT_YET_SAVED_ID);
		for(Trial trial: tl){
			trial.addParticipatingSite(validTrialSite);
			trial.setLeadingSite(validTrialSite);
			Login login = factory.getLogin();
			hibernateTemplate.persist(login);
			trial.setSponsorInvestigator(login.getPerson());
			assertEquals(1, trial.getParticipatingSites().size());
			assertEquals(validTrialSite.getId(), ((AbstractDomainObject) trial.getParticipatingSites().toArray()[0]).getId());
			hibernateTemplate.persist(trial);
			hibernateTemplate.flush();
		}
		TrialSite trialSite = (TrialSite) hibernateTemplate.get(TrialSite.class, validTrialSite.getId());
		assertEquals(validTrialSite.getId(), trialSite.getId());
		
		hibernateTemplate.refresh(validTrialSite);
		validTrialSite.getTrials();
		assertEquals(tl.size(), trialSite.getTrials().size());
	}
	
	@Test
	public void testCountry(){
		validTrialSite.setCountry("UK");
		assertEquals("UK", validTrialSite.getCountry());
		hibernateTemplate.saveOrUpdate(validTrialSite);
		assertTrue(validTrialSite.getId()!=AbstractDomainObject.NOT_YET_SAVED_ID);
		TrialSite c = (TrialSite) hibernateTemplate.get(TrialSite.class, validTrialSite.getId());
		
		assertEquals(validTrialSite.getId(), c.getId());
		assertEquals("UK", c.getCountry());
		validTrialSite.setCountry(null);
		assertEquals("", validTrialSite.getCountry());
		
	}
	
	@Test
	public void testContactPerson(){
		Person p = factory.getPerson();
		hibernateTemplate.save(p);
		validTrialSite.setContactPerson(p);
		assertEquals(p.getSurname(), validTrialSite.getContactPerson().getSurname());
		hibernateTemplate.saveOrUpdate(validTrialSite);
		assertTrue(validTrialSite.getId()!=AbstractDomainObject.NOT_YET_SAVED_ID);
		assertTrue(p.getId()!=AbstractDomainObject.NOT_YET_SAVED_ID);
		
		TrialSite c = (TrialSite) hibernateTemplate.get(TrialSite.class, validTrialSite.getId());
		assertEquals(p.getId(), c.getContactPerson().getId());
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void testMembers(){
	
		List<Person> members = new ArrayList<Person>();
	
		hibernateTemplate.saveOrUpdate(validTrialSite);
		
		for(int i=0;i<100;i++){
			Person p = factory.getPerson();
			p.setTrialSite(validTrialSite);
			assertEquals(validTrialSite.getId(), p.getTrialSite().getId());
			hibernateTemplate.saveOrUpdate(p);
		//	hibernateTemplate.getSessionFactory().getCurrentSession().saveOrUpdate(p);
			members.add(p);
		}
		hibernateTemplate.flush();
		
		TrialSite c = (TrialSite) hibernateTemplate.get(TrialSite.class, validTrialSite.getId());
		assertEquals(validTrialSite.getId(), c.getId());
			List<Person> mem = c.getMembers();
			hibernateTemplate.refresh(c);
//			hibernateTemplate.getSessionFactory().getCurrentSession().refresh(c);
			assertEquals(members.size(), c.getMembers().size());
	}
	
	
	
	@Test
	public void testPassword(){
		String[] validPasswords = {"secret0$secret","sad.al4h/ljhaslf",stringUtil.getWithLength(Login.MAX_PASSWORD_LENGTH-2)+";2", stringUtil.getWithLength(Login.MIN_PASSWORD_LENGTH-2)+",3", stringUtil.getWithLength(Login.HASH_PASSWORD_LENGTH)};
		for (String s: validPasswords){
			validTrialSite.setPassword(s);
			assertValid(validTrialSite);
		}
		
	String[] invalidPasswords = {"secret$secret",stringUtil.getWithLength(Login.MAX_PASSWORD_LENGTH),stringUtil.getWithLength(Login.MIN_PASSWORD_LENGTH-3)+";2", "0123456789", null, ""};
		for (String s: invalidPasswords){
			validTrialSite.setPassword(s);
			assertInvalid(validTrialSite);
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
		
		validTrialSite.setMembers(members);
		
		List<Login> logins = validTrialSite.getMembersWithSpecifiedRole(Role.ROLE_USER);
		assertEquals(members.size(), logins.size());
		
		logins = validTrialSite.getMembersWithSpecifiedRole(Role.ROLE_ADMIN);
		assertEquals(4, logins.size());
		for(Person p: members.subList(0, 3)){
			assertTrue(logins.contains(p.getLogin()));
		}
		logins = validTrialSite.getMembersWithSpecifiedRole(Role.ROLE_P_INVESTIGATOR);
		assertEquals(1, logins.size());
		assertTrue(logins.contains(members.get(6).getLogin()));
	}

}
