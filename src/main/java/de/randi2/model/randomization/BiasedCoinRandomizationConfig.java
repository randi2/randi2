/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.randi2.model.randomization;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import de.randi2.randomization.BiasedCoinRandomization;
import de.randi2.randomization.RandomizationAlgorithm;

/**
 *
 * @author jthoenes
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class BiasedCoinRandomizationConfig extends AbstractRandomizationConfig {

	private static final long serialVersionUID = -5150967612749185875L;

	@Override
	public RandomizationAlgorithm<BiasedCoinRandomizationConfig> createAlgorithm() {
		return new BiasedCoinRandomization(super.getTrial());
	}

}
