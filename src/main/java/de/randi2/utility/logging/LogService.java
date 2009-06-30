package de.randi2.utility.logging;

import java.util.List;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;

public interface LogService {

	public void logRandomize(String action, String username, Trial trial, TrialSubject trialSubject);
	
	public void logChange(String action, String username, AbstractDomainObject value);
	
	public void logGet(String action, String username);
	
	public List<LogEntry> getLogEntries();
	
	public List<LogEntry> getLogEntries(String username);
	
	public List<LogEntry> getLogEntries(Class<? extends AbstractDomainObject> clazz, long id);
}
