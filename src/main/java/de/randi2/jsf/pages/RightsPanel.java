/* This file is part of RANDI2.
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
package de.randi2.jsf.pages;

import java.util.List;

import de.randi2.model.Person;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;

/**
 * <p>
 * This class contains the logic for the user rights selection.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 * 
 */
public class RightsPanel {

	//Flags for the Right-Groups
	private boolean createUser = false;
	private boolean changeUser = false;
	private boolean viewUser = false;
	private boolean createCenter = false;
	private boolean changeCenter = false;
	private boolean viewCenter = false;
	private boolean createTrial = false;
	private boolean changeTrial = false;
	private boolean viewTrial = false;
	// ----
	
	// Chosen object, for which the user has some rights
	private List<TrialSite> createUserCenters = null;
	private List<TrialSite> viewUsers= null;

	private List<TrialSite> viewCenters = null;
	
	private List<TrialSite> createTrialCenters = null;
	private List<Trial> viewTrials = null;
	// ----
	
	// Flags for "chose object" logic
	private boolean createUserChosenObj = false;
	private boolean viewUsersChosenObj = false;
	private boolean viewCentersChosenObj = false;
	private boolean createTrialChosenObj = false;
	private boolean viewTrialsChosenObj = false;
	// ----
	
	
	public boolean isCreateUser() {
		return createUser;
	}
	public void setCreateUser(boolean createUser) {
		this.createUser = createUser;
	}
	public boolean isChangeUser() {
		return changeUser;
	}
	public void setChangeUser(boolean changeUser) {
		this.changeUser = changeUser;
	}
	public boolean isCreateCenter() {
		return createCenter;
	}
	public void setCreateCenter(boolean createCenter) {
		this.createCenter = createCenter;
	}
	public boolean isChangeCenter() {
		return changeCenter;
	}
	public void setChangeCenter(boolean changeCenter) {
		this.changeCenter = changeCenter;
	}
	public boolean isCreateTrial() {
		return createTrial;
	}
	public void setCreateTrial(boolean createTrial) {
		this.createTrial = createTrial;
	}
	public boolean isChangeTrial() {
		return changeTrial;
	}
	public void setChangeTrial(boolean changeTrial) {
		this.changeTrial = changeTrial;
	}
	public boolean isViewUser() {
		return viewUser;
	}
	public void setViewUser(boolean viewUser) {
		this.viewUser = viewUser;
	}
	public boolean isViewCenter() {
		return viewCenter;
	}
	public void setViewCenter(boolean viewCenter) {
		this.viewCenter = viewCenter;
	}
	public boolean isViewTrial() {
		return viewTrial;
	}
	public void setViewTrial(boolean viewTrial) {
		this.viewTrial = viewTrial;
	}
	public List<TrialSite> getCreateUserCenters() {
		return createUserCenters;
	}
	public void setCreateUserCenters(List<TrialSite> createUserCenters) {
		this.createUserCenters = createUserCenters;
	}
	public List<TrialSite> getViewUsers() {
		return viewUsers;
	}
	public void setViewUsers(List<TrialSite> viewUsers) {
		this.viewUsers = viewUsers;
	}
	public List<TrialSite> getViewCenters() {
		return viewCenters;
	}
	public void setViewCenters(List<TrialSite> viewCenters) {
		this.viewCenters = viewCenters;
	}
	public List<TrialSite> getCreateTrialCenters() {
		return createTrialCenters;
	}
	public void setCreateTrialCenters(List<TrialSite> createTrialCenters) {
		this.createTrialCenters = createTrialCenters;
	}
	public List<Trial> getViewTrials() {
		return viewTrials;
	}
	public void setViewTrials(List<Trial> viewTrials) {
		this.viewTrials = viewTrials;
	}
	public boolean isCreateUserChosenObj() {
		return createUserChosenObj;
	}
	public void setCreateUserChosenObj(boolean createUserChosenObj) {
		this.createUserChosenObj = createUserChosenObj;
	}
	public boolean isViewUsersChosenObj() {
		return viewUsersChosenObj;
	}
	public void setViewUsersChosenObj(boolean viewUsersChosenObj) {
		this.viewUsersChosenObj = viewUsersChosenObj;
	}
	public boolean isViewCentersChosenObj() {
		return viewCentersChosenObj;
	}
	public void setViewCentersChosenObj(boolean viewCentersChosenObj) {
		this.viewCentersChosenObj = viewCentersChosenObj;
	}
	public boolean isCreateTrialChosenObj() {
		return createTrialChosenObj;
	}
	public void setCreateTrialChosenObj(boolean createTrialChosenObj) {
		this.createTrialChosenObj = createTrialChosenObj;
	}
	public boolean isViewTrialsChosenObj() {
		return viewTrialsChosenObj;
	}
	public void setViewTrialsChosenObj(boolean viewTrialsChosenObj) {
		this.viewTrialsChosenObj = viewTrialsChosenObj;
	}
}
