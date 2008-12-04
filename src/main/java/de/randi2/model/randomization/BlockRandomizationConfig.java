package de.randi2.model.randomization;

public class BlockRandomizationConfig extends BaseRandomizationConfig {
	
	public enum TYPE {
		MULTIPLY, ABSOLUTE;
	}
	
	private int minimum;
	private int maximum;
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
