package de.randi2test.randomization;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.AbstractRandomizationTempData;
import de.randi2.model.randomization.BaseRandomizationConfig;
import de.randi2.randomization.RandomizationAlgorithm;

public class RandomizationAlgorithmTest {

	private Trial trial;
	private DummyRandomizationAlgorithm algorithm;
	
	private TreatmentArm getTA(int size) {
		TreatmentArm ta = new TreatmentArm();
		ta.setPlannedSubjects(size);
		return ta;
	}

	private List<TreatmentArm> list(TreatmentArm[] array) {
		return Arrays.asList(array);
	}

	private class DummyRandomizationAlgorithm
			extends
			RandomizationAlgorithm<BaseRandomizationConfig, AbstractRandomizationTempData> {

		protected DummyRandomizationAlgorithm(Trial _trial) {
			super(_trial);
		}


		@Override
		public List<TreatmentArm> getRawBlock() {
			return super.getRawBlock();
		}


		@Override
		protected TreatmentArm doRadomize(TrialSubject subject, List<TreatmentArm> rawBlock, Random random) {
			return null;
		}

	}

	@Before
	public void setUp() throws Exception {
		trial = new Trial();
		trial.setRandomizationConfiguration(new BaseRandomizationConfig());
		algorithm = new DummyRandomizationAlgorithm(trial);
	}

	@Test
	public void testEqualArms(){
		TreatmentArm ta1 = getTA(1000);
		TreatmentArm ta2 = getTA(1000);
		trial.setTreatmentArms(list(new TreatmentArm[]{ta1,ta2}));
		
		assertEquals(list(new TreatmentArm[]{ta1, ta2}), algorithm.getRawBlock());
		
		ta1 = getTA(100);
		ta2 = getTA(100);
		TreatmentArm ta3 = getTA(100);
		trial.setTreatmentArms(list(new TreatmentArm[]{ta1,ta2,ta3}));
		
		assertEquals(list(new TreatmentArm[]{ta1, ta2, ta3}), algorithm.getRawBlock());
		
		ta1 = getTA(200);
		ta2 = getTA(200);
		ta3 = getTA(200);
		TreatmentArm ta4 = getTA(200);
		trial.setTreatmentArms(list(new TreatmentArm[]{ta1,ta2, ta3, ta4}));
		
		assertEquals(list(new TreatmentArm[]{ta1, ta2, ta3, ta4}), algorithm.getRawBlock());
		
		ta1 = getTA(600);
		ta2 = getTA(600);
		ta3 = getTA(600);
		ta4 = getTA(600);
		TreatmentArm ta5 = getTA(600);
		trial.setTreatmentArms(list(new TreatmentArm[]{ta1,ta2,ta3,ta4,ta5}));
		
		assertEquals(list(new TreatmentArm[]{ta1, ta2, ta3,ta4,ta5}), algorithm.getRawBlock());
	}
	
	@Test 
	public void testDoubleArmSizes(){
		TreatmentArm ta1 = getTA(33);
		TreatmentArm ta2 = getTA(66);
		trial.setTreatmentArms(list(new TreatmentArm[]{ta1,ta2}));
		
		assertEquals(list(new TreatmentArm[]{ta1, ta2, ta2}), algorithm.getRawBlock());
		
		ta1 = getTA(33);
		ta2 = getTA(66);
		TreatmentArm ta3 = getTA(33);
		trial.setTreatmentArms(list(new TreatmentArm[]{ta1,ta2,ta3}));
		
		assertEquals(list(new TreatmentArm[]{ta1, ta2, ta2, ta3}), algorithm.getRawBlock());
	}
	
	@Test
	public void testTotallyUnequal(){
		TreatmentArm ta1 = getTA(43);
		TreatmentArm ta2 = getTA(197);
		trial.setTreatmentArms(list(new TreatmentArm[]{ta1,ta2}));
		
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		for(int i = 0; i < 43; i++){arms.add(ta1);};
		for(int i = 0; i < 197; i++){arms.add(ta2);};
		
		assertEquals(arms, algorithm.getRawBlock());
		
		ta1 = getTA(311);
		ta2 = getTA(1223);
		TreatmentArm ta3 = getTA(149);
		trial.setTreatmentArms(list(new TreatmentArm[]{ta1,ta2,ta3}));
		
		arms = new ArrayList<TreatmentArm>();
		for(int i = 0; i < 311; i++){arms.add(ta1);};
		for(int i = 0; i < 1223; i++){arms.add(ta2);};
		for(int i = 0; i < 149; i++){arms.add(ta3);};
		
		assertEquals(arms, algorithm.getRawBlock());
		
	}
}
