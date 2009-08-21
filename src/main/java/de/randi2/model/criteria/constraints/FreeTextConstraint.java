package de.randi2.model.criteria.constraints;

import java.util.List;

import de.randi2.unsorted.ContraintViolatedException;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
public class FreeTextConstraint extends AbstractConstraint<String>{


	private static final long serialVersionUID = 992550887900777486L;
	
	@Getter @Setter
	private String expectedValue;
	

	public FreeTextConstraint(){
		
	}
	
	public FreeTextConstraint(List<String> args)
			throws ContraintViolatedException {
		super(args);
	}

	
	@Override
	protected void configure(List<String> args)
			throws ContraintViolatedException {	
		if (args == null || args.size() != 1)
			throw new ContraintViolatedException();
		this.expectedValue = args.get(0);
	}

	
	
	@Override
	public void isValueCorrect(String _value) throws ContraintViolatedException {
		if (!expectedValue.equals(_value)) {
			throw new ContraintViolatedException();
		}
		
	}
	
	

}
