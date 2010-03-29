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
package de.randi2.simulation.model;

import java.text.DecimalFormat;
import java.util.Map;

import lombok.Data;
import de.randi2.model.TreatmentArm;

/**
 * Represented a result of a simulation in one treatment arm. This class
 * contains different measures like, minimum, maximum, mean and median.
 * 
 * @author Daniel Schrimpf <ds@randi2.de>
 * 
 */
@Data
public class SimualtionResultArm {

	private TreatmentArm arm;

	private int min;
	private int max;
	private double mean;
	private double median;
	private Map<String, Integer> strataCountsPerArmMin;
	private Map<String, Integer> strataCountsPerArmMax;
	private Map<String, Double> strataCountsPerArmMean;

	private DecimalFormat f = new DecimalFormat("#0.00");

	/**
	 * Returns the difference from planned subjects to minimum subjects in percent.
	 * @return the percent of the difference
	 */
	public double getMinPercent() {
		return ((min * 100.0) / arm.getPlannedSubjects()) - 100;
	}

	/**
	 * Returns the difference from planned subjects to minimum subjects in percent.
	 * @return the percent of the difference
	 */
	public double getMaxPercent() {
		return ((max * 100.0) / arm.getPlannedSubjects()) - 100;
	}

	
	/**
	 * Returns the difference from planned subjects to minimum subjects in percent as string.
	 * @return a string like XX.XX% (two decimal places)
	 */
	public String getMinPercentString() {
		return f.format(getMinPercent()) + "%";
	}

	
	/**
	 * Returns the difference from planned subjects to maximum subjects in percent as string.
	 * @return a string like XX.XX% (two decimal places)
	 */
	public String getMaxPercentString() {
		return f.format(getMaxPercent()) + "%";
	}

}
