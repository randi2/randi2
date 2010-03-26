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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import de.randi2.model.TreatmentArm;

/**
 * This class represented the values of one simulation run.
 * 
 * @author Daniel Schrimpf <ds@randi2.de>
 * 
 */
public class SimulationRun {

	private double marginalBalance = Double.NaN;

	@Getter
	@Setter
	private int[] subjectsPerArms;

	private int[] plannedSubjectsPerArm;

	@Getter
	@Setter
	private Long time;

	@Getter
	private Map<TreatmentArm, Map<String, Integer>> strataCountsPerArm;

	/**
	 * The constructor takes an array which contains the planned subjects per
	 * arm.
	 * 
	 * @param plannedSubjectsPerArm
	 *            An array which contains the planned subjects per arm.
	 */
	public SimulationRun(int[] plannedSubjectsPerArm, List<TreatmentArm> arms,
			List<String> strataIds) {
		subjectsPerArms = new int[plannedSubjectsPerArm.length];
		this.plannedSubjectsPerArm = plannedSubjectsPerArm;
		strataCountsPerArm = new HashMap<TreatmentArm, Map<String, Integer>>();
		for (TreatmentArm arm : arms) {
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			strataCountsPerArm.put(arm, map);
			for (String id : strataIds) {
				map.put(id, 0);
			}
		}
	}

	/**
	 * Calculated the marginal Balance and return the result.
	 * 
	 * @return The marginal Balance.
	 */
	public double getMarginalBalace() {
		if (Double.isNaN(marginalBalance)) {
			marginalBalance = 0.0;
			double numerator = 0.0;
			for (int i = 0; i < subjectsPerArms.length - 1; i++) {
				for (int j = i + 1; j < subjectsPerArms.length; j++) {
					marginalBalance += Math
							.abs(((subjectsPerArms[i] * 1.0) / (plannedSubjectsPerArm[i] * 1.0))
									- ((subjectsPerArms[j] * 1.0) / (plannedSubjectsPerArm[j] * 1.0)));
				}
				numerator += ((subjectsPerArms[i] * 1.0) / (plannedSubjectsPerArm[i] * 1.0));
			}
			numerator += subjectsPerArms[subjectsPerArms.length - 1]
					/ (plannedSubjectsPerArm[subjectsPerArms.length - 1] * 1.0);
			numerator = (subjectsPerArms.length - 1.0) * numerator;
			marginalBalance = marginalBalance / numerator;
			// marginalBalance = marginalBalance;
		}
		return marginalBalance;
	}

}
