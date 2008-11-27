package de.randi2.randomization;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.AbstractRandomizationTempData;
import de.randi2.model.randomization.BaseRandomizationConfig;

public abstract class RandomizationAlgorithm<Conf extends BaseRandomizationConfig, Temp extends AbstractRandomizationTempData> {
	protected Trial trial;
	protected Conf configuration;
	private Random seededRandom;

	protected Temp tempData;


	protected RandomizationAlgorithm(Trial _trial) {
		super();
		this.trial = _trial;
		this.configuration = (Conf) trial.getRandomizationConfiguration();
		this.tempData = (Temp) trial.getRandomizationTempData();
	}

	protected RandomizationAlgorithm(Trial _trial, long seed) {
		super();
		this.trial = _trial;
		this.configuration = (Conf) trial.getRandomizationConfiguration();
		this.tempData = (Temp) trial.getRandomizationTempData();
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
		// Checks before

		// Actual Randomization
		TreatmentArm assignedArm = doRadomize(subject, getRawBlock(),
				getRandom());

		// Work after
		if (!isSeeded()) {
			subject.setArm(assignedArm);
			return subject.getArm();
		} else {
			return assignedArm;
		}
	}

	protected abstract TreatmentArm doRadomize(TrialSubject subject,
			List<TreatmentArm> rawBlock, Random random);

	public int ggt(int x, int y) {
		while (x != y) {
			if (x > y) {
				x = x - y;
			} else {
				y = y - x;
			}
		}
		return x;
	}

	public int ggt(List<TreatmentArm> arms) {
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

	protected List<TreatmentArm> getRawBlock() {
		List<TreatmentArm> arms = trial.getTreatmentArms();
		List<TreatmentArm> block = new ArrayList<TreatmentArm>();
		int ggt = ggt(arms);
		for (int i = 0; i < arms.size(); i++) {
			for (int j = 0; j < (arms.get(i).getPlannedSubjects() / ggt); j++) {
				block.add(arms.get(i));
			}
		}
		return block;
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