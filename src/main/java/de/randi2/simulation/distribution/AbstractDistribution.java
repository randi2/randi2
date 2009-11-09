package de.randi2.simulation.distribution;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractDistribution<E extends Serializable> {

	protected List<E> elements;
	
	public AbstractDistribution(List<E> elements){
		this.elements = elements;
	}
	
	
	public List<E> getElements(){
		return elements;
	}
	public abstract E getNextValue();
}
