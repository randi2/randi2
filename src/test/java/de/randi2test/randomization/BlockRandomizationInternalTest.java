/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.randi2test.randomization;

import de.randi2.model.Trial;
import de.randi2.model.randomization.Block;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.randomization.BlockRandomization;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static de.randi2test.utility.RANDI2Assert.*;
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
		public Block generateBlock(Random random) {
			return super.generateBlock(random);
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
			assertOneOf(new int[]{2, 4}, rand.generateBlockSize(random));
		}
	}
	@Test
	public void testBlockSizeMultiply2(){
		conf.setType(BlockRandomizationConfig.TYPE.MULTIPLY);
		conf.setMinimum(1);
		conf.setMaximum(4);
		RandomizationHelper.addArms(trial, 10, 10);
		for(int i: upto(20)){
			assertOneOf(new int[]{2,4,6,8}, rand.generateBlockSize(random));
		}
	}

	@Test
	public void testBlockSizeMultiply3(){
		conf.setType(BlockRandomizationConfig.TYPE.MULTIPLY);
		conf.setMinimum(1);
		conf.setMaximum(2);
		RandomizationHelper.addArms(trial, 40, 10);
		for(int i: upto(20)){
			assertOneOf(new int[]{5, 10}, rand.generateBlockSize(random));
		}
	}
	@Test
	public void testBlockSizeMultiply4(){
		conf.setType(BlockRandomizationConfig.TYPE.MULTIPLY);
		conf.setMinimum(1);
		conf.setMaximum(2);
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
			assertOneOf(new int[]{25}, rand.generateBlockSize(random));
		}
	}
	@Test
	public void testBlockSizeMultiply6(){
		conf.setType(BlockRandomizationConfig.TYPE.MULTIPLY);
		conf.setMinimum(2);
		conf.setMaximum(2);
		RandomizationHelper.addArms(trial, 40,40);
		for(int i: upto(20)){
			assertOneOf(new int[]{4}, rand.generateBlockSize(random));
		}
	}


}