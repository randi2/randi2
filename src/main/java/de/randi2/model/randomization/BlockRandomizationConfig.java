package de.randi2.model.randomization;

import de.randi2.model.Trial;
import de.randi2.randomization.BlockRandomization;
import de.randi2.randomization.RandomizationAlgorithm;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class BlockRandomizationConfig extends AbstractRandomizationConfig {

	@Override
	public RandomizationAlgorithm<?, ?> getAlgorithm(Trial trial) {
		return new BlockRandomization(trial);
	}

	public enum TYPE {
		MULTIPLY, ABSOLUTE;
	}
	
	private int minimum;
	private int maximum;
	
	@Enumerated(EnumType.STRING)
	private TYPE type;
	
	public int getMinimum() {
		return minimum;
	}
	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}
	public int getMaximum() {
		return maximum;
	}
	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}
	public TYPE getType() {
		return type;
	}
	public void setType(TYPE type) {
		this.type = type;
	}
	
}
