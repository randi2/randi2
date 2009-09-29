package de.randi2.utility.validations.randomizationConfiguration;

import org.hibernate.validator.Validator;

import de.randi2.model.randomization.UrnDesignConfig;

public class UrnRandomizationConfigValidator  implements Validator<UrnRandomizationConfigA>{

	@Override
	public void initialize(UrnRandomizationConfigA parameters) {	}

	@Override
	public boolean isValid(Object value) {
		if(value instanceof UrnDesignConfig){
			UrnDesignConfig urnDesignConfig = (UrnDesignConfig) value;
			if(urnDesignConfig.getTrial().getTreatmentArms().size()>2){
				return false;
			}else return true;
		}
		return false;
	}

}
