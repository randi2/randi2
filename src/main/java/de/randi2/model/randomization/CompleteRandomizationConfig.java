/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.randi2.model.randomization;

import de.randi2.model.Trial;
import de.randi2.randomization.CompleteRandomization;
import de.randi2.randomization.RandomizationAlgorithm;

/**
 *
 * @author jthoenes
 */
public class CompleteRandomizationConfig extends AbstractRandomizationConfig {

	@Override
	public RandomizationAlgorithm<CompleteRandomizationConfig> getAlgorithm(Trial trial) {
		return new CompleteRandomization(trial);
	}

}
