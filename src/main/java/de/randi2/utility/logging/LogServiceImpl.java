package de.randi2.utility.logging;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;

public class LogServiceImpl implements LogService {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void logChange(String action, String username,
			AbstractDomainObject value) {
		LogEntry entry = new LogEntry();
		entry.setAction(action);
		entry.setUsername(username);
		entry.setClazz(value.getClass());
		entry.setIdentifier(value.getId());
		entry.setValue(value.toString());
		sessionFactory.getCurrentSession().persist(entry);
	}


	@Override
	public void logGet(String action, String username) {
		LogEntry entry = new LogEntry();
		entry.setAction(action);
		entry.setUsername(username);
		sessionFactory.getCurrentSession().persist(entry);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getLogEntries() {
		return convert(sessionFactory.getCurrentSession().createQuery(
				"from LogEntry").list());
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getLogEntries(
			Class<? extends AbstractDomainObject> clazz, long id) {
		List<LogEntry> entries = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from LogEntry as entry where entry.clazz = ? and entry.identifier = ?")
				.setParameter(0, clazz).setParameter(1, id).list();
		return convert(entries);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getLogEntries(String username) {
		List<LogEntry> entries = sessionFactory
		.getCurrentSession()
		.createQuery(
				"from LogEntry as entry where entry.username = ?")
		.setParameter(0, username).list();
		return convert(entries);
	}

	private List<String> convert(List<LogEntry> entries) {
		List<String> strings = new ArrayList<String>();
		for (LogEntry entry : entries) {
			strings.add(entry.toString());
		}
		return strings;
	}
}
