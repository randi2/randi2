package de.randi2.model.criteria.constraints;

import java.util.List;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import de.randi2.unsorted.ContraintViolatedException;

@Entity
public class DichotomousConstraint extends AbstractConstraint<String> {

	protected DichotomousConstraint(){}
	
	public DichotomousConstraint(List<String> args)
			throws ContraintViolatedException {
		super(args);
	}

	private static final long serialVersionUID = -1224367469711016048L;

	@Getter @Setter
	private String expectedValue;


	@Override
	public void isValueCorrect(String _value) throws ContraintViolatedException {
		if (!expectedValue.equals(_value)) {
			throw new ContraintViolatedException();
		}
	}

	@Override
	protected void configure(List<String> args)
			throws ContraintViolatedException {
		if (args == null || args.size() != 1)
			throw new ContraintViolatedException();
		this.expectedValue = args.get(0);
	}
	
	@Override
	public String getUIName() {
		return expectedValue;
	}
}
