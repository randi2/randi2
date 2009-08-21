package de.randi2.model.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.NotEmpty;

import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.unsorted.ContraintViolatedException;

@Entity
public class DichotomousCriterion extends AbstractCriterion<String, DichotomousConstraint> {

	private static final long serialVersionUID = -2153872079417596823L;

	@NotEmpty
	@Getter @Setter
	private String option1 = null;
	@NotEmpty
	@Getter @Setter
	private String option2 = null;


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
		if (inclusionConstraint != null) {
			inclusionConstraint.isValueCorrect(value);
		}
	}

	@Override
	public Class<DichotomousConstraint> getContstraintType() {
		return DichotomousConstraint.class;
	}

}
