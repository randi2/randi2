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
package de.randi2.jsf.backingBeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.Data;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;

/**
 * <p>
 * This class contains the logic for the user rights selection.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 * 
 */
@ManagedBean(name="rightsPanel")
@SessionScoped
public @Data class RightsPanel {

	// Flags for the Right-Groups
	private boolean createUser = false;
	private boolean changeUser = false;
	private boolean viewUser = false;
	private boolean createTrialSite = false;
	private boolean changeTrialSite = false;
	private boolean viewTrialSite = false;
	private boolean createTrial = false;
	private boolean changeTrial = false;
	private boolean viewTrial = false;
	// ----

	// Chosen object, for which the user has some rights
	private List<TrialSite> createUserTrialSites = null;
	private List<TrialSite> viewUsers = null;

	private List<TrialSite> viewTrialSites = null;

	private List<TrialSite> createTrialCenters = null;
	private List<Trial> viewTrials = null;
	// ----

	// Flags for "chose object" logic
	private boolean createUserChosenObj = false;
	private boolean viewUsersChosenObj = false;
	private boolean viewTrialSitesChosenObj = false;
	private boolean createTrialChosenObj = false;
	private boolean viewTrialsChosenObj = false;

	// ----
}
