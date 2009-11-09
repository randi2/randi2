package de.randi2.simulation.model.distribution;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class ConcreteDistribution<E extends Serializable> extends AbstractDistribution<E> {

	private int[] ratio;
	private Random rand = new Random();
	private int all = 1;
	
	public ConcreteDistribution(List<E> elements, int... ratio){
		super(elements);
		this.ratio = ratio;
		for(int i : ratio){
			all +=i;
		}
	}
	
	@Override
	public E getNextValue() {
		int  number = rand.nextInt(all);
		boolean found = false;
		int i =0;
		int sum =0;
		while (!found && i<ratio.length){
			sum +=ratio[i];
			if(sum>=number){
				found = true;	
			}else{
				i++;
			}
		}
		
		return elements.get(i);
	}

}
