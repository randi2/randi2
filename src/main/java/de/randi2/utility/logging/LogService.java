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

import java.util.List;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.utility.logging.LogEntry.ActionType;

public interface LogService {

	public void logSubjectAction(ActionType action, String username, Trial trial, TrialSubject trialSubject);
	
	public void logChange(ActionType action, String username, AbstractDomainObject value);
	
	public void logGet(ActionType action, String username);
	
	public List<LogEntry> getLogEntries();
	
	public List<LogEntry> getLogEntries(String username);
	
	public List<LogEntry> getLogEntries(Class<? extends AbstractDomainObject> clazz, long id);

	void logTrialChange(ActionType action, String username, Trial oldTrial,	Trial changedTrial);
}
