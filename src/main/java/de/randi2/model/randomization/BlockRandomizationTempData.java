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

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.MapKey;

@Entity
@DiscriminatorValue("BLOCK")
@Data
@EqualsAndHashCode(callSuper=true)
public class BlockRandomizationTempData extends AbstractRandomizationTempData {

	private static final long serialVersionUID = -5150967612749185875L;

	
	 @CollectionOfElements(targetElement = Block.class, fetch=FetchType.EAGER)
	 @MapKey(targetElement = String.class, columns = {@Column(name="mapkey",nullable=false)})
	 private Map<String, Block> blocks = new HashMap<String, Block>();
	 
	

	public Block getBlock(String stratum) {
		return blocks.get(stratum);
	}

	public void setBlock(String stratum, Block currentBlock) {
		blocks.put(stratum, currentBlock);
	}

}
