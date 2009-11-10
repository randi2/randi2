package de.randi2.simulation.distribution;

import static junit.framework.Assert.*;
import java.util.Arrays;

import org.junit.Test;

public class UniformDistributionTest {

	private int runsPerEntry = 1000;
	private double limit = 0.05;
	
	private void testUniformDistribution(UniformDistribution<Integer> ud){
		int runs = runsPerEntry*ud.elements.size();
		int[] values = new int[ud.elements.size()];
		for(int i =0; i<runs;i++){
			values[ud.getNextValue()]++;
		}
		for(int i : values){
			double mean = (1.0 * i) / runs;
			assertTrue("error: " + mean +" > " +((1.0/ud.elements.size())-limit),(mean)>((1.0/ud.elements.size())-limit));
			assertTrue("error: " + mean +" < " +((1.0/ud.elements.size())+limit),(mean)<((1.0/ud.elements.size())+limit));
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
