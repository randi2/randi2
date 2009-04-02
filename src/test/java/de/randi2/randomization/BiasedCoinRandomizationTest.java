/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2.randomization;

import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.BiasedCoinRandomizationConfig;
import de.randi2.utility.IntegerIterator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jthoenes
 */
public class BiasedCoinRandomizationTest {

	private Trial trial;
	private TrialSubject s;

	@org.junit.Before
	public void setUp() {
		trial = new Trial();
		trial.setRandomizationConfiguration(new BiasedCoinRandomizationConfig());
	}

	@Test
	public void testOneSubjectAllocation() {
		RandomizationHelper.addArms(trial, 20, 20);
		s = new TrialSubject();
		trial.randomize(s);
		assertNotNull(s.getArm());
		assertTrue(trial.getSubjects().contains(s));
	}

	@Test
	public void testFourtySubjectAllocation() {
		RandomizationHelper.addArms(trial, 20, 20);
		for (Integer i : (new IntegerIterator(40))) {
			s = new TrialSubject();
			trial.randomize(s);
		}
		assertNotNull(s.getArm());
		assertTrue(trial.getSubjects().contains(s));
	}
}
