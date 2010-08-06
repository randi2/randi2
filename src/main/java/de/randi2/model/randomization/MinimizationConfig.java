package de.randi2.model.randomization;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import de.randi2.randomization.Minimization;
import de.randi2.randomization.RandomizationAlgorithm;

@Entity
public class MinimizationConfig extends AbstractRandomizationConfig {

	
	@Getter
	@Setter
	private double p;
	@Getter
	@Setter
	private boolean withRandomizedSubjects = false;
	@Getter
	@Setter
	private boolean biasedCoinMinimization = true;
	
	public MinimizationConfig(long seed){
		super(seed);
	}
	
	public MinimizationConfig(){
		super(null);
	}
	
	@Override
	public RandomizationAlgorithm<? extends AbstractRandomizationConfig> createAlgorithm() {
		return new Minimization(super.getTrial());
	}

	@Override
		public RandomizationAlgorithm<? extends AbstractRandomizationConfig> createAlgorithm(
				long seed) {
		return new Minimization(super.getTrial(), seed);
		}
	
	@Override
		public AbstractRandomizationTempData getTempData() {
			if (tempData == null) {
				tempData = new MinimizationTempData();
			}
		return tempData;
		}
}
