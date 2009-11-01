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

import static de.randi2.utility.ArithmeticUtil.ggt;
import static de.randi2.utility.IntegerIterator.upto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;

/**
 * 
 * @author jthoenes
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class Block extends AbstractDomainObject {
	private final static long serialVersionUID = 4951058614189569984L;

	/**
	 * Creates a raw block, i.e. a minimal block containing every treatment arm
	 * once. Generates a new instance of an raw block each time, this method is
	 * called.
	 * 
	 * @return A newly generated raw block.
	 */
	public static Block generate(Trial trial) {
		Block block = new Block();
		List<TreatmentArm> arms = trial.getTreatmentArms();

		int[] sizes = new int[arms.size()];
		int i = 0;
		for (TreatmentArm arm : arms) {
			sizes[i] = arm.getPlannedSubjects();
			i++;
		}

		int divide = sizes[0];
		for (i = 1; i < sizes.length; i++) {
			divide = ggt(divide, sizes[i]);
		}

		for (TreatmentArm arm : arms) {
			int size = arm.getPlannedSubjects() / divide;
			for (int j : upto(size)) {
				block.add(arm);
			}
		}

		return block;
	}

	@ManyToMany
	@JoinTable(name = "Block_Treatmentarm", 
			joinColumns = {	@JoinColumn(name = "Block_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "Treatmentarm_id") })
	private List<TreatmentArm> block = new ArrayList<TreatmentArm>();

	

	@Transient
	public boolean isEmpty() {
		return this.block.isEmpty();
	}

	public void add(TreatmentArm arm) {
		block.add(arm);
	}

	public TreatmentArm pullFromBlock(Random rand) {
		assert (!isEmpty());
		return block.remove(rand.nextInt(block.size()));
	}


}
