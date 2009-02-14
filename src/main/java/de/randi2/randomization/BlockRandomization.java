package de.randi2.randomization;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
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
		List<TreatmentArm> block = tempData.getCurrentBlock();
		if(block == null || block.size() == 0){
			block = generateBlock(random);
			tempData.setCurrentBlock(block);
		}
		// TODO This is problematic, because someone knowing the DB
		// could see, which arm will be assigned the next time, 
		// BUT if we do not take it deterministic here, the predictability
		// will be difficult. Any ideas how to solve this?
		return block.remove(random.nextInt(block.size()));
	}

	private List<TreatmentArm> generateBlock(Random random) {
		int blockSize = generateBlockSize(random);
		List<TreatmentArm> block = new ArrayList<TreatmentArm>();
		List<TreatmentArm> rawBlock = new ArrayList<TreatmentArm>();
		int i = 0;
		while(i < blockSize){
			if(rawBlock.size() == 0){
				rawBlock = generateRawBlock();
			}
			block.add(rawBlock.remove(random.nextInt(rawBlock.size())));
			i++;
		};
		return block;
	}
	
	private int generateBlockSize(Random random){
		int range = super.configuration.getMaximum() - super.configuration.getMinimum() + 1;
		int size = random.nextInt(range) + super.configuration.getMinimum();
		if(super.configuration.getType() == BlockRandomizationConfig.TYPE.MULTIPLY){
			size *= ggt();
		}
		return size;
	}

}
