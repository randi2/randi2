package de.randi2.model.randomization;

import java.lang.reflect.Constructor;

import de.randi2.model.Trial;
import de.randi2.randomization.RandomizationAlgorithm;
import de.randi2.utility.Randi2Error;

public class BaseRandomizationConfig{
	private Class<? extends RandomizationAlgorithm<?, ?>> algorithmClass;
	
	public RandomizationAlgorithm<?, ?> getAlgorithm(Trial trial){
		try {
			return algorithmClass.getConstructor(Trial.class).newInstance(trial);
		} catch (Exception e) {
			throw new Randi2Error(e);
		}
	}

	public Class<? extends RandomizationAlgorithm<?, ?>> getAlgorithmClass() {
		return algorithmClass;
	}

	public void setAlgorithmClass(
			Class<? extends RandomizationAlgorithm<?, ?>> algorithmClass) {
		this.algorithmClass = algorithmClass;
	}
}
