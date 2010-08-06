/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2.core.unit.randomization;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.BlockRandomizationConfig;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import static de.randi2.utility.IntegerIterator.upto;
import static de.randi2.core.unit.randomization.RandomizationHelper.randomize;
import static de.randi2.testUtility.utility.RANDI2Assert.*;

/**
 *
 * @author jthoenes
 */
public class BlockRandomizationTest {

	private Trial trial;
	private TrialSubject s;
	private BlockRandomizationConfig conf;

	@Before
	public void setUp() {
		trial = new Trial();
		conf = new BlockRandomizationConfig();
		trial.setRandomizationConfiguration(conf);
	}

	@Ignore
	public void testOneSubjectAllocation() {
		RandomizationHelper.addArms(trial, 10, 10);
		conf.setMinimum(2);
		conf.setMaximum(2);
		conf.setType(BlockRandomizationConfig.TYPE.MULTIPLY);
		s = new TrialSubject();
		randomize(trial, s);
		assertNotNull(s.getArm());
		assertTrue(trial.getSubjects().contains(s));
	}
	
	@Ignore
	public void testFourSubjectAllocations() {
		RandomizationHelper.addArms(trial, 40, 40);
		conf.setMinimum(2);
		conf.setMaximum(2);
		conf.setType(BlockRandomizationConfig.TYPE.MULTIPLY);
		for (int i : upto(4)) {
			s = new TrialSubject();
			randomize(trial,s);
		}
		for(TreatmentArm arm : trial.getTreatmentArms()){
			assertEquals(2, arm.getSubjects().size());
		}
	}

	@Test
	public void testFourHundretAllocations(){
		RandomizationHelper.addArms(trial, 400, 400);
		conf.setMinimum(2);
		conf.setMaximum(2);
		conf.setType(BlockRandomizationConfig.TYPE.MULTIPLY);
		for (int i : upto(400)) {
			s = new TrialSubject();
			randomize(trial, s);
		}
		for(TreatmentArm arm : trial.getTreatmentArms()){
			assertEquals(200, arm.getSubjects().size());
		}
	}

	@Ignore
	public void testFourHundretAllocations2(){
		RandomizationHelper.addArms(trial, 400, 400);
		conf.setMinimum(1);
		conf.setMaximum(2);
		conf.setType(BlockRandomizationConfig.TYPE.MULTIPLY);
		for (int i : upto(400)) {
			s = new TrialSubject();
			randomize(trial, s);
		}
		for(TreatmentArm arm : trial.getTreatmentArms()){
			assertAtLeast(199, arm.getSubjects().size());
			assertAtMost(201, arm.getSubjects().size());
		}
	}

	@Test
	public void testVaryingAllocation(){
		RandomizationHelper.addArms(trial, 20, 20);
		conf.setMinimum(15);
		conf.setMaximum(20);
		conf.setType(BlockRandomizationConfig.TYPE.ABSOLUTE);
		for (int i : upto(20)) {
			s = new TrialSubject();
			randomize(trial, s);
		}
		for(TreatmentArm arm : trial.getTreatmentArms()){
			assertAtLeast(7, arm.getSubjects().size());
			assertAtMost(13, arm.getSubjects().size());
		}
	}
}
