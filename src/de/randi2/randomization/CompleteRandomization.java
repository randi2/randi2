package de.randi2.randomization;

import java.util.List;
import java.util.Random;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.AbstractRandomizationTempData;
import de.randi2.model.randomization.BaseRandomizationConfig;

public class CompleteRandomization extends RandomizationAlgorithm<BaseRandomizationConfig, AbstractRandomizationTempData> {

	public CompleteRandomization(Trial _trial) {
		super(_trial);
	}
	
	public CompleteRandomization(Trial _trial, long seed) {
		super(_trial, seed);
	}

	@Override
	protected TreatmentArm doRadomize(TrialSubject subject, List<TreatmentArm> rawBlock, Random random) {
		return rawBlock.get(random.nextInt(rawBlock.size()));
	}
	

	

}
