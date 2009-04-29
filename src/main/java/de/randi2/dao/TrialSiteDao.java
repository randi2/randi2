package de.randi2.dao;

import java.util.List;

import de.randi2.model.TrialSite;

public interface TrialSiteDao extends AbstractDao<TrialSite> {
	

	@Override
	public List<TrialSite> getAll();
	
	public TrialSite get(String name);

	@Override
	public void create(TrialSite object);
}
