package de.randi2.randomization;

import java.util.Random;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.MinimizationConfig;

public class Minimization extends RandomizationAlgorithm<MinimizationConfig>{


	public Minimization(Trial _trial) {
		super(_trial);
	}

	public Minimization(Trial _trial, long seed) {
		super(_trial, seed);
	}
	
	
	@Override
	protected TreatmentArm doRadomize(TrialSubject subject, Random random) {
		// TODO Auto-generated method stub
		return null;
	}

}
