package de.randi2.randomization;

import static de.randi2.randomization.RandomizationHelper.randomize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.MinimizationConfig;

public class MinimizationTest {

	private Trial trial;
	private TrialSubject s;
	private MinimizationConfig conf;

	
	@Before
	public void setUp() {
		trial = new Trial();
		conf = new MinimizationConfig();
		trial.setRandomizationConfiguration(conf);
	}


}
