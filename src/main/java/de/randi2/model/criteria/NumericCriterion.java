package de.randi2.model.criteria;

import javax.persistence.Entity;

import de.randi2.model.SubjectProperty;

/**
 * <p>
 * This class represents properties of a trial subject based on any digits.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 * 
 */
@Entity
public class NumericCriterion extends AbstractCriterion {

	private static final long serialVersionUID = -7119779388124571391L;

	@Override
	public void applyConstraints(SubjectProperty prop) {
		// TODO Auto-generated method stub

	}

	@Override
	public SubjectProperty createPropertyPrototype() {
		// TODO Auto-generated method stub
		return null;
	}
}
