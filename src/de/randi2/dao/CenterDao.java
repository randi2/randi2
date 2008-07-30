package de.randi2.dao;

import java.util.List;

import de.randi2.model.TrialSite;

public interface CenterDao extends AbstractDao<TrialSite> {
	

	public List<TrialSite> getAll();
	
	public TrialSite get(String name);

}
