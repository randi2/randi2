package de.randi2.model.criteria;

import java.io.Serializable;
import java.util.List;

import de.randi2.unsorted.ContraintViolatedException;

public abstract class AbstractConstraints<V extends Object> implements Serializable {

	private static final long serialVersionUID = -5608235144658474459L;
	
	public AbstractConstraints(List<V> args) throws ContraintViolatedException{
		configure(args);
	}
	
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
