package de.randi2.core.integration.modelDatabase.randomization;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.model.criteria.constraints.OrdinalConstraint;
import de.randi2.model.randomization.MinimizationMapElementWrapper;
import de.randi2.model.randomization.MinimizationTempData;
import de.randi2.testUtility.utility.AbstractDomainDatabaseTest;
import de.randi2.unsorted.ContraintViolatedException;

public class MinimizationTempDataTest extends AbstractDomainDatabaseTest<MinimizationTempData> {

	public MinimizationTempDataTest() {
		super(MinimizationTempData.class);
	}

	@Test
	@Transactional
	public void persistAndUpdateProbabilitiesPerPreferredTreatment(){
		Trial trial = factory.getTrial();
		TreatmentArm treatmentArm1 = new TreatmentArm();
		treatmentArm1.setDescription("description");
		treatmentArm1.setName("arm1");
		treatmentArm1.setPlannedSubjects(10);
		treatmentArm1.setTrial(trial);
		TreatmentArm treatmentArm2 = new TreatmentArm();
		treatmentArm2.setDescription("description");
		treatmentArm2.setName("arm2");
		treatmentArm2.setPlannedSubjects(20);
		treatmentArm2.setTrial(trial);
		treatmentArm1.setTrial(trial);
		TreatmentArm treatmentArm3 = new TreatmentArm();
		treatmentArm3.setDescription("description");
		treatmentArm3.setName("arm3");
		treatmentArm3.setPlannedSubjects(30);
		treatmentArm3.setTrial(trial);
		sessionFactory.getCurrentSession().persist(trial.getLeadingSite());
		sessionFactory.getCurrentSession().persist(trial.getSponsorInvestigator());
		sessionFactory.getCurrentSession().persist(trial);
		sessionFactory.getCurrentSession().persist(treatmentArm1);
		sessionFactory.getCurrentSession().persist(treatmentArm2);
		sessionFactory.getCurrentSession().persist(treatmentArm3);
		
		
		Map<TreatmentArm, MinimizationMapElementWrapper> probabilitiesPerPreferredTreatment1 = new HashMap<TreatmentArm, MinimizationMapElementWrapper>();
		Map<TreatmentArm, Double> map1 = new HashMap<TreatmentArm, Double>();
		map1.put(treatmentArm1, 1.0);
		map1.put(treatmentArm2, 2.0);
		map1.put(treatmentArm3, 3.0);
		MinimizationMapElementWrapper mw1 = new MinimizationMapElementWrapper(map1);
		probabilitiesPerPreferredTreatment1.put(treatmentArm1, mw1);
		
		Map<TreatmentArm, Double> map2 = new HashMap<TreatmentArm, Double>();
		map2.put(treatmentArm1, 4.0);
		map2.put(treatmentArm2, 5.0);
		map2.put(treatmentArm3, 6.0);
		MinimizationMapElementWrapper mw2 = new MinimizationMapElementWrapper(map2);
		probabilitiesPerPreferredTreatment1.put(treatmentArm2, mw2);
		
		Map<TreatmentArm, Double> map3 = new HashMap<TreatmentArm, Double>();
		map3.put(treatmentArm1, 7.0);
		map3.put(treatmentArm2, 8.0);
		map3.put(treatmentArm3, 9.0);
		MinimizationMapElementWrapper mw3 = new MinimizationMapElementWrapper(map3);
		probabilitiesPerPreferredTreatment1.put(treatmentArm3, mw3);
		
		
		MinimizationTempData mtemp1 = new MinimizationTempData();
		mtemp1.setProbabilitiesPerPreferredTreatment(probabilitiesPerPreferredTreatment1);
		
		sessionFactory.getCurrentSession().persist(mtemp1);
		
		
		assertTrue(mtemp1.getId()>-1);
		MinimizationTempData mtemp1DB = (MinimizationTempData) sessionFactory.getCurrentSession().get(MinimizationTempData.class, mtemp1.getId());
		assertEquals(mtemp1.getId(), mtemp1DB.getId());
		assertEquals(3,mtemp1DB.getProbabilitiesPerPreferredTreatment().keySet().size());
	
		
		assertEquals(1.0,mtemp1DB.getProbabilitiesPerPreferredTreatment().get(treatmentArm1).getMap().get(treatmentArm1));
		assertEquals(2.0,mtemp1DB.getProbabilitiesPerPreferredTreatment().get(treatmentArm1).getMap().get(treatmentArm2));
		assertEquals(3.0,mtemp1DB.getProbabilitiesPerPreferredTreatment().get(treatmentArm1).getMap().get(treatmentArm3));
		
		assertEquals(4.0,mtemp1DB.getProbabilitiesPerPreferredTreatment().get(treatmentArm2).getMap().get(treatmentArm1));
		assertEquals(5.0,mtemp1DB.getProbabilitiesPerPreferredTreatment().get(treatmentArm2).getMap().get(treatmentArm2));
		assertEquals(6.0,mtemp1DB.getProbabilitiesPerPreferredTreatment().get(treatmentArm2).getMap().get(treatmentArm3));
		
		assertEquals(7.0,mtemp1DB.getProbabilitiesPerPreferredTreatment().get(treatmentArm3).getMap().get(treatmentArm1));
		assertEquals(8.0,mtemp1DB.getProbabilitiesPerPreferredTreatment().get(treatmentArm3).getMap().get(treatmentArm2));
		assertEquals(9.0,mtemp1DB.getProbabilitiesPerPreferredTreatment().get(treatmentArm3).getMap().get(treatmentArm3));
		
		
		Map<TreatmentArm, MinimizationMapElementWrapper> probabilitiesPerPreferredTreatmentDB = mtemp1DB.getProbabilitiesPerPreferredTreatment();
		probabilitiesPerPreferredTreatmentDB.get(treatmentArm1).getMap().put(treatmentArm1, 10.0);
		probabilitiesPerPreferredTreatmentDB.get(treatmentArm1).getMap().put(treatmentArm2, 20.0);
		probabilitiesPerPreferredTreatmentDB.get(treatmentArm1).getMap().put(treatmentArm3, 30.0);
		
		sessionFactory.getCurrentSession().update(mtemp1DB);
		
		
		MinimizationTempData mtempDB1 = (MinimizationTempData) sessionFactory.getCurrentSession().get(MinimizationTempData.class, mtemp1.getId());
		assertEquals(mtempDB1.getId(), mtemp1DB.getId());
		assertEquals(3,mtempDB1.getProbabilitiesPerPreferredTreatment().keySet().size());
	
		

		TreatmentArm treatmentArmDB1 = (TreatmentArm) sessionFactory.getCurrentSession().get(TreatmentArm.class, treatmentArm1.getId());
		TreatmentArm treatmentArmDB2 = (TreatmentArm) sessionFactory.getCurrentSession().get(TreatmentArm.class, treatmentArm2.getId());
		TreatmentArm treatmentArmDB3 = (TreatmentArm) sessionFactory.getCurrentSession().get(TreatmentArm.class, treatmentArm3.getId());
		
		assertEquals(10.0,mtempDB1.getProbabilitiesPerPreferredTreatment().get(treatmentArmDB1).getMap().get(treatmentArmDB1));
		assertEquals(20.0,mtempDB1.getProbabilitiesPerPreferredTreatment().get(treatmentArmDB1).getMap().get(treatmentArmDB2));
		assertEquals(30.0,mtempDB1.getProbabilitiesPerPreferredTreatment().get(treatmentArmDB1).getMap().get(treatmentArmDB3));
		
		
	}
	
	@Test
	@Transactional
	public void persistCountConstraints(){
		Trial trial = factory.getTrial();
		TreatmentArm treatmentArm1 = new TreatmentArm();
		treatmentArm1.setDescription("description");
		treatmentArm1.setName("arm1");
		treatmentArm1.setPlannedSubjects(10);
		treatmentArm1.setTrial(trial);
		TreatmentArm treatmentArm2 = new TreatmentArm();
		treatmentArm2.setDescription("description");
		treatmentArm2.setName("arm2");
		treatmentArm2.setPlannedSubjects(20);
		treatmentArm2.setTrial(trial);
		treatmentArm1.setTrial(trial);
		TreatmentArm treatmentArm3 = new TreatmentArm();
		treatmentArm3.setDescription("description");
		treatmentArm3.setName("arm3");
		treatmentArm3.setPlannedSubjects(30);
		treatmentArm3.setTrial(trial);
		
		ArrayList<String> elements1 = new ArrayList<String>();
		elements1.add("Value1");
		DichotomousConstraint dc1;
		ArrayList<String> elements2 = new ArrayList<String>();
		elements2.add("Value2");
		DichotomousConstraint dc2;
		ArrayList<String> elements3 = new ArrayList<String>();
		elements3.add("Value3");
		elements3.add("Value4");
		elements3.add("Value5");
		OrdinalConstraint oc1;
		try {
			dc1 = new DichotomousConstraint(elements1);
			dc2 = new DichotomousConstraint(elements2);
			oc1 = new OrdinalConstraint(elements3);
		
		
		
			sessionFactory.getCurrentSession().persist(trial.getLeadingSite());
			sessionFactory.getCurrentSession().persist(trial.getSponsorInvestigator());
		sessionFactory.getCurrentSession().persist(trial);
		sessionFactory.getCurrentSession().persist(treatmentArm1);
		sessionFactory.getCurrentSession().persist(treatmentArm2);
		sessionFactory.getCurrentSession().persist(treatmentArm3);
		sessionFactory.getCurrentSession().persist(dc1);
		sessionFactory.getCurrentSession().persist(dc2);
		sessionFactory.getCurrentSession().persist(oc1);
		
		
		Map<AbstractConstraint<?>, MinimizationMapElementWrapper> countConstraints = new HashMap<AbstractConstraint<?>, MinimizationMapElementWrapper>();
		Map<TreatmentArm, Double> map1 = new HashMap<TreatmentArm, Double>();
		map1.put(treatmentArm1, 1.0);
		map1.put(treatmentArm2, 2.0);
		map1.put(treatmentArm3, 3.0);
		MinimizationMapElementWrapper mw1 = new MinimizationMapElementWrapper(map1);
		countConstraints.put(dc1, mw1);
		
		Map<TreatmentArm, Double> map2 = new HashMap<TreatmentArm, Double>();
		map2.put(treatmentArm1, 4.0);
		map2.put(treatmentArm2, 5.0);
		map2.put(treatmentArm3, 6.0);
		MinimizationMapElementWrapper mw2 = new MinimizationMapElementWrapper(map2);
		countConstraints.put(dc2, mw2);
		
		Map<TreatmentArm, Double> map3 = new HashMap<TreatmentArm, Double>();
		map3.put(treatmentArm1, 7.0);
		map3.put(treatmentArm2, 8.0);
		map3.put(treatmentArm3, 9.0);
		MinimizationMapElementWrapper mw3 = new MinimizationMapElementWrapper(map3);
		countConstraints.put(oc1, mw3);
		
		
		MinimizationTempData mtemp1 = new MinimizationTempData();
		mtemp1.setCountConstraints(countConstraints);
		
		sessionFactory.getCurrentSession().persist(mtemp1);
		
		
		assertTrue(mtemp1.getId()>-1);
		MinimizationTempData mtemp1DB = (MinimizationTempData) sessionFactory.getCurrentSession().get(MinimizationTempData.class, mtemp1.getId());
		assertEquals(mtemp1.getId(), mtemp1DB.getId());
		assertEquals(3,mtemp1DB.getCountConstraints().keySet().size());
	
		
		assertEquals(1.0,mtemp1DB.getCountConstraints().get(dc1).getMap().get(treatmentArm1));
		assertEquals(2.0,mtemp1DB.getCountConstraints().get(dc1).getMap().get(treatmentArm2));
		assertEquals(3.0,mtemp1DB.getCountConstraints().get(dc1).getMap().get(treatmentArm3));
		
		assertEquals(4.0,mtemp1DB.getCountConstraints().get(dc2).getMap().get(treatmentArm1));
		assertEquals(5.0,mtemp1DB.getCountConstraints().get(dc2).getMap().get(treatmentArm2));
		assertEquals(6.0,mtemp1DB.getCountConstraints().get(dc2).getMap().get(treatmentArm3));
		
		assertEquals(7.0,mtemp1DB.getCountConstraints().get(oc1).getMap().get(treatmentArm1));
		assertEquals(8.0,mtemp1DB.getCountConstraints().get(oc1).getMap().get(treatmentArm2));
		assertEquals(9.0,mtemp1DB.getCountConstraints().get(oc1).getMap().get(treatmentArm3));
		
		} catch (ContraintViolatedException e) {
			fail("initialization failed");
		}
	}
	
	
	@Test
	@Transactional
	public void persistCountTrialSites(){
		Trial trial = factory.getTrial();
		TreatmentArm treatmentArm1 = new TreatmentArm();
		treatmentArm1.setDescription("description");
		treatmentArm1.setName("arm1");
		treatmentArm1.setPlannedSubjects(10);
		treatmentArm1.setTrial(trial);
		TreatmentArm treatmentArm2 = new TreatmentArm();
		treatmentArm2.setDescription("description");
		treatmentArm2.setName("arm2");
		treatmentArm2.setPlannedSubjects(20);
		treatmentArm2.setTrial(trial);
		treatmentArm1.setTrial(trial);
		TreatmentArm treatmentArm3 = new TreatmentArm();
		treatmentArm3.setDescription("description");
		treatmentArm3.setName("arm3");
		treatmentArm3.setPlannedSubjects(30);
		treatmentArm3.setTrial(trial);
		
		TrialSite trialSite1 = trial.getLeadingSite();
		TrialSite trialSite2 = factory.getTrialSite();
		TrialSite trialSite3 = factory.getTrialSite();
		
		sessionFactory.getCurrentSession().persist(trial.getLeadingSite());
		sessionFactory.getCurrentSession().persist(trial.getSponsorInvestigator());
		sessionFactory.getCurrentSession().persist(trial);
		sessionFactory.getCurrentSession().persist(treatmentArm1);
		sessionFactory.getCurrentSession().persist(treatmentArm2);
		sessionFactory.getCurrentSession().persist(treatmentArm3);
		sessionFactory.getCurrentSession().persist(trialSite2);
		sessionFactory.getCurrentSession().persist(trialSite3);
		
		
		Map<TrialSite, MinimizationMapElementWrapper> countTrialSites = new HashMap<TrialSite, MinimizationMapElementWrapper>();
		Map<TreatmentArm, Double> map1 = new HashMap<TreatmentArm, Double>();
		map1.put(treatmentArm1, 1.0);
		map1.put(treatmentArm2, 2.0);
		map1.put(treatmentArm3, 3.0);
		MinimizationMapElementWrapper mw1 = new MinimizationMapElementWrapper(map1);
		countTrialSites.put(trialSite1, mw1);
		
		Map<TreatmentArm, Double> map2 = new HashMap<TreatmentArm, Double>();
		map2.put(treatmentArm1, 4.0);
		map2.put(treatmentArm2, 5.0);
		map2.put(treatmentArm3, 6.0);
		MinimizationMapElementWrapper mw2 = new MinimizationMapElementWrapper(map2);
		countTrialSites.put(trialSite2, mw2);
		
		Map<TreatmentArm, Double> map3 = new HashMap<TreatmentArm, Double>();
		map3.put(treatmentArm1, 7.0);
		map3.put(treatmentArm2, 8.0);
		map3.put(treatmentArm3, 9.0);
		MinimizationMapElementWrapper mw3 = new MinimizationMapElementWrapper(map3);
		countTrialSites.put(trialSite3, mw3);
		
		
		MinimizationTempData mtemp1 = new MinimizationTempData();
		mtemp1.setCountTrialSites(countTrialSites);
		
		sessionFactory.getCurrentSession().persist(mtemp1);
		
		
		assertTrue(mtemp1.getId()>-1);
		MinimizationTempData mtemp1DB = (MinimizationTempData) sessionFactory.getCurrentSession().get(MinimizationTempData.class, mtemp1.getId());
		assertEquals(mtemp1.getId(), mtemp1DB.getId());
		assertEquals(3,mtemp1DB.getCountTrialSites().keySet().size());
	
		
		assertEquals(1.0,mtemp1DB.getCountTrialSites().get(trialSite1).getMap().get(treatmentArm1));
		assertEquals(2.0,mtemp1DB.getCountTrialSites().get(trialSite1).getMap().get(treatmentArm2));
		assertEquals(3.0,mtemp1DB.getCountTrialSites().get(trialSite1).getMap().get(treatmentArm3));
		
		assertEquals(4.0,mtemp1DB.getCountTrialSites().get(trialSite2).getMap().get(treatmentArm1));
		assertEquals(5.0,mtemp1DB.getCountTrialSites().get(trialSite2).getMap().get(treatmentArm2));
		assertEquals(6.0,mtemp1DB.getCountTrialSites().get(trialSite2).getMap().get(treatmentArm3));
		
		assertEquals(7.0,mtemp1DB.getCountTrialSites().get(trialSite3).getMap().get(treatmentArm1));
		assertEquals(8.0,mtemp1DB.getCountTrialSites().get(trialSite3).getMap().get(treatmentArm2));
		assertEquals(9.0,mtemp1DB.getCountTrialSites().get(trialSite3).getMap().get(treatmentArm3));
		
	}
}
