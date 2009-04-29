package de.randi2.services;

import java.util.List;

import org.apache.log4j.Logger;

import de.randi2.dao.TrialSiteDao;
import de.randi2.model.TrialSite;

public class TrialSiteServiceImpl implements TrialSiteService{

	private Logger logger = Logger.getLogger(TrialServiceImpl.class);
	private TrialSiteDao siteDAO;
	
	public TrialSiteServiceImpl(TrialSiteDao _siteDAO){
		siteDAO = _siteDAO;
	}
	
	@Override
	public boolean authorize(TrialSite site, String password) {
		return site.getPassword().equals(password);
		
	}

	@Override
	public List<TrialSite> getAll() {
		return siteDAO.getAll();
	}

	@Override
	public TrialSite getObject(long objectID) {
		// TODO Auto-generated method stub
		return siteDAO.get(objectID);
	}

	@Override
	public void create(TrialSite newSite) {
		siteDAO.create(newSite);
		
	}

	@Override
	public TrialSite update(TrialSite site) {
		return siteDAO.update(site);
	}

}
