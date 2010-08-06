/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.randi2.core.unit.randomization;

import de.randi2.model.Trial;
import de.randi2.model.randomization.Block;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.randomization.BlockRandomization;
import java.util.Random;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import static de.randi2.testUtility.utility.RANDI2Assert.*;
import static de.randi2.utility.IntegerIterator.upto;

/**
 *
 * @author jthoenes
 */
public class BlockRandomizationInternalTest {

    public BlockRandomizationInternalTest() { }

	Trial trial;
	BlockRandomizationTestable rand;
	BlockRandomizationConfig conf;
	Random random;

	private class BlockRandomizationTestable extends BlockRandomization {
		public BlockRandomizationTestable(Trial t){
			super(t);
		}

		@Override
		public int generateBlockSize(Random random){
			return super.generateBlockSize(random);
		}

		@Override
		public Block generateBlock(Random random, Block block) {
			return super.generateBlock(random, block);
		}
	}

	@Before
	public void setUp(){
		trial = new Trial();
		conf = new BlockRandomizationConfig();
		trial.setRandomizationConfiguration(conf);
		rand = new BlockRandomizationTestable(trial);
		random = new Random();
	}

	@Test
	public void testBlockSizeMultiply1(){
		conf.setType(BlockRandomizationConfig.TYPE.MULTIPLY);
		conf.setMinimum(1);
		conf.setMaximum(2);
		RandomizationHelper.addArms(trial, 10, 10);
		for(int i: upto(20)){
			assertOneOf(new int[]{2}, rand.generateBlockSize(random));
			assertNoOneOf(new int[]{1,3,4}, rand.generateBlockSize(random));
		}
	}
	@Test
	public void testBlockSizeMultiply2(){
		conf.setType(BlockRandomizationConfig.TYPE.MULTIPLY);
		conf.setMinimum(1);
		conf.setMaximum(4);
		RandomizationHelper.addArms(trial, 10, 10);
		for(int i: upto(20)){
			assertOneOf(new int[]{2,4}, rand.generateBlockSize(random));
			assertNoOneOf(new int[]{1,3,5,6}, rand.generateBlockSize(random));
		}
	}

	@Test
//	@Ignore
	public void testBlockSizeMultiply3(){
		conf.setType(BlockRandomizationConfig.TYPE.MULTIPLY);
		conf.setMinimum(5);
		conf.setMaximum(20);
		RandomizationHelper.addArms(trial, 40, 10);
		for(int i: upto(20)){
			assertOneOf(new int[]{5, 10, 15, 20}, rand.generateBlockSize(random));
			assertNoOneOf(new int[]{1,2,3,4,6,7,8,9,11,12,13,14,16,17,18,19,21,25,30}, rand.generateBlockSize(random));
		}
	}
	@Test
	public void testBlockSizeMultiply4(){
		conf.setType(BlockRandomizationConfig.TYPE.MULTIPLY);
		conf.setMinimum(4);
		conf.setMaximum(10);
		RandomizationHelper.addArms(trial, 10, 20, 10);
		for(int i: upto(20)){
			assertOneOf(new int[]{4, 8}, rand.generateBlockSize(random));
		}
	}
	@Test
	public void testBlockSizeMultiply5(){
		conf.setType(BlockRandomizationConfig.TYPE.MULTIPLY);
		conf.setMinimum(5);
		conf.setMaximum(5);
		RandomizationHelper.addArms(trial, 10, 10, 10, 10, 10);
		for(int i: upto(20)){
			assertOneOf(new int[]{5}, rand.generateBlockSize(random));
		}
	}
	@Test
	public void testBlockSizeMultiply6(){
		conf.setType(BlockRandomizationConfig.TYPE.MULTIPLY);
		conf.setMinimum(2);
		conf.setMaximum(2);
		RandomizationHelper.addArms(trial, 40,40);
		for(int i: upto(20)){
			assertOneOf(new int[]{2}, rand.generateBlockSize(random));
		}
	}
	
	@Test
	public void testBlockSizeMultiply7(){
		conf.setType(BlockRandomizationConfig.TYPE.MULTIPLY);
		conf.setMinimum(2);
		conf.setMaximum(20);
		RandomizationHelper.addArms(trial, 40,40);
		for(int i: upto(1000)){
			assertOneOf(new int[]{2,4,6,8,10,12,14,16,18,20}, rand.generateBlockSize(random));
			assertNoOneOf(new int[]{3,5,7,9,11,13,15,17,19}, rand.generateBlockSize(random));
		}
	}


}