package de.randi2.model.randomization;

import java.util.ArrayList;
import java.util.List;

import de.randi2.model.TreatmentArm;

public class BlockRandomizationTempData extends AbstractRandomizationTempData {

	private List<TreatmentArm> currentBlock = new ArrayList<TreatmentArm>();

	public List<TreatmentArm> getCurrentBlock() {
		return currentBlock;
	}

	public void setCurrentBlock(List<TreatmentArm> currentBlock) {
		this.currentBlock = currentBlock;
	}

}
