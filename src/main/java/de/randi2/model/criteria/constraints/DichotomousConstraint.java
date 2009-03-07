package de.randi2.model.criteria.constraints;

import java.util.List;

import javax.persistence.Entity;

import de.randi2.unsorted.ContraintViolatedException;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class DichotomousConstraint extends AbstractConstraint<String> {

	public DichotomousConstraint(List<String> args)
			throws ContraintViolatedException {
		super(args);
	}

	private static final long serialVersionUID = -1224367469711016048L;

	private String expectedValue;

	public String getExpectedValue() {
		return expectedValue;
	}
	
	public void setExpectedValue(String _expectedValue){
		this.expectedValue = _expectedValue;
	}

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
	public String toString() {
		return new  ToStringBuilder(this)
				.append(expectedValue).toString();
	}
}
