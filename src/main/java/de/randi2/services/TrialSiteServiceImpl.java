package de.randi2.services;

import java.util.List;

import de.randi2.model.TrialSite;

public class TrialSiteServiceImpl implements TrialSiteService{

	@Override
	public boolean authorize(TrialSite site, String password) {
		return false;
		
	}

	@Override
	public List<TrialSite> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrialSite getObject(long objectID) {
		// TODO Auto-generated method stub
		return null;
	}

}
