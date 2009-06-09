package de.randi2.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.annotation.Secured;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.dao.salt.SystemWideSaltSource;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.TrialSiteDao;
import de.randi2.model.TrialSite;

public class TrialSiteServiceImpl implements TrialSiteService{

	private Logger logger = Logger.getLogger(TrialServiceImpl.class);
	
	@Autowired private TrialSiteDao siteDAO;
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private SystemWideSaltSource saltSource;
	
	@Override
	@Secured({"ROLE_ANONYMOUS"})
	public boolean authorize(TrialSite site, String password) {
		return site.getPassword().equals(passwordEncoder.encodePassword(password,saltSource.getSystemWideSalt()));
		
	}

	@Override
	@Secured({"ROLE_USER","ROLE_ANONYMOUS", "AFTER_ACL_COLLECTION_READ"})
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<TrialSite> getAll() {
		logger.info("user: " + SecurityContextHolder.getContext().getAuthentication().getName() + " get all trial sites");
		return siteDAO.getAll();
	}

	@Override
	@Secured({"ROLE_USER", "AFTER_ACL_READ"})
	@Transactional(propagation=Propagation.SUPPORTS)
	public TrialSite getObject(long objectID) {
		logger.info("user: " + SecurityContextHolder.getContext().getAuthentication().getName() + " get trial site with id=" + objectID);
		return siteDAO.get(objectID);
	}

	@Override
	@Secured({"ACL_TRIALSITE_CREATE"})
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void create(TrialSite newSite) {
		logger.info("user: " + SecurityContextHolder.getContext().getAuthentication().getName() + " create trial site with name " + newSite.getName());
		newSite.setPassword(passwordEncoder.encodePassword(newSite.getPassword(), saltSource.getSystemWideSalt()));
		siteDAO.create(newSite);
		
	}

	@Override
	@Secured({"ACL_TRIALSITE_WRITE"})
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public TrialSite update(TrialSite site) {
		logger.info("user: " + SecurityContextHolder.getContext().getAuthentication().getName() + " update trial site with name " + site.getName() + " (id="+site.getId()+")");
		return siteDAO.update(site);
	}

}
