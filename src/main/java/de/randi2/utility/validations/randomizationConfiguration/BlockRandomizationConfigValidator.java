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
