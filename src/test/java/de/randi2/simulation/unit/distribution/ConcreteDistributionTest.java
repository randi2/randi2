package de.randi2.simulation.unit.distribution;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.randi2.simulation.distribution.ConcreteDistribution;
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
		int[] values = new int[cd.getElements().size()];
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
	
	@Test
	public void testSeed(){
		List<Integer> results = new ArrayList<Integer>();
		ConcreteDistribution<Integer> cd = new ConcreteDistribution<Integer>(100, Arrays.asList(0,1,2,3), 2,5,2,1);
		for(int i=0;i<1000;i++){
			results.add(cd.getNextValue());
		}
		List<ConcreteDistribution<Integer>> cds = new ArrayList<ConcreteDistribution<Integer>>(); 
		for(int i=0;i<100;i++){
			cds.add(new ConcreteDistribution<Integer>(100, Arrays.asList(0,1,2,3), 2,5,2,1));
		}
		for(Integer result : results){
			for(ConcreteDistribution<Integer> acd : cds){
				assertEquals(result, acd.getNextValue());
			}
		}
		
	}
	
}
