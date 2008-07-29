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
package de.randi2.jsf.handlers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;

import com.icesoft.faces.component.selectinputtext.SelectInputText;

import de.randi2.dao.CenterDao;
import de.randi2.jsf.Randi2;
import de.randi2.model.Center;
import de.randi2.model.Login;
import de.randi2.model.Person;

/**
 * <p>
 * This class cares about the center object or objects.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 */
public class CenterHandler {

	private CenterDao centerDao;

	private boolean centerSavedPVisible = false;

	public boolean isCenterSavedPVisible() {
		return centerSavedPVisible;
	}

	public void setCenterSavedPVisible(boolean centerSavedPVisible) {
		this.centerSavedPVisible = centerSavedPVisible;
	}

	public String hideCenterSavedPopup() {
		this.centerSavedPVisible = false;
		return Randi2.SUCCESS;
	}

	/**
	 * The current logged in user.
	 */
	private Login currentUser = null;

	/**
	 * List with all centers as SelectItems
	 */
	private List<SelectItem> centerList = null;

	/**
	 * List with the matched centers as SelectItems for autocomplete widget
	 */
	private List<SelectItem> matchesList = null;

	private Center selectedCenter = null;

	/**
	 * List with all center's members as SelectItems
	 */
	private List<SelectItem> membersList = null;

	/**
	 * List with the matched center's members as SelectItems for autocomplete
	 * widget
	 */
	private List<SelectItem> memMatchesList = null;

	private Person selectedMember = null;

	private Center showedCenter = null;

	private boolean editable = false;

	private boolean creatingMode = false;

	public Center getShowedCenter() {
		if (showedCenter == null) {
			showedCenter = this.getCurrentUser().getPerson().getCenter();
		}
		return showedCenter;
	}

	public void setShowedCenter(Center showedCenter) {
		if (showedCenter == null) {
			creatingMode = true;
			this.showedCenter = new Center();
			this.showedCenter.setContactPerson(new Person());
		} else {
			creatingMode = false;
			this.showedCenter = showedCenter;
		}
	}

	public Login getCurrentUser() {
		if (currentUser == null) {
			currentUser = ((LoginHandler) FacesContext.getCurrentInstance()
					.getApplication().getVariableResolver().resolveVariable(
							FacesContext.getCurrentInstance(), "loginHandler"))
					.getLogin();
		}
		return currentUser;
	}

	public void setCurrentUser(Login currentUser) {
		this.currentUser = currentUser;
	}

	public boolean isEditable() {
		// TODO if the user has the right to edit the center properties this
		// method should return true
		// Temporary I'll just look, if the current user is a member of this
		// center - if it is so, then he can edit it
		// properties.
		if (this.getShowedCenter().equals(
				this.getCurrentUser().getPerson().getCenter())) {
			editable = true;
		} else {
			editable = creatingMode;
		}
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String saveCenter() {
		try {
			centerDao.save(this.showedCenter);

			// Making the centerSavedPopup visible
			this.centerSavedPVisible = true;

			this.creatingMode = false;

			return Randi2.SUCCESS;
		} catch (InvalidStateException exp) {
			// TODO for a stable release delete the following stacktrace
			exp.printStackTrace();
			for (InvalidValue v : exp.getInvalidValues()) {
				Randi2
						.showMessage(v.getPropertyName() + " : "
								+ v.getMessage());
			}
			return Randi2.ERROR;
		} catch (Exception e) {
			// TODO for a stable release delete the following stacktrace
			e.printStackTrace();
			Randi2.showMessage(e);
			return Randi2.ERROR;
		}

	}

	public boolean isCreatingMode() {
		return creatingMode;
	}

	public void setCreatingMode(boolean creatingMode) {
		this.creatingMode = creatingMode;
	}

	public CenterDao getCenterDao() {
		return centerDao;
	}

	public void setCenterDao(CenterDao centerDao) {
		this.centerDao = centerDao;
	}

	public List<Center> getCenters(){
		return centerDao.getAll();
	}
	
	public List<SelectItem> getCenterList() {
		if (matchesList == null) {
			if (centerList == null) {
				List<Center> centers = centerDao.getAll();
				centerList = new Vector<SelectItem>(centers.size());
				for (Center c : centers) {
					centerList.add(new SelectItem(c, c.getName()));
				}
				Collections.sort(centerList,
						CenterHandler.CENTERNAME_COMPERATOR);
			}
			return centerList;
		}
		return matchesList;
	}

	public void setCenterList(List<SelectItem> centerList) {
		this.centerList = centerList;
	}

	// TEMPORARY SOLUTION - WILL BE DELETED WHEN AN APPROPRIATE DB-METHOD CAN BE
	// USED
	public static final Comparator<SelectItem> CENTERNAME_COMPERATOR = new Comparator<SelectItem>() {
		public int compare(SelectItem s1, SelectItem s2) {
			return s1.getLabel().compareToIgnoreCase(s2.getLabel());
		}
	};

	// ----

	/**
	 * Eventlistener for center autocomplete widget.
	 * 
	 * @param event
	 */
	public void updateCenterList(ValueChangeEvent event) {
		System.out.println("Event!");
		SelectItem searchCenter = new SelectItem("", (String) event
				.getNewValue());
		int maxMatches = ((SelectInputText) event.getComponent()).getRows();
		List<SelectItem> matchList = new Vector<SelectItem>(maxMatches);

		// DB-Method!
		if (centerList == null) {
			List<Center> centers = centerDao.getAll();
			centerList = new Vector<SelectItem>(centers.size());
			for (Center c : centers) {
				centerList.add(new SelectItem(c, c.getName()));
			}
			Collections.sort(centerList, CenterHandler.CENTERNAME_COMPERATOR);
		}
		int insert = Collections.binarySearch(centerList, searchCenter,
				CenterHandler.CENTERNAME_COMPERATOR);
		if (insert < 0) {
			insert = Math.abs(insert) - 1;
		}
		for (int i = 0; i < maxMatches; i++) {
			// quit the match list creation if the index is larger then
			// max entries in the dictionary if we have added maxMatches.
			if ((insert + i) >= centerList.size() || i >= maxMatches) {
				break;
			}
			matchList.add(centerList.get(insert + i));
		}
		// assign new matchesList
		if (this.matchesList != null) {
			this.matchesList.clear();
			this.matchesList = null;
		}
		this.matchesList = matchList;
		//
		// Get the auto complete component from the event and assing
		if (event.getComponent() instanceof SelectInputText) {
			SelectInputText autoComplete = (SelectInputText) event
					.getComponent();
			selectedCenter = null;
			// if no selected item then return the previously selected item.
			if (autoComplete.getSelectedItem() != null) {
				selectedCenter = (Center) autoComplete.getSelectedItem()
						.getValue();
			}
			// otherwise if there is a selected item get the value from the
			// match list
			else {
				if (matchesList != null) {
					for (SelectItem si : matchesList) {
						if (si.getLabel().equals(
								autoComplete.getValue().toString()))
							selectedCenter = (Center) autoComplete
									.getSelectedItem().getValue();
					}
				}
			}
			((LoginHandler) FacesContext.getCurrentInstance().getApplication()
					.getVariableResolver().resolveVariable(
							FacesContext.getCurrentInstance(), "loginHandler"))
					.setUserCenter(selectedCenter);
		}
	}

	/**
	 * Eventlistener for members autocomplete widget.
	 * 
	 * @param event
	 */
	public void updateMembersList(ValueChangeEvent event) {
		if (selectedCenter != null && event.getNewValue()!=null && event.getNewValue().toString().trim().length()>0) {
			System.out.println("Event2!");
			SelectItem searchMember = new SelectItem("", (String) event
					.getNewValue());
			int maxMatches = ((SelectInputText) event.getComponent()).getRows();
			List<SelectItem> matchList = new Vector<SelectItem>(maxMatches);

			// DB-Method!
			if (membersList == null) {
				List<Person> members = selectedCenter.getMembers();
				membersList = new Vector<SelectItem>(members.size());
				for (Person p : members) {
					membersList.add(new SelectItem(p, p.getSurname() + ", "
							+ p.getFirstname()));
				}
				Collections.sort(membersList,
						CenterHandler.CENTERNAME_COMPERATOR);
			}
			int insert = Collections.binarySearch(membersList, searchMember,
					CenterHandler.CENTERNAME_COMPERATOR);
			if (insert < 0) {
				insert = Math.abs(insert) - 1;
			}
			for (int i = 0; i < maxMatches; i++) {
				// quit the match list creation if the index is larger then
				// max entries in the dictionary if we have added maxMatches.
				if ((insert + i) >= membersList.size() || i >= maxMatches) {
					break;
				}
				matchList.add(membersList.get(insert + i));
			}
			// assign new matchesList
			if (this.memMatchesList != null) {
				this.memMatchesList.clear();
				this.memMatchesList = null;
			}
			this.memMatchesList = matchList;
			//
			// Get the auto complete component from the event and assing
			if (event.getComponent() instanceof SelectInputText) {
				SelectInputText autoComplete = (SelectInputText) event
						.getComponent();
				selectedMember = null;
				// if no selected item then return the previously selected item.
				if (autoComplete.getSelectedItem() != null) {
					selectedMember = (Person) autoComplete.getSelectedItem()
							.getValue();
				}
				// otherwise if there is a selected item get the value from the
				// match list
				else {
					if (matchesList != null) {
						for (SelectItem si : matchesList) {
							if (si.getLabel().equals(
									autoComplete.getValue().toString()))
								selectedMember = (Person) autoComplete
										.getSelectedItem().getValue();
						}
					}
				}
				((LoginHandler) FacesContext.getCurrentInstance()
						.getApplication().getVariableResolver()
						.resolveVariable(FacesContext.getCurrentInstance(),
								"loginHandler"))
						.setUserAssistant(selectedMember);
			}
		}
	}

	public List<SelectItem> getMembersList() {
		if (memMatchesList == null) {
			if (membersList == null) {
				List<Person> members = selectedCenter.getMembers();
				;
				membersList = new Vector<SelectItem>(members.size());
				for (Person p : members) {
					membersList.add(new SelectItem(p, p.getSurname() + ", "
							+ p.getFirstname()));
				}
				Collections.sort(membersList,
						CenterHandler.CENTERNAME_COMPERATOR);
			}
			return membersList;
		}
		return memMatchesList;
	}

	public void setMembersList(List<SelectItem> membersList) {
		this.membersList = membersList;
	}

}
