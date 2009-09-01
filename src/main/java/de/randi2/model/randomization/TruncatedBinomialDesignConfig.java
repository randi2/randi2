package de.randi2.model.randomization;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import de.randi2.randomization.RandomizationAlgorithm;
import de.randi2.randomization.TruncatedBinomialDesign;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class TruncatedBinomialDesignConfig extends AbstractRandomizationConfig {

	private static final long serialVersionUID = -4994698552861007117L;

	@Override
	public RandomizationAlgorithm<? extends AbstractRandomizationConfig> createAlgorithm() {
		return new TruncatedBinomialDesign(super.getTrial());
	}

}
