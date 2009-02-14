package de.randi2.randomization;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.AbstractRandomizationConfig;

public abstract class RandomizationAlgorithm<Conf extends AbstractRandomizationConfig> {
	protected Trial trial;
	protected Conf configuration;
	private Random seededRandom;

	protected RandomizationAlgorithm(Trial _trial) {
		super();
		this.trial = _trial;
		this.configuration = (Conf) trial.getRandomizationConfiguration();
	}

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

	public int ggt(int x, int y) {
		assert(x > 0);
		assert(y > 0);		
		while (x != y) {
			if (x > y) {
				x = x - y;
			} else {
				y = y - x;
			}
		}
		return x;
	}

	public int ggt() {
		List<TreatmentArm> arms = trial.getTreatmentArms();
		int[] sizes = new int[arms.size()];
		int i = 0;
		for (TreatmentArm arm : arms) {
			sizes[i] = arm.getPlannedSubjects();
			i++;
		}

		int result;
		result = sizes[0];
		for (i = 1; i < sizes.length; i++) {
			result = ggt(result, sizes[i]);
		}
		return result;
	}

	/**
	 * Creates a raw block, i.e. a minimal block containing every treatment arm
	 * once. Generates a new instance of an raw block each time, this method is
	 * called.
	 * 
	 * @return A newly generated raw block.
	 */
	protected List<TreatmentArm> generateRawBlock() {
		List<TreatmentArm> arms = trial.getTreatmentArms();
		List<TreatmentArm> block = new ArrayList<TreatmentArm>();
		int ggt = ggt();
		for (int i = 0; i < arms.size(); i++) {
			for (int j = 0; j < (arms.get(i).getPlannedSubjects() / ggt); j++) {
				block.add(arms.get(i));
			}
		}
		return block;
	}
	
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
