package de.randi2.simulation.distribution;

import static junit.framework.Assert.*;
import java.util.Arrays;

import org.junit.Test;

public class UniformDistributionTest {

	@Test
	public void testUniformDistribution(){
		UniformDistribution<Integer> ud = new UniformDistribution<Integer>(Arrays.asList(0,1,2,3,4,5));
		int[] values = new int[ud.elements.size()];
		for(int i =0; i<100000;i++){
			values[ud.getNextValue()]++;
		}
		for(int i : values){
			System.out.println(i/100000.0);
			assertTrue((i/100000.0)>0.16);
			assertTrue((i/100000.0)<0.17);
		}
	}
}
