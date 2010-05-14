package de.randi2.jsf.wrappers;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import de.randi2.model.criteria.constraints.AbstractConstraint;


public class DistributedConstraintWrapper {

	private AbstractConstraint<? extends Serializable> constraint;
	
	@Getter @Setter
	private int ratio = 0;
	
	
	public DistributedConstraintWrapper(
			AbstractConstraint<? extends Serializable> constraint) {
		super();
		this.constraint = constraint;
	}

	public AbstractConstraint<? extends Serializable> getConstraint() {
		return constraint;
	}

	public void setConstraint(AbstractConstraint<? extends Serializable> constraint) {
		this.constraint = constraint;
	}


}
