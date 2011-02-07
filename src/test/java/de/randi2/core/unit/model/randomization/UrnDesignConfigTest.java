package de.randi2.core.unit.model.randomization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.randomization.UrnDesignConfig;
import de.randi2.randomization.RandomizationAlgorithm;
import de.randi2.testUtility.utility.AbstractDomainTest;

public class UrnDesignConfigTest  extends AbstractDomainTest<UrnDesignConfig> {

	UrnDesignConfig conf;
	
	public UrnDesignConfigTest(){
		super(UrnDesignConfig.class);
	}
	
	
	@Test
	public void testTrial_null(){
		conf = new UrnDesignConfig();
		conf.setTrial(null);
		assertNull(conf.getTrial());
	}
	
	@Test
	public void testTrial_withEmptyRandomizationConfig(){
		conf = new UrnDesignConfig();
		Trial trial = new Trial();
		assertNull(trial.getRandomizationConfiguration());
		conf.setTrial(trial);
		assertEquals(trial, conf.getTrial());
		assertEquals(conf, trial.getRandomizationConfiguration());
	}
	
	@Test
	public void testTrial_withRandomizationConfig(){
		conf = new UrnDesignConfig();
		Trial trial = new Trial();
		trial.setRandomizationConfiguration(new UrnDesignConfig());
		assertNotNull(trial.getRandomizationConfiguration());
		conf.setTrial(trial);
		assertEquals(trial, conf.getTrial());
		assertFalse(conf.equals(trial.getRandomizationConfiguration()));
	}
	
	@Test
	public void testGetAlgortihm_withSeed(){
		conf = new UrnDesignConfig(1234);
		conf.setTrial(new Trial());
		RandomizationAlgorithm<?> algorithm = conf.getAlgorithm();
		assertNotNull(algorithm);
		assertEquals(1234, algorithm.getSeed());
		assertEquals(algorithm, conf.getAlgorithm());
		
	}
	
	@Test
	public void testGetAlgortihm_withoutSeed(){
		conf = new UrnDesignConfig();
		conf.setTrial(new Trial());
		RandomizationAlgorithm<?> algorithm = conf.getAlgorithm();
		assertNotNull(algorithm);
		assertEquals(Long.MIN_VALUE, algorithm.getSeed());
		assertEquals(algorithm, conf.getAlgorithm());
	}
	
	@Test
	public void testResetAlgorithm_withSeed(){
		conf = new UrnDesignConfig(1234);
		conf.setTrial(new Trial());
		RandomizationAlgorithm<?> algorithm = conf.getAlgorithm();
		assertEquals(1234, algorithm.getSeed());
		assertEquals(algorithm, conf.getAlgorithm());
		conf.resetAlgorithm();
		assertEquals(1234, conf.getAlgorithm().getSeed());
		assertNotSame(algorithm, conf.getAlgorithm());
	}
	
	
	@Test
	public void testResetAlgortihm_withoutSeed(){
		conf = new UrnDesignConfig();
		conf.setTrial(new Trial());
		RandomizationAlgorithm<?> algorithm = conf.getAlgorithm();
		assertEquals(Long.MIN_VALUE, algorithm.getSeed());
		assertEquals(algorithm, conf.getAlgorithm());
		conf.resetAlgorithm();
		assertEquals(Long.MIN_VALUE, conf.getAlgorithm().getSeed());
		assertNotSame(algorithm, conf.getAlgorithm());
	}
	
	
	@Test
	public void testResetAlgorithmWithNextSeed(){
		conf = new UrnDesignConfig(1234);
		conf.setTrial(new Trial());
		RandomizationAlgorithm<?> algorithm = conf.getAlgorithm();
		assertEquals(1234, algorithm.getSeed());
		assertEquals(algorithm, conf.getAlgorithm());
		conf.resetAlgorithmWithNextSeed();
		assertEquals(1234+10000, conf.getAlgorithm().getSeed());
		assertNotSame(algorithm, conf.getAlgorithm());
	}
	
	
	@Test
	public void testResetAlgorithmWithNextSeed_withoutSeed(){
		conf = new UrnDesignConfig();
		conf.setTrial(new Trial());
		RandomizationAlgorithm<?> algorithm = conf.getAlgorithm();
		assertEquals(Long.MIN_VALUE, algorithm.getSeed());
		assertEquals(algorithm, conf.getAlgorithm());
		conf.resetAlgorithmWithNextSeed();
		assertEquals(Long.MIN_VALUE, conf.getAlgorithm().getSeed());
		assertNotSame(algorithm, conf.getAlgorithm());
	}
	
	
	@Test
	public void testResetAlgorithmNewSeed(){
		conf = new UrnDesignConfig();
		conf.setTrial(new Trial());
		RandomizationAlgorithm<?> algorithm = conf.getAlgorithm();
		assertEquals(Long.MIN_VALUE, algorithm.getSeed());
		assertEquals(algorithm, conf.getAlgorithm());
		conf.resetAlgorithm(123);
		assertEquals(123, conf.getAlgorithm().getSeed());
		assertNotSame(algorithm, conf.getAlgorithm());
	}
	
	
	@Test
	public void testCountReplacedBalls(){
		conf = new UrnDesignConfig();
		conf.setTrial(new Trial());
		conf.setCountReplacedBalls(10);
		assertEquals(10, conf.getCountReplacedBalls());
	}
	
	@Test
	public void testInitializeCountBalls(){
		conf = new UrnDesignConfig();
		conf.setTrial(new Trial());
		conf.setInitializeCountBalls(10);
		assertEquals(10, conf.getInitializeCountBalls());
	}
	
	@Test
	public void testTempData(){
		conf = new UrnDesignConfig();
		conf.setTempData(null);
		assertNotNull(conf.getTempData());
	}
	
	@Test
	public void testTrialHasOnlyTwoArms(){
		conf = new UrnDesignConfig();
		Trial trial = new Trial();
		Set<TreatmentArm> arms = new HashSet<TreatmentArm>();
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setName("arm1");
		arms.add(arm1);
		trial.setTreatmentArms(arms);
		conf.setTrial(trial);
		assertInvalid(conf);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setName("arm2");
		arms.add(arm2);
		trial.setTreatmentArms(arms);
		conf.setTrial(trial);
		assertValid(conf);
	}
}