/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2.core.unit.randomization;

import static de.randi2.core.unit.randomization.RandomizationHelper.randomize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.BiasedCoinRandomizationConfig;
import de.randi2.utility.IntegerIterator;

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
		randomize(trial,s);
		assertNotNull(s.getArm());
		assertTrue(trial.getSubjects().contains(s));
	}

	@Test
	public void testFourtySubjectAllocation() {
		RandomizationHelper.addArms(trial, 20, 20);
		for (Integer i : (new IntegerIterator(40))) {
			s = new TrialSubject();
			randomize(trial,s);
		}
		assertNotNull(s.getArm());
		assertTrue(trial.getSubjects().contains(s));
	}
}
