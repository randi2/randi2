package de.randi2.model.randomization;

import static junit.framework.Assert.*;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.test.utility.AbstractDomainTest;

public class MinimizationMapElementWrapperTest extends AbstractDomainTest<MinimizationMapElementWrapper> {

	public MinimizationMapElementWrapperTest() {
		super(MinimizationMapElementWrapper.class);
	}

	@Test
	public void persistAndUpdateObject(){
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
		
		Map<TreatmentArm, Double> map = new HashMap<TreatmentArm, Double>();
		map.put(treatmentArm1, 1.0);
		map.put(treatmentArm2, 2.0);
		map.put(treatmentArm3, 3.0);
		MinimizationMapElementWrapper mw = new MinimizationMapElementWrapper(map);
		hibernateTemplate.persist(mw);
		assertTrue(mw.getId()>-1);
		MinimizationMapElementWrapper mwDB = hibernateTemplate.get(MinimizationMapElementWrapper.class, mw.getId());
		assertEquals(3, mwDB.getMap().keySet().size());
		assertEquals(1.0, mwDB.getMap().get(treatmentArm1));
		assertEquals(2.0, mwDB.getMap().get(treatmentArm2));
		assertEquals(3.0, mwDB.getMap().get(treatmentArm3));
		mwDB.getMap().put(treatmentArm1, 10.0);
		hibernateTemplate.update(mwDB);
		MinimizationMapElementWrapper mwDB1 = hibernateTemplate.get(MinimizationMapElementWrapper.class, mw.getId());
		assertEquals(3, mwDB1.getMap().keySet().size());
		assertEquals(10.0, mwDB1.getMap().get(treatmentArm1));
		assertEquals(2.0, mwDB1.getMap().get(treatmentArm2));
		assertEquals(3.0, mwDB1.getMap().get(treatmentArm3));
	}
}
