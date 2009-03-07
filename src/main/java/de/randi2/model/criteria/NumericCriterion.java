package de.randi2.model.criteria;

import de.randi2.unsorted.ContraintViolatedException;
import java.util.List;

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
public class NumericCriterion extends AbstractCriterion<Float> {

	private static final long serialVersionUID = -7119779388124571391L;


	@Override
	public AbstractConstraints<Float> getConstraints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConstraints(AbstractConstraints<Float> _constraints) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Float> getConfiguredValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInclusionCriterion() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void isValueCorrect(Float value) throws ContraintViolatedException {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
