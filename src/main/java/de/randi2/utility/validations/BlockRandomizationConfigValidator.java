package de.randi2.utility.validations;

import org.hibernate.validator.Validator;

public class BlockRandomizationConfigValidator implements Validator<BlockRandomizationConfigA> {

	@Override
	public void initialize(BlockRandomizationConfigA parameters) {
	}

	@Override
	public boolean isValid(Object value) {
			if(value instanceof de.randi2.model.randomization.BlockRandomizationConfig){
				de.randi2.model.randomization.BlockRandomizationConfig config = (de.randi2.model.randomization.BlockRandomizationConfig) value;
				return (config.getMaximum() >= config.getMinimum() && config.getMinimum() >=2);
			}else return false;
	}

}
