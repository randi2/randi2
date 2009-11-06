package de.randi2.simulation.model;

import java.io.Serializable;

import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.simulation.model.distribution.AbstractDistribution;



public class DistributionSubjectProperty {

	

	private AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> criterion;
	


	private AbstractDistribution distribution;
	
	public DistributionSubjectProperty(
			AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> criterion,
			AbstractDistribution distribution) {
		super();
		this.criterion = criterion;
		this.distribution = distribution;
	}

	public Serializable getNextSubjectValue(){
		return criterion.getConfiguredValues().get(distribution.getNextInt(criterion.getConfiguredValues().size()));
	}

	public AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> getCriterion() {
		return criterion;
	}
	
	public Class<? extends AbstractDistribution> getDistributionType(){
		return distribution.getClass();
	}
}
