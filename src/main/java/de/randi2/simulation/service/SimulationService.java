package de.randi2.simulation.service;

import java.util.List;

import de.randi2.model.Trial;
import de.randi2.simulation.model.DistributionSubjectProperty;
import de.randi2.simulation.model.SimulationResult;
import de.randi2.simulation.model.distribution.AbstractDistribution;

public interface SimulationService {

	public SimulationResult simulateTrial(Trial trial,List<DistributionSubjectProperty> properties,AbstractDistribution distributionTrialSites, int runs);
}
