package de.randi2.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.annotation.Secured;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.TrialSiteDao;
import de.randi2.model.TrialSite;

public class TrialSiteServiceImpl implements TrialSiteService{

	private Logger logger = Logger.getLogger(TrialServiceImpl.class);
	
	@Autowired private TrialSiteDao siteDAO;
	
	@Override
	@Secured({"ROLE_ANONYMOUS"})
	public boolean authorize(TrialSite site, String password) {
		return site.getPassword().equals(password);
		
	}

	@Override
	@Secured({"ROLE_USER","ROLE_ANONYMOUS", "AFTER_ACL_COLLECTION_READ"})
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<TrialSite> getAll() {
		return siteDAO.getAll();
	}

	@Override
	@Secured({"ROLE_USER", "AFTER_ACL_READ"})
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public TrialSite getObject(long objectID) {
		return siteDAO.get(objectID);
	}

	@Override
	@Secured({"ROLE_USER", "ACL_TRIALSITE_CREATE"})
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void create(TrialSite newSite) {
		siteDAO.create(newSite);
		
	}

	@Override
	@Secured({"ROLE_USER", "ACL_TRIALSITE_WRITE"})
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public TrialSite update(TrialSite site) {
		return siteDAO.update(site);
	}

}
