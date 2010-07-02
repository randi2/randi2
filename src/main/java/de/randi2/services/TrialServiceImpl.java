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

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.TrialDao;
import de.randi2.model.Login;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.utility.mail.MailService;
import de.randi2.utility.mail.exceptions.MailErrorException;

@Service("trialService")
public class TrialServiceImpl implements TrialService {

	private Logger logger =  Logger.getLogger(TrialServiceImpl.class);
	@Autowired private TrialDao trialDao;
	@Autowired private SessionFactory sessionFactory;
	@Autowired private MailService mailService;
		
	@Override
	@Secured({"ACL_TRIAL_CREATE"})
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void create(Trial newTrial) {
		logger.info("user: " + SecurityContextHolder.getContext().getAuthentication().getName() + " create a new trial site with name " +newTrial.getName());
		//added relationship between trial and treatment arm
		for (TreatmentArm tA : newTrial.getTreatmentArms()) {
			tA.setTrial(newTrial);
		}
		trialDao.create(newTrial);
		sessionFactory.getCurrentSession().flush();
	}
	
	
	@Override
//	secured with own SecurityAspect
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Trial randomize(Trial trial, TrialSubject subject) {
		logger.debug("user: " + SecurityContextHolder.getContext().getAuthentication().getName() + " randomized in trial " +trial.getName());
		subject.setTrialSite(((Login)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPerson().getTrialSite());
		subject.setInvestigator(((Login)SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
		TreatmentArm assignedArm = trial.getRandomizationConfiguration().getAlgorithm().randomize(subject);
		subject.setArm(assignedArm);
		//TODO Internal Subject's Identification
		subject.setRandNumber(subject.getTrialSite().getName()+"_"+trial.getAbbreviation()+"_"+assignedArm.getName()+"_"+(assignedArm.getSubjects().size()+1));
		subject.setCounter((trial.getSubjects().size()+1));
		if(subject.getIdentification() == null) subject.setIdentification(subject.getRandNumber());
		sessionFactory.getCurrentSession().persist(subject);
		assignedArm.addSubject(subject);
		sendRandomisationMail(trial, ((Login)SecurityContextHolder.getContext().getAuthentication().getPrincipal()), subject);
		Trial t = trialDao.update(trial);
		sessionFactory.getCurrentSession().flush();
		return t;
	}



	@Override
	@Secured({"ACL_TRIAL_WRITE"})
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Trial update(Trial trial) {
		logger.info("user: " + SecurityContextHolder.getContext().getAuthentication().getName() + " update trial site with name " + trial.getName() + "(id: "+trial.getId()+")");
		return trialDao.update(trial);
	}

	@Override
	@Secured({"AFTER_ACL_COLLECTION_READ"})
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Trial> getAll() {
		logger.info("user: " + SecurityContextHolder.getContext().getAuthentication().getName() + " get all trial sites");
		return trialDao.getAll();
	}

	@Override
	@Secured({"ROLE_USER", "AFTER_ACL_READ"})
	@Transactional(propagation=Propagation.SUPPORTS)
	public Trial getObject(long objectID) {
		logger.info("user: " + SecurityContextHolder.getContext().getAuthentication().getName() + " get trial site with id=" + objectID);
		return trialDao.get(objectID);
	}
	
	private void sendRandomisationMail(Trial trial, Login user, TrialSubject subject){
		
		Map<String, Object> newUserMessageFields = new HashMap<String, Object>();
		newUserMessageFields.put("user", user);
		newUserMessageFields.put("trial", trial);
		newUserMessageFields.put("trialSubject", subject);
		// Map of variables for the subject
		Map<String, Object> newUserSubjectFields = new HashMap<String, Object>();
		newUserSubjectFields.put("trialName", trial.getName());

		Locale language = user.getPrefLocale();


		mailService.sendMail(user.getPerson().getEmail(), "Randomize", language,
					newUserMessageFields, newUserSubjectFields);
		
	
		
		 newUserMessageFields = new HashMap<String, Object>();
		newUserMessageFields.put("user", 	trial.getSponsorInvestigator().getLogin());
		newUserMessageFields.put("trial", trial);
		newUserMessageFields.put("trialSubject", subject);
		newUserMessageFields.put("url", "http://randi2.com/CHANGEME");
		// Map of variables for the subject
	

		try{
		language = trial.getSponsorInvestigator().getLogin().getPrefLocale();
		}catch (Exception e) {
			language = Locale.getDefault();
		}
		
		mailService.sendMail(trial.getSponsorInvestigator().getEmail(), "Randomize", language,
					newUserMessageFields, newUserSubjectFields);
		
	}


	@Override
	public List<TrialSubject> getSubjects(Trial trial,Login investigator) {
		return trialDao.getSubjects(trial,investigator);
	}

}
