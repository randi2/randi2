package de.randi2.randomization;

import java.util.List;
import java.util.Random;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.BiasedCoinRandomizationConfig;

public class BiasedCoinRandomization extends RandomizationAlgorithm<BiasedCoinRandomizationConfig> {

	public BiasedCoinRandomization(Trial _trial) {
		super(_trial);
	}
	
	public BiasedCoinRandomization(Trial _trial, long seed) {
		super(_trial, seed);
	}

	@Override
	protected TreatmentArm doRadomize(TrialSubject subject, Random random) {
		List<TreatmentArm> block = generateRawBlock();
		return block.get(random.nextInt(block.size()));
	}
}
