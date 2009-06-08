package de.randi2.model.criteria;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;

import de.randi2.model.criteria.constraints.DateConstraint;
import de.randi2.unsorted.ContraintViolatedException;

@Entity
public class DateCriterion extends AbstractCriterion<GregorianCalendar, DateConstraint>{


	
	@Override
	public void isValueCorrect(GregorianCalendar value) throws ContraintViolatedException {
		if(value == null){
			throw new ContraintViolatedException();
		}else	if(inclusionCriterion!=null){
			inclusionCriterion.isValueCorrect(value);
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
