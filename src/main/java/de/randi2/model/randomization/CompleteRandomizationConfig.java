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

import de.randi2.randomization.CompleteRandomization;
import de.randi2.randomization.RandomizationAlgorithm;

/**
 *
 * @author jthoenes
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class CompleteRandomizationConfig extends AbstractRandomizationConfig{

	private static final long serialVersionUID = 2285655954951237292L;

	
	public CompleteRandomizationConfig(long seed){
		super(seed);
	}
	
	public CompleteRandomizationConfig(){
		super(null);
	}
	
	@Override
	public RandomizationAlgorithm<? extends AbstractRandomizationConfig> createAlgorithm() {
		return new CompleteRandomization(super.getTrial());
	}
	
	@Override
		public RandomizationAlgorithm<? extends AbstractRandomizationConfig> createAlgorithm(
				long seed) {
			return new CompleteRandomization(super.getTrial(), seed);
		}

}
