package de.randi2.model.criteria.constraints;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import de.randi2.unsorted.ContraintViolatedException;

@Entity
public class DateConstraint extends AbstractConstraint<GregorianCalendar> {


	private static final long serialVersionUID = -9068633271254996713L;
	
	@Getter @Setter
	private GregorianCalendar firstDate;
	@Getter @Setter
	private GregorianCalendar secondDate;

	public DateConstraint() {

	}

	public DateConstraint(List<GregorianCalendar> args) throws ContraintViolatedException {
		super(args);
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
		}else if( firstDate!=null && secondDate!=null && (value.before(firstDate) || value.after(secondDate))){
			throw new ContraintViolatedException();
		}else if(firstDate!=null && secondDate==null && value.before(firstDate) ){
			throw new ContraintViolatedException();
		}else if( firstDate==null && secondDate!=null && value.after(secondDate) ){
			throw new ContraintViolatedException();
		}
	}
	
	@Override
	public String getUIName() {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if(firstDate!=null && secondDate!=null){
			return (format.format(firstDate) + "|" + format.format(secondDate));
		}else if(firstDate!=null){
			return ">" + format.format(firstDate);
		}else {
			return "<" + format.format(secondDate);
		}
	}
}
