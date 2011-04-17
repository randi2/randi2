/* 
 * (c) 2008- RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
package de.randi2.utility.validations.randomizationConfiguration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import de.randi2.model.randomization.ResponseAdaptiveRandomizationConfig;

/**
 * @author Natalie Waskowzow
 *
 */
public class ResponseAdaptiveRandomizationConfigValidator implements
		ConstraintValidator<ResponseAdaptiveRandomizationConfigA, ResponseAdaptiveRandomizationConfig> {

	@Override
	public void initialize(
			ResponseAdaptiveRandomizationConfigA constraintAnnotation) {
	}

	@Override
	public boolean isValid(ResponseAdaptiveRandomizationConfig config,
			ConstraintValidatorContext constraintContext) {
		
		int countBallsResponseFailure = config.getCountBallsResponseFailure();
		int countBallsResponseSuccess = config.getCountBallsResponseSuccess();
		int countTreatmentArms = config.getTrial().getTreatmentArms().size();
		
		return ((countBallsResponseFailure * (countTreatmentArms - 1) >= 0)
				&& (countBallsResponseFailure * (countTreatmentArms - 1) <= countBallsResponseSuccess)
				&& (countBallsResponseFailure % (countTreatmentArms - 1) == 0) && (countBallsResponseSuccess
				% (countTreatmentArms - 1) == 0));
	}

}
