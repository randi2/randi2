package de.randi2.dao;

import de.randi2.model.TrialSite;

public interface TrialSiteDao extends AbstractDao<TrialSite> {
	

	
	public TrialSite get(String name);

	@Override
	public void create(TrialSite object);
}
