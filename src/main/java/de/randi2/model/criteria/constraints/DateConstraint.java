package de.randi2.model.criteria.constraints;

import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;

import de.randi2.unsorted.ContraintViolatedException;

@Entity
public class DateConstraint extends AbstractConstraint<GregorianCalendar> {

	private GregorianCalendar expectedValue;

	public DateConstraint() {

	}

	public DateConstraint(List<GregorianCalendar> args) throws ContraintViolatedException {
		super(args);
		// TODO Auto-generated constructor stub
	}

	

	public GregorianCalendar getExpectedValue() {
		return expectedValue;
	}

	public void setExpectedValue(GregorianCalendar expectedValue) {
		this.expectedValue = expectedValue;
	}

	@Override
	protected void configure(List<GregorianCalendar> list)
			throws ContraintViolatedException {
		if(list==null || list.size() !=1){
			throw new ContraintViolatedException();
		}else{
			expectedValue = list.get(0);
		}
	}

	@Override
	public void isValueCorrect(GregorianCalendar _value)
			throws ContraintViolatedException {
		if(!expectedValue.equals(_value)) throw new ContraintViolatedException();
	}
}
