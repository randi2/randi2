package de.randi2.model.randomization;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Where;

import de.randi2.randomization.RandomizationAlgorithm;
import de.randi2.randomization.UrnDesign;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class UrnDesignConfig extends AbstractRandomizationConfig {

	private static final long serialVersionUID = 8210824864257296786L;
	
	private int countReplacedBalls;
	
	private int initializeCountBalls;
	@Override
	public RandomizationAlgorithm<? extends AbstractRandomizationConfig> createAlgorithm() {
		return new UrnDesign(super.getTrial());
	}

	@Override
	public AbstractRandomizationTempData getTempData(){
		if (tempData == null){
			tempData = new UrnDesignTempData();
		}
		return tempData;
	}
	
	
}
