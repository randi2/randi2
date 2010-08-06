package de.randi2.core.unit.randomization;

import static de.randi2.core.unit.randomization.RandomizationHelper.randomize;
import static de.randi2.utility.IntegerIterator.upto;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.AssertTrue;
import org.junit.Before;
import org.junit.Test;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.Urn;
import de.randi2.model.randomization.UrnDesignConfig;
import de.randi2.model.randomization.UrnDesignTempData;

public class UrnDesignTest {

	
	private Trial trial;
	private TrialSubject s;
	private UrnDesignConfig conf;

	@Before
	public void setUp() {
		trial = new Trial();
		conf = new UrnDesignConfig();
		trial.setRandomizationConfiguration(conf);
	}
	
	@Test
	public void testFourtySubjectAllocations() {
		int replacedBalls = 4;
		int initializeCount = 10;
		RandomizationHelper.addArms(trial, 20, 20);
		conf.setCountReplacedBalls(replacedBalls);
		conf.setInitializeCountBalls(initializeCount);
		s = new TrialSubject();
		int[] countBalls = new int[2];
		countBalls[0] = initializeCount;
		countBalls[1] = initializeCount;
		for (int i : upto(40)) {
			randomize(trial,s);
			if(s.getArm().getName().equals(trial.getTreatmentArms().get(0).getName())){
				countBalls[1]+= replacedBalls;
				countBalls[0]--;
			}else{
				countBalls[0]+= replacedBalls;
				countBalls[1]--;
				}
			assertTrue(checkUrn(countBalls));
		}
		assertEquals(40, trial.getSubjects().size());
	}
	
	
	@Test
	public void test100SubjectAllocations() {
		int replacedBalls = 2;
		int initializeCount = 4;
		RandomizationHelper.addArms(trial, 50, 50);
		conf.setCountReplacedBalls(replacedBalls);
		conf.setInitializeCountBalls(initializeCount);
		s = new TrialSubject();
		int[] countBalls = new int[2];
		countBalls[0] = initializeCount;
		countBalls[1] = initializeCount;
		for (int i : upto(100)) {
			randomize(trial,s);
			if(s.getArm().getName().equals(trial.getTreatmentArms().get(0).getName())){
				countBalls[1]+= replacedBalls;
				countBalls[0]--;
			}else{
				countBalls[0]+= replacedBalls;
				countBalls[1]--;
				}
			assertTrue(checkUrn(countBalls));
		}
		assertEquals(100, trial.getSubjects().size());
	}
	
	private boolean checkUrn(int[] countBalls){
		String stratum = "";
		if(trial.isStratifyTrialSite()) stratum = s.getTrialSite().getId() + "";
		stratum += s.getStratum();
		Urn urn = ((UrnDesignTempData)conf.getTempData()).getUrn(stratum);
		int[] count = new int[2];
		count[0] = 0;
		count[1] = 0;
		for(TreatmentArm arm : urn.getUrn()){
			if(arm.getName().equals(trial.getTreatmentArms().get(0).getName())){
				count[0]++;
			}else{
				count[1]++;
			}
		}
		return countBalls[0]==count[0] || countBalls[1]==count[1] ;
	}
}
