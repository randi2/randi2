package de.randi2.services;

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
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.simulation.model.SimulationResult;
import de.randi2.simulation.service.SimulationService;
import de.randi2.simulation.service.SimulationServiceImpl;
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
		int blocksize = 4;
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
	
//		BlockRandomizationConfig config =  new BlockRandomizationConfig();
//		config.setMaximum(blocksize);
//		config.setMinimum(blocksize);
		CompleteRandomizationConfig config = new CompleteRandomizationConfig();
		
		validTrial.setRandomizationConfiguration(config);
		
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

			validTrial.addCriterion(cr);
			validTrial.addCriterion(cr1);
			validTrial.addCriterion(cr2);


		} catch (ContraintViolatedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		SimulationResult result = service.simulateTrial(validTrial, 1000);
		
		System.out.println(result.getAmountRuns());
		for(int i = 0;i<arms.size();i++){
			System.out.println("---------arm " + arms.get(i).getName() + "---------------------------");
			System.out.println("Median " + result.getMedians()[i]);
			System.out.println("mean " +result.getMeans()[i]);
			System.out.println("min " + result.getMins()[i]);
			System.out.println("max " +result.getMaxs()[i]);
		}
		
		System.out.println("exit");
	}
}
