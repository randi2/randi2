package de.randi2.randomization;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.NullRandomizationConfiguration;
import de.randi2.model.randomization.NullRandomizationTempData;

public class CompleteRandomization extends RandomizationAlgorithm<NullRandomizationConfiguration, NullRandomizationTempData> {

	protected CompleteRandomization(Trial _trial) {
		super(_trial);
	}

	@Override
	public TreatmentArm randomize(TrialSubject subject) {
		return null;
	}
	

	

}
