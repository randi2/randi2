package de.randi2.core.integration.modelDatatbase;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.TreatmentArm;
import de.randi2.testUtility.utility.AbstractDomainDatabaseTest;

public class TreatmentArmDatabaseTest extends AbstractDomainDatabaseTest<TreatmentArm> {

	private TreatmentArm validTreatmentArm;
	
	public TreatmentArmDatabaseTest(){
		super(TreatmentArm.class);
	}
	
	@Before
	public void setUp(){
		super.setUp();
		validTreatmentArm = new TreatmentArm();
		validTreatmentArm.setDescription("description");
		validTreatmentArm.setName("arm");
		validTreatmentArm.setPlannedSubjects(10);
		validTreatmentArm.setTrial(factory.getTrial());
	}
	
	
	@Test
	@Transactional
	public void databaseIntegrationTest(){
		entityManager.persist(validTreatmentArm.getTrial().getLeadingSite());
		entityManager.persist(validTreatmentArm.getTrial().getSponsorInvestigator());
		entityManager.persist(validTreatmentArm.getTrial());
		entityManager.persist(validTreatmentArm);
		assertTrue(validTreatmentArm.getId() >0);
		TreatmentArm dbArm = entityManager.find(TreatmentArm.class, validTreatmentArm.getId());
		assertEquals(validTreatmentArm.getDescription(), dbArm.getDescription());
		assertEquals(validTreatmentArm.getName(), dbArm.getName());
		assertEquals(validTreatmentArm.getPlannedSubjects(),dbArm.getPlannedSubjects());
		assertEquals(validTreatmentArm.getTrial().getId(), dbArm.getTrial().getId());
		assertEquals(validTreatmentArm.getTrial().getName(), dbArm.getTrial().getName());
	}

}
