package de.randi2.simulation.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.randi2.model.TreatmentArm;
import static junit.framework.Assert.*;

public class SimulationResultTest {

	
	
	@Test
	public void testDuration(){
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setPlannedSubjects(100);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setPlannedSubjects(100);
		arms.add(arm1);
		arms.add(arm2);
		SimulationResult simResult = new SimulationResult(arms, null);
		for(int i = 0; i<1000;i++){
			SimulationRun run = new SimulationRun(new int[]{100,100});
			run.setTime(10L);
			simResult.addSimulationRun(run);
		}
		assertEquals(10, simResult.getDuration());
		
		
		simResult = new SimulationResult(arms, null);
		for(long i = 1; i<1001;i++){
			SimulationRun run = new SimulationRun(new int[]{100,100});
			run.setTime(i);
			simResult.addSimulationRun(run);
		}
		assertEquals(500, simResult.getDuration());
	}
	
	
	@Test
	public void testMarginalBalance(){
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setPlannedSubjects(100);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setPlannedSubjects(100);
		arms.add(arm1);
		arms.add(arm2);
		
		SimulationResult simResult = new SimulationResult(arms, null);
		SimulationRun simRun = new SimulationRun(new int[] { 20, 20 });
		simRun.setSubjectsPerArms(new int[] { 8, 22 });
		double roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult, 0.46667);
		simRun.setTime(10l);
		simResult.addSimulationRun(simRun);
		simRun = new SimulationRun(new int[] { 20, 20 });
		simRun.setSubjectsPerArms(new int[] { 11, 29 });
		roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult, 0.45);
		simRun.setTime(10l);
		simResult.addSimulationRun(simRun);
		simRun = new SimulationRun(new int[] { 20, 20 });
		simRun.setSubjectsPerArms(new int[] { 20, 20 });
		roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult, 0.0);
		simRun.setTime(10l);
		simResult.addSimulationRun(simRun);
		simRun = new SimulationRun(new int[] { 20, 20 });
		simRun.setSubjectsPerArms(new int[] { 21, 19 });
		roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult, 0.05);
		simRun.setTime(10l);
		simResult.addSimulationRun(simRun);
		simRun = new SimulationRun(new int[] { 20, 20 });
		simRun.setSubjectsPerArms(new int[] { 15, 25 });
		roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult, 0.25);
		simRun.setTime(10l);
		simResult.addSimulationRun(simRun);
		simRun = new SimulationRun(new int[] { 20, 20 });
		simRun.setSubjectsPerArms(new int[] { 23, 17 });
		roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult, 0.15);
		simRun.setTime(10l);
		simResult.addSimulationRun(simRun);
		
		assertEquals(0.0, simResult.getMarginalBalanceMin());
		
		roundedResult = Math
		.round((simResult.getMarginalBalanceMax() * 100000.0)) / 100000.0;;
		assertEquals(0.46667, roundedResult);
		
		roundedResult = Math
		.round((simResult.getMarginalBalanceMean() * 100000.0)) / 100000.0;;
		assertEquals(0.22778, roundedResult);
		
	}
	
	
	@Test
	public void testSubjectCounts(){
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setPlannedSubjects(100);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setPlannedSubjects(100);
		arms.add(arm1);
		arms.add(arm2);
		
		SimulationResult simResult = new SimulationResult(arms, null);
		SimulationRun simRun = new SimulationRun(new int[] { 20, 20 });
		simRun.setSubjectsPerArms(new int[] { 8, 22 });
		double roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult, 0.46667);
		simRun.setTime(10l);
		simResult.addSimulationRun(simRun);
		simRun = new SimulationRun(new int[] { 20, 20 });
		simRun.setSubjectsPerArms(new int[] { 11, 29 });
		roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult, 0.45);
		simRun.setTime(10l);
		simResult.addSimulationRun(simRun);
		simRun = new SimulationRun(new int[] { 20, 20 });
		simRun.setSubjectsPerArms(new int[] { 20, 20 });
		roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult, 0.0);
		simRun.setTime(10l);
		simResult.addSimulationRun(simRun);
		simRun = new SimulationRun(new int[] { 20, 20 });
		simRun.setSubjectsPerArms(new int[] { 21, 19 });
		roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult, 0.05);
		simRun.setTime(10l);
		simResult.addSimulationRun(simRun);
		simRun = new SimulationRun(new int[] { 20, 20 });
		simRun.setSubjectsPerArms(new int[] { 15, 25 });
		roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult, 0.25);
		simRun.setTime(10l);
		simResult.addSimulationRun(simRun);
		simRun = new SimulationRun(new int[] { 20, 20 });
		simRun.setSubjectsPerArms(new int[] { 23, 17 });
		roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult, 0.15);
		simRun.setTime(10l);
		simResult.addSimulationRun(simRun);
		
		simResult.analyze();
		assertEquals(8, simResult.getSimResultArms().get(0).getMin());
		assertEquals(23, simResult.getSimResultArms().get(0).getMax());
		assertEquals(20.0, simResult.getSimResultArms().get(0).getMedian());
		roundedResult = Math
		.round((simResult.getSimResultArms().get(0).getMean() * 100000.0)) / 100000.0;;
		assertEquals(16.33333, roundedResult);
		
		assertEquals(17, simResult.getSimResultArms().get(1).getMin());
		assertEquals(29, simResult.getSimResultArms().get(1).getMax());
		assertEquals(23.0, simResult.getSimResultArms().get(1).getMedian());
		roundedResult = Math
		.round((simResult.getSimResultArms().get(1).getMean() * 100000.0)) / 100000.0;;
		assertEquals(22.0, roundedResult);
		

	}
}
