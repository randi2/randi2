package de.randi2test.randomization;


import static de.randi2test.utility.RANDI2Assert.assertInList;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.BaseRandomizationConfig;
import de.randi2.randomization.CompleteRandomization;

public class CompleteRandomizationTest {

	private Trial trial;
	
	@Before
	public void setUp() throws Exception {
		trial = new Trial();
	}
	
	private TreatmentArm getTA(int size) {
		TreatmentArm ta = new TreatmentArm();
		ta.setPlannedSubjects(size);
		return ta;
	}
	
	
	@Test
	public void testInitialization(){
		BaseRandomizationConfig conf = new BaseRandomizationConfig();
		conf.setAlgorithmClass(CompleteRandomization.class);
		Object algorithm = conf.getAlgorithm(trial);
		assertTrue(algorithm instanceof CompleteRandomization);
	}
	
	@Test
	public void testRandomization(){
		BaseRandomizationConfig conf = new BaseRandomizationConfig();
		conf.setAlgorithmClass(CompleteRandomization.class);
		trial.setRandomizationConfiguration(conf);
		TreatmentArm ta1 = getTA(10);
		TreatmentArm ta2 = getTA(10);
		
		trial.setTreatmentArms(Arrays.asList(new TreatmentArm[]{ta1, ta2}));
		TrialSubject subject = new TrialSubject();
		assertNull(subject.getArm());
		trial.randomize(subject);
		assertNotNull(subject.getArm());
		assertInList(subject.getArm(), new TreatmentArm[]{ta1,ta2});
	}

}
