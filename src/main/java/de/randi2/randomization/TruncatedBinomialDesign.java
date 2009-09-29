package de.randi2.randomization;

import static de.randi2.utility.ArithmeticUtil.ggt;
import static de.randi2.utility.IntegerIterator.upto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.Block;
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
		List<TreatmentArm> possibleArms = new ArrayList<TreatmentArm>();
		for(TreatmentArm arm : trial.getTreatmentArms()){
			if(arm.getCurrentSubjectsAmount() < arm.getPlannedSubjects()){
				possibleArms.add(arm);
			}
		}
		if(possibleArms.isEmpty()){
			return null;
		}else{
			Block block = new Block();
		
			int[] sizes = new int[possibleArms.size()];
			int i = 0;
			for (TreatmentArm arm : possibleArms) {
				sizes[i] = arm.getPlannedSubjects();
				i++;
			}

			int divide = sizes[0];
			for (i = 1; i < sizes.length; i++) {
				divide = ggt(divide, sizes[i]);
			}

			for (TreatmentArm arm : possibleArms) {
				int size = arm.getPlannedSubjects() / divide;
				for (int j : upto(size)) {
					block.add(arm);
				}
			}
			
			return block.pullFromBlock(random);
		}
	}

}
