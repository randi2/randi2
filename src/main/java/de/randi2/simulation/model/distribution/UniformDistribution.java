package de.randi2.simulation.model.distribution;

import java.util.Random;


public class UniformDistribution extends AbstractDistribution {

	Random random = new Random();
	
	
	@Override
	public int getNextInt(int n) {
		return random.nextInt(n);
	}

	
	
}
