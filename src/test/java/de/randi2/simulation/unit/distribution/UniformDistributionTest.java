package de.randi2.simulation.unit.distribution;

import static junit.framework.Assert.*;
import java.util.Arrays;

import org.junit.Test;

import de.randi2.simulation.distribution.UniformDistribution;

public class UniformDistributionTest {

	private int runsPerEntry = 1000;
	private double limit = 0.05;
	
	private void testUniformDistribution(UniformDistribution<Integer> ud){
		int runs = runsPerEntry*ud.getElements().size();
		int[] values = new int[ud.getElements().size()];
		for(int i =0; i<runs;i++){
			values[ud.getNextValue()]++;
		}
		for(int i : values){
			double mean = (1.0 * i) / runs;
			assertTrue("error: " + mean +" > " +((1.0/ud.getElements().size())-limit),(mean)>((1.0/ud.getElements().size())-limit));
			assertTrue("error: " + mean +" < " +((1.0/ud.getElements().size())+limit),(mean)<((1.0/ud.getElements().size())+limit));
		}
	}
	
	
	
	@Test
	public void test1000Times(){
		for(int i=0; i<1000;i++){
			testUniformDistribution(new UniformDistribution<Integer>(Arrays.asList(0,1,2,3,4,5)));
			testUniformDistribution(new UniformDistribution<Integer>(Arrays.asList(0,1)));
			testUniformDistribution(new UniformDistribution<Integer>(Arrays.asList(0,1,2,3)));
		}
	}
}
