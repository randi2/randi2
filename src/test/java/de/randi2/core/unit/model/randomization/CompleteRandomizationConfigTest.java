package de.randi2.core.unit.model.randomization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import de.randi2.model.Trial;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.randomization.RandomizationAlgorithm;
import de.randi2.testUtility.utility.AbstractDomainTest;

public class CompleteRandomizationConfigTest extends AbstractDomainTest<CompleteRandomizationConfig> {

	CompleteRandomizationConfig conf;
	
	public CompleteRandomizationConfigTest(){
		super(CompleteRandomizationConfig.class);
	}
	
	
	@Test
	public void testTrial_null(){
		conf = new CompleteRandomizationConfig();
		conf.setTrial(null);
		assertNull(conf.getTrial());
	}
	
	@Test
	public void testTrial_withEmptyRandomizationConfig(){
		conf = new CompleteRandomizationConfig();
		Trial trial = new Trial();
		assertNull(trial.getRandomizationConfiguration());
		conf.setTrial(trial);
		assertEquals(trial, conf.getTrial());
		assertEquals(conf, trial.getRandomizationConfiguration());
	}
	
	@Test
	public void testTrial_withRandomizationConfig(){
		conf = new CompleteRandomizationConfig();
		Trial trial = new Trial();
		trial.setRandomizationConfiguration(new CompleteRandomizationConfig());
		assertNotNull(trial.getRandomizationConfiguration());
		conf.setTrial(trial);
		assertEquals(trial, conf.getTrial());
		assertFalse(conf.equals(trial.getRandomizationConfiguration()));
	}
	
	@Test
	public void testGetAlgortihm_withSeed(){
		conf = new CompleteRandomizationConfig(1234);
		conf.setTrial(new Trial());
		RandomizationAlgorithm<?> algorithm = conf.getAlgorithm();
		assertNotNull(algorithm);
		assertEquals(1234, algorithm.getSeed());
		assertEquals(algorithm, conf.getAlgorithm());
		
	}
	
	@Test
	public void testGetAlgortihm_withoutSeed(){
		conf = new CompleteRandomizationConfig();
		conf.setTrial(new Trial());
		RandomizationAlgorithm<?> algorithm = conf.getAlgorithm();
		assertNotNull(algorithm);
		assertEquals(Long.MIN_VALUE, algorithm.getSeed());
		assertEquals(algorithm, conf.getAlgorithm());
	}
	
	@Test
	public void testResetAlgorithm_withSeed(){
		conf = new CompleteRandomizationConfig(1234);
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
		conf = new CompleteRandomizationConfig();
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
		conf = new CompleteRandomizationConfig(1234);
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
		conf = new CompleteRandomizationConfig();
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
		conf = new CompleteRandomizationConfig();
		conf.setTrial(new Trial());
		RandomizationAlgorithm<?> algorithm = conf.getAlgorithm();
		assertEquals(Long.MIN_VALUE, algorithm.getSeed());
		assertEquals(algorithm, conf.getAlgorithm());
		conf.resetAlgorithm(123);
		assertEquals(123, conf.getAlgorithm().getSeed());
		assertNotSame(algorithm, conf.getAlgorithm());
	}
}
