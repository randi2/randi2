/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.randi2.model.randomization;

import javax.persistence.Entity;

import de.randi2.randomization.CompleteRandomization;
import de.randi2.randomization.RandomizationAlgorithm;

/**
 *
 * @author jthoenes
 */
@Entity
public class CompleteRandomizationConfig extends AbstractRandomizationConfig{

	private static final long serialVersionUID = 2285655954951237292L;

	@Override
	public RandomizationAlgorithm<? extends AbstractRandomizationConfig> createAlgorithm() {
		return new CompleteRandomization(super.getTrial());
	}

}
