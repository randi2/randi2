package de.randi2.model.randomization;

import de.randi2.randomization.RandomizationAlgorithm;
import de.randi2.randomization.TruncatedBinomialDesign;

public class TruncatedBinomialDesignConfig extends AbstractRandomizationConfig {

	private static final long serialVersionUID = -4994698552861007117L;

	@Override
	public RandomizationAlgorithm<? extends AbstractRandomizationConfig> createAlgorithm() {
		return new TruncatedBinomialDesign(super.getTrial());
	}

}
