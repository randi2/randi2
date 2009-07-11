package de.randi2.utility.logging;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;

public class LogServiceImpl implements LogService {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void logChange(LogEntry.ActionType action, String username,
			AbstractDomainObject value) {
		LogEntry entry = new LogEntry();
		entry.setAction(action);
		entry.setUsername(username);
		entry.setClazz(value.getClass());
		entry.setIdentifier(value.getId());
		entry.setValue(value.toString());
		entry.setUiName(value.getUIName());
		sessionFactory.getCurrentSession().persist(entry);
	}
	
	
	@Override
	public void logRandomize(LogEntry.ActionType action, String username, Trial trial,
			TrialSubject trialSubject) {
		LogEntry entry = new LogEntry();
		entry.setAction(action);
		entry.setUsername(username);
		entry.setClazz(trial.getClass());
		entry.setIdentifier(trial.getId());
		entry.setValue(trialSubject.toString());
		entry.setUiName(trialSubject.getUIName());
		sessionFactory.getCurrentSession().persist(entry);
		
	}


	@Override
	public void logGet(LogEntry.ActionType action, String username) {
		LogEntry entry = new LogEntry();
		entry.setAction(action);
		entry.setUsername(username);
		sessionFactory.getCurrentSession().persist(entry);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<LogEntry> getLogEntries() {
		return sessionFactory.getCurrentSession().createQuery(
				"from LogEntry").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<LogEntry> getLogEntries(
			Class<? extends AbstractDomainObject> clazz, long id) {
		List<LogEntry> entries = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from LogEntry as entry where entry.clazz = ? and entry.identifier = ?")
				.setParameter(0, clazz).setParameter(1, id).list();
		return entries;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<LogEntry> getLogEntries(String username) {
		List<LogEntry> entries = sessionFactory
		.getCurrentSession()
		.createQuery(
				"from LogEntry as entry where entry.username = ?")
		.setParameter(0, username).list();
		return entries;
	}

	public static List<String> convert(List<LogEntry> entries) {
		List<String> strings = new ArrayList<String>();
		for (LogEntry entry : entries) {
			strings.add(entry.toString());
		}
		return strings;
	}




	
}
