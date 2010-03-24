package de.randi2.simulation.model;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class SimulationRunTest {

	@Test
	public void testMarginalBalance2Arms() {
		SimulationRun simRun = new SimulationRun(new int[] { 10, 20 });
		simRun.setSubjectsPerArms(new int[] { 8, 22 });
		double roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult, 0.15789);

		simRun = new SimulationRun(new int[] { 20, 20 });
		simRun.setSubjectsPerArms(new int[] { 11, 29 });
		roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult, 0.45);

		simRun = new SimulationRun(new int[] { 20, 20 });
		simRun.setSubjectsPerArms(new int[] { 20, 20 });
		roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult, 0.0);
		
		simRun = new SimulationRun(new int[] { 100, 500 });
		simRun.setSubjectsPerArms(new int[] { 120, 480 });
		roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult, 0.11111);
	}

	@Test
	public void testMarginalBalance3Arms() {
		SimulationRun simRun = new SimulationRun(new int[] { 10, 20, 30 });
		simRun.setSubjectsPerArms(new int[] { 8, 20, 32 });
		double roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult,  0.09302);

		simRun = new SimulationRun(new int[] { 20, 20, 20 });
		simRun.setSubjectsPerArms(new int[] { 20, 20, 20 });
		roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult, 0.0);
		
		simRun = new SimulationRun(new int[] { 100, 100, 100 });
		simRun.setSubjectsPerArms(new int[] { 80, 105, 115 });
		roundedResult = Math
				.round((simRun.getMarginalBalace() * 100000.0)) / 100000.0;
		assertEquals(roundedResult, 0.11667);
	}


	
}
