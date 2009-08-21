package de.randi2.model.randomization;

import de.randi2.randomization.BlockRandomization;
import de.randi2.randomization.RandomizationAlgorithm;
import de.randi2.utility.validations.BlockRandomizationConfigA;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@BlockRandomizationConfigA
@Data
@EqualsAndHashCode(callSuper=true)
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


	public enum TYPE {

		MULTIPLY, ABSOLUTE;
	}
	private int minimum;
	private int maximum;
	@Enumerated(EnumType.STRING)
	private TYPE type;

	@Transient
	public boolean isVariableBlockSize(){
		return maximum == minimum;
	}
}
