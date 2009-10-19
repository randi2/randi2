package de.randi2.randomization;

import java.util.ArrayList;
import java.util.List;
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
		//calculate the p-values for this allocation
		List<TreatmentArm> arms = trial.getTreatmentArms();
		ArrayList<Double> a = new ArrayList<Double>();
		for(TreatmentArm arm : arms){
			int plannedSubjects = configuration.isWithRandomizedSubjects() ? (arm.getPlannedSubjects()-arm.getCurrentSubjectsAmount()):arm.getPlannedSubjects();
			double sum = 0;
			int totalPlannedSubjects = 0;
			for(TreatmentArm arm1 :arms){
				if(!arm.equals(arm1)){
					sum+=configuration.isWithRandomizedSubjects() ? (arm1.getPlannedSubjects()-arm1.getCurrentSubjectsAmount()) :arm1.getPlannedSubjects();
				}
				totalPlannedSubjects +=arm1.getPlannedSubjects();
			}
			double value = plannedSubjects*configuration.getP() + (1-configuration.getP())/(trial.getTreatmentArms().size() -1)*sum;
			value = value / (configuration.isWithRandomizedSubjects() ? totalPlannedSubjects-trial.getTotalSubjectAmount(): totalPlannedSubjects);
			a.add(value);
		}
		//get Treatment arm with calculated p-values
		double randomNumber = random.nextDouble();
		double sum = 0;
		int i = 0;
		TreatmentArm arm = null;
		while(i<a.size() && arm == null){
			sum+=a.get(i);
			if(randomNumber <sum){
				arm = arms.get(i);
			}
			i++;
		}
		return arm;
	}

}
