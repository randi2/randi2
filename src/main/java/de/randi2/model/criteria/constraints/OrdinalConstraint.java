package de.randi2.model.criteria.constraints;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import de.randi2.unsorted.ContraintViolatedException;

@Embeddable 
public class OrdinalConstraint extends AbstractConstraint<String> {

	private static final long serialVersionUID = 3642808577019112783L;

	public OrdinalConstraint(List<String> args)
			throws ContraintViolatedException {
		super(args);
		// TODO Auto-generated constructor stub
	}

	@Transient
	public List<String> expectedValues;

	public List<String> getExpectedValues() {
		return expectedValues;
	}

	public void setExpectedValues(List<String> expectedValues) {
		this.expectedValues = expectedValues;
	}

	@Override
	public void isValueCorrect(String _value) throws ContraintViolatedException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected void configure(List<String> args)
			throws ContraintViolatedException {
		// TODO Auto-generated method stub
		
	}
}
