/* 
 * (c) 2008- RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
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
