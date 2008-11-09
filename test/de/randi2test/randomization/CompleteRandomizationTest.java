package de.randi2test.randomization;


import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static de.randi2test.utility.RANDI2Assert.assertInList;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.BaseRandomizationConfig;
import de.randi2.randomization.CompleteRandomization;
import de.randi2.randomization.RandomizationAlgorithm;

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
		TreatmentArm ta3 = getTA(20);
		TreatmentArm ta4 = getTA(15);
		
		trial.setTreatmentArms(Arrays.asList(new TreatmentArm[]{ta1, ta2}));
		TrialSubject subject = new TrialSubject();
		assertNull(subject.getArm());
		trial.randomize(subject);
		assertNotNull(subject.getArm());
		assertInList(subject.getArm(), new TreatmentArm[]{ta1,ta2});
	}

}
