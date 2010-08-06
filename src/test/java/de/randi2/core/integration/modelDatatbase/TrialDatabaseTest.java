package de.randi2.core.integration.modelDatatbase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.testUtility.utility.AbstractDomainDatabaseTest;

@Transactional
public class TrialDatabaseTest  extends AbstractDomainDatabaseTest<Trial> {

	public TrialDatabaseTest() {
		super(Trial.class);
	}

	private Trial validTrial;

	@Before
	public void setUp() {
		// Valides Trial
		super.setUp();
		validTrial = factory.getTrial();
		sessionFactory.getCurrentSession().save(validTrial.getLeadingSite().getContactPerson());
	}
	
	
	@Test
	public void databaseIntegrationTest() {
		TrialSite leadingSite = factory.getTrialSite();
		leadingSite.setContactPerson(factory.getPerson());
		// sessionFactory.getCurrentSession().persist(leadingSite.getContactPerson());
		// sessionFactory.getCurrentSession().flush();
		sessionFactory.getCurrentSession().persist(leadingSite);
		validTrial.setSponsorInvestigator(leadingSite.getContactPerson());
		validTrial.setLeadingSite(leadingSite);

		TrialSite pTrialSite = factory.getTrialSite();
		pTrialSite.setContactPerson(factory.getPerson());
		// sessionFactory.getCurrentSession().persist(pTrialSite.getContactPerson());
		sessionFactory.getCurrentSession().persist(pTrialSite);
		validTrial.addParticipatingSite(pTrialSite);
		CompleteRandomizationConfig conf = new CompleteRandomizationConfig();
		validTrial.setRandomizationConfiguration(conf);
		sessionFactory.getCurrentSession().persist(validTrial);
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setName("arm1");
		arm1.setTrial(validTrial);
		arm1.setPlannedSubjects(100);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setName("arm2");
		arm2.setTrial(validTrial);
		arm2.setPlannedSubjects(100);
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
		sessionFactory.getCurrentSession().persist(arm1);
		sessionFactory.getCurrentSession().persist(arm2);
		validTrial.setTreatmentArms(arms);

		sessionFactory.getCurrentSession().saveOrUpdate(validTrial);
		assertTrue(validTrial.getId() > 0);
//		validTrial = (Trial) sessionFactory.getCurrentSession().get(Trial.class, validTrial
//				.getId());

		List<TrialSubject> subjects = new ArrayList<TrialSubject>();
		for (int i = 0; i < 100; i++) {
			TrialSubject subject = new TrialSubject();

			TreatmentArm assignedArm = validTrial
					.getRandomizationConfiguration().getAlgorithm().randomize(
							subject);
			subject.setIdentification("identification" + i);
			subject.setArm(assignedArm);
			subject.setRandNumber(assignedArm.getName() + "_"
					+ (assignedArm.getSubjects().size() + 1));
			subject.setCounter((validTrial.getSubjects().size() + 1));
			if (subject.getIdentification() == null)
				subject.setIdentification(subject.getRandNumber());
			subjects.add(subject);
		}
		sessionFactory.getCurrentSession().update(validTrial);
		Trial dbTrial = (Trial) sessionFactory.getCurrentSession().get(Trial.class, validTrial
				.getId());

		assertEquals(validTrial.getId(), dbTrial.getId());
		assertEquals(validTrial.getName(), dbTrial.getName());
		assertEquals(validTrial.getDescription(), dbTrial.getDescription());
		assertEquals(validTrial.getLeadingSite().getName(), dbTrial
				.getLeadingSite().getName());
		assertEquals(validTrial.getRandomizationConfiguration().getId(),
				dbTrial.getRandomizationConfiguration().getId());
		assertEquals(validTrial.getRandomizationConfiguration().getAlgorithm()
				.getClass(), dbTrial.getRandomizationConfiguration()
				.getAlgorithm().getClass());
//		 TrialSite dbTrialSite =
//		 (TrialSite)sessionFactory.getCurrentSession().get(TrialSite.class,
//		 pTrialSite.getId());
//		 assertEquals(1, dbTrialSite.getTrials());
//		 assertEquals(validTrial.getParticipatingSites().size(),
//		 dbTrial.getParticipatingSites().size());
//		assertEquals(validTrial.getTreatmentArms().get(0).getSubjects().size(),
//				dbTrial.getTreatmentArms().get(0).getSubjects().size());
//		assertEquals(validTrial.getTreatmentArms().get(1).getSubjects().size(),
//				dbTrial.getTreatmentArms().get(1).getSubjects().size());

	}

}
