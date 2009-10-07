package de.randi2.model.criteria.constraints;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CollectionOfElements;

import de.randi2.unsorted.ContraintViolatedException;

@Entity
public class OrdinalConstraint extends AbstractConstraint<String> {

	private static final long serialVersionUID = 3642808577019112783L;

	
	protected OrdinalConstraint(){}
	
	public OrdinalConstraint(List<String> args)
			throws ContraintViolatedException {
		super(args);
		// TODO Auto-generated constructor stub
	}

	@CollectionOfElements(fetch=FetchType.EAGER)
	@Getter @Setter
	public Set<String> expectedValues;


	@Override
	public void isValueCorrect(String _value) throws ContraintViolatedException {
		if(!expectedValues.contains(_value)){
			throw new ContraintViolatedException();
		}
		
	}

	@Override
	protected void configure(List<String> args)
			throws ContraintViolatedException {
		if(args == null || args.size() <1){
			throw new ContraintViolatedException();
		}
		this.expectedValues = new HashSet<String>(args);
		
	}
	
	@Override
	public String getUIName() {
		StringBuffer result = new StringBuffer();
		for(String s: expectedValues){
			result.append(s + "|");
		}
		return result.toString();
	}
}
