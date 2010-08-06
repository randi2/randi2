package de.randi2.core.integration.modelDatatbase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.TrialSite;
import de.randi2.testUtility.utility.AbstractDomainDatabaseTest;

public class PersonDatabaseTest extends AbstractDomainDatabaseTest<Person> {

	
	private Person validPerson;

	public PersonDatabaseTest() {
		super(Person.class);
	}

	@Before
	public void setUp() {
		super.setUp();
		validPerson = super.factory.getPerson();
		validPerson.setLogin(factory.getLogin());
		validPerson.getLogin().setPerson(validPerson);
	}
	
	@Test
	@Transactional
	public void testLogin() {
		Login l = factory.getLogin();
		l.setPerson(validPerson);
		validPerson.setLogin(l);
		assertNotNull(validPerson.getLogin());
		sessionFactory.getCurrentSession().saveOrUpdate(validPerson);
		assertEquals(validPerson.getLogin().getId(),l.getId());
		Person p = (Person) sessionFactory.getCurrentSession().get(Person.class, validPerson
				.getId());
		assertNotNull(p);
		assertEquals(validPerson.getId(), p.getId());
		assertEquals(validPerson.getFirstname(), p.getFirstname());
		assertEquals(validPerson.getSurname(), p.getSurname());
		assertNotNull(p.getLogin());
		assertEquals(validPerson.getLogin().getId(), p.getLogin().getId());

	}
	
	@Test
	@Transactional
	public void testAssistant() {
		Person assistant = factory.getPerson();
		assistant.setSurname(stringUtil.getWithLength(20));
		validPerson.setAssistant(assistant);
		validPerson.setSurname(stringUtil.getWithLength(20));
		assertNotNull(validPerson.getAssistant());
		sessionFactory.getCurrentSession().saveOrUpdate(validPerson.getAssistant());
		sessionFactory.getCurrentSession().saveOrUpdate(validPerson);

		assertTrue(validPerson.getAssistant().getId() != AbstractDomainObject.NOT_YET_SAVED_ID);

		Person p = (Person) sessionFactory.getCurrentSession().get(Person.class, validPerson
				.getId());

		assertNotNull(p);
		assertEquals(validPerson.getId(), p.getId());
		assertNotNull(p.getAssistant());
		assertEquals(assistant.getId(), p.getAssistant().getId());
	}
	
	
	@Test
	@Transactional
	public void testTrialSite() {
		TrialSite trialSite = factory.getTrialSite();
		sessionFactory.getCurrentSession().save(trialSite.getContactPerson());
		sessionFactory.getCurrentSession().saveOrUpdate(trialSite);
		
		validPerson.setSurname(stringUtil.getWithLength(20));
		validPerson.setTrialSite(trialSite);
		assertNotNull(validPerson.getTrialSite());

		sessionFactory.getCurrentSession().saveOrUpdate(validPerson);

		assertTrue(validPerson.getTrialSite().getId() != AbstractDomainObject.NOT_YET_SAVED_ID);

		Person p = (Person) sessionFactory.getCurrentSession().get(Person.class, validPerson
				.getId());

		assertNotNull(p);
		assertEquals(validPerson.getId(), p.getId());
		assertNotNull(p.getTrialSite());
		assertEquals(trialSite.getId(), p.getTrialSite().getId());
	}
	
	@Test
	@Transactional
	public void databaseIntegrationTest() {
		sessionFactory.getCurrentSession().save(validPerson);
		assertTrue(validPerson.getId()>0);
		assertTrue(validPerson.getLogin().getId()>0);
		Person person = (Person)sessionFactory.getCurrentSession().get(Person.class, validPerson.getId());
		assertEquals(validPerson.getId(), person.getId());
		assertEquals(validPerson.getLogin().getId(), person.getLogin().getId());
	}

}
