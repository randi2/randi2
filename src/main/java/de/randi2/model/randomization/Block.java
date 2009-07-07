/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;

/**
 * 
 * @author jthoenes
 */
@Entity
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

	public List<TreatmentArm> getBlock() {
		return block;
	}

	public void setBlock(List<TreatmentArm> block) {
		this.block = block;
	}

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

	@Override
	public String toString() {
		return new StringBuilder().append(block).toString();
	}

}
