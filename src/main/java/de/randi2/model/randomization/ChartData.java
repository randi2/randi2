package de.randi2.model.randomization;

import java.util.List;

/**
 * !Comment
 * 
 * @author Lukasz Plotnicki
 * 
 */
public class ChartData {

	/**
	 * Description of the points on the X axis. The size of this list must be
	 * equal the amount of the data-points.
		<managed-property>
			<property-name>chartsService</property-name>
			<property-class>de.randi2.services.ChartsService</property-class>
			<value>#{chartsService}</value>
		</managed-property>
	 */
	private List<String> xLabels;

	/**
	 * The names of the functions.
	 */
	private List<String> functionNames;

	/**
	 * The data-set for the chart. Each array defines the data-set for one
	 * "x label" (e.g. 10d,20d,30d -> represent for "month 1" - x label - the
	 * values of the 3 functions at this point of time)
	 */
	private List<double[]> data;

	public List<String> getxLabels() {
		return xLabels;
	}

	public void setxLabels(List<String> xLabels) {
		this.xLabels = xLabels;
	}

	public List<String> getFunctionNames() {
		return functionNames;
	}

	public void setFunctionNames(List<String> functionNames) {
		this.functionNames = functionNames;
	}

	public List<double[]> getData() {
		return data;
	}

	public void setData(List<double[]> data) {
		this.data = data;
	}

}
