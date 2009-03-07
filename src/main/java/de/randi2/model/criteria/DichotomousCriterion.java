package de.randi2.model.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.hibernate.validator.NotEmpty;

import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.unsorted.ContraintViolatedException;

@Entity
public class DichotomousCriterion extends AbstractCriterion<String, DichotomousConstraint> {

	private static final long serialVersionUID = -2153872079417596823L;

	private String option2 = null;
	private String option1 = null;

//	@OneToOne
//	private DichotomousConstraint inclusionCriterion;
	
	@NotEmpty
	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	@NotEmpty
	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.model.criteria.AbstractCriterion#getConfiguredValues()
	 */
	@Override
	public List<String> getConfiguredValues() {
		if (option1 == null || option2 == null
				|| option1.isEmpty() || option2.isEmpty()) {
			return null; // The Values are not configured.
		} else if (configuredValues == null) {
			configuredValues = new ArrayList<String>();
			configuredValues.add(option1);
			configuredValues.add(option2);
		} else {
			configuredValues.clear();
			configuredValues.add(option1);
			configuredValues.add(option2);
		}
		return configuredValues;
	}

	@Override
	public void isValueCorrect(String value) throws ContraintViolatedException {
		if (!(option1.equals(value) || option2.equals(value))) {
			throw new ContraintViolatedException();
		}
		if (inclusionCriterion != null) {
			inclusionCriterion.isValueCorrect(value);
		}
	}

	@Override
	public Class<DichotomousConstraint> getContstraintType() {
		return DichotomousConstraint.class;
	}



}
