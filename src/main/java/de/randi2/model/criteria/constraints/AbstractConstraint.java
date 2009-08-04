package de.randi2.model.criteria.constraints;

import java.util.List;

import javax.persistence.Entity;

import de.randi2.model.AbstractDomainObject;
import de.randi2.unsorted.ContraintViolatedException;

@Entity(name="constraints")
public abstract class AbstractConstraint<V extends Object> extends AbstractDomainObject{

	private static final long serialVersionUID = -5608235144658474459L;
	
	public AbstractConstraint(List<V> args) throws ContraintViolatedException{
		configure(args);
	}
	
	protected AbstractConstraint(){}
	
	protected abstract void configure(List<V> args) throws ContraintViolatedException;

	public abstract void isValueCorrect(V _value) throws ContraintViolatedException;

	public boolean checkValue(V value){
		try{
			isValueCorrect(value);
			return true;
		}
		catch(ContraintViolatedException e){
			return false;
		}
	}
}
