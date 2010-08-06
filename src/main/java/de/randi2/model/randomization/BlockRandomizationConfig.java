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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import de.randi2.randomization.BlockRandomization;
import de.randi2.randomization.RandomizationAlgorithm;

@Entity
//@BlockRandomizationConfigA
@Data
@EqualsAndHashCode(callSuper=true)
public class BlockRandomizationConfig extends AbstractRandomizationConfig {

	private static final long serialVersionUID = -7933864896327057988L;
	
	
	public BlockRandomizationConfig(long seed){
		super(seed);
	}
	
	public BlockRandomizationConfig(){
		super(null);
	}

	@Override
	public RandomizationAlgorithm<BlockRandomizationConfig> createAlgorithm() {
		return new BlockRandomization(super.getTrial());
	}
	
	@Override
	public RandomizationAlgorithm<? extends AbstractRandomizationConfig> createAlgorithm(
				long seed) {
			return new BlockRandomization(super.getTrial(), seed);
	}

	@Override
	public AbstractRandomizationTempData getTempData() {
		if (tempData == null) {
			tempData = new BlockRandomizationTempData();
		}
		return tempData;
	}


	public enum TYPE {

		MULTIPLY, ABSOLUTE;
	}
	private int minimum;
	private int maximum;
	@Enumerated(EnumType.STRING)
	private TYPE type;

	@Transient
	public boolean isVariableBlockSize(){
		return maximum != minimum;
	}
	
}
