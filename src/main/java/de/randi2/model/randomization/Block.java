/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.randi2.model.randomization;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.randomization.RandomizationAlgorithm;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

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

		List<TreatmentArm> arms = trial.getTreatmentArms();
		Block block = new Block();
		int blockSize = RandomizationAlgorithm.minimumBlockSize(trial);
		for (int i = 0; i < arms.size(); i++) {
			for (int j = 0; j < (arms.get(i).getPlannedSubjects() / blockSize); j++) {
				block.add(arms.get(i));
			}
		}
		return block;
	}

	@OneToMany
	private List<TreatmentArm> block = new ArrayList<TreatmentArm>();

	public List<TreatmentArm> getBlock() {
		return block;
	}

	public void setBlock(List<TreatmentArm> block) {
		this.block = block;
	}

	@Transient
	public boolean isEmpty(){
		return this.block.isEmpty();
	}

	public void add(TreatmentArm arm){
		block.add(arm);
	}

	public TreatmentArm pullFromBlock(Random rand){
		return block.remove(rand.nextInt(block.size()));
	}
	
}
