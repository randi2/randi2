package de.randi2.core.unit.randomization;

import static de.randi2.utility.IntegerIterator.upto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.SubjectProperty;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.randomization.ResponseAdaptiveRandomizationConfig;
import de.randi2.model.randomization.ResponseAdaptiveRandomizationTempData;
import de.randi2.model.randomization.ResponseAdaptiveUrn;
import de.randi2.randomization.ResponseAdaptiveRandomization;
import de.randi2.unsorted.ContraintViolatedException;


public class ResponseAdaptiveRandomizationTest {
	
	private Trial trial;
	private TrialSubject s;
	private ResponseAdaptiveRandomizationConfig conf;
	
	@Before
	public void setUp() {
		trial = new Trial();
		conf = new ResponseAdaptiveRandomizationConfig();
		trial.setRandomizationConfiguration(conf);
	}
	
	@Test
	public void testOneSubjectAllocation() {
		int initializeCount = 4;
		int countSuccess= 10;
		int countFailure=2;
		RandomizationHelper.addArms(trial, 50, 50);
		conf.setInitializeCountBalls(initializeCount);
		conf.setCountBallsResponseFailure(countFailure);
		conf.setCountBallsResponseSuccess(countSuccess);
		s = new TrialSubject();
		int[] countBalls = new int[2];
		countBalls[0] = initializeCount;
		countBalls[1] = initializeCount;
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>(trial.getTreatmentArms());

		RandomizationHelper.randomize(trial, s);
		DichotomousCriterion treatmentResponse = new DichotomousCriterion();
		treatmentResponse.setOption1("success");
		treatmentResponse.setOption2("failure");
		trial.setTreatmentResponse(treatmentResponse);
		SubjectProperty<String> response = new SubjectProperty<String>(treatmentResponse);
		try {
			response.setValue("success");
		} catch (ContraintViolatedException e) {
			fail("could not set value to subject property");
		}
		s.setResponseProperty(response);
		ResponseAdaptiveRandomization algorithm = new ResponseAdaptiveRandomization(trial);
		algorithm.addResponse(s);
		if(s.getArm().getName().equals(arms.get(0).getName())){
			countBalls[0]+=10;
			countBalls[1]+=2;
		}else{
			countBalls[0]+=2;
			countBalls[1]+=10;
		}
			
		assertTrue(checkUrn(countBalls));  
		
		assertEquals(1, trial.getSubjects().size());
	}
	
	
	@Test
	public void testOneSubjectAllocationInitializeCount0() {
		int initializeCount = 0;
		int countSuccess= 10;
		int countFailure=2;
		RandomizationHelper.addArms(trial, 50, 50);
		conf.setInitializeCountBalls(initializeCount);
		conf.setCountBallsResponseFailure(countFailure);
		conf.setCountBallsResponseSuccess(countSuccess);
		s = new TrialSubject();
		int[] countBalls = new int[2];
		countBalls[0] = initializeCount;
		countBalls[1] = initializeCount;
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>(trial.getTreatmentArms());

		RandomizationHelper.randomize(trial, s);
		if(s.getArm().getName().equals(arms.get(0).getName()))
			countBalls[0]++;
		else
			countBalls[1]++;
		DichotomousCriterion treatmentResponse = new DichotomousCriterion();
		treatmentResponse.setOption1("success");
		treatmentResponse.setOption2("failure");
		trial.setTreatmentResponse(treatmentResponse);
		SubjectProperty<String> response = new SubjectProperty<String>(treatmentResponse);
		try {
			response.setValue("success");
		} catch (ContraintViolatedException e) {
			fail("could not set value to subject property");
		}
		s.setResponseProperty(response);
		ResponseAdaptiveRandomization algorithm = new ResponseAdaptiveRandomization(trial);
		algorithm.addResponse(s);
		if(s.getArm().getName().equals(arms.get(0).getName())){
			countBalls[0]+=10;
			countBalls[1]+=2;
		}else{
			countBalls[0]+=2;
			countBalls[1]+=10;
		}
			
		assertTrue(checkUrn(countBalls));  
		
		assertEquals(1, trial.getSubjects().size());
	}
	
	@Test
	public void test100SubjectAllocation() {
		int initializeCount = 4;
		int countSuccess= 10;
		int countFailure=4;
		RandomizationHelper.addArms(trial, 50, 50, 50);
		conf.setInitializeCountBalls(initializeCount);
		conf.setCountBallsResponseFailure(countFailure);
		conf.setCountBallsResponseSuccess(countSuccess);
		s = new TrialSubject();
		int[] countBalls = new int[3];
		countBalls[0] = initializeCount;
		countBalls[1] = initializeCount;
		countBalls[2] = initializeCount;
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>(trial.getTreatmentArms());
		
		DichotomousCriterion treatmentResponse = new DichotomousCriterion();
		treatmentResponse.setOption1("success");
		treatmentResponse.setOption2("failure");
		trial.setTreatmentResponse(treatmentResponse);
		
		for (int i : upto(100)) {
			RandomizationHelper.randomize(trial, s);
			SubjectProperty<String> response = new SubjectProperty<String>(treatmentResponse);
			try {
				if (i % 2 == 0) {
					response.setValue("success");
				} else {
					response.setValue("failure");
				}
			} catch (ContraintViolatedException e) {
				fail("could not set value to subject property");
			}
			s.setResponseProperty(response);
			ResponseAdaptiveRandomization algorithm = new ResponseAdaptiveRandomization(trial);
			algorithm.addResponse(s);
			if (i % 2 == 0) {
				if (s.getArm().getName().equals(arms.get(0).getName())) {
					countBalls[0] += countSuccess;
					countBalls[1] += countFailure / (arms.size() - 1);
					countBalls[2] += countFailure / (arms.size() - 1);
				}
				else if (s.getArm().getName().equals(arms.get(1).getName())) {
					countBalls[0] += countFailure / (arms.size() - 1);
					countBalls[1] += countSuccess;
					countBalls[2] += countFailure / (arms.size() - 1);
				} 
				else if (s.getArm().getName().equals(arms.get(2).getName())){
					countBalls[0] += countFailure / (arms.size() - 1);
					countBalls[1] += countFailure / (arms.size() - 1);
					countBalls[2] += countSuccess;
				}
			} else {
				if (s.getArm().getName().equals(arms.get(0).getName())) {
					countBalls[0] += countFailure;
					countBalls[1] += countSuccess / (arms.size() - 1);
					countBalls[2] += countSuccess / (arms.size() - 1);
				}
				else if (s.getArm().getName().equals(arms.get(1).getName())) {
					countBalls[0] += countSuccess / (arms.size() - 1);
					countBalls[1] += countFailure;
					countBalls[2] += countSuccess / (arms.size() - 1);
				}
				else if (s.getArm().getName().equals(arms.get(2).getName())){
					countBalls[0] += countSuccess / (arms.size() - 1);
					countBalls[1] += countSuccess / (arms.size() - 1);
					countBalls[2] += countFailure;
				}
			}
			assertTrue(checkUrn(countBalls));  
		}
		assertEquals(100, trial.getSubjects().size());
	}
	
	private boolean checkUrn(int[] countBalls){
		boolean countCorrect = true;
		String stratum = "";
		if(trial.isStratifyTrialSite()) 
			stratum = s.getTrialSite().getId() + "";
		stratum += s.getStratum();
		ResponseAdaptiveUrn urn = ((ResponseAdaptiveRandomizationTempData)conf.getTempData()).getResponseAdaptiveUrn(stratum);
		int[] count = new int[countBalls.length];
		for(int i=0; i<countBalls.length; i++){
			count[i] = 0;
		}
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>(trial.getTreatmentArms());
		for(TreatmentArm arm : urn.getResponseAdaptiveUrn()){
			for(int i=0; i<arms.size();i++){
				if(arm.getName().equals(arms.get(i).getName())){
					count[i]++;
				}
			}
		}
		for(int i = 0; i<countBalls.length; i++){
			if(countBalls[i]!=count[i]){
				countCorrect=false;
				break;
			}
				
		}
		return countCorrect;
	}
	
}
