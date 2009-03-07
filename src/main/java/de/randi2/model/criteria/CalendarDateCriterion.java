package de.randi2.model.criteria;

import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;
import org.hibernate.exception.ConstraintViolationException;

/**
 * <p>
 * This class represents properties of a trial subject based on calendar dates.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 * 
 */
@Entity
public class CalendarDateCriterion extends AbstractCriterion<GregorianCalendar> {

	private static final long serialVersionUID = 6916473157612145090L;
	
	private GregorianCalendar date = null;

	@Override
	public AbstractConstraints<GregorianCalendar> getConstraints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConstraints(AbstractConstraints<GregorianCalendar> _constraints) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GregorianCalendar> getConfiguredValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInclusionCriterion() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void isValueCorrect(GregorianCalendar value) throws ConstraintViolationException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
