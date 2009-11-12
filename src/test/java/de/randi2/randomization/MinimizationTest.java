package de.randi2.randomization;

import static de.randi2.randomization.RandomizationHelper.randomize;
import static de.randi2.utility.IntegerIterator.upto;
import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.SubjectProperty;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.OrdinalCriterion;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.model.criteria.constraints.OrdinalConstraint;
import de.randi2.model.randomization.MinimizationConfig;
import de.randi2.simulation.distribution.ConcreteDistribution;
import de.randi2.simulation.distribution.UniformDistribution;
import de.randi2.simulation.model.DistributionSubjectProperty;
import de.randi2.unsorted.ContraintViolatedException;

public class MinimizationTest {

	private Trial trial;
	private TrialSubject s;
	private MinimizationConfig conf;

	
	@Before
	public void setUp() {
		trial = new Trial();
		
	}


	@Test
	public void testNaiveMinimization(){
		RandomizationHelper.addArms(trial, 70,50,30);
		conf = new MinimizationConfig();
		conf.setWithRandomizedSubjects(false);
		conf.setP(0.95);
		trial.setRandomizationConfiguration(conf);
		for (int i : upto(150)){
			randomize(trial, new TrialSubject());
		}
		assertEquals(150, trial.getSubjects().size());
		
	}
	
	@Test
	public void testNaiveMinimizationWithRandomizedSubjects(){
		RandomizationHelper.addArms(trial, 70,50,30);
		conf = new MinimizationConfig();
		conf.setWithRandomizedSubjects(true);
		conf.setP(0.9);
		trial.setRandomizationConfiguration(conf);
		for (int i : upto(150)){
			randomize(trial, new TrialSubject());
		}
		assertEquals(150, trial.getSubjects().size());
	}	
	
	@Test
	public void testNaiveMinimizationWithRandomizedSubjects_5_Treatments(){
		RandomizationHelper.addArms(trial, 56,23,78,47,29);
		conf = new MinimizationConfig();
		conf.setWithRandomizedSubjects(true);
		conf.setP(0.95);
		trial.setRandomizationConfiguration(conf);
		for (int i : upto(233)){
			randomize(trial, new TrialSubject());
		}
		assertEquals(233, trial.getSubjects().size());
	}
	
	
	@Test
	public void testBiasedCoinMinimization(){
		RandomizationHelper.addArms(trial, 10,20,30);
		
		ArrayList<DistributionSubjectProperty> dProperties = new ArrayList<DistributionSubjectProperty>();
		DichotomousCriterion cr1 = new DichotomousCriterion();
		cr1.setName("SEX");
		cr1.setOption1("M");
		cr1.setOption2("F");
		OrdinalCriterion cr2 = new OrdinalCriterion();
		List<String> elements = new ArrayList<String>();
		elements.add("1");
		elements.add("2");
		elements.add("3");
		elements.add("4");
		elements.add("5");
		elements.add("6");
		cr2.setElements(elements);
		cr2.setName("TrialSite");
		try {
			
			cr1.addStrata(new DichotomousConstraint(Arrays
					.asList(new String[] { "M" })));

			cr1.addStrata(new DichotomousConstraint(Arrays
					.asList(new String[] { "F" })));
		
			
			cr2.addStrata(new OrdinalConstraint(Arrays
					.asList(new String[] { "1" })));
			cr2.addStrata(new OrdinalConstraint(Arrays
					.asList(new String[] { "2" })));
			cr2.addStrata(new OrdinalConstraint(Arrays
					.asList(new String[] { "3" })));
			cr2.addStrata(new OrdinalConstraint(Arrays
					.asList(new String[] { "4" })));
			cr2.addStrata(new OrdinalConstraint(Arrays
					.asList(new String[] { "5" })));
			cr2.addStrata(new OrdinalConstraint(Arrays
					.asList(new String[] { "6" })));

			dProperties.add(new DistributionSubjectProperty(cr1,  new UniformDistribution<String>(cr1.getConfiguredValues(),1)));
			dProperties.add(new DistributionSubjectProperty(cr2,  new UniformDistribution<String>(cr2.getConfiguredValues(),1)));

			
			
		} catch (ContraintViolatedException e) {
			e.printStackTrace();
		}
		
		trial.addCriterion(cr1);
		trial.addCriterion(cr2);
		
		Minimization algorithm = new Minimization(trial, 1);
		conf = new MinimizationConfig();
		conf.setWithRandomizedSubjects(false);
		conf.setBiasedCoinMinimization(true);
		conf.setP(0.70);
		algorithm.configuration = conf;
		trial.setRandomizationConfiguration(conf);
		Minimization alg = (Minimization)conf.getAlgorithm();
		alg.getProbabilitiesPerPreferredTreatment();
		TrialSubject subject = new TrialSubject();
		for (int i : upto(60)){
			randomize(trial, generateTrialSubject(dProperties, subject));
			System.out.println("--");
		}
		assertEquals(60, trial.getSubjects().size());
	}
	
	
	private static TrialSubject generateTrialSubject(List<DistributionSubjectProperty> properties, TrialSubject oldSubject) {
		oldSubject.setProperties(null);
		HashSet<SubjectProperty<?>> tempSet = new HashSet<SubjectProperty<?>>();
		for (DistributionSubjectProperty dsp :properties) {
			SubjectProperty<Serializable> pr = new SubjectProperty<Serializable>(
					dsp.getCriterion());
			try {
				pr.setValue(dsp.getNextSubjectValue());
				System.out.println(pr.getValue());
			} catch (ContraintViolatedException e) {
			}
			tempSet.add(pr);
		}
		oldSubject.setProperties(tempSet);
		return oldSubject;
	}
}
