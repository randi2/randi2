package de.randi2.simulation.distribution;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import lombok.Getter;

public class ConcreteDistribution<E extends Serializable> extends AbstractDistribution<E> {

	@Getter
	private int[] ratio;
	private Random rand = new Random();
	private int all = 0;
	
	public ConcreteDistribution(List<E> elements, int... ratio){
		super(elements);
		this.ratio = ratio;
		for(int i : ratio){
			all +=i;
		}
	}
	
	@Override
	public E getNextValue() {
		double  number = rand.nextDouble();
		boolean found = false;
		int i =0;
		double sum =0.0;
		while (!found && i<ratio.length){
			sum +=ratio[i];
			if((sum/all)>=number){
				found = true;	
			}else{
				i++;
			}
		}
		
		return elements.get(i);
	}

}
