package de.randi2.model.criteria;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;

import de.randi2.model.criteria.constraints.DateConstraint;
import de.randi2.unsorted.ContraintViolatedException;

@Entity
public class DateRangeCriterion extends AbstractCriterion<GregorianCalendar, DateConstraint>{


	private GregorianCalendar firstDate;
	private GregorianCalendar secondDate;
	
		
	public GregorianCalendar getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(GregorianCalendar firstDate) {
		this.firstDate = firstDate;
	}

	public GregorianCalendar getSecondDate() {
		return secondDate;
	}

	public void setSecondDate(GregorianCalendar secondDate) {
		this.secondDate = secondDate;
	}

	
	@Override
	public void isValueCorrect(GregorianCalendar value) throws ContraintViolatedException {
		if(value == null || value.before(firstDate) || value.after(secondDate)){
			throw new ContraintViolatedException();
		}
		if(inclusionCriterion!=null){
			inclusionCriterion.isValueCorrect(value);
		}
	}
	
	@Override
	public List<GregorianCalendar> getConfiguredValues() {
		if(secondDate==null || firstDate == null){
			return null;
		}else if(configuredValues == null){
			configuredValues = new ArrayList<GregorianCalendar>();
		}else{
			configuredValues.clear();
		}
		configuredValues.add(firstDate);
		configuredValues.add(secondDate);
		return configuredValues;
	}
	
	@Override
	public Class<DateConstraint> getContstraintType() {
		return DateConstraint.class;
	}
	
}
