package de.randi2.model.randomization;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.test.utility.AbstractDomainTest;

public class MinimizationTempDataTest extends AbstractDomainTest<MinimizationTempData> {

	public MinimizationTempDataTest() {
		super(MinimizationTempData.class);
	}

	@Test
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
		hibernateTemplate.persist(trial.getLeadingSite());
		hibernateTemplate.persist(trial.getSponsorInvestigator());
		hibernateTemplate.persist(trial);
		hibernateTemplate.persist(treatmentArm1);
		hibernateTemplate.persist(treatmentArm2);
		hibernateTemplate.persist(treatmentArm3);
		
		
		
		Map<TreatmentArm, Double> map1 = new HashMap<TreatmentArm, Double>();
		map1.put(treatmentArm1, 1.0);
		map1.put(treatmentArm2, 2.0);
		map1.put(treatmentArm3, 3.0);
		MinimizationMapElementWrapper mw1 = new MinimizationMapElementWrapper(map1);
		
		MinimizationTempData mtemp1 = new MinimizationTempData();
		Map<TreatmentArm, MinimizationMapElementWrapper> probabilitiesPerPreferredTreatment1 = new HashMap<TreatmentArm, MinimizationMapElementWrapper>();
		probabilitiesPerPreferredTreatment1.put(treatmentArm1, mw1);
		mtemp1.setProbabilitiesPerPreferredTreatment(probabilitiesPerPreferredTreatment1);
		hibernateTemplate.persist(mtemp1);
	}
}
