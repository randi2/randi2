package de.randi2.simulation.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.OrdinalCriterion;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.model.criteria.constraints.OrdinalConstraint;
import de.randi2.model.randomization.MinimizationConfig;
import de.randi2.simulation.distribution.ConcreteDistribution;
import de.randi2.simulation.distribution.UniformDistribution;
import de.randi2.simulation.model.DistributionSubjectProperty;
import de.randi2.simulation.model.SimulationResult;
import de.randi2.simulation.model.SimulationRun;
import de.randi2.test.utility.DomainObjectFactory;
import de.randi2.unsorted.ContraintViolatedException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring.xml", "/META-INF/subconfig/test.xml"})
public class SimulationServiceTest {

	private SimulationService service = new SimulationServiceImpl();
	@Autowired
	protected DomainObjectFactory factory;

	private Trial validTrial;

	@Before
	public void setUp() {
		validTrial = factory.getTrial();
	}
	
	@Test
	public void test(){
		System.out.println("init");
		validTrial.addParticipatingSite(factory.getTrialSite());
		validTrial.addParticipatingSite(factory.getTrialSite());
		int blocksize = 6;
		int randomizations = 60;
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setPlannedSubjects(randomizations/2);
		arm1.setName("arm1");
		arm1.setTrial(validTrial);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setPlannedSubjects(randomizations/2);
		arm2.setName("arm2");
		arm2.setTrial(validTrial);
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
		validTrial.setTreatmentArms(arms);
	
//		BlockRandomizationConfig config =  new BlockRandomizationConfig();
//		config.setMaximum(blocksize);
//		config.setMinimum(blocksize);
//		CompleteRandomizationConfig config = new CompleteRandomizationConfig();
		MinimizationConfig config = new MinimizationConfig();
		config.setWithRandomizedSubjects(false);
		config.setBiasedCoinMinimization(true);
		config.setP(0.70);
		
		validTrial.setRandomizationConfiguration(config);
		ArrayList<DistributionSubjectProperty> dProperties = new ArrayList<DistributionSubjectProperty>();
		DichotomousCriterion cr = new DichotomousCriterion();
		cr.setName("SEX");
		cr.setOption1("M");
		cr.setOption2("F");
		DichotomousCriterion cr1 = new DichotomousCriterion();
		cr1.setOption1("1");
		cr1.setOption2("2");
		cr1.setName("Tum.Status");
		DichotomousCriterion cr2 = new DichotomousCriterion();
		cr2.setOption1("1");
		cr2.setOption2("2");
		cr2.setName("Fit.Level");
		OrdinalCriterion cr3 = new OrdinalCriterion();
		List<String> elements = new ArrayList<String>();
		elements.add("1");
		elements.add("2");
		elements.add("3");
		elements.add("4");
		cr3.setElements(elements);
		cr3.setName("Tumor Level");
		try {
			
			cr.addStrata(new DichotomousConstraint(Arrays
					.asList(new String[] { "M" })));

			cr.addStrata(new DichotomousConstraint(Arrays
					.asList(new String[] { "F" })));
		
			cr1.addStrata(new DichotomousConstraint(Arrays
					.asList(new String[] { "1" })));
			cr1.addStrata(new DichotomousConstraint(Arrays
					.asList(new String[] { "2" })));
			cr2.addStrata(new DichotomousConstraint(Arrays
					.asList(new String[] { "1" })));
			cr2.addStrata(new DichotomousConstraint(Arrays
					.asList(new String[] { "2" })));
			
			cr3.addStrata(new OrdinalConstraint(Arrays
					.asList(new String[] { "1" })));
			cr3.addStrata(new OrdinalConstraint(Arrays
					.asList(new String[] { "2" })));
			cr3.addStrata(new OrdinalConstraint(Arrays
					.asList(new String[] { "3" })));
			cr3.addStrata(new OrdinalConstraint(Arrays
					.asList(new String[] { "4" })));
			
			dProperties.add(new DistributionSubjectProperty(cr,  new UniformDistribution<String>(cr.getConfiguredValues())));
			dProperties.add(new DistributionSubjectProperty(cr1,  new UniformDistribution<String>(cr1.getConfiguredValues())));
			dProperties.add(new DistributionSubjectProperty(cr2,  new UniformDistribution<String>(cr2.getConfiguredValues())));
			dProperties.add(new DistributionSubjectProperty(cr3,  new ConcreteDistribution<String>(cr3.getConfiguredValues(),2,4,2,1)));

		} catch (ContraintViolatedException e) {}
		
		
		SimulationResult result = service.simulateTrial(validTrial,dProperties,new UniformDistribution<TrialSite>(new ArrayList<TrialSite>(validTrial.getParticipatingSites())), 1000, 10000);
		
		System.out.println("Runs: " + result.getAmountRuns());
		System.out.println("Time: " + result.getDuration() + "ms");
		System.out.println("Max marginal balance: " + result.getMarginalBalanceMax());
		System.out.println("Min marginal balance: " + result.getMarginalBalanceMin());
		System.out.println("Mean marginal balance: " + result.getMarginalBalanceMean());
		for(int i = 0;i<arms.size();i++){
			System.out.println("---------arm " + arms.get(i).getName() + "---------------------------");
			System.out.println("Median " + result.getMedians()[i]);
			System.out.println("mean " +result.getMeans()[i]);
			System.out.println("min " + result.getMins()[i]);
			System.out.println("max " +result.getMaxs()[i]);
		}
	}
}
