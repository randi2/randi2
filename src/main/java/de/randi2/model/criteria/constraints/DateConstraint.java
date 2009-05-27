package de.randi2.model.criteria.constraints;

import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;

import de.randi2.unsorted.ContraintViolatedException;

@Entity
public class DateConstraint extends AbstractConstraint<GregorianCalendar> {

	private GregorianCalendar exaptedValue;

	public DateConstraint() {

	}

	public DateConstraint(List<GregorianCalendar> args) throws ContraintViolatedException {
		super(args);
		// TODO Auto-generated constructor stub
	}

	public GregorianCalendar getExaptedValue() {
		return exaptedValue;
	}

	public void setExaptedValue(GregorianCalendar exaptedValue) {
		this.exaptedValue = exaptedValue;
	}

	@Override
	protected void configure(List<GregorianCalendar> args)
			throws ContraintViolatedException {
	}

	@Override
	public void isValueCorrect(GregorianCalendar _value)
			throws ContraintViolatedException {
	}
}
