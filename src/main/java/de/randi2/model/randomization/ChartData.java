package de.randi2.model.randomization;

import java.util.List;

import lombok.Data;

/**
 * Contains the data for all type of charts.
 * 
 * @author Lukasz Plotnicki
 * 
 */
@Data
public class ChartData {

	/**
	 * Description of the points on the X axis. The size of this list must be
	 * equal the amount of the data-points. <managed-property>
	 * <property-name>chartsService</property-name>
	 * <property-class>de.randi2.services.ChartsService</property-class>
	 * <value>#{chartsService}</value> </managed-property>
	 */
	private List<String> xLabels;

	/**
	 * The data-set for the chart. Each array defines the data-set for one
	 * "x label" (e.g. 10d,20d,30d -> represent for "month 1" - x label - the
	 * values of the 3 functions at this point of time)
	 */
	private List<double[]> data;
	
	private double[] dataPieChart;
}
