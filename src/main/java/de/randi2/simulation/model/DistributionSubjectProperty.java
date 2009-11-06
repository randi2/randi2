package de.randi2.simulation.model;

import java.io.Serializable;

import lombok.Data;

import de.randi2.model.SubjectProperty;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.simulation.model.distribution.AbstractDistribution;



public class DistributionSubjectProperty {

	
	private AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> criterion;
	
	private AbstractDistribution distribution;
	
	public SubjectProperty<Serializable> getNextSubjectProperty(){
		return null;
	}
}
