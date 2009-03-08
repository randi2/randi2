package de.randi2.randomization;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.Block;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.BlockRandomizationTempData;

public class BlockRandomization extends RandomizationAlgorithm<BlockRandomizationConfig> {

	public BlockRandomization(Trial _trial){
		super(_trial);
	}
	
	public BlockRandomization(Trial _trial, long seed) {
		super(_trial, seed);
	}

	@Override
	protected TreatmentArm doRadomize(TrialSubject subject, Random random) {
		BlockRandomizationTempData tempData = super.configuration.getTempData();
		Block block = tempData.getBlock(subject.getProperties().hashCode());
		if(block == null || block.isEmpty()){
			block = generateBlock(random);
			tempData.setBlock(subject.getProperties().hashCode(), block);
		}

		return block.pullFromBlock(random);
	}

	private Block generateBlock(Random random) {
		int blockSize = generateBlockSize(random);
		Block block = new Block();
		Block rawBlock = Block.generate(trial);
		int i = 0;
		while(i < blockSize){
			if(rawBlock.isEmpty()){
				rawBlock = Block.generate(trial);
			}
			block.add(rawBlock.pullFromBlock(random));
			i++;
		}
		return block;
	}
	
	private int generateBlockSize(Random random){
		int range = super.configuration.getMaximum() - super.configuration.getMinimum() + 1;
		int size = random.nextInt(range) + super.configuration.getMinimum();
		if(super.configuration.getType() == BlockRandomizationConfig.TYPE.MULTIPLY){
			size *= RandomizationAlgorithm.minimumBlockSize(trial);
		}
		return size;
	}

}
