package de.randi2.model.criteria;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.Embedded;
import javax.persistence.Entity;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.IndexColumn;

import de.randi2.model.SubjectProperty;
import de.randi2.utility.StratumProc;

@Entity
public class DichotomousCriterion extends AbstractCriterion<String> {

	private static final long serialVersionUID = -2153872079417596823L;

	private static final String TRUE_STRING = "TRUE";
	private static final String FALSE_STRING = "FALSE";

	@CollectionOfElements
	@IndexColumn(name = "options")
	private String options[] = new String[2];

	private boolean isBinary = true;

	/**
	 * If the object represents an inclusion criteria, this field has the
	 * constraints.
	 */
	@Embedded
	private DichotomousConstraints criterionConstraints = null;

	public void setBinary() {
		this.isBinary = true;
		this.options[0] = null;
		this.options[1] = null;
	}

	public void setStringOptions(String option1, String option2) {
		this.isBinary = false;
		this.options[0] = option1;
		this.options[1] = option2;
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
			prop.addPossibleValue(options[0]);
			prop.addPossibleValue(options[1]);
			if (isStratum) {
				prop.setStratumComputation(StratumProc.binaryStratification(
						options[0], options[1]));
			}
		}
	}

	public String[] getOptions() {
		return options.clone();
	}

	public void setOptions(String[] options) {
		this.options = options.clone();
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
		if ((options[0] == null && options[1] == null)
				|| (options[0].isEmpty() && options[1].isEmpty()))
			return null; // The Values are not configured.
		else if (configuredValues == null) {
			configuredValues = new ArrayList<String>();
			configuredValues.add(options[0]);
			configuredValues.add(options[1]);
		} else {
			configuredValues.clear();
			configuredValues.add(options[0]);
			configuredValues.add(options[1]);
		}
		return configuredValues;
	}

}
