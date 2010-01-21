package de.randi2.simulation.model;

import java.io.Serializable;

import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.simulation.distribution.AbstractDistribution;



public class DistributionSubjectProperty {

	

	public DistributionSubjectProperty(
			AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> criterion,
			AbstractDistribution<?> distribution) {
		super();
		this.criterion = criterion;
		this.distribution = distribution;
	}

	private AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> criterion;
	


	private AbstractDistribution<?> distribution;
	
//	public <E extends Serializable> DistributionSubjectProperty(
//			AbstractCriterion<E, ? extends AbstractConstraint<E>> criterion,
//			AbstractDistribution<E > distribution) {
//		super();
//		this.criterion = criterion;
//		this.distribution = distribution;
//	}

	public Serializable getNextSubjectValue(){
		return  distribution.getNextValue();
	}

	public AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> getCriterion() {
		return criterion;
	}
	

}
