package de.randi2.core.unit.model.randomization;

import static org.junit.Assert.*;

import javax.validation.constraints.AssertTrue;

import org.junit.Test;

import de.randi2.model.Trial;
import de.randi2.model.randomization.MinimizationConfig;
import de.randi2.model.randomization.UrnDesignConfig;
import de.randi2.randomization.RandomizationAlgorithm;
import de.randi2.testUtility.utility.AbstractDomainTest;

public class MinimizationConfigTest extends AbstractDomainTest<MinimizationConfig> {

	MinimizationConfig conf;
	
	public MinimizationConfigTest(){
		super(MinimizationConfig.class);
	}
	
	
	@Test
	public void testTrial_null(){
		conf = new MinimizationConfig();
		conf.setTrial(null);
		assertNull(conf.getTrial());
	}
	
	@Test
	public void testTrial_withEmptyRandomizationConfig(){
		conf = new MinimizationConfig();
		Trial trial = new Trial();
		assertNull(trial.getRandomizationConfiguration());
		conf.setTrial(trial);
		assertEquals(trial, conf.getTrial());
		assertEquals(conf, trial.getRandomizationConfiguration());
	}
	
	@Test
	public void testTrial_withRandomizationConfig(){
		conf = new MinimizationConfig();
		Trial trial = new Trial();
		trial.setRandomizationConfiguration(new MinimizationConfig());
		assertNotNull(trial.getRandomizationConfiguration());
		conf.setTrial(trial);
		assertEquals(trial, conf.getTrial());
		assertFalse(conf.equals(trial.getRandomizationConfiguration()));
	}
	
	@Test
	public void testGetAlgortihm_withSeed(){
		conf = new MinimizationConfig(1234);
		conf.setTrial(new Trial());
		RandomizationAlgorithm<?> algorithm = conf.getAlgorithm();
		assertNotNull(algorithm);
		assertEquals(1234, algorithm.getSeed());
		assertEquals(algorithm, conf.getAlgorithm());
		
	}
	
	@Test
	public void testGetAlgortihm_withoutSeed(){
		conf = new MinimizationConfig();
		conf.setTrial(new Trial());
		RandomizationAlgorithm<?> algorithm = conf.getAlgorithm();
		assertNotNull(algorithm);
		assertEquals(Long.MIN_VALUE, algorithm.getSeed());
		assertEquals(algorithm, conf.getAlgorithm());
	}
	
	@Test
	public void testResetAlgorithm_withSeed(){
		conf = new MinimizationConfig(1234);
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
		conf = new MinimizationConfig();
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
		conf = new MinimizationConfig(1234);
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
		conf = new MinimizationConfig();
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
		conf = new MinimizationConfig();
		conf.setTrial(new Trial());
		RandomizationAlgorithm<?> algorithm = conf.getAlgorithm();
		assertEquals(Long.MIN_VALUE, algorithm.getSeed());
		assertEquals(algorithm, conf.getAlgorithm());
		conf.resetAlgorithm(123);
		assertEquals(123, conf.getAlgorithm().getSeed());
		assertNotSame(algorithm, conf.getAlgorithm());
	}
	
	@Test
	public void testTempData(){
		conf = new MinimizationConfig();
		conf.setTempData(null);
		assertNotNull(conf.getTempData());
	}
	
	@Test
	public void testPValue(){
		conf = new MinimizationConfig();
		conf.setP(10.1);
		assertEquals(10.1,conf.getP(),0);
	}
	
	@Test
	public void testWithRandomizedSubjects(){
		conf = new MinimizationConfig();
		conf.setWithRandomizedSubjects(true);
		assertTrue(conf.isWithRandomizedSubjects());
		conf.setWithRandomizedSubjects(false);
		assertFalse(conf.isWithRandomizedSubjects());
	}
	
	@Test
	public void testBiasedCoinMinimization(){
		conf = new MinimizationConfig();
		conf.setBiasedCoinMinimization(true);
		assertTrue(conf.isBiasedCoinMinimization());
		conf.setBiasedCoinMinimization(false);
		assertFalse(conf.isBiasedCoinMinimization());
	}
}

