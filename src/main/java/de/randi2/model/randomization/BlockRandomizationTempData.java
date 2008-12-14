package de.randi2.model.randomization;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.IndexColumn;

import de.randi2.model.TreatmentArm;

@Entity
public class BlockRandomizationTempData extends AbstractRandomizationTempData {

	@OneToMany
	private List<TreatmentArm> currentBlock = new ArrayList<TreatmentArm>();

	public List<TreatmentArm> getCurrentBlock() {
		return currentBlock;
	}

	public void setCurrentBlock(List<TreatmentArm> currentBlock) {
		this.currentBlock = currentBlock;
	}

}
