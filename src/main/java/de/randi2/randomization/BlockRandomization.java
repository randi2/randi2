package de.randi2.randomization;

import java.util.Random;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.Block;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.BlockRandomizationTempData;

public class BlockRandomization extends RandomizationAlgorithm<BlockRandomizationConfig> {


	public BlockRandomization(Trial _trial) {
		super(_trial);
	}

	public BlockRandomization(Trial _trial, long seed) {
		super(_trial, seed);
	}

	@Override
	protected TreatmentArm doRadomize(TrialSubject subject, Random random) {
		BlockRandomizationTempData tempData = super.configuration.getTempData();
		//TODO !!!
		Block block = tempData.getBlock(subject.getStratum());
		if (block == null || block.isEmpty()) {
			block = generateBlock(random);
			tempData.setBlock(subject.getStratum(), block);
		}

		return block.pullFromBlock(random);
	}

	protected Block generateBlock(Random random) {
		int blockSize = generateBlockSize(random);
		Block block = new Block();
		Block rawBlock = Block.generate(trial);
		int i = 0;
		while (i < blockSize) {
			if (rawBlock.isEmpty()) {
				rawBlock = Block.generate(trial);
			}
			block.add(rawBlock.pullFromBlock(random));
			i++;
		}
		return block;
	}

	protected int generateBlockSize(Random random) {
		int range = super.configuration.getMaximum() - super.configuration.getMinimum() + 1;
		int size = random.nextInt(range) + super.configuration.getMinimum();
		if (super.configuration.getType() == BlockRandomizationConfig.TYPE.MULTIPLY) {
			int minBlockSize = minimumBlockSize(trial);
			while(size % minBlockSize != 0){
				size =  random.nextInt(range) + super.configuration.getMinimum();
			}
		}
		return size;
	}

	private int minimumBlockSize(Trial trial){
		return Block.generate(trial).getBlock().size();
	}
}
