/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2.randomization;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jthoenes
 */
public class RandomizationHelper {

	public static void addArms(Trial t, int... sizes) {
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		for (int i = 0; i < sizes.length; i++) {
			TreatmentArm arm = new TreatmentArm();
			arm.setName("dummy:" + (i + 1));
			arm.setPlannedSubjects(sizes[i]);
			arms.add(arm);
		}
		t.setTreatmentArms(arms);
	}
}
