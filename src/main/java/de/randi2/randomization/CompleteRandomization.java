/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2.randomization;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jthoenes
 */
public class CompleteRandomization extends RandomizationAlgorithm<CompleteRandomizationConfig> {

	public CompleteRandomization(Trial _trial) {
		super(_trial);
	}

	public CompleteRandomization(Trial _trial, long seed) {
		super(_trial, seed);
	}


	@Override
	protected TreatmentArm doRadomize(TrialSubject subject, Random random) {
		List<TreatmentArm> arms = trial.getTreatmentArms();
		return arms.get(random.nextInt(arms.size()));
	}
}
