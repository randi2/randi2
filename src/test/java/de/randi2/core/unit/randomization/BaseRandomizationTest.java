/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2.core.unit.randomization;

import de.randi2.model.Trial;
import de.randi2.model.randomization.BiasedCoinRandomizationConfig;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.CompleteRandomizationConfig;

import de.randi2.randomization.BiasedCoinRandomization;
import de.randi2.randomization.BlockRandomization;
import de.randi2.randomization.CompleteRandomization;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BaseRandomizationTest {

	private Trial trial;

	@Before
	public void setUp() {
		trial = new Trial();
	}

	@Test
	public void testInitializationCompleteRandomization() {
		CompleteRandomizationConfig conf = new CompleteRandomizationConfig();
		trial.setRandomizationConfiguration(conf);
		assertTrue(CompleteRandomization.class.isInstance(conf.getAlgorithm()));
	}

	@Test
	public void testInitializationBiasedCoinRandomization() {
		BiasedCoinRandomizationConfig conf = new BiasedCoinRandomizationConfig();
		trial.setRandomizationConfiguration(conf);
		assertTrue(BiasedCoinRandomization.class.isInstance(conf.getAlgorithm()));
	}

	@Test
	public void testInitializationBlockRandomization() {
		BlockRandomizationConfig conf = new BlockRandomizationConfig();
		trial.setRandomizationConfiguration(conf);
		assertTrue(BlockRandomization.class.isInstance(conf.getAlgorithm()));
	}
}
