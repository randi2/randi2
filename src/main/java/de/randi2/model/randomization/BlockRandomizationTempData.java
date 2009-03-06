package de.randi2.model.randomization;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;


import de.randi2.model.TreatmentArm;

@Entity
public class BlockRandomizationTempData extends AbstractRandomizationTempData {

	private static final long serialVersionUID = -5150967612749185875L;

	@OneToMany
	private List<TreatmentArm> currentBlock = new ArrayList<TreatmentArm>();

	public List<TreatmentArm> getCurrentBlock() {
		return currentBlock;
	}

	public void setCurrentBlock(List<TreatmentArm> currentBlock) {
		this.currentBlock = currentBlock;
	}

}
