package de.randi2.randomization;

import java.util.Random;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.TruncatedBinomialDesignConfig;

public class TruncatedBinomialDesign extends
		RandomizationAlgorithm<TruncatedBinomialDesignConfig> {

	public TruncatedBinomialDesign(Trial trial) {
		super(trial);
	}

	protected TruncatedBinomialDesign(Trial trial, long seed) {
		super(trial, seed);
	}

	@Override
	protected TreatmentArm doRadomize(TrialSubject subject, Random random) {
		return null;
	}

}
