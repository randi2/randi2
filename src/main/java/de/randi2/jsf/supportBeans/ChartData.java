package de.randi2.jsf.supportBeans;

import java.util.List;

/**
 * !Comment
 * @author Lukasz Plotnicki
 *
 */
public class ChartData {
	
	private List<String> xLabels;
	
	private List<String> yLabels;
	
	private List<Double> data;

	public List<String> getxLabels() {
		return xLabels;
	}

	public void setxLabels(List<String> xLabels) {
		this.xLabels = xLabels;
	}

	public List<String> getyLabels() {
		return yLabels;
	}

	public void setyLabels(List<String> yLabels) {
		this.yLabels = yLabels;
	}

	public List<Double> getData() {
		return data;
	}

	public void setData(List<Double> data) {
		this.data = data;
	}
	
	

}
