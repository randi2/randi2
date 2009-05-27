package de.randi2.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.annotation.Secured;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.TrialDao;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;

public class TrialServiceImpl implements TrialService {

	private Logger logger =  Logger.getLogger(TrialServiceImpl.class);
	@Autowired private TrialDao trialDao;
	@Autowired private SessionFactory sessionFactory;
		
	@Override
	@Secured({"ACL_TRIAL_CREATE"})
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void create(Trial newTrial) {
		trialDao.create(newTrial);
	}

	@Override
	@Secured({"ACL_TRIALSUBJECT_CREATE"})
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Trial randomize(Trial trial, TrialSubject subject) {
		TreatmentArm assignedArm = trial.getRandomizationConfiguration().getAlgorithm().randomize(subject);
		subject.setArm(assignedArm);
		sessionFactory.getCurrentSession().persist(subject);
		assignedArm.addSubject(subject);
		return trialDao.update(trial);
	}



	@Override
	@Secured({"ACL_TRIAL_WRITE"})
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Trial update(Trial trial) {
		return trialDao.update(trial);
	}

	@Override
	@Secured({"AFTER_ACL_COLLECTION_READ"})
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Trial> getAll() {
		return trialDao.getAll();
	}

	@Override
	@Secured({"ROLE_USER", "AFTER_ACL_READ"})
	@Transactional(propagation=Propagation.SUPPORTS)
	public Trial getObject(long objectID) {
		return trialDao.get(objectID);
	}

}
