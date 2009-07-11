package de.randi2.randomization;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.AbstractRandomizationConfig;

public abstract class RandomizationAlgorithm<Conf extends AbstractRandomizationConfig> implements Serializable{
	protected Trial trial;
	protected Conf configuration;
	private Random seededRandom;

	@SuppressWarnings("unchecked")
	protected RandomizationAlgorithm(Trial _trial) {
		super();
		this.trial = _trial;
		this.configuration = (Conf) trial.getRandomizationConfiguration();
	}

	@SuppressWarnings("unchecked")
	protected RandomizationAlgorithm(Trial _trial, long seed) {
		super();
		this.trial = _trial;
		this.configuration = (Conf) trial.getRandomizationConfiguration();
		this.seededRandom = new Random(seed);
	}

	/**
	 * Randomizes a trial subject into the treatment arms of the trial. If the
	 * algorithm is not seeded (that is to say, we are in real use and not in
	 * test mode) the subject will be assigned to the treatment by the
	 * randomization. If the algorithm has been seeded, the subjects treatment
	 * property remains unchanged.
	 * 
	 * @param subject
	 *            The subject to be randomized. The treatment arm will be
	 *            assigned, unless the algorithm is seeded.
	 * @return The randomized treatment arm.
	 */
	public TreatmentArm randomize(TrialSubject subject) {
		// Actual Randomization
		TreatmentArm assignedArm = doRadomize(subject, getRandom());

		// Work after
		return assignedArm;
	}

	protected abstract TreatmentArm doRadomize(TrialSubject subject,
			Random random);
	
	private int sampleIndex(List<? extends Object> urn, Random random){
		return random.nextInt(urn.size());
	}
	
	protected TreatmentArm sampleWithRemove(List<TreatmentArm> urn, Random random){
		return urn.remove(sampleIndex(urn, random));
	}
	
	protected TreatmentArm sampleWithoutRemove(List<TreatmentArm> urn, Random random){
		return urn.get(sampleIndex(urn, random));
	}
	
	

	private boolean isSeeded() {
		return seededRandom != null;
	}

	private Random getRandom() {
		if (isSeeded()) {
			return seededRandom;
		} else {
			return new Random();
		}
	}
}
