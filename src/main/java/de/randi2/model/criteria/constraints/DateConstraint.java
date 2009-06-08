package de.randi2.model.criteria.constraints;

import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;

import de.randi2.unsorted.ContraintViolatedException;

@Entity
public class DateConstraint extends AbstractConstraint<GregorianCalendar> {

	private GregorianCalendar firstDate;
	private GregorianCalendar secondDate;

	public DateConstraint() {

	}

	public DateConstraint(List<GregorianCalendar> args) throws ContraintViolatedException {
		super(args);
	}

	


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
	protected void configure(List<GregorianCalendar> list)
			throws ContraintViolatedException {
		if(list==null || list.size() ==0 || list.size()>2){
			throw new ContraintViolatedException();
		}else if (list.size()==1) {
			firstDate = list.get(0);
		}else{
			firstDate = list.get(0);
			secondDate = list.get(1);
		}
	}

	@Override
	public void isValueCorrect(GregorianCalendar value)
			throws ContraintViolatedException {
		if(value ==null){
			throw new ContraintViolatedException();
		}else if(value != null && firstDate!=null && secondDate!=null && (value.before(firstDate) || value.after(secondDate))){
			throw new ContraintViolatedException();
		}else if(firstDate!=null && secondDate==null && value.before(firstDate) ){
			throw new ContraintViolatedException();
		}else if( firstDate==null && secondDate!=null && value.after(secondDate) ){
			throw new ContraintViolatedException();
		}
	}
}
