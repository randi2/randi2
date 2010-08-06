package de.randi2.simulation.unit.model;

import java.io.Serializable;
import java.util.Arrays;

import org.junit.Test;

import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.simulation.distribution.UniformDistribution;
import de.randi2.unsorted.ContraintViolatedException;

import static junit.framework.Assert.*;

public class DistributionSubjectPropertyTest {

	
	
	@Test
	public void testDistributionSubjectProperty(){
		DichotomousCriterion dc = new DichotomousCriterion();
		dc.setOption1("option1");
		dc.setOption2("option2");
		try {
			dc.setStrata(Arrays.asList(new DichotomousConstraint(Arrays.asList(new String[]{"option1"})),new DichotomousConstraint(Arrays.asList(new String[]{"option2"}))));
		} catch (ContraintViolatedException e) {
			fail();
		}
		UniformDistribution<DichotomousConstraint> distri = new UniformDistribution<DichotomousConstraint>(dc.getStrata());
		double limit = 0.05;
	
		int[] values = new int[2];
		for(int i =0; i<100000;i++){
			if(distri.getNextValue().getExpectedValue().equals("option1")){
				values[0]++;
			}else values[1]++;
			
		}
		for(int i : values){
			double mean = (1.0 * i) / 100000;
			assertTrue("error: " + mean +" > " +((0.5)-limit),(mean)>((0.5)-limit));
			assertTrue("error: " + mean +" < " +((0.5)+limit),(mean)<((0.5)+limit));
		}
		
	}
}
