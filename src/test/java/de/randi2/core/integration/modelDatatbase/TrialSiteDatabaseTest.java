package de.randi2.core.integration.modelDatatbase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.testUtility.utility.AbstractDomainDatabaseTest;

public class TrialSiteDatabaseTest extends
		AbstractDomainDatabaseTest<TrialSite> {

	private TrialSite validTrialSite;

	public TrialSiteDatabaseTest() {
		super(TrialSite.class);
	}

	@Before
	public void setUp() {
		super.setUp();
		validTrialSite = factory.getTrialSite();
		sessionFactory.getCurrentSession().save(
				validTrialSite.getContactPerson());
	}

	@Test
	@Transactional(propagation = Propagation.SUPPORTS)
	public void testTrials() {
		List<Trial> tl = new ArrayList<Trial>();

		tl.add(factory.getTrial());
		tl.add(factory.getTrial());
		tl.add(factory.getTrial());

		sessionFactory.getCurrentSession().saveOrUpdate(validTrialSite);
		sessionFactory.getCurrentSession().flush();
		assertTrue(validTrialSite.getId() != AbstractDomainObject.NOT_YET_SAVED_ID);
		for (Trial trial : tl) {
			trial.addParticipatingSite(validTrialSite);
			trial.setLeadingSite(validTrialSite);
			Login login = factory.getLogin();
			sessionFactory.getCurrentSession().persist(login);
			trial.setSponsorInvestigator(login.getPerson());
			assertEquals(1, trial.getParticipatingSites().size());
			assertEquals(validTrialSite.getId(), ((AbstractDomainObject) trial
					.getParticipatingSites().toArray()[0]).getId());
			sessionFactory.getCurrentSession().persist(trial);
			sessionFactory.getCurrentSession().flush();
		}
		TrialSite trialSite = (TrialSite) sessionFactory.getCurrentSession()
				.get(TrialSite.class, validTrialSite.getId());
		assertEquals(validTrialSite.getId(), trialSite.getId());

		sessionFactory.getCurrentSession().refresh(validTrialSite);
		validTrialSite.getTrials();
		assertEquals(tl.size(), trialSite.getTrials().size());

		List<Trial> trials = new ArrayList<Trial>();
		trials.add(new Trial());
		validTrialSite.setTrials(trials);
		assertEquals(trials, validTrialSite.getTrials());
	}

	@Test
	@Transactional
	public void testContactPerson() {
		Person p = factory.getPerson();
		sessionFactory.getCurrentSession().save(p);
		validTrialSite.setContactPerson(p);
		assertEquals(p.getSurname(), validTrialSite.getContactPerson()
				.getSurname());
		sessionFactory.getCurrentSession().saveOrUpdate(validTrialSite);
		assertTrue(validTrialSite.getId() != AbstractDomainObject.NOT_YET_SAVED_ID);
		assertTrue(p.getId() != AbstractDomainObject.NOT_YET_SAVED_ID);

		TrialSite c = (TrialSite) sessionFactory.getCurrentSession().get(
				TrialSite.class, validTrialSite.getId());
		assertEquals(p.getId(), c.getContactPerson().getId());
	}

	@Test
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void testMembers() {

		List<Person> members = new ArrayList<Person>();

		sessionFactory.getCurrentSession().saveOrUpdate(validTrialSite);

		for (int i = 0; i < 100; i++) {
			Person p = factory.getPerson();
			p.setTrialSite(validTrialSite);
			assertEquals(validTrialSite.getId(), p.getTrialSite().getId());
			sessionFactory.getCurrentSession().saveOrUpdate(p);
			members.add(p);
		}
		sessionFactory.getCurrentSession().flush();

		TrialSite c = (TrialSite) sessionFactory.getCurrentSession().get(
				TrialSite.class, validTrialSite.getId());
		assertEquals(validTrialSite.getId(), c.getId());
		sessionFactory.getCurrentSession().refresh(c);
		assertEquals(members.size(), c.getMembers().size());
	}

}
