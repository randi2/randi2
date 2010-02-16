package de.randi2.simulation.service;

import java.util.List;

import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.simulation.distribution.AbstractDistribution;
import de.randi2.simulation.model.DistributionSubjectProperty;
import de.randi2.simulation.model.SimulationResult;

public interface SimulationService {

	public SimulationResult simulateTrial(Trial trial,List<DistributionSubjectProperty> properties,AbstractDistribution<TrialSite> distributionTrialSites, int runs, long maxTime);
	public long estimateSimulationDuration(Trial trial, List<DistributionSubjectProperty> properties, AbstractDistribution<TrialSite> distributionTrialSites, int runs, long maxTime);
}
