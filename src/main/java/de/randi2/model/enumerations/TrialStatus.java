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
package de.randi2.model.enumerations;

/**
 * Defines the state of the trial.
 * 
 * @author L. Plotnicki <l.plotnicki@dkfz.de>
 * 
 */
public enum TrialStatus {
	/**
	 * The trial is active, subjects can be randomized. Only the description and
	 * the end date of the study can be edited. New protocol can be uploaded.
	 */
	ACTIVE,
	/**
	 * The trial is stored in the system but no randomization is allowed. Any
	 * property of the trial (as well as any property of the randomization
	 * algorithm) can be changed.
	 */
	IN_PREPARATION,
	/**
	 * No randomization and changes to the trial possible.
	 */
	FINISHED,
	/**
	 * Similar to ACTIVE but the randomization process is not allowed.
	 */
	PAUSED;
}
