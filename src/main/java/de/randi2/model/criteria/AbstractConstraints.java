package de.randi2.model.criteria;

import java.io.Serializable;
import de.randi2.unsorted.ContraintViolatedException;

public abstract class AbstractConstraints<V extends Object> implements Serializable {
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
