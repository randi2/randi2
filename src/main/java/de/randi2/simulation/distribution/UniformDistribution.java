package de.randi2.simulation.distribution;

import java.io.Serializable;
import java.util.List;
import java.util.Random;


public  class  UniformDistribution<E extends Serializable> extends AbstractDistribution<E> {

	

	public UniformDistribution(List<E> elements) {
		super(elements);
	}
	
	public UniformDistribution(List<E> elements, long seed) {
		super(elements, seed);
	}


	@Override
	public E getNextValue() {
		return elements.get(random.nextInt(elements.size()));
	}

	
	
}
