package de.randi2.randomization;

import java.util.Random;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.UrnDesignConfig;

public class UrnDesign extends RandomizationAlgorithm<UrnDesignConfig> {

	protected UrnDesign(Trial trial) {
		super(trial);
	}
	
	protected UrnDesign(Trial trial, long seed) {
		super(trial, seed);
	}


	@Override
	protected TreatmentArm doRadomize(TrialSubject subject, Random random) {
		return null;
	}

}
