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
package de.randi2.model.randomization;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import de.randi2.randomization.RandomizationAlgorithm;
import de.randi2.randomization.ResponseAdaptiveRandomization;
import de.randi2.utility.validations.randomizationConfiguration.ResponseAdaptiveRandomizationConfigA;

/**
 * @author Natalie Waskowzow
 *
 */
@Entity
@ResponseAdaptiveRandomizationConfigA
@Data
@EqualsAndHashCode(callSuper = true)
public class ResponseAdaptiveRandomizationConfig extends
		AbstractRandomizationConfig {
	
	/**
	 * generated serial version id
	 */
	private static final long serialVersionUID = -6445531786408124628L;
	
	private int initializeCountBalls;
	private int countBallsResponseSuccess;
	private int countBallsResponseFailure;

	public ResponseAdaptiveRandomizationConfig(Long seed) {
		super(seed);
	}
	
	public ResponseAdaptiveRandomizationConfig(){
		super(null);
	}

	/**
	 * @see de.randi2.model.randomization.AbstractRandomizationConfig#createAlgorithm()
	 */
	@Override
	public RandomizationAlgorithm<? extends AbstractRandomizationConfig> createAlgorithm() {
		return new ResponseAdaptiveRandomization(super.getTrial());
	}

	/**
	 * @see de.randi2.model.randomization.AbstractRandomizationConfig#createAlgorithm(long)
	 */
	@Override
	public RandomizationAlgorithm<? extends AbstractRandomizationConfig> createAlgorithm(
			long seed) {
		return new ResponseAdaptiveRandomization(super.getTrial(), seed);
	}
	
	@Override
	public AbstractRandomizationTempData getTempData() {
		if (tempData == null) {
			tempData = new ResponseAdaptiveRandomizationTempData();
		}
		return tempData;
	}

}
