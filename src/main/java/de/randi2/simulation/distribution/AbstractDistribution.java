package de.randi2.simulation.distribution;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public abstract class AbstractDistribution<E extends Serializable> {

	protected List<E> elements;
	
	protected Random random;
	
	public AbstractDistribution(List<E> elements, long seed){
		this.elements = elements;
		random =new Random(seed);
	}
	
	public AbstractDistribution(List<E> elements){
			this.elements = elements;
			random = new Random();
	}
	
	
	public List<E> getElements(){
		return elements;
	}
	public abstract E getNextValue();
}
