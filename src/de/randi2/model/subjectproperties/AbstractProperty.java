package de.randi2.model.subjectproperties;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;

import de.randi2.model.criteria.AbstractCriterion;

public abstract class AbstractProperty {
	
	@ManyToOne(cascade = CascadeType.ALL)
	private AbstractCriterion criterion;
	
}
