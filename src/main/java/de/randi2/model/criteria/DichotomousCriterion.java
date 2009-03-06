package de.randi2.model.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import org.hibernate.validator.NotEmpty;

import de.randi2.model.SubjectProperty;
import de.randi2.utility.StratumProc;

@Entity
public class DichotomousCriterion extends AbstractCriterion<String> {

	private static final long serialVersionUID = -2153872079417596823L;

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

	private String option2 = null;

	/**
	 * If the object represents an inclusion criteria, this field has the
	 * constraints.
	 */
	@Embedded
	private DichotomousConstraints criterionConstraints = null;

	@Override
	public SubjectProperty<String> createPropertyPrototype() {
		SubjectProperty<String> prop = new SubjectProperty<String>();
		applyConstraints(prop);
		return prop;
	}

	@Override
	public void applyConstraints(SubjectProperty<String> prop) {
		prop.addPossibleValue(option1);
		prop.addPossibleValue(option2);
		if (isStratum) {
			prop.setStratumComputation(StratumProc.binaryStratification(
					option1, option2));
		}
	}

	@Override
	public DichotomousConstraints getConstraints() {
		if (criterionConstraints == null)
			criterionConstraints = new DichotomousConstraints();
		return criterionConstraints;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.criteria.AbstractCriterion#setConstraints(de.randi2.model.criteria.AbstractConstraints)
	 */
	@Override
	public void setConstraints(AbstractConstraints<String> _constraints) {
		criterionConstraints = (DichotomousConstraints) _constraints;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.criteria.AbstractCriterion#getConfiguredValues()
	 */
	@Override
	public List<String> getConfiguredValues() {
		if ((option1 == null && option2 == null)
				|| (option1.isEmpty() && option2.isEmpty()))
			return null; // The Values are not configured.
		else if (configuredValues == null) {
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

}
