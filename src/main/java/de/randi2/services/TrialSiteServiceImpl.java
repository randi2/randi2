/* 
 * (c) 2008- RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
package de.randi2.services;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.dao.SystemWideSaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.TrialSiteDao;
import de.randi2.model.Person;
import de.randi2.model.TrialSite;

@Service("trialSiteService")
public class TrialSiteServiceImpl implements TrialSiteService{

	private Logger logger = Logger.getLogger(TrialServiceImpl.class);
	
	@Autowired private TrialSiteDao siteDAO;
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private SystemWideSaltSource saltSourceTrialSite;
	
	@Override
	@Secured({"ROLE_ANONYMOUS"})
	public boolean authorize(TrialSite site, String password) {
		return site.getPassword().equals(passwordEncoder.encodePassword(password,saltSourceTrialSite.getSystemWideSalt()));
		
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
		newSite.setPassword(passwordEncoder.encodePassword(newSite.getPassword(), saltSourceTrialSite.getSystemWideSalt()));
		siteDAO.create(newSite);
		
	}

	@Override
	@Secured({"ACL_TRIALSITE_WRITE"})
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public TrialSite update(TrialSite site) {
		logger.info("user: " + SecurityContextHolder.getContext().getAuthentication().getName() + " update trial site with name " + site.getName() + " (id="+site.getId()+")");
		return siteDAO.update(site);
	}

	@Override
	@Secured({"ROLE_USER", "AFTER_ACL_READ"})
	@Transactional(propagation=Propagation.SUPPORTS)
	public TrialSite getTrialSiteFromPerson(Person person) {
		return siteDAO.get(person);
	}

	@Override
	@Secured({"ACL_TRIALSITE_WRITE"})
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void addPerson(TrialSite site, Person person) throws ServiceException {
		if(site == null || site.getId()<1 || person == null || person.getId() <1) 
			throw new ServiceException("Invalid value");
		site = siteDAO.refresh(site);
		site.getMembers().add(person);
		siteDAO.update(site);
	}
	
	@Override
	@Secured({"ACL_TRIALSITE_WRITE"})
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void changePersonTrialSite(TrialSite newSite,
			Person person) throws ServiceException {
		TrialSite oldSite = siteDAO.get(person);
		newSite = siteDAO.refresh(newSite);
		if(!oldSite.getMembers().remove(person)) throw new ServiceException();
		newSite.getMembers().add(person);
		siteDAO.update(oldSite);
		siteDAO.update(newSite);
	}
	
	@Override
	@Secured({"ROLE_USER", "AFTER_ACL_READ"})
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Person> getMembers(TrialSite site){
		site = siteDAO.refresh(site);
		if(site.getMembers().size()>0) site.getMembers().get(0);
		return site.getMembers();
	}

}
