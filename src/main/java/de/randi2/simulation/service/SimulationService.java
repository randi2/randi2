package de.randi2.simulation.service;

import java.util.List;

import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.simulation.distribution.AbstractDistribution;
import de.randi2.simulation.model.DistributionSubjectProperty;
import de.randi2.simulation.model.SimulationResult;

public interface SimulationService {

	/**
	 * The method simulated a trial with the passed parameters and return the simulation result.
	 * @param trial The simulation trial. 
	 * @param properties The patient properties with their specified distribution.
	 * @param distributionTrialSites The trial sites with their specified distribution.
	 * @param runs The count of the simulation runs.
	 * @param maxTime The maximal time for the simulation.
	 * @return The simulation result.
	 */
	public SimulationResult simulateTrial(Trial trial,List<DistributionSubjectProperty> properties,AbstractDistribution<TrialSite> distributionTrialSites, int runs, long maxTime, boolean collectRawData);
	
//	public long estimateSimulationDuration(Trial trial, List<DistributionSubjectProperty> properties, AbstractDistribution<TrialSite> distributionTrialSites, int runs, long maxTime);
}
