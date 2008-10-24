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

/**
 * <p>
 * This class contains the logic for the user rights selection.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 * 
 */
public class RightsPanel {

	private boolean createUser = false;
	private boolean changeUser = false;
	private boolean viewUser = false;
	private boolean createCenter = false;
	private boolean changeCenter = false;
	private boolean viewCenter = false;
	private boolean createTrial = false;
	private boolean changeTrial = false;
	private boolean viewTrial = false;
	
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
}
