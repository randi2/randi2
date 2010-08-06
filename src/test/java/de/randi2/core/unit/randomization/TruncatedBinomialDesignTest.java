package de.randi2.core.unit.randomization;

import static de.randi2.core.unit.randomization.RandomizationHelper.randomize;
import static de.randi2.utility.IntegerIterator.upto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.TruncatedBinomialDesignConfig;

public class TruncatedBinomialDesignTest {

	private Trial trial;
	private TrialSubject s;
	private TruncatedBinomialDesignConfig conf;

	@Before
	public void setUp() {
		trial = new Trial();
		conf = new TruncatedBinomialDesignConfig();
		trial.setRandomizationConfiguration(conf);
	}

	@Test
	public void testOneSubjectAllocation() {
		RandomizationHelper.addArms(trial, 10, 10);
		s = new TrialSubject();
		randomize(trial,s);
		assertNotNull(s.getArm());
		assertTrue(trial.getSubjects().contains(s));
	}

	@Test
	public void testFourtySubjectAllocations() {
		RandomizationHelper.addArms(trial, 20, 20);
		s = new TrialSubject();
		for (int i : upto(40)) {
			randomize(trial,s);
		}
		assertEquals(40, trial.getSubjects().size());
		assertEquals(20, trial.getTreatmentArms().get(0).getCurrentSubjectsAmount());
		assertEquals(20, trial.getTreatmentArms().get(1).getCurrentSubjectsAmount());
	}
	
	@Test
	public void testSubjectAllocations1() {
		RandomizationHelper.addArms(trial, 100, 100);
		s = new TrialSubject();
		for (int i : upto(200)) {
			randomize(trial,s);
			if(trial.getTreatmentArms().get(0).getCurrentSubjectsAmount() == 100 && !s.getArm().getName().equals(trial.getTreatmentArms().get(0).getName())){
				assertEquals(s.getArm().getName(), trial.getTreatmentArms().get(1).getName()); 
			}
			if(trial.getTreatmentArms().get(1).getCurrentSubjectsAmount() == 100 && !s.getArm().getName().equals(trial.getTreatmentArms().get(1).getName())){
				assertEquals(s.getArm().getName(), trial.getTreatmentArms().get(0).getName()); 
			}
		}
		assertEquals(200, trial.getSubjects().size());
		assertEquals(100, trial.getTreatmentArms().get(0).getCurrentSubjectsAmount());
		assertEquals(100, trial.getTreatmentArms().get(1).getCurrentSubjectsAmount());
	}
}
