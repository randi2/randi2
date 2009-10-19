package de.randi2.randomization;

import static de.randi2.randomization.RandomizationHelper.randomize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;
import static de.randi2.utility.IntegerIterator.*;
import static de.randi2.randomization.RandomizationHelper.randomize;

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
		
	}


	@Test
	public void testNaiveMinimization(){
		RandomizationHelper.addArms(trial, 70,40,30);
		conf = new MinimizationConfig();
		conf.setP(0.99);
		trial.setRandomizationConfiguration(conf);
		for (int i : upto(150)){
			randomize(trial, new TrialSubject());
		}
		assertEquals(150, trial.getSubjects().size());
	}
}
