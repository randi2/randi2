package de.randi2.core.unit.model.randomization;

import static junit.framework.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.randomization.Block;
import de.randi2.testUtility.utility.AbstractDomainTest;

public class BlockTest extends AbstractDomainTest<Block> {

	public BlockTest(){
		super(Block.class);
	}

	@Test
	public void testIsEmpty_true(){
		Block block = new Block();
		assertTrue(block.isEmpty());
	}
	
	@Test
	public void testIsEmpty_false(){
		Block block = new Block();
		block.add(new TreatmentArm());
		assertFalse(block.isEmpty());
	}
	
	@Test
	public void testAddTreatmentArm(){
		Block block = new Block();
		for(int i = 1; i<=10 ;i++){
			block.add(new TreatmentArm());
			assertEquals(i, block.getBlock().size());
		}
	}
	
	
	@Test
	public void testPullFromBlock(){
		Block block = new Block();
		for(int i = 1; i<=10 ;i++){
			block.add(new TreatmentArm());
		}
		for(int i = 9; i>=0 ;i--){
			assertNotNull(block.pullFromBlock(new Random()));
			assertEquals(i, block.getBlock().size());
		}
	}
	
	@Test 
	public void testGenerateBlock_ArmsWithEqualPatientAmount(){
		Trial trial = new Trial();
		Set<TreatmentArm> arms = new HashSet<TreatmentArm>();
		for(int i=0; i<10; i++){
			TreatmentArm arm = new TreatmentArm();
			arm.setPlannedSubjects(10);
			arm.setName("arm" + i);
			arms.add(arm);
			trial.setTreatmentArms(arms);
			assertEquals(i+1,(Block.generate(trial)).getBlock().size());
		}
	}
	
	@Test 
	public void testGenerateBlock_ArmsWithPatientAmountRatio(){
		Trial trial = new Trial();
		Set<TreatmentArm> arms = new HashSet<TreatmentArm>();
		int sum = 0;
		for(int i=0; i<10; i++){
			sum = sum + (i+1);
			TreatmentArm arm = new TreatmentArm();
			arm.setPlannedSubjects(10*(i+1));
			arm.setName("arm" + i);
			arms.add(arm);
			trial.setTreatmentArms(arms);
			assertEquals(sum,(Block.generate(trial)).getBlock().size());
		}
	}
}
