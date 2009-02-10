/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2test.randomization;

import de.randi2.model.Trial;
import de.randi2.model.randomization.BaseRandomizationConfig;
import de.randi2.randomization.BlockRandomization;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author jthoenes
 */
public class BlockRandomizationTest {

	private Trial trial;

	public BlockRandomizationTest() {
	}

	@Before
	public void setUp() {
		trial = new Trial();
	}

	@Test
	public void testInitialization() {
		BaseRandomizationConfig conf = new BaseRandomizationConfig();
		conf.setAlgorithmClass(BlockRandomization.class);
		Object algorithm = conf.getAlgorithm(trial);
		assertTrue(algorithm instanceof BlockRandomization);
	}
}