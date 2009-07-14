package de.randi2.model.criteria;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;

import de.randi2.model.criteria.constraints.DateConstraint;
import de.randi2.unsorted.ContraintViolatedException;

@Entity
public class DateCriterion extends AbstractCriterion<GregorianCalendar, DateConstraint>{

	private static final long serialVersionUID = -2091043770001920047L;

	@Override
	public void isValueCorrect(GregorianCalendar value) throws ContraintViolatedException {
		if(value == null){
			throw new ContraintViolatedException();
		}else	if(inclusionConstraint!=null){
			inclusionConstraint.isValueCorrect(value);
		}
	}
	
	@Override
	public List<GregorianCalendar> getConfiguredValues() {
		return new ArrayList<GregorianCalendar>();
	}
	
	@Override
	public Class<DateConstraint> getContstraintType() {
		return DateConstraint.class;
	}
	
}
