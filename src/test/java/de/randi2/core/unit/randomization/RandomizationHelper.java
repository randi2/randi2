/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2.core.unit.randomization;

import java.util.HashSet;
import java.util.Set;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;

/**
 *
 * @author jthoenes
 */
public class RandomizationHelper {

	public static void addArms(Trial t, int... sizes) {
		Set<TreatmentArm> arms = new HashSet<TreatmentArm>();
		for (int i = 0; i < sizes.length; i++) {
			TreatmentArm arm = new TreatmentArm();
			arm.setId(i+1);
			arm.setName("dummy:" + (i + 1));
			arm.setPlannedSubjects(sizes[i]);
			arms.add(arm);
		}
		t.setTreatmentArms(arms);
	}
	
	public static void randomize(Trial t, TrialSubject s){
		TreatmentArm assignedArm = t.getRandomizationConfiguration().getAlgorithm().randomize(s);
		s.setArm(assignedArm);
		assignedArm.addSubject(s);
	}
}
