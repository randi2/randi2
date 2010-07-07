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
	@Transactional(propagation = Propagation.REQUIRED)
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
