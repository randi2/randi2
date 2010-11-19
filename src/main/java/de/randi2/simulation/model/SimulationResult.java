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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import de.randi2.model.TreatmentArm;
import de.randi2.model.randomization.AbstractRandomizationConfig;
import java.util.Collections;

/**
 * This class represented the results of a simulation. It contains the
 * simulation runs, different measures and the functionality to analyze the
 * simulation.
 * 
 * @author Daniel Schrimpf <ds@randi2.de>
 * 
 */
public class SimulationResult {

	@Getter
	private int amountRuns;

	@Getter
	private List<TreatmentArm> arms = new ArrayList<TreatmentArm>();

	@Getter
	private List<SimulationResultArm> simResultArms = new ArrayList<SimulationResultArm>();

	private int[] mins;
	private int[] maxs;

	private double marginalBalanceMin = Double.NaN;
	private double marginalBalanceMax = Double.NaN;;
	private double marginalBalanceMean = Double.NaN;;

	private double[] means;

	private double[] medians;

	private long duration = Long.MIN_VALUE;

	@Getter
	private List<SimulationRun> runs = new ArrayList<SimulationRun>();

	@Getter
	private AbstractRandomizationConfig algConf;

	private int[] plannedSubjectsPerArm;

	@Getter
	@Setter
	private String algorithmDescription;

	
	@Getter @Setter
	private Map<String,String> strataIdNames;
	
	@Getter @Setter
	private List<SimulationRawDataEntry> rawData = new ArrayList<SimulationRawDataEntry>();
	
	/**
	 * 
	 * @param arms
	 *            A list of the treatment arms.
	 * @param algConf
	 *            The algorithm of the simulation.
	 */
	public SimulationResult(List<TreatmentArm> arms,
			AbstractRandomizationConfig algConf, Map<String,String> strataIdNames) {
		this.arms = arms;
		this.algConf = algConf;
		this.strataIdNames  = strataIdNames;
	}

	/**
	 * Add one Simulation run to the list of the simulation runs.
	 * 
	 * @param run
	 *            The simulation run.
	 */
	public void addSimulationRun(SimulationRun run) {
		amountRuns++;
		runs.add(run);
	}

	/**
	 * This method create and initialize a simulation run.
	 * 
	 * @return The initialized simulation run.
	 */
	public SimulationRun getEmptyRun() {
		if (plannedSubjectsPerArm == null) {
			plannedSubjectsPerArm = new int[arms.size()];
			for (int i = 0; i < arms.size(); i++) {
				plannedSubjectsPerArm[i] = arms.get(i).getPlannedSubjects();
			}
		}
		return new SimulationRun(plannedSubjectsPerArm, arms, strataIdNames);
	}

	/**
	 * This method returns the mean of the patient amount over all simulation
	 * runs per arm.
	 * 
	 * @return The array with the results per arm.
	 */
	public double[] getMeans() {
		if (means == null) {
			analyze();
		}
		return means;
	}

	/**
	 * This method returns the minimum of the patient amount over all simulation
	 * runs per arm.
	 * 
	 * @return The array with the results per arm.
	 */
	public int[] getMins() {
		if (mins == null) {
			analyze();
		}
		return mins;
	}

	/**
	 * This method returns the maximum of the patient amount over all simulation
	 * runs per arm.
	 * 
	 * @return The array with the results per arm.
	 */
	public int[] getMaxs() {
		if (maxs == null) {
			analyze();
		}
		return maxs;
	}

	/**
	 * This method returns the medians of the patient amount over all simulation
	 * runs per arm.
	 * 
	 * @return The array with the results per arm.
	 */
	public double[] getMedians() {
		if (medians == null) {
			analyze();
		}
		return medians;
	}

	/**
	 * This method returns the minimum marginal balance over all simulation
	 * runs.
	 * 
	 * @return The minimum marginal balance.
	 */
	public double getMarginalBalanceMin() {
		if (Double.isNaN(marginalBalanceMin)) {
			analyze();
		}
		return marginalBalanceMin;
	}

	/**
	 * This method returns the maximal marginal balance over all simulation
	 * runs.
	 * 
	 * @return The maximal marginal balance.
	 */
	public double getMarginalBalanceMax() {
		if (Double.isNaN(marginalBalanceMax)) {
			analyze();
		}
		return marginalBalanceMax;
	}

	/**
	 * This method returns the mean of the marginal balances over all simulation
	 * runs.
	 * 
	 * @return The mean of the marginal balances.
	 */
	public double getMarginalBalanceMean() {
		if (Double.isNaN(marginalBalanceMean)) {
			analyze();
		}
		return marginalBalanceMean;
	}

	/**
	 * Calculate and return the duration of all simulation runs.
	 * 
	 * @return the
	 */
	public long getDuration() {
		if (duration == Long.MIN_VALUE) {
			analyze();
			duration = duration / 1000;
		}
		return duration;
	}

	/**
	 * This method analyze the simulation and set the measures, the method is
	 * called automatic if the measures are not initialized.
	 */
	public void analyze() {
		if (runs.size() >= 2) {
			// initialize the variables
			duration = 0;
			simResultArms = new ArrayList<SimulationResultArm>();
			mins = new int[arms.size()];
			for (int i = 0; i < mins.length; i++) {
				mins[i] = Integer.MAX_VALUE;
			}
			maxs = new int[arms.size()];
			means = new double[arms.size()];
			medians = new double[arms.size()];

			marginalBalanceMax = Double.MIN_VALUE;
			marginalBalanceMin = Double.MAX_VALUE;
			marginalBalanceMean = 0.0;
			Map<TreatmentArm, Map<String, Integer>> strataCountsPerArmMin = new HashMap<TreatmentArm, Map<String,Integer>>();
			Map<TreatmentArm, Map<String, Integer>> strataCountsPerArmMax = new HashMap<TreatmentArm, Map<String,Integer>>();
			Map<TreatmentArm, Map<String, Double>> strataCountsPerArmMean = new HashMap<TreatmentArm, Map<String,Double>>();
			
			// HashMap to calculate the median per arm.
			HashMap<Integer, ArrayList<Integer>> tempMedian = new HashMap<Integer, ArrayList<Integer>>();
			for (int i = 0; i < arms.size(); i++) {
				tempMedian.put(i, new ArrayList<Integer>());
				strataCountsPerArmMin.put(arms.get(i), new HashMap<String, Integer>());
				strataCountsPerArmMax.put(arms.get(i), new HashMap<String, Integer>());
				strataCountsPerArmMean.put(arms.get(i), new HashMap<String, Double>());
				for(String strataId : strataIdNames.keySet()){
					strataCountsPerArmMax.get(arms.get(i)).put(strataId, 0);
					strataCountsPerArmMin.get(arms.get(i)).put(strataId, Integer.MAX_VALUE);
					strataCountsPerArmMean.get(arms.get(i)).put(strataId, 0.0);
				}
			}

			// first loop over all simulation runs to calculate the
			// minimal/maximal
			// values, duration and marginal balances
			for (int i = 0; i < runs.size(); i++) {
				for (int j = 0; j < arms.size(); j++) {
					if (runs.get(i).getSubjectsPerArms()[j] < mins[j])
						mins[j] = runs.get(i).getSubjectsPerArms()[j];
					if (runs.get(i).getSubjectsPerArms()[j] > maxs[j])
						maxs[j] = runs.get(i).getSubjectsPerArms()[j];
					tempMedian.get(j).add(runs.get(i).getSubjectsPerArms()[j]);
					means[j] += runs.get(i).getSubjectsPerArms()[j];
					for(String strataId : runs.get(i).getStrataCountsPerArm().get(arms.get(j)).keySet()){
						if(strataCountsPerArmMax.get(arms.get(j)).get(strataId) < runs.get(i).getStrataCountsPerArm().get(arms.get(j)).get(strataId)){
							strataCountsPerArmMax.get(arms.get(j)).put(strataId, runs.get(i).getStrataCountsPerArm().get(arms.get(j)).get(strataId));
						}
						if(strataCountsPerArmMin.get(arms.get(j)).get(strataId) > runs.get(i).getStrataCountsPerArm().get(arms.get(j)).get(strataId)){
							strataCountsPerArmMin.get(arms.get(j)).put(strataId, runs.get(i).getStrataCountsPerArm().get(arms.get(j)).get(strataId));
						}
						strataCountsPerArmMean.get(arms.get(j)).put(strataId, strataCountsPerArmMean.get(arms.get(j)).get(strataId) + runs.get(i).getStrataCountsPerArm().get(arms.get(j)).get(strataId));
					}
				}

				if (runs.get(i).getMarginalBalace() < marginalBalanceMin)
					marginalBalanceMin = runs.get(i).getMarginalBalace();
				if (runs.get(i).getMarginalBalace() > marginalBalanceMax)
					marginalBalanceMax = runs.get(i).getMarginalBalace();
				marginalBalanceMean += runs.get(i).getMarginalBalace();
				duration += runs.get(i).getTime();
			}

			// second loop over all treatment arms to calculate the mean and the
			// median and create the reslut per arm
			for (int i = 0; i < arms.size(); i++) {
				means[i] = means[i] / amountRuns;
				ArrayList<Integer> listMedian = tempMedian.get(i);
				Collections.sort(listMedian);
				if (listMedian.size() % 2 == 0) {
					medians[i] = (listMedian.get((listMedian.size() / 2)) + listMedian
							.get((listMedian.size() / 2) + 1)) / 2;
				} else {
					medians[i] = listMedian.get((listMedian.size() / 2) + 1);
				}
				for(String strataId : strataCountsPerArmMean.get(arms.get(i)).keySet()){
					strataCountsPerArmMean.get(arms.get(i)).put(strataId, ( strataCountsPerArmMean.get(arms.get(i)).get(strataId) / amountRuns));
				}
				
				SimulationResultArm rArm = new SimulationResultArm(algorithmDescription, strataIdNames);
				rArm.setArm(arms.get(i));
				rArm.setMean(means[i]);
				rArm.setMedian(medians[i]);
				rArm.setMin(mins[i]);
				rArm.setMax(maxs[i]);
				rArm.setStrataCountsPerArmMin(strataCountsPerArmMin.get(arms.get(i)));
				rArm.setStrataCountsPerArmMax(strataCountsPerArmMax.get(arms.get(i)));
				rArm.setStrataCountsPerArmMean(strataCountsPerArmMean.get(arms.get(i)));
				simResultArms.add(rArm);
			}
			marginalBalanceMean = marginalBalanceMean / amountRuns;
		}
	}

	@Override
	public String toString() {
		return getAmountRuns() + " " + getMarginalBalanceMax();
	}
}
