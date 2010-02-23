package de.randi2.simulation.distribution;

import static junit.framework.Assert.*;
import java.util.Arrays;

import org.junit.Test;

import de.randi2.utility.Pair;


public class ConcreteDistributionTest {

	private int runsPerEntry = 1000;
	private double limit = 0.05;
	
	private void testConcreteDistribution(ConcreteDistribution<Integer> cd){
		int all = 0;
		for(int i : cd.getRatio()){
			all+=i;
		}
		int runs = runsPerEntry*all;
		int[] values = new int[cd.elements.size()];
		for(int i =0; i<runs;i++){
			values[cd.getNextValue()]++;
		}
		for(int i = 0; i< values.length;i++){
			double mean = (1.0 * values[i]) / runs;
			double realMean = (1.0/all)*cd.getRatio()[i];
			double limitL = realMean-limit;
			double limitH = realMean+limit;
			
			assertTrue("error: " + mean +" > " + limitL,(mean)>limitL);
			assertTrue("error: " + mean +" < " +limitH,(mean)<limitH);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test1000Times(){
		for(int i=0; i<1000;i++){
			testConcreteDistribution(new ConcreteDistribution<Integer>(Arrays.asList(0,1,2,3), 2,5,2,1));
			testConcreteDistribution(new ConcreteDistribution<Integer>(Arrays.asList(0,1),2,1));
			testConcreteDistribution(new ConcreteDistribution<Integer>(Arrays.asList(0,1),1,2));
			testConcreteDistribution(new ConcreteDistribution<Integer>(Arrays.asList(0,1),1,3));
			testConcreteDistribution(new ConcreteDistribution<Integer>(Arrays.asList(0,1,2,3), 5,5,2,1));
			testConcreteDistribution(new ConcreteDistribution<Integer>(Pair.of(0, 5),Pair.of(1, 5),Pair.of(2, 2),Pair.of(3, 1)));
		}
	}
	
}
