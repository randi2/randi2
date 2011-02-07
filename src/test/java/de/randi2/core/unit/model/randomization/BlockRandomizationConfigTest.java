package de.randi2.core.unit.model.randomization;

import static org.junit.Assert.*;

import org.junit.Test;

import de.randi2.model.Trial;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.randomization.RandomizationAlgorithm;
import de.randi2.testUtility.utility.AbstractDomainTest;

public class BlockRandomizationConfigTest extends AbstractDomainTest<BlockRandomizationConfig> {

	BlockRandomizationConfig conf;
	
	public BlockRandomizationConfigTest(){
		super(BlockRandomizationConfig.class);
	}
	
	
	@Test
	public void testTrial_null(){
		conf = new BlockRandomizationConfig();
		conf.setTrial(null);
		assertNull(conf.getTrial());
	}
	
	@Test
	public void testTrial_withEmptyRandomizationConfig(){
		conf = new BlockRandomizationConfig();
		Trial trial = new Trial();
		assertNull(trial.getRandomizationConfiguration());
		conf.setTrial(trial);
		assertEquals(trial, conf.getTrial());
		assertEquals(conf, trial.getRandomizationConfiguration());
	}
	
	@Test
	public void testTrial_withRandomizationConfig(){
		conf = new BlockRandomizationConfig();
		Trial trial = new Trial();
		trial.setRandomizationConfiguration(new BlockRandomizationConfig());
		assertNotNull(trial.getRandomizationConfiguration());
		conf.setTrial(trial);
		assertEquals(trial, conf.getTrial());
		assertFalse(conf.equals(trial.getRandomizationConfiguration()));
	}
	
	@Test
	public void testGetAlgortihm_withSeed(){
		conf = new BlockRandomizationConfig(1234);
		conf.setTrial(new Trial());
		RandomizationAlgorithm<?> algorithm = conf.getAlgorithm();
		assertNotNull(algorithm);
		assertEquals(1234, algorithm.getSeed());
		assertEquals(algorithm, conf.getAlgorithm());
		
	}
	
	@Test
	public void testGetAlgortihm_withoutSeed(){
		conf = new BlockRandomizationConfig();
		conf.setTrial(new Trial());
		RandomizationAlgorithm<?> algorithm = conf.getAlgorithm();
		assertNotNull(algorithm);
		assertEquals(Long.MIN_VALUE, algorithm.getSeed());
		assertEquals(algorithm, conf.getAlgorithm());
	}
	
	@Test
	public void testResetAlgorithm_withSeed(){
		conf = new BlockRandomizationConfig(1234);
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
		conf = new BlockRandomizationConfig();
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
		conf = new BlockRandomizationConfig(1234);
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
		conf = new BlockRandomizationConfig();
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
		conf = new BlockRandomizationConfig();
		conf.setTrial(new Trial());
		RandomizationAlgorithm<?> algorithm = conf.getAlgorithm();
		assertEquals(Long.MIN_VALUE, algorithm.getSeed());
		assertEquals(algorithm, conf.getAlgorithm());
		conf.resetAlgorithm(123);
		assertEquals(123, conf.getAlgorithm().getSeed());
		assertNotSame(algorithm, conf.getAlgorithm());
	}
	
	@Test
	public void testTYPE(){
		conf = new BlockRandomizationConfig();
		conf.setType(BlockRandomizationConfig.TYPE.ABSOLUTE);
		assertEquals(BlockRandomizationConfig.TYPE.ABSOLUTE, conf.getType());
		conf.setType(BlockRandomizationConfig.TYPE.MULTIPLY);
		assertEquals(BlockRandomizationConfig.TYPE.MULTIPLY, conf.getType());
	}
	
	@Test
	public void testMinimumEqualsMaximum(){
		conf = new BlockRandomizationConfig();
		for(int i = 2; i<10; i++){
			conf.setMinimum(i);	
			conf.setMaximum(i);
			assertValid(conf);
		}
	}
	
	@Test
	public void testMinimumSmallerThanMaximum(){
		conf = new BlockRandomizationConfig();
		for(int i=2; i<=20;i++){
			conf.setMinimum(i);	
			conf.setMaximum(20);
			assertValid(conf);
		}
	}
	
	@Test
	public void testMaximumSmallerThanMinimum(){
		conf = new BlockRandomizationConfig();
		for(int i=2; i<20;i++){
			conf.setMinimum(20);	
			conf.setMaximum(i);
			assertInvalid(conf, "20 < " + i +" is valid");
		}
	}
	
	@Test
	public void testMinimumSmallerThanTwo(){
		conf = new BlockRandomizationConfig();
	 	conf.setMinimum(1);	
		conf.setMaximum(8);
		assertInvalid(conf);
	}
	
	@Test 
	public void testIsVariableBlocksize_True(){
		conf = new BlockRandomizationConfig();
	 	conf.setMinimum(1);	
		conf.setMaximum(8);
		assertTrue(conf.isVariableBlockSize());
	}
	
	@Test 
	public void testIsVariableBlocksize_False(){
		conf = new BlockRandomizationConfig();
	 	conf.setMinimum(8);	
		conf.setMaximum(8);
		assertFalse(conf.isVariableBlockSize());
	}
	
	@Test
	public void testTempData(){
		conf = new BlockRandomizationConfig();
		conf.setTempData(null);
		assertNotNull(conf.getTempData());
	}

}