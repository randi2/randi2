package de.randi2.randomization;

import java.util.ArrayList;
import java.util.List;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.AbstractRandomizationTempData;
import de.randi2.model.randomization.AbstractRandomizationConfiguration;

public abstract class RandomizationAlgorithm<Conf extends AbstractRandomizationConfiguration, Temp extends AbstractRandomizationTempData> {
	protected Trial trial;
	protected Conf configuration;
	protected Temp tempData;

	protected RandomizationAlgorithm(Trial _trial) {
		super();
		this.trial = _trial;
		this.configuration = (Conf) trial.getRandomizationConfiguration();
		this.tempData = (Temp) trial.getRandomizationTempData();
	}

	public abstract TreatmentArm randomize(TrialSubject subject);

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

}
