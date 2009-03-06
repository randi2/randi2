/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.randi2.model.randomization;

import de.randi2.randomization.CompleteRandomization;
import de.randi2.randomization.RandomizationAlgorithm;

/**
 *
 * @author jthoenes
 */
public class CompleteRandomizationConfig extends AbstractRandomizationConfig {

	private static final long serialVersionUID = -5150967612749185875L;

	@Override
	public RandomizationAlgorithm<CompleteRandomizationConfig> createAlgorithm() {
		return new CompleteRandomization(super.getTrial());
	}

}
