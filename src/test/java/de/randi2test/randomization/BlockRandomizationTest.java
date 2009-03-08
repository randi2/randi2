/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2test.randomization;

import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.BlockRandomizationConfig;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jthoenes
 */
public class BlockRandomizationTest {

	private Trial trial;
	private TrialSubject s;
	private BlockRandomizationConfig conf;

	@org.junit.Before
	public void setUp() {
		trial = new Trial();
		conf = new BlockRandomizationConfig();
		trial.setRandomizationConfiguration(conf);
	}

	@Test
	public void testOneSubjectAllocation() {
		RandomizationHelper.addArms(trial, 10, 10);
		conf.setMinimum(2); conf.setMaximum(2);
		s = new TrialSubject();
		trial.randomize(s);
		assertNotNull(s.getArm());
		assertTrue(trial.getSubjects().contains(s));
	}
}
