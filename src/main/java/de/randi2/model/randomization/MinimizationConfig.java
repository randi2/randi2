package de.randi2.model.randomization;

import javax.persistence.Entity;

import lombok.Data;
import de.randi2.randomization.Minimization;
import de.randi2.randomization.RandomizationAlgorithm;

@Entity
@Data
public class MinimizationConfig extends AbstractRandomizationConfig {

	
	private double p;
	private boolean withRandomizedSubjects = false;
	private boolean biasedCoinMinimization = true;
	
	@Override
	public RandomizationAlgorithm<? extends AbstractRandomizationConfig> createAlgorithm() {
		return new Minimization(super.getTrial());
	}

	@Override
		public AbstractRandomizationTempData getTempData() {
			if (tempData == null) {
				tempData = new MinimizationTempData();
			}
		return tempData;
		}
}
