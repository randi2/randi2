package de.randi2.model.criteria;

import de.randi2.unsorted.ContraintViolatedException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import org.hibernate.validator.NotEmpty;

@Entity
public class DichotomousCriterion extends AbstractCriterion<String> {

	private static final long serialVersionUID = -2153872079417596823L;

	@Embeddable
	public class DichotomousConstraints extends AbstractConstraints<String> {

		public DichotomousConstraints(List<String> args)
				throws ContraintViolatedException {
			super(args);
		}
		private static final long serialVersionUID = -1224367469711016048L;
		public String expectedValue;

		public String getExpectedValue() {
			return expectedValue;
		}

		public void setExpectedValue(String expectedValue) {
			this.expectedValue = expectedValue;
		}

		@Override
		public void isValueCorrect(String _value) throws ContraintViolatedException {
			if (!expectedValue.equals(_value)) {
				throw new ContraintViolatedException();
			}
		}

		@Override
		protected void configure(List<String> args)
				throws ContraintViolatedException {
			// TODO Auto-generated method stub
		}
	}
	private String option2 = null;
	private String option1 = null;

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
	/**
	 * If the object represents an inclusion criteria, this field has the
	 * constraints.
	 */
	@Embedded
	private DichotomousConstraints criterionConstraints = null;

	@Override
	public AbstractConstraints<String> getConstraints() {
		return criterionConstraints;
	}


	/* (non-Javadoc)
	 * @see de.randi2.model.criteria.AbstractCriterion#getConfiguredValues()
	 */
	@Override
	public List<String> getConfiguredValues() {
		if ((option1 == null && option2 == null) || (option1.isEmpty() && option2.isEmpty())) {
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
	public void defineConstraints(List<String> constraintValues) {
		try {
			criterionConstraints = new DichotomousConstraints(constraintValues);
		} catch (ContraintViolatedException ex) {
			criterionConstraints = null;
		}
	}

	@Override
	public boolean isInclusionCriterion() {
		return criterionConstraints != null;
	}

	@Override
	public void isValueCorrect(String value) throws ContraintViolatedException {
		if(!(option1.equals(value) || option2.equals(name))){
			throw new ContraintViolatedException();
		}
		if (criterionConstraints != null){
			criterionConstraints.isValueCorrect(value);
		}
	}
}
