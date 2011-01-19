package de.randi2.core.unit.model.randomization;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.randomization.Urn;
import de.randi2.model.randomization.UrnDesignConfig;
import de.randi2.testUtility.utility.AbstractDomainTest;

public class UrnTest extends AbstractDomainTest<Urn> {

	public UrnTest() {
		super(Urn.class);
	}

	@Test
	public void testAddTreatmentArm() {
		Urn urn = new Urn();
		for (int i = 1; i <= 10; i++) {
			urn.add(new TreatmentArm());
			assertEquals(i, urn.getUrn().size());
		}
	}

	@Test
	public void testDrawFromUrn() {
		Urn urn = new Urn();
		for (int i = 1; i <= 10; i++) {
			urn.add(new TreatmentArm());
		}
		for (int i = 9; i >= 0; i--) {
			assertNotNull(urn.drawFromUrn(new Random()));
			assertEquals(i, urn.getUrn().size());
		}
	}

	@Test
	public void testGenerateUrn() {
		UrnDesignConfig conf = new UrnDesignConfig();
		Trial trial = new Trial();
		conf.setTrial(trial);
		Set<TreatmentArm> arms = new HashSet<TreatmentArm>();
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setPlannedSubjects(10);
		arm1.setName("arm1");
		arms.add(arm1);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setPlannedSubjects(10);
		arm2.setName("arm2");
		arms.add(arm2);
		trial.setTreatmentArms(arms);
		for(int i=1;i<10;i++){
			conf.setInitializeCountBalls(i);
			assertEquals(i*2, Urn.generate(conf).getUrn().size());	
		}
		
	}

}