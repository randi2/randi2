package de.randi2.services;

import java.util.List;

import de.randi2.dao.TrialSiteDao;
import de.randi2.model.TrialSite;

public class TrialSiteServiceImpl implements TrialSiteService{

	private TrialSiteDao siteDAO;
	
	public TrialSiteServiceImpl(TrialSiteDao _siteDAO){
		siteDAO = _siteDAO;
	}
	
	@Override
	public boolean authorize(TrialSite site, String password) {
		return false;
		
	}

	@Override
	public List<TrialSite> getAll() {
		return siteDAO.getAll();
	}

	@Override
	public TrialSite getObject(long objectID) {
		// TODO Auto-generated method stub
		return null;
	}

}
