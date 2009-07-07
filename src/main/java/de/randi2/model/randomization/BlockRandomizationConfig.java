package de.randi2.model.randomization;

import de.randi2.randomization.BlockRandomization;
import de.randi2.randomization.RandomizationAlgorithm;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

@Entity
public class BlockRandomizationConfig extends AbstractRandomizationConfig {

	private static final long serialVersionUID = -7933864896327057988L;
	
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	private BlockRandomizationTempData tempData = null;

	@Override
	public RandomizationAlgorithm<BlockRandomizationConfig> createAlgorithm() {
		return new BlockRandomization(super.getTrial());
	}

	public BlockRandomizationTempData getTempData() {
		if (tempData == null) {
			tempData = new BlockRandomizationTempData();
		}
		return tempData;
	}

	public void setTempData(BlockRandomizationTempData _tempData) {
		this.tempData = _tempData;
	}

	public enum TYPE {

		MULTIPLY, ABSOLUTE;
	}
	private int minimum;
	private int maximum;
	@Enumerated(EnumType.STRING)
	private TYPE type;

	public int getMinimum() {
		return minimum;
	}

	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}

	public int getMaximum() {
		return maximum;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}
}
