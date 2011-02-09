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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.TrialDao;
import de.randi2.dao.TrialSiteDao;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.enumerations.TrialStatus;
import de.randi2.model.exceptions.TrialStateException;
import de.randi2.utility.mail.MailServiceInterface;

@Service("trialService")
public class TrialServiceImpl implements TrialService {

	private Logger logger = Logger.getLogger(TrialServiceImpl.class);
	@Autowired
	private TrialDao trialDao;
	
	@Autowired
	private TrialSiteDao trialSiteDao;
	
	protected EntityManager entityManager;

	@PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
	        this. entityManager = entityManager;
	}
	
	@Autowired
	private MailServiceInterface mailService;

	@Override
	@Secured({ "ACL_TRIAL_CREATE" })
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void create(Trial newTrial) {
		logger.info("user: "
				+ SecurityContextHolder.getContext().getAuthentication()
						.getName() + " create a new trial site with name "
				+ newTrial.getName());
		// added relationship between trial and treatment arm
		for (TreatmentArm tA : newTrial.getTreatmentArms()) {
			tA.setTrial(newTrial);
		}
		trialDao.create(newTrial);
	}

	@Override
	// secured with own SecurityAspect
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Trial randomize(Trial trial, TrialSubject subject) {
		trial = entityManager.find(Trial.class, trial.getId());
		logger.debug("user: "
				+ SecurityContextHolder.getContext().getAuthentication()
						.getName() + " randomized in trial " + trial.getName());
		subject.setTrialSite(trialSiteDao.get(((Login) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()).getPerson())); //TODO if trialSiteDao.get == null
		subject.setInvestigator(((Login) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()));
		TreatmentArm assignedArm = trial.getRandomizationConfiguration()
				.getAlgorithm().randomize(subject);
		subject.setArm(assignedArm);
		// TODO Internal Subject's Identification
		subject.setRandNumber(subject.getTrialSite().getName() + "_"
				+ trial.getAbbreviation() + "_" + assignedArm.getName() + "_"
				+ (assignedArm.getSubjects().size() + 1));
		subject.setCounter((trial.getSubjects().size() + 1));
		if (subject.getIdentification() == null)
			subject.setIdentification(subject.getRandNumber());
		entityManager.persist(subject);
		assignedArm.addSubject(subject);
		sendRandomisationMail(trial, ((Login) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal()), subject);
		Trial t = trialDao.update(trial);
		entityManager.flush();
		return t;
	}

	@Override
	@Secured({ "ACL_TRIAL_WRITE" })
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Trial update(Trial trial) throws TrialStateException,
			IllegalArgumentException {
		if (trial == null) {
			throw new IllegalArgumentException("Trial can't be NULL");
		} else if (trial.getId() == AbstractDomainObject.NOT_YET_SAVED_ID) {
			throw new IllegalArgumentException(
					"Trial must be a persistent object");
		}
		/*
		 * Get the old version of the object
		 */
		Trial oldObject = trialDao.get(trial.getId());
		switch (oldObject.getStatus()) {
		case ACTIVE:
			/*
			 * If object has been change, examine the changes
			 */
			if (!oldObject.equals(trial)) {
				/*
				 * Clone the object revert the allowed changes and check if the
				 * object's are the same
				 */
				Trial temp = clone(trial);
				temp.setStatus(oldObject.getStatus());
				temp.setProtocol(oldObject.getProtocol());
				temp.setEndDate(oldObject.getEndDate());
				temp.setDescription(oldObject.getDescription());
				temp.setVersion(oldObject.getVersion());
				/*
				 * If not throw an exception
				 */
				if (!temp.equals(oldObject))
					throw new TrialStateException(
							"Object changes not permitted!");
				/*
				 * If only the allowed attribute has been changed check the
				 * state
				 */
				else {
					if (trial.getStatus() == TrialStatus.IN_PREPARATION)
						throw new TrialStateException(
								"Status change not permitted");
				}
			}
			break;
		case FINISHED:
			throw new TrialStateException("Object changes not permitted!");
		case IN_PREPARATION:
			/*
			 * If object has been change, examine the changes
			 */
			if (!oldObject.equals(trial)) {
				/*
				 * Check if the state (if changed) is ACTIVE
				 */
				if (trial.getStatus() != TrialStatus.IN_PREPARATION
						&& trial.getStatus() != TrialStatus.ACTIVE)
					throw new TrialStateException("Status change not permitted");
			}
			break;
		case PAUSED:
			/*
			 * If object has been change, examine the changes
			 */
			if (!oldObject.equals(trial)) {
				/*
				 * Clone the object revert the allowed changes and check if the
				 * object's are the same
				 */
				Trial temp = clone(trial);
				temp.setStatus(oldObject.getStatus());
				temp.setProtocol(oldObject.getProtocol());
				temp.setEndDate(oldObject.getEndDate());
				temp.setDescription(oldObject.getDescription());
				temp.setVersion(oldObject.getVersion());
				/*
				 * If not throw an exception
				 */
				if (!temp.equals(oldObject))
					throw new TrialStateException(
							"Object changes not permitted!");
				/*
				 * If only the allowed attribute has been changed check the
				 * state
				 */
				else {
					if (trial.getStatus() == TrialStatus.IN_PREPARATION)
						throw new TrialStateException(
								"Status change not permitted");
				}
			}
			break;
		}
		logger.info("user: "
				+ SecurityContextHolder.getContext().getAuthentication()
						.getName() + " update trial site with name "
				+ trial.getName() + "(id: " + trial.getId() + ")");
		return trialDao.update(trial);
	}

	@Override
	@Secured({ "AFTER_ACL_COLLECTION_READ" })
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Trial> getAll() {
		logger.info("user: "
				+ SecurityContextHolder.getContext().getAuthentication()
						.getName() + " get all trials");
		return trialDao.getAll();
	}

	@Override
	@Secured({ "ROLE_USER", "AFTER_ACL_READ" })
	@Transactional(propagation = Propagation.SUPPORTS)
	public Trial getObject(long objectID) {
		logger.info("user: "
				+ SecurityContextHolder.getContext().getAuthentication()
						.getName() + " get trial site with id=" + objectID);
		Trial trial = trialDao.get(objectID);
		//lazy loading for participating sites and subject criteria
		if(trial.getParticipatingSites().size()>0) trial.getParticipatingSites().iterator().next();
		if(trial.getCriteria().size()>0){
			trial.getCriteria().get(0);
			for(AbstractCriterion<?, ?> crit : trial.getCriteria()){
				if(crit.getStrata().size()>0) crit.getStrata().get(0);
			}
		}
		for(TreatmentArm arm : trial.getTreatmentArms()){
			if(arm.getSubjects().size()>0) arm.getSubjects().get(0);
		}
		return trial;
	}

	private void sendRandomisationMail(Trial trial, Login user,
			TrialSubject subject) {

		Map<String, Object> newUserMessageFields = new HashMap<String, Object>();
		newUserMessageFields.put("user", user);
		newUserMessageFields.put("trial", trial);
		newUserMessageFields.put("trialSubject", subject);
		// Map of variables for the subject
		Map<String, Object> newUserSubjectFields = new HashMap<String, Object>();
		newUserSubjectFields.put("trialName", trial.getName());

		Locale language = user.getPrefLocale();

		mailService.sendMail(user.getPerson().getEmail(), "Randomize",
				language, newUserMessageFields, newUserSubjectFields);

		newUserMessageFields = new HashMap<String, Object>();
		newUserMessageFields.put("user", trial.getSponsorInvestigator()
				.getLogin());
		newUserMessageFields.put("trial", trial);
		newUserMessageFields.put("trialSubject", subject);
		newUserMessageFields.put("url", "http://randi2.com/CHANGEME");
		// Map of variables for the subject

		try {
			language = trial.getSponsorInvestigator().getLogin()
					.getPrefLocale();
		} catch (Exception e) {
			language = Locale.getDefault();
		}

		mailService.sendMail(trial.getSponsorInvestigator().getEmail(),
				"Randomize", language, newUserMessageFields,
				newUserSubjectFields);

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<TrialSubject> getSubjects(Trial trial, Login investigator) {
		if(trial.getId()>0){
			trial = trialDao.refresh(trial);
			return trialDao.getSubjects(trial, investigator);
		}else return new ArrayList<TrialSubject>();
	}

	/**
	 * Util method for cloning objects
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T clone(T o) {
		T clone = null;

		try {
			clone = (T) o.getClass().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		// Walk up the superclass hierarchy
		for (Class obj = o.getClass(); !obj.equals(Object.class); obj = obj
				.getSuperclass()) {
			Field[] fields = obj.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				try {
					// for each class/suerclass, copy all fields
					// from this object to the clone
					fields[i].set(clone, fields[i].get(o));
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				}
			}
		}
		return clone;
	}
}
