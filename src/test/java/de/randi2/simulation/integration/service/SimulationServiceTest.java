package de.randi2.simulation.integration.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.randi2.core.integration.services.AbstractServiceTest;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.simulation.distribution.UniformDistribution;
import de.randi2.simulation.model.DistributionSubjectProperty;
import de.randi2.simulation.model.SimulationResult;
import de.randi2.simulation.service.SimulationService;
import de.randi2.simulation.service.SimulationServiceImpl;
import de.randi2.testUtility.utility.DomainObjectFactory;

public class SimulationServiceTest extends AbstractServiceTest{

	private SimulationService service = new SimulationServiceImpl();
	@Autowired
	protected DomainObjectFactory factory;

	private Trial validTrial;

	@Before
	public void setUp() {
		validTrial = factory.getTrial();
	}
	
	@Test
	public void testWithoutStrata(){
		validTrial.addParticipatingSite(factory.getTrialSite());
		validTrial.addParticipatingSite(factory.getTrialSite());
		int blocksize = 10;
		int randomizations = 100;
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
		
		
		BlockRandomizationConfig configb =  new BlockRandomizationConfig();
		configb.setMaximum(blocksize);
		configb.setMinimum(blocksize);
		validTrial.setRandomizationConfiguration(configb);
		SimulationResult result = service.simulateTrial(validTrial,new ArrayList<DistributionSubjectProperty>(),new UniformDistribution<TrialSite>(new ArrayList<TrialSite>(validTrial.getParticipatingSites())), 1000, 10000000, false);
		assertEquals(1000, result.getAmountRuns());
		double roundedResult = Math
		.round(( result.getMarginalBalanceMax() * 100000.0)) / 100000.0;;
		assertEquals(0.0, roundedResult);
		
		roundedResult = Math
		.round(( result.getMarginalBalanceMin() * 100000.0)) / 100000.0;;
		assertEquals(0.0, roundedResult);

		roundedResult = Math
		.round(( result.getMarginalBalanceMean() * 100000.0)) / 100000.0;;
		assertEquals(0.0, roundedResult);
		
		for(int i = 0;i<arms.size();i++){
			roundedResult = Math
			.round(( result.getMedians()[i] * 100000.0)) / 100000.0;;
			assertEquals(50.0, roundedResult);
			roundedResult = Math
			.round(( result.getMeans()[i] * 100000.0)) / 100000.0;;
			assertEquals(50.0, roundedResult);
			assertEquals(50,  result.getMins()[i]);
			assertEquals(50, result.getMaxs()[i]);
		}
		
	}
	
	@Test
	public void testWithStrata(){
		validTrial.addParticipatingSite(factory.getTrialSite());
		validTrial.addParticipatingSite(factory.getTrialSite());
		int blocksize = 10;
		int randomizations = 100;
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
		
		
		BlockRandomizationConfig configb =  new BlockRandomizationConfig();
		configb.setMaximum(blocksize);
		configb.setMinimum(blocksize);
		validTrial.setRandomizationConfiguration(configb);
		SimulationResult result = service.simulateTrial(validTrial,new ArrayList<DistributionSubjectProperty>(),new UniformDistribution<TrialSite>(new ArrayList<TrialSite>(validTrial.getParticipatingSites())), 1000, 10000000, false);
		assertEquals(1000, result.getAmountRuns());
		double roundedResult = Math
		.round(( result.getMarginalBalanceMax() * 100000.0)) / 100000.0;;
		assertEquals(0.0, roundedResult);
		
		roundedResult = Math
		.round(( result.getMarginalBalanceMin() * 100000.0)) / 100000.0;;
		assertEquals(0.0, roundedResult);

		roundedResult = Math
		.round(( result.getMarginalBalanceMean() * 100000.0)) / 100000.0;;
		assertEquals(0.0, roundedResult);
		
		for(int i = 0;i<arms.size();i++){
			roundedResult = Math
			.round(( result.getMedians()[i] * 100000.0)) / 100000.0;;
			assertEquals(50.0, roundedResult);
			roundedResult = Math
			.round(( result.getMeans()[i] * 100000.0)) / 100000.0;;
			assertEquals(50.0, roundedResult);
			assertEquals(50,  result.getMins()[i]);
			assertEquals(50, result.getMaxs()[i]);
		}
		
	}
	
	
	@Test
	public void testWithTrialSiteStrata(){
		validTrial.addParticipatingSite(factory.getTrialSite());
		validTrial.addParticipatingSite(factory.getTrialSite());
		int blocksize = 10;
		int randomizations = 100;
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
		validTrial.setStratifyTrialSite(true);
		
		BlockRandomizationConfig configb =  new BlockRandomizationConfig();
		configb.setMaximum(blocksize);
		configb.setMinimum(blocksize);
		validTrial.setRandomizationConfiguration(configb);
		SimulationResult result = service.simulateTrial(validTrial,new ArrayList<DistributionSubjectProperty>(),new UniformDistribution<TrialSite>(new ArrayList<TrialSite>(validTrial.getParticipatingSites())), 1000, 10000000, false);
		assertEquals(1000, result.getAmountRuns());
		double roundedResult = Math
		.round(( result.getMarginalBalanceMax() * 100000.0)) / 100000.0;
		assertTrue(roundedResult<0.1);
		
		roundedResult = Math
		.round(( result.getMarginalBalanceMin() * 100000.0)) / 100000.0;;
		assertEquals(0.0, roundedResult);

		roundedResult = Math
		.round(( result.getMarginalBalanceMean() * 100000.0)) / 100000.0;;
		assertTrue(roundedResult<0.05);
		
		for(int i = 0;i<arms.size();i++){
			roundedResult = Math
			.round(( result.getMedians()[i] * 100000.0)) / 100000.0;;
			assertEquals(50.0, roundedResult);
			roundedResult = Math
			.round(( result.getMeans()[i] * 100000.0)) / 100000.0;;
			assertTrue(49.7<roundedResult);
			assertTrue(50.3>roundedResult);
		}
		
	}
}
