/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2.core.unit.randomization;

import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static de.randi2.core.unit.randomization.RandomizationHelper.randomize;
import static de.randi2.utility.IntegerIterator.*;

/**
 *
 * @author jthoenes
 */
public class CompleteRandomizationTest {

	private Trial trial;
	private TrialSubject s;
	private CompleteRandomizationConfig conf;

	@Before
	public void setUp() {
		trial = new Trial();
		conf = new CompleteRandomizationConfig();
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
	}
}