package de.randi2.services;

import java.util.List;

import org.apache.log4j.Logger;

import de.randi2.dao.TrialDao;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;

public class TrialServiceImpl implements TrialService {

	private Logger logger =  Logger.getLogger(TrialServiceImpl.class);
	private TrialDao trialDao;
	
	public TrialServiceImpl(TrialDao trialDao) {
		this.trialDao = trialDao;
	}

	@Override
	public void create(Trial newTrial) {
		trialDao.create(newTrial);
	}

	@Override
	public Trial randomize(Trial trial, TrialSubject subject) {
		TreatmentArm assignedArm = trial.getRandomizationConfiguration().getAlgorithm().randomize(subject);
		subject.setArm(assignedArm);
		assignedArm.addSubject(subject);
		return trialDao.update(trial);
	}

	@Override
	public Trial update(Trial trial) {
		return trialDao.update(trial);
	}

	@Override
	public List<Trial> getAll() {
		return trialDao.getAll();
	}

	@Override
	public Trial getObject(long objectID) {
		return trialDao.get(objectID);
	}

}
