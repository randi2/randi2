package de.randi2.dao;

import de.randi2.model.Trial;

public interface TrialDao {

	public Trial get(Long id);
	
	public void save(Trial trial);
	
	
}
