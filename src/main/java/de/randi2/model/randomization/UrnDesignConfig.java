package de.randi2.model.randomization;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import de.randi2.randomization.RandomizationAlgorithm;
import de.randi2.randomization.UrnDesign;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class UrnDesignConfig extends AbstractRandomizationConfig {

	private static final long serialVersionUID = 8210824864257296786L;

	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	private UrnDesignTempData tempData = null;
	
	private int countReplacedBalls;
	
	private int initializeCountBalls;
	@Override
	public RandomizationAlgorithm<? extends AbstractRandomizationConfig> createAlgorithm() {
		return new UrnDesign(super.getTrial());
	}

	public UrnDesignTempData getTempData(){
		if (tempData == null){
			tempData = new UrnDesignTempData();
		}
		return tempData;
	}
}
