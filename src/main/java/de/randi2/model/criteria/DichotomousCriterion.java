package de.randi2.model.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import de.randi2.model.SubjectProperty;
import de.randi2.utility.StratumProc;

@Entity
public class DichotomousCriterion extends AbstractCriterion<String> {

	private static final long serialVersionUID = -2153872079417596823L;

	private static final String TRUE_STRING = "TRUE";
	private static final String FALSE_STRING = "FALSE";

	private String option1 = null;
	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	private String option2 = null;
	

	private boolean isBinary = true;

	/**
	 * If the object represents an inclusion criteria, this field has the
	 * constraints.
	 */
	@Embedded
	private DichotomousConstraints criterionConstraints = null;

	public void setBinary() {
		this.isBinary = true;
		this.option1 = null;
		this.option2 = null;
	}

	public void setStringOptions(String option1, String option2) {
		this.isBinary = false;
		this.option1 = option1;
		this.option2 = option2;
	}

	@Override
	public SubjectProperty<String> createPropertyPrototype() {
		SubjectProperty<String> prop = new SubjectProperty<String>();
		applyConstraints(prop);
		return prop;
	}

	@Override
	public void applyConstraints(SubjectProperty<String> prop) {
		if (this.isBinary) {
			prop.addPossibleValue(TRUE_STRING);
			prop.addPossibleValue(FALSE_STRING);
			if (isStratum) {
				prop.setStratumComputation(StratumProc.binaryStratification(
						TRUE_STRING, FALSE_STRING));
			}
		} else {
			prop.addPossibleValue(option1);
			prop.addPossibleValue(option2);
			if (isStratum) {
				prop.setStratumComputation(StratumProc.binaryStratification(
						option1, option2));
			}
		}
	}

	@Override
	public DichotomousConstraints getConstraints() {
		if (criterionConstraints == null)
			criterionConstraints = new DichotomousConstraints();
		return criterionConstraints;
	}

	@Override
	public void setConstraints(AbstractConstraints<String> _constraints) {
		criterionConstraints = (DichotomousConstraints) _constraints;
	}

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
