package de.randi2.simulation.model.distribution;

import java.io.Serializable;
import java.util.List;
import java.util.Random;


public  class  UniformDistribution<E extends Serializable> extends AbstractDistribution<E> {

	public UniformDistribution(List<E> elements) {
		super(elements);
	}

	Random random = new Random();

	@Override
	public E getNextValue() {
		return elements.get(random.nextInt(elements.size()));
	}

	
	
}
