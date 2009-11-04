package de.randi2.simulation.service;

import de.randi2.model.Trial;
import de.randi2.simulation.model.SimulationResult;

public interface SimulationService {

	public SimulationResult simulateTrial(Trial trial, int runs);
}
